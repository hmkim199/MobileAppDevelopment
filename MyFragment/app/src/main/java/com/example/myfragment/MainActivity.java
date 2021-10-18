package com.example.myfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentOne.ToolbarListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(int fontsize, String text){
        FragmentTwo fragmentTwo = (FragmentTwo) getSupportFragmentManager().findFragmentById(R.id.fragment2);

        fragmentTwo.changeText(fontsize, text);
    }
}