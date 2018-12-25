package com.example.innapc.reciperadar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        Button SignupBtn = (Button) findViewById(R.id.SignupBtn);
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText insertName = (EditText) findViewById(R.id.insertName);
                EditText insertEmail = (EditText) findViewById(R.id.insertEmail);
                EditText insertPass = (EditText) findViewById(R.id.insertPass);
                EditText insertAuthorizedPass = (EditText) findViewById(R.id.insertAuthorizedPass);

                Intent startIntent = new Intent(getApplicationContext(), SignIn_Activity.class);
                startActivity(startIntent);

            }
        });
    }


}
