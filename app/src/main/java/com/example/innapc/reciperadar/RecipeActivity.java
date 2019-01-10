package com.example.innapc.reciperadar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class RecipeActivity extends AppCompatActivity {

    private TextView catText;
    private TextView nameText;
    private TextView ingText;
    private TextView preText;
    private ResultsActivity ra;
    public Firebase recipesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_recipe);
        catText = findViewById(R.id.CatText);
        nameText = findViewById(R.id.nameText);
        ingText = findViewById(R.id.ingText);
        preText = findViewById(R.id.preText);

        String nameRec = ra.getNameRec();
        String cate = String.valueOf(ra.getCate().get(nameRec));
        Log.d("getCate",String.valueOf(ra.getCate().get(nameRec)));
        String ing = String.valueOf(ra.getIng().get(nameRec));
        Log.d("getIng",String.valueOf(ra.getIng().get(nameRec)));
        String pre = String.valueOf(ra.getPre().get(nameRec));
        Log.d("getPre",String.valueOf(ra.getPre().get(nameRec)));

        catText.setText("Category: " +cate);
        nameText.setText("Recipe Name: " + nameRec);
        ingText.setText("Ingredients: " + ing);
        preText.setText("Preparation: " + pre);


    }
}
