package com.abdulla.bmi.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class EditFoodActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText etNameFood, etClary;
    Spinner spinner;
    ImageView imgFood;
    Button btnUploadPhoto, btnSave;
    Uri uri;
    String firbaseUr,nameFood , claryFood, documentId,uId, categorys;;
    Intent intent;
    ProgressDialog dialog;
    int id;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.bmiProject.R.layout.activity_edit_food_activty);
        etNameFood = findViewById(com.example.bmiProject.R.id.etNameFood);
        btnUploadPhoto = findViewById(com.example.bmiProject.R.id.btnUploadPhoto);
        etClary = findViewById(com.example.bmiProject.R.id.etClary);
        spinner = findViewById(com.example.bmiProject.R.id.spinner);
        imgFood = findViewById(com.example.bmiProject.R.id.imgFood);
        btnSave = findViewById(com.example.bmiProject.R.id.btnSave);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        uId = firebaseAuth.getUid();
        intent = getIntent();
        nameFood = intent.getStringExtra("name");
        claryFood = intent.getStringExtra("c");
        categorys = intent.getStringExtra("cat");
        documentId = intent.getStringExtra("dId");
        firbaseUr = intent.getStringExtra("image");
        id = intent.getIntExtra("id",1);

        etNameFood.setText(nameFood);
        etClary.setText(claryFood);
        etNameFood.setText(nameFood);
        Glide.with(getApplicationContext()).load(firbaseUr).into(imgFood);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                com.example.bmiProject.R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(id);

        spinner.setOnItemSelectedListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opengalary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengalary, 20);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNameFood.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "الاسم اجباري ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (etClary.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "السعرات اجبارية ", Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String, Object> stringObjectHashMap = new HashMap<>();
                String claryFoods=etClary.getText().toString();
                String nameFoods=etNameFood.getText().toString();


                stringObjectHashMap.put("claryFoods", claryFoods);
                stringObjectHashMap.put("fburi", firbaseUr);
                stringObjectHashMap.put("nameFoods", nameFoods);
                stringObjectHashMap.put("categoryFoodsName", id);
                firebaseFirestore.collection("food").document(documentId)
                        .update(stringObjectHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                           startActivity(new Intent(getApplicationContext() , activityListFood.class));

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
                uri = data.getData();
                uplode(uri);
            }
        }
    }

    private void uplode(Uri uri1) {
        dialog = ProgressDialog.show(this, "","استنا شوي ", true);
        final StorageReference reference = storageReference.child(uId);
        reference.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri).into(imgFood);
                        firbaseUr = uri.toString();
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categorys = adapterView.getItemAtPosition(i).toString();
        id = adapterView.getSelectedItemPosition();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}