package com.clipboard.bluetooth;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    }
}