package com.example.mishate.uznuintime;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class Signup_form extends AppCompatActivity {
    private DatabaseReference databaseReference;
  private FirebaseAuth mAuth;
   FirebaseAuth.AuthStateListener mAuthListener;
    public static String user;
    EditText et_email;
    EditText et_password;
    Button regBtn;
    Button login;
    TextView login_page;
    GoogleApiClient client;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);


        et_email = (EditText) findViewById(R.id.email_field);
        et_password = (EditText) findViewById(R.id.password_field);
        regBtn = (Button) findViewById(R.id.btn_reg);
        login = (Button) findViewById(R.id.login_btn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 email = et_email.getText().toString().trim();
                 password = et_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    et_email.setError("Введіть електронну пошту.");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    et_password.setError("Введіть пароль.");
                    return;
                }

                if(password.length() < 6) {
                    et_password.setError("Пароль має містити більше 6 символів");
                    return;
                }
                mAuth=FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            Toast.makeText(Signup_form.this, "Реєстрація успішна", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), profile_page.class));

                        }
                        else
                        {
                            Toast.makeText(Signup_form.this, "Помилка!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = et_email.getText().toString().trim();
                final String password = et_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    et_email.setError("Введіть електронну пошту.");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    et_password.setError("Введіть пароль.");
                    return;
                }

                if(password.length() < 6) {
                    et_password.setError("Пароль має містити більше 6 символів");
                    return;
                }

                mAuth=FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            user = email;
                            finishAfterTransition();
                            Toast.makeText(Signup_form.this, "Авторизація успішна", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Signup_form.this,teacher_page.class));
                        }
                        else
                        {

                            Toast.makeText(Signup_form.this, "Неправильний email/пароль" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            et_email.setText(null);
                            et_password.setText(null);


                        }

                    }
                });

            }
        });

        login_page = (TextView) findViewById(R.id.log_here);
        login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), reset_password.class);
                startActivity(myIntent);



            }
        }
            );
    }
}