package com.app.ace.retrofit;

import android.content.Context;

import com.app.ace.global.AppConstants;
import com.app.ace.helpers.BasePreferenceHelper;

import java.io.IOException;

;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServiceFactory {

    private static WebService webService;

    static BasePreferenceHelper preferenceHelper;

    public static WebService getWebServiceInstanceWithCustomInterceptor(Context context, String endPoint) {

        preferenceHelper = new BasePreferenceHelper(context);

        if (preferenceHelper.getToken() != null) {
            AppConstants._token = preferenceHelper.getToken();
        } else {
            AppConstants._token = "";
        }


         OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("_token", AppConstants._token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });



        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(endPoint)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(OKHttpClientCreator.createCustomInterceptorClient(context))
//                .build();

        webService = retrofit.create(WebService.class);

        return webService;

    }

    public static WebService getWebServiceInstanceWithDefaultInterceptor(Context context, String endPoint) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OKHttpClientCreator.createDefaultInterceptorClient(context))
                .build();

        webService = retrofit.create(WebService.class);

        return webService;

    }

}
