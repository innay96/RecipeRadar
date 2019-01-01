package com.example.innapc.reciperadar;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    public DatabaseReference recipesDatabase;
    private ArrayList<String> dontWant= SignIn_Activity.getDontEat();
    private ArrayList<Recipe> results;
    private  boolean isOk;
 //   private ListView lv;
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
                                                              Recipe reci = new Recipe(recipesDatabase);
                                                              isOk = true;
                                                              for (int j = 0; j < dontWant.size() && isOk; j++)
                                                                  if (reci.ingredients.contains(dontWant.get(j)))
                                                                      isOk = false;
                                                              if (isOk)
                                                                  results.add(reci);
                                                          }
                                                      }
                                                  }

                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                  }
                                              });
                                        //      setContentView(R.layout.activity_results);
/*
        lv = (ListView) findViewById(R.id.resultsList);

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                R.id.userInfo );

        lv.setAdapter(arrayAdapter);

**/




        loadRecyclerViewData();
    }
    private void loadRecyclerViewData(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading the results...");
        progressDialog.show();}


}
