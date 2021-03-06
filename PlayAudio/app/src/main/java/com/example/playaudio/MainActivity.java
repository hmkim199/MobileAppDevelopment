package com.example.playaudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.playaudio.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;
    private static String audioFilePath;
    private boolean isRecording = false;
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        audioSetup();
    }

    protected boolean hasMicrophone(){
        PackageManager packageManager = this.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    private void audioSetup(){
        if(!hasMicrophone()){
            binding.stopButton.setEnabled(false);
            binding.recordButton.setEnabled(false);
            binding.playButton.setEnabled(false);
            binding.stopRecordButton.setEnabled(false);
        }else{
            binding.playButton.setEnabled(false);
            binding.stopButton.setEnabled(false);
            binding.stopRecordButton.setEnabled(false);
        }

        audioFilePath = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()+"/myaudio.3gp";

        requestPermission(Manifest.permission.RECORD_AUDIO, RECORD_REQUEST_CODE);
    }

    public void recordAudio(View view){
        isRecording = true;
        binding.stopButton.setEnabled(false);
        binding.recordButton.setEnabled(false);
        binding.playButton.setEnabled(false);
        binding.stopRecordButton.setEnabled(true);

        try{
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        }catch(Exception e){
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    public void stopAudio(View view){
        binding.stopButton.setEnabled(false);
        binding.recordButton.setEnabled(true);
        binding.playButton.setEnabled(true);
        binding.stopRecordButton.setEnabled(false);

        mediaPlayer.release();
        mediaPlayer = null;

    }

    public void stopRecord(View view){
        binding.stopButton.setEnabled(false);
        binding.recordButton.setEnabled(true);
        binding.playButton.setEnabled(true);
        binding.stopRecordButton.setEnabled(false);

        if(isRecording){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        }else{
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playAudio(View view) throws IOException {
        binding.stopButton.setEnabled(true);
        binding.recordButton.setEnabled(false);
        binding.playButton.setEnabled(false);
        binding.stopRecordButton.setEnabled(false);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    protected void requestPermission(String permissionType, int requestCode){
        int permission = ContextCompat.checkSelfPermission(this, permissionType);

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{permissionType}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case RECORD_REQUEST_CODE:{
                if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    binding.recordButton.setEnabled(false);
                    Toast.makeText(this, "RECORD PERMISSION REQUIRED", Toast.LENGTH_LONG).show();
                }else{
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_REQUEST_CODE);
                }
            }
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "EXTERNAL STORAGE PERMISSION REQUIRED", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}