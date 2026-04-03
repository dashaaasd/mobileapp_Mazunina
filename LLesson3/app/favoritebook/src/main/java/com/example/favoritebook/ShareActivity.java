package com.example.favoritebook;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    private TextView textViewDevBook;
    private TextView textViewDevQuote;
    private EditText editTextUserBook;
    private EditText editTextUserQuote;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        textViewDevBook = findViewById(R.id.textViewDevBook);
        textViewDevQuote = findViewById(R.id.textViewDevQuote);
        editTextUserBook = findViewById(R.id.editTextUserBook);
        editTextUserQuote = findViewById(R.id.editTextUserQuote);
        buttonSend = findViewById(R.id.buttonSend);

        // Получение данных из MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String book_name = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quotes_name = extras.getString(MainActivity.QUOTES_KEY);
            textViewDevBook.setText("Любимая книга разработчика: " + book_name);
            textViewDevQuote.setText("Цитата из книги: " + quotes_name);
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userBook = editTextUserBook.getText().toString();
                String userQuote = editTextUserQuote.getText().toString();

                String resultText = String.format("Название Вашей любимой книги: %s. Цитата: %s",
                        userBook, userQuote);

                // Отправка введенных пользователем данных по нажатию на кнопку
                Intent data = new Intent();
                data.putExtra(MainActivity.USER_MESSAGE, resultText);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}