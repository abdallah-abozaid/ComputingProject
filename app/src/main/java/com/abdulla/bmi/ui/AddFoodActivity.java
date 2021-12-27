package com.abdulla.bmi.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bmiProject.Foods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFoodActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText etNameFood, etClary;
    Spinner spinner;
    ImageView imgFood;
    Button btnUploadPhoto, btnSave;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    StorageReference references;
    Uri imgUri, uri;
    Foods listFoods;
    String uIds, categorty;
    ProgressDialog dialog;
    int selspinner = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.bmiProject.R.layout.activity_add_food);
        etNameFood = findViewById(com.example.bmiProject.R.id.et_nameFood);
        imgFood = findViewById(com.example.bmiProject.R.id.imgFood);
        btnSave = findViewById(com.example.bmiProject.R.id.btnSave);
        etClary = findViewById(com.example.bmiProject.R.id.etClary);
        spinner = findViewById(com.example.bmiProject.R.id.spinner);
        btnUploadPhoto = findViewById(com.example.bmiProject.R.id.btnUploadPhoto);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        uIds = firebaseAuth.getUid();
        references = FirebaseStorage.getInstance().getReference();
        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 20);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                com.example.bmiProject.R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        categorty = spinner.getSelectedItem().toString();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etNameFood.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "يجب ادخال اسم الوجبة", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etClary.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "يحب ادخال عدد السعرات ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (uri == null) {
                    Toast.makeText(getApplicationContext(), "الرجاء اختيار صورة", Toast.LENGTH_LONG).show();
                    return;
                }
                listFoods = new Foods();
                listFoods.setuId(uIds);

                listFoods.setClaryFoods(etClary.getText().toString());
                listFoods.setNameFoods(etNameFood.getText().toString());
                listFoods.setCategoryFoodsName(selspinner + "");
                listFoods.setFBUri(uri + "");
                listFoods.setuId(uIds);

                firebaseFirestore.collection("food").add(listFoods)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    etClary.setText("");
                                    spinner.setSelection(0);
                                    etNameFood.setText("");
                                    Glide.with(getApplicationContext()).load(com.example.bmiProject.R.drawable.ic_launcher_background).into(imgFood);
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20) {
            if (resultCode == Activity.RESULT_OK) {
                imgUri = data.getData();
                Glide.with(getApplicationContext()).load(imgUri).into(imgFood);
                upload(imgUri);
            }
        }
    }

    private void upload(Uri image) {
        dialog = ProgressDialog.show(this, "انتظر ", "الرجاء الانتظار قليلا", true);
        final StorageReference reference = this.references.child(uIds);
        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        AddFoodActivity.this.uri = uri;
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categorty = adapterView.getItemAtPosition(i).toString();
        selspinner = adapterView.getSelectedItemPosition();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}