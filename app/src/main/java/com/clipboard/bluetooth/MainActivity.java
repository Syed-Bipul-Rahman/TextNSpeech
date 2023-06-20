package com.clipboard.bluetooth;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //declaration of global variables
    Button button;
    EditText editText;
    ImageView mic;

    TextToSpeech t1;
    String userToSpeak;
    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization of variables
        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        mic = findViewById(R.id.imageView);


        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userToSpeak = editText.getText().toString();
                if (userToSpeak.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Text", Toast.LENGTH_SHORT).show();
                } else {
                    t1.speak(userToSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), userToSpeak, Toast.LENGTH_SHORT).show();
                }

            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");
                try {
                    startActivityForResult(intent, REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Sorry your phone does not support Speech input", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && data != null) {

                    ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editText.setText(res.get(0));
                    t1.speak(res.get(0), TextToSpeech.QUEUE_FLUSH, null);


                }
                break;
            }
        }

    }

    @Override
    protected void onDestroy() {

        //shutdown
        if (t1!=null){
            t1.stop();
            t1.shutdown();
        }

        super.onDestroy();
    }
}