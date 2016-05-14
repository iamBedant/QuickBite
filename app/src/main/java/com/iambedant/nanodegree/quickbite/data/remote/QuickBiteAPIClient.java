package com.iambedant.nanodegree.quickbite.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisines;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.SearchResult;
import com.iambedant.nanodegree.quickbite.util.Constants;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface QuickBiteAPIClient {

    String ENDPOINT = "https://developers.zomato.com/api/v2.1/";

    @GET("search")
    Observable<SearchResult> getSearchResult( @QueryMap Map<String, String> options);

    @GET("cuisines")
    Observable<Cuisines> getCuisines(@QueryMap Map<String, String> options);

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

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    // Do anything with response here
                    //if we ant to grab a specific cookie or something..
                    return response;
                }
            });

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //this is where we will add whatever we want to our request headers.
                    Request request = chain.request().newBuilder().addHeader("Accept", "application/json")
                            .addHeader("user-key", Constants.ZOMATO_API_KEY).build();
                    return chain.proceed(request);
                }
            });


            return builder.build();
        }
    }
}
