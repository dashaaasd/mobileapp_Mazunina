package com.example.lesson3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnSendTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendTime = findViewById(R.id.btnSendTime);

        btnSendTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем системное время
                Long dateInMillis = System.currentTimeMillis();
                String format = "yyyy-MM-dd HH:mm:ss";
                final SimpleDateFormat sdf = new SimpleDateFormat(format);
                String dateString = sdf.format(new Date(dateInMillis));

                // Создаем Intent для перехода на SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("current_time", dateString);
                startActivity(intent);
            }
        });
    }
}