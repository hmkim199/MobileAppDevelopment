package com.example.startedservices;

import static androidx.core.app.JobIntentService.enqueueWork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.startedservices.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    static final int SERVICE_ID=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }

    public void buttonClick(View view){
        //Intent intent = new Intent();
        //enqueueWork(this, MyJobIntentService.class, SERVICE_ID, intent);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }
}