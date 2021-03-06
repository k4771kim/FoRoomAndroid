/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.forroom.suhyemin.kimbogyun.songmin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.panorama.PanoramaClient;
import com.google.android.gms.panorama.PanoramaClient.OnPanoramaInfoLoadedListener;

import java.io.File;

/**
 * Displays examples of integrating with the panorama viewer API.
 */
public class PhotoActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, OnPanoramaInfoLoadedListener {

    public static final String TAG = PhotoActivity.class.getSimpleName();

    boolean start = false;
    private PanoramaClient mClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClient = new PanoramaClient(this, this, this);
//        Log.e("a", "1");
        mClient.connect();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (start) {
            finish();
        } else {
            start = true;
        }

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        String filepath = getIntent().getStringExtra("imgpath");
        File target = new File(filepath);
        Uri uri = Uri.fromFile(target);
//        Log.e("^^", uri + "");
        mClient.loadPanoramaInfo(this, uri);
    }

    @Override
    public void onPanoramaInfoLoaded(ConnectionResult result, Intent viewerIntent) {
        if (result.isSuccess()) {
            Log.i(TAG, "found viewerIntent: " + viewerIntent);
            if (viewerIntent != null) {
                startActivity(viewerIntent);
            }
        } else {
            Log.e(TAG, "error: " + result);
        }
    }

    @Override
    public void onDisconnected() {
        // Do nothing
    }

    @Override
    public void onConnectionFailed(ConnectionResult status) {
        Log.e(TAG, "connection failed: " + status);
        // TODO fill in
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.e("a", "4");
        mClient.disconnect();
    }


}
