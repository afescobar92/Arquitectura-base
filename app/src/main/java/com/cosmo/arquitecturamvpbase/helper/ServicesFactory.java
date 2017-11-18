package com.cosmo.arquitecturamvpbase.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.simpleframework.xml.convert.Convert;

import java.util.concurrent.TimeUnit;
import java.util.logging.XMLFormatter;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by leidyzulu on 16/09/17.
 */

public class ServicesFactory {


    private static final String API_BASE_PATH = Constants.URL_BASE;
    private static final String API_BASE_XML_PATH = Constants.URL_XML_BASE;
    private RestAdapter restAdapter;

    public enum TypeConverter{
        JSON,
        XML
    }

    public ServicesFactory(TypeConverter type) {
        switch (type){
            case JSON:
                createServicesFactoryInstance(getGsonConverter(), API_BASE_PATH);
                break;
            case XML:
                createServicesFactoryInstance(getXMLConverter(), API_BASE_XML_PATH);
                break;
            default:
                createServicesFactoryInstance(getGsonConverter(), API_BASE_PATH);
        }

    }

    private void createServicesFactoryInstance(Converter converter, String baseUrl) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(Constants.TIME_OUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS);
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setConverter(converter)
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    private Converter getGsonConverter() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return new GsonConverter(gson);
    }

    private Converter getXMLConverter() {
        return new SimpleXMLConverter();
    }
    public Object getInstance(Class service) {
        return restAdapter.create(service);
    }
}
