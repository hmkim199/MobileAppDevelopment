package com.example.todaysaying;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.todaysaying.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab1;

//    private ActivityResultLauncher<Intent> resultLauncher;
//    public static final int sub = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("오늘의 문구");
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);

        fab1.setOnClickListener(this);

//        resultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if(result.getResultCode() == RESULT_OK){
//                            Intent data = result.getData();
//
//                            String returnString = data.getExtras().getString("returnData");
//                            //binding.textView1.setText(returnString);
//                        }
//                    }
//                }
//        );
    }

    @Override
    public void onClick(View v) {
        //int id = v.getId();
        //anim();
        Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(intent);//액티비티 띄우기
    }

//    public void createSentence(View view){
//        Intent intent = new Intent(this, SecondActivity.class);
//
//        String myString = binding.editText1.getText().toString();
//        intent.putExtra("qString", myString);
//        resultLauncher.launch(intent);
//    }
}