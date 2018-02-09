package no.geonorge.nedlasting.utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
public final class ISOTimestampAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final DateFormat iso8601Format;

    public ISOTimestampAdapter() {
        this.iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        this.iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public Date deserialize(String value) throws ParseException {
        return iso8601Format.parse(value);
    }

    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        String dateFormatAsString = iso8601Format.format(src);
        return new JsonPrimitive(dateFormatAsString);
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
            return new Timestamp(date.getTime());
        } else if (typeOfT == java.sql.Date.class) {
            return new java.sql.Date(date.getTime());
        } else {
            throw new IllegalArgumentException(getClass() + " cannot deserialize to " + typeOfT);
        }
    }

    private Date deserializeToDate(JsonElement json) {
        try {
            return iso8601Format.parse(json.getAsString());
        } catch (ParseException e) {
            throw new JsonSyntaxException(json.getAsString(), e);
        }
    }
    
    public static boolean isTimestamp(String v) {
        // first quick look
        if (v == null || v.length() < 10 || v.indexOf('-') != 4) {
            return false;
        }
        try {
            new ISOTimestampAdapter().deserialize(v);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
}