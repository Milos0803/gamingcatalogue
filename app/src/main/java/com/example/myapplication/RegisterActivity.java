package com.example.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccount;
    private EditText uNameReg, PassReg, eMailReg, PassReEnter;
    private ProgressDialog loadingBar;
    private TextView backToLogin;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateAccount = (Button) findViewById(R.id.createAccountButton);
        uNameReg = (EditText) findViewById(R.id.userNameInputRegister);
        PassReg = (EditText) findViewById(R.id.passwordInputRegister);
        PassReEnter = (EditText) findViewById(R.id.reEnterPassword);
        eMailReg = (EditText) findViewById(R.id.enterEmailInput);
        backToLogin = (TextView) findViewById(R.id.backLogin);
        loadingBar = new ProgressDialog(this);


        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        CreateAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = firebaseDatabase.getReference("users");

                String username = uNameReg.getText().toString().toUpperCase();
                String password = PassReg.getText().toString().toUpperCase();
                String ReEnterPass = PassReEnter.getText().toString().toUpperCase();
                String email = eMailReg.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (username.isEmpty()) {
                    uNameReg.setError("Unesite Vas username!");

                } else if (username.length() < 6 || username.length() > 15) {
                    uNameReg.setError("Username mora da ima vise od 6 , a manje od 15 karaktera.");

                } else if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
                    PassReg.setError("Polje ne sme biti prazno i mora imati vise od 6 a manje od 15 karaktera");

                } else if (!ReEnterPass.equals(password)) {
                    PassReEnter.setError("Password se ne podudara!");

                } else if (email.isEmpty() || !email.matches(emailPattern)) {
                    eMailReg.setError("Neispravna E-mail adresa!");
                } else {


                    Users users = new Users(username, password, email);
                    reference.child(username).setValue(users);
                    Toast.makeText(getApplicationContext(), "Uspesno ste napravili account", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);


                }


            }


        });


    }
}



