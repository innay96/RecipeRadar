package com.example.innapc.reciperadar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    private DatabaseReference recipesDatabase; // connector between the app and the database

    public Firebase productsDatabase;
    private ListView productsListView;
    private ArrayList<String> productsList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_admin);
        productsDatabase = new Firebase("https://reciperadar.firebaseio.com/Pending");
        productsListView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, productsList);

        productsListView.setAdapter(arrayAdapter);

        productsDatabase.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                Map<String, Object> lubna = (Map<String, Object>) dataSnapshot.getValue();

                HashMap<String, String> result = new HashMap<>();
                String name = lubna.keySet().toString();

                result.put(dataSnapshot.getKey(), name.substring(1,name.indexOf("]")));
                productsList.add(result.toString().substring(1,result.toString().indexOf("}")));
                arrayAdapter.notifyDataSetChanged();

                    //**BELL**
                   /* Map<String, Object> currentLubnaObject = (Map<String, Object>) lubna.get(childKey);
                    Log.d("currentLubnaObject " , currentLubnaObject.toString()); // returns the ingredients and prepare*/
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


    /**
     * adds the recipe to the Database
     * the tree goes like this:
     * Recipes ---> NameOfCategory ---> NameOfRecipe ---> recipeList (the ingredients)
     */
    public void addRecipe(ArrayList<String> ingredients, EditText category, EditText recipeName){

            recipesDatabase = FirebaseDatabase.getInstance().getReference().child("Recipes").child(category.getText().toString()).child(recipeName.getText().toString());
            recipesDatabase.setValue(ingredients)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AdminActivity.this,"Item was added successfully!!",
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(AdminActivity.this,"Failed adding item",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
    }
}
