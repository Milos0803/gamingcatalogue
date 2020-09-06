package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[a-zA-Z])" +
            "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{4,}" +
            "$");
    ;
    private Button loginBtn, registerBtn;
    private EditText uNameLogin, iPassword;
    private TextView imAdmin,noAdmin;
    private ProgressDialog loadingBar;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginBtn = (Button) findViewById(R.id.LoginButtonID);
        registerBtn = (Button) findViewById(R.id.RegisterButtonID);
        uNameLogin = (EditText) findViewById(R.id.usernameInputLogin);
        iPassword = (EditText) findViewById(R.id.passwordInput);
        imAdmin = (TextView) findViewById(R.id.imAdminText);
        noAdmin= (TextView) findViewById(R.id.imNotAdminText);





        noAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imAdmin.setVisibility(View.INVISIBLE);
                loginBtn.setText("Login");
                imAdmin.setVisibility(View.VISIBLE);
                noAdmin.setVisibility(View.INVISIBLE);
                registerBtn.setVisibility(View.VISIBLE);
            }
        });




        imAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setText("Admin Login");
                imAdmin.setVisibility(View.INVISIBLE);
                noAdmin.setVisibility(View.VISIBLE);
                registerBtn.setVisibility(View.INVISIBLE);
                


            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUsername();
                validatePassword();
                isUser();


            }


        });




        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }


        });


    }

    private Boolean validateUsername() {
        String username = uNameLogin.getText().toString().toUpperCase().trim();

        if (username.isEmpty()) {
            uNameLogin.setError("Polje ne sme biti prazno!");
            return false;

        } else {
            uNameLogin.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = iPassword.getText().toString().trim();

        if (password.isEmpty()) {
            iPassword.setError("Polje ne sme biti prazno!");
            return false;


        } else {
            iPassword.setError(null);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {

        final String userEnteredUsername = uNameLogin.getText().toString().toUpperCase().trim();
        final String userEnteredPassword = iPassword.getText().toString().toUpperCase().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    uNameLogin.setError(null);

                    String passwordDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    String usernameDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);

                    if (passwordDB.equals(userEnteredPassword)) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);

                    }



                    if (!passwordDB.equals(userEnteredPassword)) {
                        iPassword.setError("Pogresan password");

                    }
                }
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        }


    }








