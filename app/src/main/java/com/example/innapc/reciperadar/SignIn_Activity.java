package com.example.innapc.reciperadar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.example.innapc.reciperadar.R.id.addBtn;
import static com.example.innapc.reciperadar.R.id.logoutBtn;
import static com.example.innapc.reciperadar.R.id.nameEmail;
import static com.example.innapc.reciperadar.R.id.searchButton;

public class SignIn_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView email;
    private Button logout;
    private Button search;
    private FloatingActionButton addRecipe;
    private CheckBox gluten;
    private CheckBox milk;
    private CheckBox peanuts;
    private CheckBox eggs;
    private CheckBox sugar;
    private CheckBox mushrooms;
    public boolean noMilk=false;
    public boolean noGluten=false;
    public boolean noPeanuts=false;
    public boolean noEggs=false;
    public boolean noSugar=false;
    public boolean noMushrooms=false;
    static public ArrayList<String> dontEat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        email = (TextView)findViewById(nameEmail);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(logoutBtn);
        user = mAuth.getCurrentUser();
        addRecipe = (FloatingActionButton)findViewById(addBtn);
        search= (Button)findViewById(searchButton);

        dontEat = new ArrayList<>();


        gluten=(CheckBox)findViewById(R.id.glutenCheckBox);
        gluten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){ noGluten=true;
            dontEat.add("Gluten"); }
            else {noGluten=false;
                dontEat.remove("Gluten");}
        }

        });
        milk=(CheckBox)findViewById(R.id.milkCheckBox);
        milk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {noMilk=true;
                    dontEat.add("Milk");}
                else {noMilk=false;
                    dontEat.remove("Milk");}
            }

        });

        eggs=(CheckBox)findViewById(R.id.eggsCheckBox);
        eggs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ noEggs=true;
                    dontEat.add("Eggs");}
                else {noEggs=false;
                    dontEat.remove("Eggs");}
            }

        });
        peanuts=(CheckBox)findViewById(R.id.peanutsCheckBox);
        peanuts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {noPeanuts=true;
                    dontEat.add("Peanuts");}
                else {noPeanuts=false;
                    dontEat.remove("Peanuts");}
            }

        });
        sugar=(CheckBox)findViewById(R.id.sugarCheckBox);
        sugar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {noSugar=true;
                    dontEat.add("Sugar");}
                else {noSugar=false;
                    dontEat.remove("Sugar");}
            }

        });
        mushrooms=(CheckBox)findViewById(R.id.mushroomsCheckBox);
        mushrooms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {noMushrooms=true;
                    dontEat.add("Mushrooms");}
                    else {noMushrooms=false;
                    dontEat.remove("Mushrooms");}
            }

        });


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

/**
 * searches the recipes that we want
 */
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==search){
                    if (user != null) {
                        ResultsActivity.setIdentity("Sign");
                        startActivity(new Intent(getApplicationContext(), ResultsActivity.class));
                    }
                }
            }
        });
    }
    static ArrayList<String> getDontEat(){
        return dontEat;
    }
}
