package com.example.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText editTextAge;
    private EditText editTextJob;
    private Button buttonSend;
    private TextView textViewResult;
    private MyLooper myLooper;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        editTextAge = findViewById(R.id.editTextAge);
        editTextJob = findViewById(R.id.editTextJob);
        buttonSend = findViewById(R.id.buttonSend);
        textViewResult = findViewById(R.id.textViewResult);

        // Создаем Handler для главного потока
        mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // Получаем результат из фонового потока
                Bundle bundle = msg.getData();
                String result = bundle.getString("result");
                Log.d(TAG, "Получен результат в главном потоке: " + result);
                textViewResult.setText("Результат:\n" + result);
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        };

        // Создаем и запускаем фоновый поток с Looper
        myLooper = new MyLooper(mainHandler);
        myLooper.start();

        // Обработчик нажатия кнопки
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToBackgroundThread();
            }
        });
    }

    private void sendDataToBackgroundThread() {
        // Получаем данные из полей ввода
        String ageStr = editTextAge.getText().toString();
        String job = editTextJob.getText().toString();

        // Проверка на пустые поля
        if (ageStr.isEmpty() || job.isEmpty()) {
            Toast.makeText(this, "Заполните оба поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        // Создаем сообщение для отправки в фоновый поток
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putInt("age", age);
        bundle.putString("job", job);
        msg.setData(bundle);

        // Отправляем сообщение в Handler фонового потока
        if (myLooper.mHandler != null) {
            myLooper.mHandler.sendMessage(msg);
            Log.d(TAG, "Отправлены данные: возраст=" + age + ", профессия=" + job);
            Toast.makeText(this, "Данные отправлены, ожидание " + age + " сек...", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Handler фонового потока не инициализирован");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Останавливаем Looper при уничтожении Activity
        if (myLooper != null) {
            myLooper.quit();
        }
    }
}