package com.stanissudo.gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stanissudo.gymlog.database.GymLogRepository;
import com.stanissudo.gymlog.database.entities.User;
import com.stanissudo.gymlog.databinding.ActivityLoginBinding;
import com.stanissudo.gymlog.databinding.ActivityMainBinding;

import java.util.function.BiConsumer;

import javax.security.auth.login.LoginException;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private GymLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(v -> {
            verifyUser((userId, success) -> {
                if (success && userId != null) {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), userId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void verifyUser(BiConsumer<Integer, Boolean> callback) {
        String username = binding.usernameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and Password may not be blank.", Toast.LENGTH_SHORT).show();
            callback.accept(null, false);
            return;
        }

        repository.getUserByUserName(username).observe(this, user -> {
            if (user != null && password.equals(user.getPassword())) {
                callback.accept(user.getId(), true);
            } else {
                callback.accept(null, false);
            }
        });
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
