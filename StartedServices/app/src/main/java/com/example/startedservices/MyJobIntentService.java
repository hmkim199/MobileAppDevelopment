package com.example.startedservices;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class MyJobIntentService extends JobIntentService {
    private static final String TAG="ServiceExample";

    @Override
    protected void onHandleWork(@NonNull Intent intent){
        Log.i(TAG, "Job Service Started");

        int i = 0;
        while(i<3){
            try{
                i++;
                Thread.sleep(10000);
            }catch(Exception e){

            }
            Log.i(TAG, "Service Running");
        }
    }
}
