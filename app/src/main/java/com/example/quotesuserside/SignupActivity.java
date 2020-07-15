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

import com.example.quotesuserside.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText name, email, contact, password;
    private Button signup;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.signup_name);
        email = (EditText) findViewById(R.id.signup_email);
        contact = (EditText) findViewById(R.id.signup_contact);
        password = (EditText) findViewById(R.id.signup_password);
        signup = (Button) findViewById(R.id.signup_button);
        progressBar = findViewById(R.id.signup_progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                signup.setVisibility(View.GONE);

                final String NAME = name.getText().toString().trim();
                final String EMAIL = email.getText().toString().trim();
                final String CONTACT = contact.getText().toString().trim();
                final String PASS = password.getText().toString().trim();
                final String ID = databaseReference.push().getKey();
                if (NAME.isEmpty()) {
                    name.setError("Name is required");
                    name.requestFocus();
                    return;
                }

                if (EMAIL.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }

                if (CONTACT.isEmpty()) {
                    contact.setError("Contact is required");
                    contact.requestFocus();
                    return;
                }

                if (PASS.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(EMAIL, PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            UserModel userModel = new UserModel(ID, NAME, EMAIL, CONTACT, PASS);

                            databaseReference.child(ID).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignupActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    signup.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                            Toast.makeText(SignupActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    public void openLoginActivity(View view) {
        startActivity(new Intent(SignupActivity.this, SignInActivity.class));
    }
}
