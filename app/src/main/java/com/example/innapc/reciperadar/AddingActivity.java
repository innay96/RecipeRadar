package com.example.innapc.reciperadar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView category;
    private TextView recipeName;
    private CheckBox eggs;
    private CheckBox milk;
    private CheckBox peanuts;
    private CheckBox gluten;
    private CheckBox mushrooms;
    private CheckBox sugar;
    private Button send;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        Firebase.setAndroidContext(this);
        category = findViewById(R.id.categoryText);
        recipeName = findViewById(R.id.recipeNameText);
        eggs = findViewById(R.id.eggsCheckBox);
        milk = findViewById(R.id.milkCheckBox);
        peanuts = findViewById(R.id.peanutsCheckBox);
        mushrooms = findViewById(R.id.mushroomsCheckBox);
        sugar = findViewById(R.id.sugarCheckBox);
        gluten = findViewById(R.id.glutenCheckBox);
        send = findViewById(R.id.sendBtn);

        database = FirebaseDatabase.getInstance();
        /**
         * should be saved and sent to the ADMIN for verification , then be added to the database
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d("myTag", category.toString());
               // if(category.equals("Cakes") || category.equals("cakes")) {
                  // mRef = new Firebase("https://reciperadar.firebaseio.com/Cakes");

                DatabaseReference myRef = database.getReferenceFromUrl("https://reciperadar.firebaseio.com/Recipes");
                myRef.setValue("lolo");

            }
        });

    }
}
