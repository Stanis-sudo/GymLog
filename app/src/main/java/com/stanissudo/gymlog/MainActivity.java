package com.stanissudo.gymlog;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.stanissudo.gymlog.database.GymLogRepository;
import com.stanissudo.gymlog.database.entities.GymLog;
import com.stanissudo.gymlog.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private GymLogRepository repository;
    public static final String TAG = "Stan_GymLog";
    String exercise = "";
    double weight = 0.0;
    int repetitions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new GymLogRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "It worked!", Toast.LENGTH_SHORT).show();
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
            }
        });
    }
public void insertGymLogRecord(){
    GymLog log = new GymLog(exercise, weight, repetitions);
    repository.insertGymLog(log);
}
    private void updateDisplay() {
        String currentInfo = binding.logDisplayTextView.getText().toString();
        String newDisplay = String.format(Locale.US, "Exercise:%s%nWeight:%.2f%nReps:%d%n=-=-=-=%n%s", exercise, weight, repetitions, currentInfo);
        binding.logDisplayTextView.setText(newDisplay);
    }

    private void getInformationFromDisplay() {
        exercise = binding.exerciseInputEditText.getText().toString();
        try {
            weight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Weight edit text.");
        }
        try {
            repetitions = Integer.parseInt(binding.repetitionInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Repetitions edit text.");
        }
    }

}