package com.app.ace.helpers;

import android.app.Activity;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by khan_muhammad on 3/22/2017.
 */

public abstract class TwitterEmailHelper extends TwitterAuthClient{

    @Override
    public void requestEmail(TwitterSession session, Callback<String> callback) {
        super.requestEmail(session, callback);

        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(Twitter.getSessionManager().getActiveSession(), new com.twitter.sdk.android.core.Callback<String>() {
            @Override
            public void success(Result<String> result) {

                onRequestEmailSuccess(result.toString());

            }

            @Override
            public void failure(TwitterException exception) {
                onRequestEmailFail(exception);
            }
        });


    }

    public abstract void onRequestEmailSuccess(String email);
    public abstract void onRequestEmailFail(TwitterException exception);
}
