package com.app.ace.helpers;

import android.content.Context;
import android.util.Log;

import com.app.ace.entities.ResponseWrapper;
import com.app.ace.global.WebServiceConstants;
import com.app.ace.retrofit.WebService;
import com.app.ace.retrofit.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/15/2017.
 */

public class TokenUpdater {
    private static final TokenUpdater tokenUpdater = new TokenUpdater();

    public static TokenUpdater getInstance() {
        return tokenUpdater;
    }
    private WebService webservice;

    private TokenUpdater() {
    }
    public void UpdateToken(Context context , final String userid, String DeviceType, String Token){
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(context,
                WebServiceConstants.SERVICE_BASE_URL);
       Call<ResponseWrapper>call = webservice.updateToken(userid,DeviceType,Token);
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                Log.i("UPDATETOKEN",response.body().getResponse()+""+userid);
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                Log.e("UPDATETOKEN",t.toString());
            }
        });
    }
}