package com.youragent.util;

public class Constants {

    public static final String NOMINATIM_OSM_URI =
            "https://nominatim.openstreetmap.org/search.php?city=%s&state=%s&addressdetails=1&format=json";

    public static final String OPEN_AI_URI =
            "https://api.openai.com/v1/chat/completions";

    public static final String OPEN_AI_MODEL = "gpt-3.5-turbo-0125";

    public static final String QUICK_FACT_PROMPT =
            "Provide a fun interesting fact about %s. Preface the response with %s. " +
                    "Do not mention the words fun or interesting. ";

    public static final int OPEN_AI_MAX_TOKENS = 70;
}
