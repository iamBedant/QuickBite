package com.iambedant.nanodegree.quickbite.data.remote;

import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Kuliza-193 on 6/5/2016.
 */

public class MyOkHttpInterceptor implements Interceptor {
    private final String TAG = MyOkHttpInterceptor.class.getSimpleName();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        request = chain.request().newBuilder().addHeader("Accept", "application/json")
                .addHeader("user-key", Constants.ZOMATO_API_KEY).build();

        String body = "NA";
        if(chain.request().body()!=null){
            body =  bodyToString(chain.request().body());
        }

        String myString = request.url() + "->" + request.headers().toString()+"\n Params  --->"+body+"\n";

        Logger.d(TAG, myString);
        return chain.proceed(request);
    }

    private  String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }
}
