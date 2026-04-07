package com.example.mp4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.work.*;

public class BackgroundTaskFragment extends Fragment {

    private Button buttonStartTask;
    private TextView textViewStatus;
    private WorkManager workManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background_task, container, false);

        buttonStartTask = view.findViewById(R.id.buttonStartTask);
        textViewStatus = view.findViewById(R.id.textViewStatus);
        workManager = WorkManager.getInstance(requireContext());

        buttonStartTask.setOnClickListener(v -> startBackgroundTask());

        return view;
    }

    private void startBackgroundTask() {
        textViewStatus.setText("Задача запущена...");

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .build();

        workManager.enqueue(workRequest);
        Toast.makeText(getContext(), "Задача добавлена", Toast.LENGTH_SHORT).show();

        // Отслеживаем завершение
        workManager.getWorkInfoByIdLiveData(workRequest.getId())
                .observe(getViewLifecycleOwner(), workInfo -> {
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            textViewStatus.setText("Задача завершена!");
                        } else {
                            textViewStatus.setText("Ошибка!");
                        }
                    }
                });
    }
}