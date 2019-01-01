package com.example.innapc.reciperadar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.innapc.reciperadar.R.id.addBtn;
import static com.example.innapc.reciperadar.R.id.logoutBtn;
import static com.example.innapc.reciperadar.R.id.nameEmail;

public class SignIn_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView email;
    private Button logout;
    private FloatingActionButton addRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (TextView)findViewById(nameEmail);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(logoutBtn);
        user = mAuth.getCurrentUser();
        addRecipe = (FloatingActionButton)findViewById(addBtn);


        /**
         * fill the name of the user after "hello"
         */
        if (user != null){
            String Email = user.getEmail();
            email.setText(Email);
        }

        /**
         * logs out and return to the main menu
         */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==logout){
                    if (user != null) {
                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
            }
        });

        /**
         * the plus button , goes to the "add recipe" menu
         */
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==addRecipe){
                    startActivity(new Intent(getApplicationContext(), AddingActivity.class));
                }
            }
        });

    }
}
