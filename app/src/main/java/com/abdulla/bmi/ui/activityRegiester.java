package com.abdulla.bmi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activityRegiester extends AppCompatActivity {
    TextView login;
    Button create;
    EditText userName, password, agenPassword, mail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.bmiProject.R.layout.activity_rigester);
        login = findViewById(com.example.bmiProject.R.id.login);
        password = findViewById(com.example.bmiProject.R.id.password);
        userName = findViewById(com.example.bmiProject.R.id.userName);
        agenPassword = findViewById(com.example.bmiProject.R.id.agenPassword);
        mail = findViewById(com.example.bmiProject.R.id.mail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        create = findViewById(com.example.bmiProject.R.id.create);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "الاسم مطلوب", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mail.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "الايميل مطلوب", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals("")) {
                    password.setError("كلمة السر مهمة");
                    return;
                }

                if (!password.getText().toString().equals(agenPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "الرجاء التاكد من تطابق كلمتا المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = mail.getText().toString();
                String pass = password.getText().toString();
                 firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        DocumentReference reference = firebaseFirestore.collection("User")
                                                .document(firebaseAuth.getUid());
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("userName", userName.getText().toString());
                                        user.put("Email", mail.getText().toString());
                                        user.put("password", password.getText().toString());
                                        reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(getApplicationContext(), activityCompleteRegiester.class);
                                                intent.putExtra("userId",firebaseAuth.getUid());
                                                startActivity(intent);
                                            }
                                        });
                                    } else {
                                        Toast.makeText(activityRegiester.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

            }
        });

    }
}