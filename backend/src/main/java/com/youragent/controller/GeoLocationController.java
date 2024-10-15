package com.youragent.controller;

import com.youragent.service.geolocationservice.GeoLocationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "${frontend.address}")
public class GeoLocationController {

    private final GeoLocationService geoLocationService;

    public GeoLocationController(@NonNull GeoLocationService geoLocationService) {
        this.geoLocationService = geoLocationService;
    }

    @GetMapping(path = "/")
    public String sayNothing() {
        return "Hello!";
    }

    @GetMapping(path = "/locationInquiry")
    public ResponseEntity<?> processLocationInquiry(@RequestParam("place") @NonNull String place,
                                                    @RequestParam("state") @NonNull String state) {
        log.info(String.format("GET: processLocationInquiry place=%s, state=%s", place, state));

        final String county = geoLocationService.getLocationCounty(place, state);

        if (county != null && !county.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(county);
        }

        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
    }
}
