package com.youragent.service.geolocationservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.youragent.model.dto.PlaceState;
import com.youragent.service.redisservice.RedisService;
import com.youragent.service.restservice.RestService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.youragent.util.Constants;

@Slf4j
@Service
public class GeoLocationServiceImpl implements GeoLocationService {

    private final Set<String> ADDRESS_TYPE = Set.of("city", "town", "village", "census");

    private final RedisService<PlaceState, String> redisService;

    private final RestService<JsonNode> restService;

    public GeoLocationServiceImpl(@NonNull final RedisService<PlaceState, String> redisService,
                                  @NonNull final RestService<JsonNode> restService) {
        this.redisService = redisService;
        this.restService = restService;
    }

    public String getLocationCounty(String place, String state) {
        log.info("processing getLocationCounty({}, {})", place, state);

        final PlaceState cityState = PlaceState.builder().place(place).state(state).build();

        final CompletableFuture<String> locationCounty = redisService.getValue(cityState);

        try {
            if (locationCounty.get() != null) {
                // - found county value associated with CityState key in redis
                // - process and skip request to OSM
                log.info("County ({}) was retrieved in cache", locationCounty.get());
                return locationCounty.get();

            } else {
                // - did not county value with given CityState key
                // - process county lookup to OSM
                // - store retrieved county in redis db as CityState:County
                JsonNode jsonNode = restService.getRequest(
                        String.format(Constants.NOMINATIM_OSM_URI, cleanInput(place), cleanInput(state)),
                        JsonNode.class
                );

                for (JsonNode location: jsonNode) {
                    try {
                        log.info(location .toPrettyString());
                        final String addressTypeValue = location.get("addresstype").asText();
                        if (ADDRESS_TYPE.contains(addressTypeValue)) {
                            final String county = location.get("address").get("county").asText();
                            if (!county.isEmpty()) {
                                log.info("County ({}) was retrieved via Nominatim OSM Call", county);
                                redisService.setValue(cityState, county);
                                return county;
                            }
                        }
                    } catch (NullPointerException e) {
                        log.warn(e.getMessage());
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private String cleanInput(@NonNull String place) {

        String placeBeingScrubbed = place;

        if (placeBeingScrubbed.contains("-")) {
            placeBeingScrubbed = placeBeingScrubbed.substring(0, place.indexOf("-"));
        }

        return placeBeingScrubbed.replace(" ", "_").trim();
    }

}
