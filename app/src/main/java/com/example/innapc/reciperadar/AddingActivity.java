package com.example.innapc.reciperadar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.ArrayList;

public class AddingActivity extends AppCompatActivity {
    private EditText category;
    private EditText recipeName;
    private Button send;

    private ArrayList<String> recipesList =  new ArrayList<>(); // contains the value of the recipe (the ingredients)

    private AdminActivity admin = new AdminActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        Firebase.setAndroidContext(this);
        category = findViewById(R.id.categoryText);
        recipeName = findViewById(R.id.recipeNameText);
        send = findViewById(R.id.sendBtn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == send){
                    admin.addRecipe(recipesList, category, recipeName); // sends to the admin to get an approval
                }
            }
        });
    }

    /**
     * checks which checkbox was checked and adds it to the recipesList
     * @param view
     */
    public void selectItem(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case R.id.eggsCheckBox:
                if(checked)
                    recipesList.add("Eggs");
                else
                    recipesList.remove("Eggs");
                break;
            case R.id.milkCheckBox:
                if(checked)
                    recipesList.add("Milk");
                else
                    recipesList.remove("Milk");
                break;
            case R.id.peanutsCheckBox:
                if(checked)
                    recipesList.add("Peanuts");
                else
                    recipesList.remove("Peanuts");
                break;
            case R.id.mushroomsCheckBox:
                if(checked)
                    recipesList.add("Mushrooms");
                else
                    recipesList.remove("Mushrooms");
                break;
            case R.id.sugarCheckBox:
                if(checked)
                    recipesList.add("Sugar");
                else
                    recipesList.remove("Sugar");
                break;
            case R.id.glutenCheckBox:
                if(checked)
                    recipesList.add("Gluten");
                else
                    recipesList.remove("Gluten");
                break;
        }

    }
}
