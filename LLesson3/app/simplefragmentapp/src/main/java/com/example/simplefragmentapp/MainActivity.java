package com.example.simplefragmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFirst = findViewById(R.id.btnFirst);
        Button btnSecond = findViewById(R.id.btnSecond);

        // Проверяем, есть ли контейнер для фрагментов (вертикальная ориентация)
        if (findViewById(R.id.fragmentContainer) != null) {
            // Показываем первый фрагмент при запуске
            if (savedInstanceState == null) {
                showFragment(new FirstFragment(), R.id.fragmentContainer);
            }
        } else {
            // Горизонтальная ориентация - показываем оба фрагмента
            if (savedInstanceState == null) {
                showFragment(new FirstFragment(), R.id.fragmentContainer1);
                showFragment(new SecondFragment(), R.id.fragmentContainer2);
            }
        }

        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.fragmentContainer) != null) {
                    // Вертикальная ориентация
                    showFragment(new FirstFragment(), R.id.fragmentContainer);
                } else {
                    // Горизонтальная ориентация
                    showFragment(new FirstFragment(), R.id.fragmentContainer1);
                }
            }
        });

        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.fragmentContainer) != null) {
                    // Вертикальная ориентация
                    showFragment(new SecondFragment(), R.id.fragmentContainer);
                } else {
                    // Горизонтальная ориентация
                    showFragment(new SecondFragment(), R.id.fragmentContainer2);
                }
            }
        });
    }

    private void showFragment(Fragment fragment, int containerId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment);
        ft.commit();
    }
}