package com.example.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {

    private static final String TAG = "MyLooper";
    public Handler mHandler;
    private Handler mainHandler;

    // Конструктор принимает Handler главного потока
    public MyLooper(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        // Подготавливаем Looper для этого потока
        Looper.prepare();

        // Создаем Handler для обработки сообщений в этом потоке
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // Получаем данные из сообщения
                Bundle bundle = msg.getData();
                int age = bundle.getInt("age");
                String job = bundle.getString("job");

                Log.d(TAG, "Получены данные в фоновом потоке:");
                Log.d(TAG, "Возраст: " + age);
                Log.d(TAG, "Профессия: " + job);

                // Имитация задержки (возраст = количество секунд)
                try {
                    Log.d(TAG, "Ожидание " + age + " секунд...");
                    Thread.sleep(age * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Формируем результат
                String result = "Возраст: " + age + " лет, Профессия: " + job;
                Log.d(TAG, "Результат обработки: " + result);

                // Отправляем результат обратно в главный поток
                Message resultMsg = Message.obtain();
                Bundle resultBundle = new Bundle();
                resultBundle.putString("result", result);
                resultMsg.setData(resultBundle);
                mainHandler.sendMessage(resultMsg);
            }
        };

        // Запускаем бесконечный цикл Looper
        Looper.loop();
    }

    // Метод для остановки Looper
    public void quit() {
        if (mHandler != null) {
            mHandler.getLooper().quit();
        }
    }
}