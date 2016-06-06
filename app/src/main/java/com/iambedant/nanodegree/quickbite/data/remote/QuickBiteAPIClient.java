package com.iambedant.nanodegree.quickbite.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisines;
import com.iambedant.nanodegree.quickbite.data.model.Reviews.Reviews;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.SearchResult;

import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface QuickBiteAPIClient {

    String ENDPOINT = "https://developers.zomato.com/api/v2.1/";

    @GET("search")
    Observable<SearchResult> getSearchResult(@QueryMap Map<String, String> options);

    @GET("cuisines")
    Observable<Cuisines> getCuisines(@QueryMap Map<String, String> options);


    @GET("reviews")
    Observable<Reviews> getReviews(@QueryMap Map<String, String> options);


    class Creator {
        private static OkHttpClient okHttpClient = buildClient();


        public static QuickBiteAPIClient newQuickBiteAPIClient() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(QuickBiteAPIClient.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(QuickBiteAPIClient.class);
        }


        public static OkHttpClient buildClient() {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new MyOkHttpInterceptor());
            return builder.build();
        }

    }


}
