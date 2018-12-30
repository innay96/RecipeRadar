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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.innapc.reciperadar.R.id.*;

public class SignIn_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView email;
    private Button logout;
    private FloatingActionButton addRecipe;
    private StorageReference mStorageRef;
    private Button show;
    private static final int GALLERY_INTENT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (TextView)findViewById(nameEmail);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(logoutBtn);
        user = mAuth.getCurrentUser();
        addRecipe = (FloatingActionButton)findViewById(addBtn);
        mStorageRef = FirebaseStorage.getInstance().getReference();


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
         * should show the images of existed recipes
         */
        show.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (v==show){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,GALLERY_INTENT);
                }
            }

        });
       /* Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = mStorageRef.child("images/rivers.jpg");

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });*/

    }
}
