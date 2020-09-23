package com.example.myapplication;
import androidx.annotation.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.model.ArticleModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView articleList;
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("articles");
        mDatabase.keepSynced(true);




        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        articleList = (RecyclerView)findViewById(R.id.articleList);
        articleList.setHasFixedSize(true);
        articleList.setLayoutManager(new LinearLayoutManager(this));






        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    protected void onStart(){
     super.onStart();
     Query firebaseQuery = mDatabase.orderByChild("name");
        FirebaseRecyclerAdapter<ArticleModel,ArticleViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ArticleModel, ArticleViewHolder>
                (ArticleModel.class,R.layout.news_row,ArticleViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(ArticleViewHolder articleViewHolder, ArticleModel articleModel, int i) {
                articleViewHolder.setName(articleModel.getName());
                articleViewHolder.setDesc(articleModel.getDescription());
                articleViewHolder.setPrice(articleModel.getPrice());
                articleViewHolder.setImage(getApplicationContext(),articleModel.getImage_path());
            }
        };

        articleList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ArticleViewHolder extends  RecyclerView.ViewHolder{
      View mView;
      public ArticleViewHolder(View itemView){
          super(itemView);
          mView=itemView;
      }
      public void setName(String name){
          TextView post_title = (TextView)mView.findViewById(R.id.post_title);
          post_title.setText(name);
      }
        public void setDesc(String description){
            TextView post_description = (TextView)mView.findViewById(R.id.post_description);
            post_description.setText(description);
        }
        public void setPrice(String price){
            TextView post_price = (TextView)mView.findViewById(R.id.post_price);
            post_price.setText(price);
        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout: {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(getApplicationContext(),"You are loged out " , Toast.LENGTH_LONG).show();
                finish();

            }break;
            case R.id.addNews: {
                startActivity(new Intent(getApplicationContext(),addNewsActivity.class));
            }break;
            case R.id.home: {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }break;

        }
        return false;
    }


}