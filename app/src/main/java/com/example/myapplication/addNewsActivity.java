package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.ArticleModel;
import com.example.myapplication.model.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;

public class addNewsActivity extends AppCompatActivity {
    private TextView textView;
    private EditText aName,aPrice,aDesc,aImagePath;
    private Button addArticle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);



        aName = (EditText) findViewById(R.id.articleName);
        aPrice = (EditText) findViewById(R.id.articlePrice);
        aDesc = (EditText) findViewById(R.id.descriptionArticle);
        aImagePath = (EditText) findViewById(R.id.imagePath);
        addArticle = (Button) findViewById(R.id.addArticleButton);
        textView = (TextView) findViewById(R.id.backTo);




        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addNewsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        addArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = firebaseDatabase.getReference("articles");


                String ArticleName = aName.getText().toString();
                String ArticlePrice = aPrice.getText().toString();
                String ArticleDesc = aDesc.getText().toString();
                String aImage = aImagePath.getText().toString();

                if (ArticleName.isEmpty()) {
                    aName.setError("Unesite ime artikla!");

                } else if (ArticlePrice.isEmpty()) {
                    aPrice.setError("Polje ne sme biti prazno");

                } else if (ArticleDesc.isEmpty()) {
                    aDesc.setError("Unesite kratak opis");
                } else if (aImage.isEmpty()) {
                    aImagePath.setError("Unesite link fotografije");
                } else {


                    ArticleModel articles = new ArticleModel(ArticleName, ArticlePrice, ArticleDesc, aImage);
                    reference.child(ArticleName).setValue(articles);
                    Toast.makeText(getApplicationContext(), "Uspesno ste dodali artikl", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(addNewsActivity.this, HomeActivity.class);
                    startActivity(intent);


                }
            }


            });
    }



}