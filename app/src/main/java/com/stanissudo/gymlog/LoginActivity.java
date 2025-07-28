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

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer userId = verifyUser();
                if(userId == null){
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), userId);
                    startActivity(intent);
                }
            }
        });
    }

    private Integer verifyUser() {
        String username = binding.usernameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Username and Password may not be blank.", Toast.LENGTH_SHORT).show();
            return null;
        }
        User user = repository.getUserByUserName(username);
        if(user != null && password.equals(user.getPassword())){
            return user.getId();
        }
        return null;
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
