package com.example.todaysaying;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todaysaying.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.todaysaying.SecondActivity.myDBHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab1;
    private FloatingActionButton deleteFab;
    private Button randomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("오늘의 문구");
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changeToDefaultSentence("");
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(intent);//액티비티 띄우기
                initializeDB(view, false);
            }
        });

        deleteFab = (FloatingActionButton) findViewById(R.id.deleteFab);
        deleteFab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changeToDefaultSentence("");
                initializeDB(view, true);
                Toast.makeText(getApplicationContext(), "전체 데이터가 삭제되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        randomButton = (Button) findViewById(R.id.randomButton);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDBOne(view);
            }
        });
    }

    public void initializeDB(View view, Boolean hasToUpgrade){
        myDBHelper myDBHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        if(hasToUpgrade){
            myDBHelper.onUpgrade(sqlDB, 1, 2);
        }
        sqlDB.close();
//        Toast.makeText(getApplicationContext(), "Initialized", Toast.LENGTH_LONG).show();
    }

    public void searchDBOne(View view){
        myDBHelper myDBHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;

        String sQuery = "select count(*) from sqlite_master where name='mySentence'";
        cursor = sqlDB.rawQuery(sQuery, null);
        cursor.moveToFirst();
        if(cursor.getInt(0) == 0){
            Toast.makeText(getApplicationContext(), "문구를 추가하세요!", Toast.LENGTH_LONG).show();
            return;
        }

        sQuery = "select count(*) from mySentence";
        cursor = sqlDB.rawQuery(sQuery, null);
        cursor.moveToFirst();
        if(cursor.getInt(0) == 0){
            Toast.makeText(getApplicationContext(), "문구를 추가하세요!", Toast.LENGTH_LONG).show();
            return;
        }

        cursor = sqlDB.rawQuery("select * from mySentence order by random() limit 1;", null);
        cursor.moveToFirst();

        if(cursor.getString(2) != null){
            // 데이터 이미지 넘기기
            //System.out.println("이미지뷰!!!!!!!!!!!!!!!!!!!!!!");
            binding.textView.setVisibility(View.INVISIBLE);
            binding.imageView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(cursor.getString(2));
            Glide.with(getApplicationContext()).load(uri).override(500, 500).into(binding.imageView);
        }
        else if(cursor.getString(1) != null){
            // 데이터 글 넘기기
            changeToDefaultSentence(cursor.getString(1));
        }

        cursor.close();
        sqlDB.close();
    }

    private void changeToDefaultSentence(String str){
        binding.textView.setVisibility(View.VISIBLE);
        binding.imageView.setVisibility(View.INVISIBLE);
        binding.textView.setText(str);
    }
}