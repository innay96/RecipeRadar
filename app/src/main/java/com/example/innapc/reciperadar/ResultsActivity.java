package com.example.innapc.reciperadar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {
    private static String identity ="";

    FirebaseDatabase database;
    private ArrayList<String> dontWant;
    private ArrayList<String> results= new ArrayList<>();
    private  boolean isOk = true;
    private ListView lv;


    static String nameRecipe = "";

    static HashMap<String, String> Ingredients = new HashMap<>();//ingredients
    static HashMap<String, String> Preparation = new HashMap<>();//preparation
    public Firebase productsDatabase;
    private static HashMap<String, String> result = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_results);

        if(identity == "Admin")
            dontWant = AdminActivity.getDontEat();
        else if(identity == "Sign")
            dontWant = SignIn_Activity.getDontEat();

        lv = (ListView)findViewById(R.id.resultsView);
        database=FirebaseDatabase.getInstance();
        productsDatabase = new Firebase("https://reciperadar.firebaseio.com/Recipes");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);

        lv.setAdapter(arrayAdapter);
        productsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                Map<String, Object> lubna = (Map<String, Object>) dataSnapshot.getValue();
                final String name = lubna.keySet().toString().substring(1,lubna.keySet().toString().indexOf("]")); // name of recipe
                result.put(name, dataSnapshot.getKey()); // value = category, key = name recipe

                final String[] n = name.split(", ");

                int i = 0;
                for (String childKey: lubna.keySet()) {
                    Map<String, Object> currentLubnaObject = (Map<String, Object>) lubna.get(childKey);
                    Ingredients.put(n[i],currentLubnaObject.toString().substring(currentLubnaObject.toString().indexOf("=")+1, currentLubnaObject.toString().indexOf("Prepare")-2));
                    Preparation.put(n[i],currentLubnaObject.toString().substring(currentLubnaObject.toString().indexOf("Prepare=")+8,currentLubnaObject.toString().indexOf("}")));
                    for (int j = 0; j < dontWant.size() && isOk; j++) {
                        for(int k = 0; k < Ingredients.size(); k++) {
                            if (Ingredients.get(n[i]).contains(dontWant.get(j)))
                                isOk = false;
                        }
                    }
                    if(isOk){
                        results.add(n[i]);
                        arrayAdapter.notifyDataSetChanged();
                    }
                    isOk = true;
                    i++;
                    Log.d("aIsIne ", Ingredients.toString());
                    Log.d("aIsPre ", Preparation.toString());
                    Log.d("aIs ", results.toString());
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final String selectedItem = (String) parent.getItemAtPosition(position);
                        final String[] s = selectedItem.split(",");
                        for (int i = 0; i < s.length; i++) {
                            if (s[i].contains("=")) {
                                nameRecipe = s[i].substring(s[0].indexOf('=') + 1);
                                startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
                            }
                            else {
                                nameRecipe = s[i].substring(s[i].indexOf(' ') + 1);
                                startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
                            }

                        }
                    }
                });
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
    public static String getNameRec(){
        Log.d("getName",nameRecipe);
        return nameRecipe;
    }
    public static HashMap<String, String> getCate(){
        return result;
    }
    public static HashMap<String, String> getIng(){
        return Ingredients;
    }
    public static HashMap<String, String> getPre(){
        return Preparation;
    }
   public static void setIdentity(String id){
       identity = id;
   }

}
