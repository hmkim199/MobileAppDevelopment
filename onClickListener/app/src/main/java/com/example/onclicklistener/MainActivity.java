package com.example.onclicklistener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.onclicklistener.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.textView1.setText("Button Clicked");
                    }
                }
        );

        binding.button1.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View v){
                        binding.textView1.setText("Long Button Clicked");
                        return false;
                    }
                }
        );
    }
}