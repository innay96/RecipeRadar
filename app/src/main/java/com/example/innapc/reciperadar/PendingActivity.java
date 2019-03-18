package com.example.innapc.reciperadar;

<<<<<<< HEAD
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
=======
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.innapc.reciperadar.R.id.PendingBtn;
import static com.example.innapc.reciperadar.R.id.addBtn;
import static com.example.innapc.reciperadar.R.id.logoutBtn;
import static com.example.innapc.reciperadar.R.id.nameEmail;
import static com.example.innapc.reciperadar.R.id.searchButton;
import static com.example.innapc.reciperadar.R.id.search_button;

public class PendingActivity extends AppCompatActivity {

    private DatabaseReference recipesDatabase; // connector between the app and the database
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView email;
    private Button logout;
    private Button search;
    private Button pending;
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

>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_pending);
<<<<<<< HEAD
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

=======
        email = (TextView) findViewById(nameEmail);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button) findViewById(logoutBtn);
        user = mAuth.getCurrentUser();
        addRecipe = (FloatingActionButton) findViewById(addBtn);
        search = (Button) findViewById(searchButton);
        pending = (Button) findViewById(PendingBtn);

       /* gluten = (CheckBox) findViewById(R.id.glutenCheckBox);
        gluten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noGluten = true;
                    dontEat.add("Gluten");
                }
            }

        });
        milk = (CheckBox) findViewById(R.id.milkCheckBox);
        milk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noMilk = true;
                    dontEat.add("Milk");
                }
            }

        });

        eggs = (CheckBox) findViewById(R.id.eggsCheckBox);
        eggs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noEggs = true;
                    dontEat.add("Eggs");
                }
            }

        });
        peanuts = (CheckBox) findViewById(R.id.peanutsCheckBox);
        peanuts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noPeanuts = true;
                    dontEat.add("Peanuts");
                }
            }

        });
        sugar = (CheckBox) findViewById(R.id.sugarCheckBox);
        sugar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noSugar = true;
                    dontEat.add("Sugar");
                }
            }

        });
        mushrooms = (CheckBox) findViewById(R.id.mushroomsCheckBox);
        mushrooms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noMushrooms = true;
                    dontEat.add("Mushrooms");
                }
            }

        });


        /**
         * fill the name of the user after "hello"
         */
        if (user != null) {
            String Email = user.getEmail();
            email.setText(Email);
        }

        /**
         * logs out and return to the main menu
         */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == logout) {
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
                if (v == addRecipe) {
                    startActivity(new Intent(getApplicationContext(), AddingActivity.class));
                }
            }
        });

        /*
         * the pending button, goes to see if there are pending recipes
         */
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == pending) {
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                }
            }
        });

/**
 * searches the recipes that we want
 */
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == search) {
                    if (user != null) {
                        startActivity(new Intent(getApplicationContext(), ResultsActivity.class));
                    }
                }
            }
        });
    }
    static ArrayList<String> getDontEat () {
        return dontEat;
    }



>>>>>>> origin/master
    /**
     * adds the recipe to the Database
     * the tree goes like this:
     * Recipes ---> NameOfCategory ---> NameOfRecipe ---> recipeList (the ingredients)
     */
<<<<<<< HEAD
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
=======
    public void addRecipe(ArrayList<String> ingredients, EditText category, EditText recipeName){

        recipesDatabase = FirebaseDatabase.getInstance().getReference().child("Recipes").child(category.getText().toString()).child(recipeName.getText().toString());
        recipesDatabase.setValue(ingredients)
>>>>>>> origin/master
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PendingActivity.this,"Item was added successfully!!",
                                Toast.LENGTH_LONG).show();
<<<<<<< HEAD
                        delete(pos, arrayAdapter, category, recipeName);
=======
>>>>>>> origin/master
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
<<<<<<< HEAD

}

=======
}
>>>>>>> origin/master
