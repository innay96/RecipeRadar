package com.example.innapc.reciperadar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

import static com.example.innapc.reciperadar.R.id.AcceptBtn;
import static com.example.innapc.reciperadar.R.id.DeclineBtn;
import static com.example.innapc.reciperadar.R.id.PendingText;


public class PendingActivity extends AppCompatActivity {
    private DatabaseReference recipesDatabase; // connector between the app and the database
    private Button Accept;
    private Button Decline;
    private TextView Pending_Recipe;
    public Firebase productsDatabase;
    private ListView productsListView;
    private ArrayList<String> productsList = new ArrayList<>();

    HashMap<String, String> Ingredients = new HashMap<>();//ingredients
    HashMap<String, String> Preparation = new HashMap<>();//preparation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_pending);
        Pending_Recipe = (TextView)findViewById(PendingText);
        Accept = (Button)findViewById(AcceptBtn);
        Decline = (Button)findViewById(DeclineBtn);
        productsDatabase = new Firebase("https://reciperadar.firebaseio.com/Pending");
        productsListView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productsList);

        productsListView.setAdapter(arrayAdapter);

        productsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final com.firebase.client.DataSnapshot dataSnapshot, String s) {
                Map<String, Object> lubna = (Map<String, Object>) dataSnapshot.getValue();
                HashMap<String, String> result = new HashMap<>();
                final String name = lubna.keySet().toString().substring(1,lubna.keySet().toString().indexOf("]")); // name of recipe
                result.put(dataSnapshot.getKey(), name); // key = category, value = name recipe
                productsList.add(result.toString().substring(1, result.toString().indexOf("}")));
                arrayAdapter.notifyDataSetChanged();

                final String[] n = name.split(", ");

                int i = 0;
                for (String childKey: lubna.keySet()) {
                    Map<String, Object> currentLubnaObject = (Map<String, Object>) lubna.get(childKey);
                    Ingredients.put(n[i],currentLubnaObject.toString().substring(currentLubnaObject.toString().indexOf("=")+1, currentLubnaObject.toString().indexOf("Prepare")-2));
                    Preparation.put(n[i],currentLubnaObject.toString().substring(currentLubnaObject.toString().indexOf("Prepare=")+8,currentLubnaObject.toString().indexOf("}")));
                    i++;
                }

                    productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final String selectedItem = (String) parent.getItemAtPosition(position);
                            final String[] s = selectedItem.split(",");
                            String nameRecipe = "";
                            for (int i = 0; i < s.length; i++){
                                if(s[i].contains("="))
                                    nameRecipe = s[i].substring(s[0].indexOf('=')+1);
                                else
                                    nameRecipe = s[i].substring(s[i].indexOf(' ')+1);

                            }

                            final String finalName = nameRecipe;
                            final int pos = position;
                            Accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (v == Accept) {
                                        addRecipe(selectedItem.substring(0, selectedItem.indexOf('=')), finalName, Ingredients, Preparation,
                                                pos, arrayAdapter);
                                    }
                                }
                            });

                            Decline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (v == Decline) {
                                        delete(pos, arrayAdapter, selectedItem.substring(0, selectedItem.indexOf('=')), finalName);
                                    }
                                }
                            });
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

    /**
     * deletes child from pending database
     */
    private void delete(int pos, ArrayAdapter<String> arrayAdapter, String cat, String recName){
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference().child("Pending").child(cat).child(recName);
        mPostReference.removeValue();
        productsList.remove(pos);
        arrayAdapter.notifyDataSetChanged();
    }

    /**
     * adds the recipe to the Database
     * the tree goes like this:
     * Recipes ---> NameOfCategory ---> NameOfRecipe ---> recipeList (the ingredients)
     */
    public void addRecipe(final String category, final String recipeName, HashMap<String, String> ingredient, HashMap<String, String> prepation,
                          final int pos, final ArrayAdapter<String> arrayAdapter){
        recipesDatabase = FirebaseDatabase.getInstance().getReference().child("Recipes").child(category).child(recipeName);
        recipesDatabase.child("Ingredients").setValue(ingredient.get(recipeName))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PendingActivity.this,"Item was added successfully!!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(PendingActivity.this,"Failed adding item",
                                Toast.LENGTH_LONG).show();
                    }
                });
        recipesDatabase.child("Prepare").setValue(prepation.get(recipeName))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PendingActivity.this,"Item was added successfully!!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(PendingActivity.this,"Failed adding item",
                                Toast.LENGTH_LONG).show();
                    }
                });
        recipesDatabase.child("Name").setValue(recipeName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PendingActivity.this,"Item was added successfully!!",
                                Toast.LENGTH_LONG).show();
                        delete(pos, arrayAdapter, category, recipeName);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(PendingActivity.this,"Failed adding item",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

}

