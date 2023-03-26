package com.example.learnconstraintlayout;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_Activity extends AppCompatActivity {
    EditText emailEditTextRegister, passwordEditTextRegiser, verifypasswordEditTextRegister;
    Button buttonRegister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView loginTextView;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(Register_Activity.this, "Login successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Register_Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarRegister);
        loginTextView = findViewById(R.id.loginTextView);
        emailEditTextRegister = findViewById(R.id.emailEditTextRegister);
        passwordEditTextRegiser= findViewById(R.id.passwordEditTextRegister);
        verifypasswordEditTextRegister = findViewById(R.id.verifyPasswordEditText);
        buttonRegister = findViewById(R.id.buttonRegister);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = String.valueOf(emailEditTextRegister.getText());
                String password = String.valueOf(passwordEditTextRegiser.getText());
                String verifypassword = String.valueOf(verifypasswordEditTextRegister.getText());
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register_Activity.this, "Enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register_Activity.this, "Enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(verifypassword)) {
                    Toast.makeText(Register_Activity.this, "Verify password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.equals(verifypassword)) {
                    Toast.makeText(Register_Activity.this, "Password and verify password are different", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Register_Activity.this, "Register successfully.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register_Activity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}