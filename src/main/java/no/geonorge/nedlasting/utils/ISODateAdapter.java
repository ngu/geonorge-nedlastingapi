package no.geonorge.nedlasting.utils;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

/**
 * Gson adapter to make it serialize/deserialize dates according to ISO 8601 as
 * used in JavaScript.
 * 
 * @see https://gist.github.com/bbirec/5748489
 */
public final class ISODateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }
        Date date = deserializeToDate(json);
        if (typeOfT == Date.class) {
            return date;
        } else if (typeOfT == Timestamp.class) {
            return new Date(date.getTime());
        } else if (typeOfT == java.util.Date.class) {
            return new java.sql.Date(date.getTime());
        } else {
            throw new IllegalArgumentException(getClass() + " cannot deserialize to " + typeOfT);
        }
    }

    private Date deserializeToDate(JsonElement json) {
        try {
            return Date.valueOf(json.getAsString());
        } catch (IllegalArgumentException e) {
            throw new JsonSyntaxException(json.getAsString(), e);
        }
    }
}