package com.abdulla.bmi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bmiProject.DatePick;
import com.example.bmiProject.Records;
import com.example.bmiProject.TimePick;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class activityNewRecord extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    Button maxLength, minLength, minWei, maxWei, save;
    EditText length, weight;

    int countW = 50, countL = 120;


    Records records;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userId;
    TextView date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.bmiProject.R.layout.activity_new_record);
        maxLength = findViewById(com.example.bmiProject.R.id.maxLength);
        minLength = findViewById(com.example.bmiProject.R.id.minLength);
        minWei = findViewById(com.example.bmiProject.R.id.minWei);
        maxWei = findViewById(com.example.bmiProject.R.id.maxWei);
        save = findViewById(com.example.bmiProject.R.id.btn_saveData);
        date = findViewById(com.example.bmiProject.R.id.date);
        length = findViewById(com.example.bmiProject.R.id.length);
        weight = findViewById(com.example.bmiProject.R.id.weight);
        time = findViewById(com.example.bmiProject.R.id.time);
        records = new Records();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getUid();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePick();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePick();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(c.getTime());

        date.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time.setText(hourOfDay + ":" + minute);

    }

    public void maxLength(View view) {
        countL++;
        length.setText(countL + "");
    }

    public void minLength(View view) {
        if (!length.getText().toString().equals("120")) {
            countL--;
            length.setText(countL + "");
        }
    }

    public void minWei(View view) {
        if (weight.getText().toString().equals("50")) {
            Toast.makeText(getApplicationContext(), "ادخل قيمة اكبر من 50", Toast.LENGTH_SHORT).show();
        } else {
            countW--;
            weight.setText(countW + "");
        }
    }

    public void maxWei(View view) {
        countW++;
        weight.setText(countW + "");
    }


    public void save(View view) {

        if (date.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "ادخل التاريخ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (time.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "ادخل الوقت", Toast.LENGTH_SHORT).show();
            return;
        }
        records.setuId(userId);
        records.setDateRecords(date.getText().toString());
        records.setLengthRecords(length.getText().toString());
        records.setWeightRecords(weight.getText().toString());
        records.setTimeRecords(time.getText().toString());
        records.setuId(userId);
        firebaseFirestore.collection("Records").add(records)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                            date.setText("");
                            time.setText("");
                            length.setText("");
                            weight.setText("");
                            Intent intent =new Intent(getApplicationContext(), activityHome.class);
                            startActivity(intent);
                        }
                    }
                });


    }
}
