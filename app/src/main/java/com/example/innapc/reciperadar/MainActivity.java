package com.example.innapc.reciperadar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText password;
    private EditText email;
    private Button signUpBtn;
    private Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance(); // connector to the firebase - sign up helper
        currentUser = mAuth.getCurrentUser(); // connector to the firebase - sign in helper
        email = (EditText) findViewById(R.id.emailEdit);
        password = (EditText) findViewById(R.id.passwordEdit);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        /**
         * SignIn btn = goes to login function
         */
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        /**
         * SignUp btn = goes to register function
         */
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

    }

    /**
     * checks if the email and password exist in the database
     * if not, throws an exception
     */
    public void LoginUser(){
        final String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && (isAdmin(Email))){//if login successful and user is admin
                            currentUser = mAuth.getCurrentUser();
                            finish();

                            startActivity(new Intent(getApplicationContext(),
                                    PendingActivity.class));
                        }
                        else if(task.isSuccessful() && (!isAdmin(Email))){//if login successful and user is not admin
                            currentUser = mAuth.getCurrentUser();
                            finish();
                            startActivity(new Intent(getApplicationContext(),
                                    SignIn_Activity.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "couldn't login",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * checks if the email entered is an Admin user or not
     * @param Email
     * @return boolean
     */
    private boolean isAdmin(@NonNull String Email){
        boolean isadmin=false;
        String admin1="inna.yakubov1996@gmail.com";
        String admin2="annaharonov@gmail.com";
        String admin3="orenisabella@gmail.com";
        String admin4="admin@gmail.com";
        if(Email.equals(admin1) || Email.equals(admin2) || Email.equals(admin3) || Email.equals(admin4)){
            isadmin=true;
        }
        return isadmin;
    }

    /**
     * checks if the email and password filleds are empty, throws an exception
     * if not, saves the email and password in firebase for future use (IF THW EMAIL IS NOT TAKEN !!!)
     */
    public void RegisterUser(){
        final String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //User is Admin
                            if (task.isSuccessful() && (isAdmin(Email))){//if login successful and user is admin
                                currentUser = mAuth.getCurrentUser();
                                startActivity(new Intent(getApplicationContext(),
                                        AdminActivity.class));
                            }
                            //User is successfully registered and logged in
                            //start SignIn Activity here
                            else if(task.isSuccessful() && (!isAdmin(Email))){//if login successful and user is not admin
                                Toast.makeText(MainActivity.this, "registration successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), SignIn_Activity.class));
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Couldn't register, try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}

