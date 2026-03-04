package com.example.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }


    public void onOkClicked() {
        String message = "Вы выбрали кнопку \"Иду дальше\"!";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        textViewResult.setText(message);
    }

    public void onCancelClicked() {
        String message = "Вы выбрали кнопку \"Нет\"!";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        textViewResult.setText(message);
    }

    public void onNeutralClicked() {
        String message = "Вы выбрали кнопку \"На паузе\"!";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        textViewResult.setText(message);
    }

    public void onClickShowTimeDialog(View view) {
        MyTimeDialogFragment timeDialog = new MyTimeDialogFragment();
        timeDialog.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickShowDateDialog(View view) {
        MyDateDialogFragment dateDialog = new MyDateDialogFragment();
        dateDialog.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickShowProgressDialog(View view) {
        MyProgressDialogFragment progressDialog = new MyProgressDialogFragment();
        progressDialog.show(getSupportFragmentManager(), "progress");
    }

    public void onTimeSelected(String time) {
        textViewResult.setText(time);
        Toast.makeText(this, time, Toast.LENGTH_SHORT).show();
    }

    public void onDateSelected(String date) {
        textViewResult.setText(date);
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
    }

    public void onProgressFinished() {
        String message = "Загрузка завершена!";
        textViewResult.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}