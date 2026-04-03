package com.example.favoritebook;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewBook;
    private Button buttonOpen;
    public static final String USER_MESSAGE = "USER_MESSAGE";
    public static final String BOOK_NAME_KEY = "BOOK_NAME";
    public static final String QUOTES_KEY = "QUOTES";

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String userMessage = data.getStringExtra(USER_MESSAGE);
                            textViewBook.setText(userMessage);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBook = findViewById(R.id.textViewBook);
        buttonOpen = findViewById(R.id.buttonOpen);

        textViewBook.setText("Тут появится название вашей любимой книги и любимая цитата из нее!");

        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoAboutBook();
            }
        });
    }

    private void getInfoAboutBook() {
        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtra(BOOK_NAME_KEY, "Гарри Поттер");
        intent.putExtra(QUOTES_KEY, "После стольких лет? Всегда!");
        activityResultLauncher.launch(intent);
    }
}