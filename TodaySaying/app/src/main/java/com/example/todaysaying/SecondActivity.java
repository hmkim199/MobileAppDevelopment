package com.example.todaysaying;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.todaysaying.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;
    ImageView imageView;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        imageView = findViewById(R.id.main_image);
//        Bundle extras = getIntent().getExtras();
//        if(extras == null){
//            return ;
//        }
//
//        String qString = extras.getString("qString");
        //binding.textView2.setText(qString);

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent data = result.getData();
                            int callType = getIntent().getIntExtra("CallType", 0);
                            if(callType == 0){
                                Glide.with(getApplicationContext()).load(data.getData()).override(500, 500).into(imageView);
                            }
                            //String returnString = data.getExtras().getString("returnData");
                            //binding.textView1.setText(returnString);
                        }
                    }
                }
        );

        Button getImageBtn = findViewById(R.id.getImageBtn);
        getImageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("CallType", 0);
                resultLauncher.launch(intent);
                //startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            Glide.with(getApplicationContext()).load(data.getData()).into(imageView);
        }
    }

    public void returnText(View view){
        finish();
    }

    @Override
    public void finish() {
        Intent data = new Intent();

        //String returnString = binding.editText2.getText().toString();
        //data.putExtra("returnData", returnString);

        //setResult(RESULT_OK, data);
        super.finish();
    }
}
