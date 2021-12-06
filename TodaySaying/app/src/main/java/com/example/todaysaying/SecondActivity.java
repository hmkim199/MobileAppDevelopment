package com.example.todaysaying;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
    Uri uri = null;

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
                                //System.out.println("데이터!!!!!!!!!!!!!!!"+data.getData());
                                uri = data.getData();
                                Glide.with(getApplicationContext()).load(uri).override(500, 500).into(imageView);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 0){
//            System.out.println("데이터!!!!!!!!!!!!!!!"+data.getData());
//            Glide.with(getApplicationContext()).load(data.getData()).into(imageView);
//        }
//    }

    public void returnText(View view){
        insertDB(view);
        finish();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        String returnString = binding.editText1.getText().toString();
        //data.putExtra("returnData", returnString);

        //setResult(RESULT_OK, data);
        super.finish();
    }

    public static class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL("delete from mySentence;");
            //db.execSQL("drop table if exists mySentence;");
            db.execSQL("create table mySentence (_id integer primary key autoincrement, sentence text, image text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists mySentence;");
            onCreate(db);
        }
    }

//    public void initializeDB(View view){
//        myDBHelper myDBHelper = new myDBHelper(this);
//        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
//        //myDBHelper.onUpgrade(sqlDB, 1, 2);
//        sqlDB.close();
//        Toast.makeText(getApplicationContext(), "Initialized", Toast.LENGTH_LONG).show();
//    }

    public void insertDB(View view){
        myDBHelper myDBHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        //binding.mainImage.getImage
        if (uri == null){
            sqlDB.execSQL("insert into mySentence(sentence) values('" + binding.editText1.getText().toString() +"');");
        }
        else{
            sqlDB.execSQL("insert into mySentence(image) values('" + uri.toString() +"');");
        }
        sqlDB.close();
        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
    }

//    public void searchDBAll(View view){
//        myDBHelper myDBHelper = new myDBHelper(this);
//        SQLiteDatabase sqlDB = myDBHelper.getReadableDatabase();
//        Cursor cursor;
//        cursor = sqlDB.rawQuery("select * from mySentence;", null);
//
//        String string1 = "Movie Title" + System.lineSeparator();
//        String string2 = "Director" + System.lineSeparator();
//        String string3 = "Released Year" + System.lineSeparator();
//
//        string1 +="-----------"+System.lineSeparator();
//        string2 +="-----------"+System.lineSeparator();
//        string3 +="-----------"+System.lineSeparator();
//
//        while(cursor.moveToNext()){
//            string1 += cursor.getString(0)+System.lineSeparator();
//            string2 += cursor.getString(1)+System.lineSeparator();
//            string3 += cursor.getString(2)+System.lineSeparator();
//
//        }
////        binding.textView4.setText(string1);
////        binding.textView5.setText(string2);
////        binding.textView6.setText(string3);
//
//        cursor.close();
//        sqlDB.close();
//    }
}
