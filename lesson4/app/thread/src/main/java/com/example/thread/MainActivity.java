package com.example.thread;

import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thread.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int threadCounter = 0;
    private StringBuilder logBuilder = new StringBuilder();
    private static final String TAG = "ThreadProject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setupButtonListeners();
    }


    private void setupButtonListeners() {
        // Кнопка расчета среднего количества пар
        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInBackgroundThread();
            }
        });

        // Кнопка демонстрации блокировки UI (вызовет ANR при долгом нажатии)
        binding.buttonBlockUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demonstrateUIBlocking();
            }
        });
    }

    private void calculateInBackgroundThread() {
        // Получаем данные из полей ввода
        String totalPairsStr = binding.editTextTotalPairs.getText().toString();
        String studyDaysStr = binding.editTextStudyDays.getText().toString();

        if (totalPairsStr.isEmpty() || studyDaysStr.isEmpty()) {
            binding.textViewResult.setText("Пожалуйста, заполните все поля!");
            return;
        }

        final int totalPairs = Integer.parseInt(totalPairsStr);
        final int studyDays = Integer.parseInt(studyDaysStr);
        final int threadNumber = threadCounter++;
        final String startTime = getCurrentTime();

        // Создаем и запускаем фоновый поток
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String threadName = Thread.currentThread().getName();
                final int defaultPriority = Thread.currentThread().getPriority();

                // Логируем информацию о потоке
                Log.d(TAG, "Запущен поток №" + threadNumber +
                        " студентом группы БСБО-XX-XX, номер по списку XX");
                Log.d(TAG, "Имя потока: " + threadName);
                Log.d(TAG, "Приоритет потока до изменения: " + defaultPriority);

                // Симуляция длительных вычислений (2 секунды)
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Выполняем расчет среднего количества пар в день
                double averagePairs = calculateAverage(totalPairs, studyDays);
                String endTime = getCurrentTime();

                // Логируем результат
                Log.d(TAG, "Поток №" + threadNumber + " завершил вычисления");
                Log.d(TAG, "Результат: " + averagePairs);

                // Обновляем UI в главном потоке
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = String.format(Locale.getDefault(),
                                "Среднее количество пар в день: %.2f\n",
                                averagePairs);
                        binding.textViewResult.setText(result);

                    }
                });
            }
        }).start(); // Запуск потока

    }


    private double calculateAverage(int totalPairs, int studyDays) {
        if (studyDays <= 0) {
            return 0;
        }
        // Демонстрация сложных вычислений с дополнительной логикой
        double average = (double) totalPairs / studyDays;

        return average;
    }

    private void demonstrateUIBlocking() {


        // Имитация длительной операции в главном потоке
        long endTime = System.currentTimeMillis() + 20 * 1000;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    long waitTime = endTime - System.currentTimeMillis();
                    if (waitTime > 0) {
                        wait(waitTime);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}