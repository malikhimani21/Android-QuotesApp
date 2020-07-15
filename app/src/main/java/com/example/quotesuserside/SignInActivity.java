package com.example.quotesuserside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText email, password;
    private Button buttonLogin;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        buttonLogin = findViewById(R.id.signin_button);
        progressBar = findViewById(R.id.signin_progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                buttonLogin.setVisibility(View.GONE);

                String EMAIL = email.getText().toString().trim();
                String PASS = password.getText().toString().trim();

                if (EMAIL.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }

                if (PASS.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(EMAIL, PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                            progressBar.setVisibility(View.GONE);
                            buttonLogin.setVisibility(View.VISIBLE);

                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }

    public void openSignupActivity(View view) {
        Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
