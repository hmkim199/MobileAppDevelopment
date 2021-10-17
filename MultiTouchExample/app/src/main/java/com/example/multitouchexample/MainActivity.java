package com.example.multitouchexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.multitouchexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.myLayout.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent m) {
                        handleTouch(m);
                        return true;
                    }
                }
        );
    }

    void handleTouch(MotionEvent m){
        int pointerCount = m.getPointerCount();

        for (int i = 0; i < pointerCount; i++){

            int x = (int) m.getX(i);
            int y = (int) m.getY(i);
            int id = m.getPointerId(i);
            int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;

            switch(action){
                case MotionEvent.ACTION_DOWN:
                    actionString = "DOWN";
                    break;
                case MotionEvent.ACTION_UP:
                    actionString = "UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionString = "MOVE";
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    actionString = "PNTR_UP";
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    actionString = "PNTR_DOWN";
                    break;
                default:
                    actionString = "";
            }

            String touchStatus = "Action: "+ actionString + " Index: "+actionIndex + "ID: "+id+"X: "+x+"Y: "+y;

            if(id==0){
                binding.textView1.setText(touchStatus);
            }
            else{
                binding.textView2.setText(touchStatus);
            }

        }
    }
}