package com.example.innapc.reciperadar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingActivity extends AppCompatActivity {
    private EditText category;
    private EditText recipeName;
    private EditText prepare;
    private EditText ingredient;
    private Button send;

    private DatabaseReference recipesDatabase; // connector between the app and the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        Firebase.setAndroidContext(this);
        category = findViewById(R.id.categoryText);
        recipeName = findViewById(R.id.recipeNameText);
        prepare = findViewById(R.id.prepareText);
        ingredient = findViewById(R.id.ingredientsText);
        send = findViewById(R.id.sendBtn);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == send){
                    if(category.getText().toString().equals(""))
                        Toast.makeText(AddingActivity.this, "Category is null!",
                                Toast.LENGTH_LONG).show();
                    else if(recipeName.getText().toString().equals(""))
                        Toast.makeText(AddingActivity.this, "Name is null!",
                                Toast.LENGTH_LONG).show();
                    else if(ingredient.getText().toString().equals(""))
                        Toast.makeText(AddingActivity.this, "Ingredient is null!",
                                Toast.LENGTH_LONG).show();
                    else if(prepare.getText().toString().equals(""))
                        Toast.makeText(AddingActivity.this, "Prepare is null!",
                                Toast.LENGTH_LONG).show();
                    else {
                        recipesDatabase = FirebaseDatabase.getInstance().getReference().child("Pending").child(category.getText().toString())
                                .child(recipeName.getText().toString());
                        recipesDatabase.child("Ingredients").setValue(ingredient.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddingActivity.this,"Item was added successfully!!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(AddingActivity.this,"Failed adding item",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                        recipesDatabase.child("Prepare").setValue(prepare.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddingActivity.this,"Item was added successfully!!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(AddingActivity.this,"Failed adding item",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                    }
                }
            }
        });


    }

}
