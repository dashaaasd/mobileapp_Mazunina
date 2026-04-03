package com.example.lesson3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewResult = findViewById(R.id.textViewResult);

        // Получаем Intent, который запустил эту Activity
        Intent intent = getIntent();
        String timeString = intent.getStringExtra("current_time");


        int myNumber = 6;

        // Вычисляем квадрат номера
        int square = myNumber * myNumber;


        String resultText = String.format("Квадрат значения моего номера по списку в группе составляет %d, а текущее время %s",
                square, timeString);

        textViewResult.setText(resultText);
    }
}