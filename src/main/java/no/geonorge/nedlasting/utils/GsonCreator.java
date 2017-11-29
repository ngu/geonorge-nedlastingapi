package no.geonorge.nedlasting.utils;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonCreator {

    private GsonCreator() {

    }
    
    public static GsonBuilder builder() {
        GsonBuilder builder = new GsonBuilder();
        // can not use setDateFormat as this need to be UTC
        builder.registerTypeAdapter(java.sql.Date.class, new ISODateAdapter());
        builder.registerTypeAdapter(Date.class, new ISOTimestampAdapter());
        return builder;
    }

    public static Gson create() {
        return builder().create();
    }

}
