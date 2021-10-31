package com.example.explicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.explicitintent.databinding.ActivityMainBinding;
import com.example.explicitintent.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return ;
        }

        String qString = extras.getString("qString");
        binding.textView2.setText(qString);
    }

    public void returnText(View view){
        finish();
    }

    @Override
    public void finish() {
        Intent data = new Intent();

        String returnString = binding.editText2.getText().toString();
        data.putExtra("returnData", returnString);

        setResult(RESULT_OK, data);
        super.finish();
    }
}