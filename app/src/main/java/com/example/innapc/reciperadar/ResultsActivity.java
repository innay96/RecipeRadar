package com.example.innapc.reciperadar;

<<<<<<< HEAD
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
                    Log.d("checkPending", currentLubnaObject.toString().substring(currentLubnaObject.toString().indexOf("=")+1, currentLubnaObject.toString().indexOf("Prepare")-2));
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
=======
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    public DatabaseReference recipesDatabase;
    private ArrayList<String> dontWant= SignIn_Activity.getDontEat();
    private ArrayList<String> results= new ArrayList<String>();
    private  boolean isOk;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        database=FirebaseDatabase.getInstance();
        recipesDatabase = database.getReference("Recipes");
        recipesDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        //Recipe reci = new Recipe(recipesDatabase);
                        Recipe reci = dataSnapshot.getValue(Recipe.class);
                        isOk = true;
                        for (int j = 0; j < dontWant.size() && isOk; j++)
                            if (reci.ingredients.contains(dontWant.get(j)))
                                isOk = false;
                        if (isOk)
                            results.add(reci.toString());
                    }
                }
            }/**
            String ans="";
            for( int k=0; k<results.size(); k++){
                ans+= results.toString();

            }
            TextView textView5=null;
            textView5.setText(ans);
         */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                }
        });

        loadRecyclerViewData();
    }
    private void loadRecyclerViewData(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading the results...");
        progressDialog.show();
    }
>>>>>>> origin/master

}
