package com.chhating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    TextView login_signup;
    Button log_button;
    EditText email,password;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    android.app.ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        log_button=findViewById(R.id.login_button);
        login_signup=findViewById(R.id.login_signin);

        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);

        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, Registation.class);
                startActivity(intent);
                finish();
            }
        });

        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();

                if ((TextUtils.isEmpty(mail))){
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!mail.matches(emailPattern)) {
                    progressDialog.dismiss();
                    email.setError("Give Valid Email Address");
                } else if (password.length()<6) {
                    progressDialog.dismiss();
                    password.setError("More thn Six Character");
                    Toast.makeText(Login.this, "Password needs to Longer Then Six Character", Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.show();
                                try{
                                    Intent intent=new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }catch (Exception e){
                                    Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
}