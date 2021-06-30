package org.service;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        try {
            // if provided as String - '2011-12-03T10:15:30+01:00[Europe/Paris]'
            if (jsonPrimitive.isString()) {
                return LocalDateTime.parse(jsonPrimitive.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            // if provided as Long
            if (jsonPrimitive.isNumber()) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonPrimitive.getAsLong()), ZoneId.systemDefault());
            }

            throw new JsonParseException("Unable to parse ZonedDateTime");
        } catch (RuntimeException e) {
            throw new JsonParseException("Unable to parse ZonedDateTime", e);
        }
    }
}