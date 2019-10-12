package com.example.rentbuysell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class User_Description extends AppCompatActivity {

    private static final int Request_Call=2;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__description);
        getdata();


    }
    private void getdata()
    {if(getIntent().hasExtra("Name")&&getIntent().hasExtra("Price")&&getIntent().hasExtra("Shortdesc")&&getIntent().hasExtra("ImageURL")&&getIntent().hasExtra("Mobile_no"))
    {
        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String desc = i.getStringExtra("Shortdesc");
        String price = i.getStringExtra("Price");
        String imageurl=i.getStringExtra("ImageURL");
        String uid=i.getStringExtra("UID");
        addData(name,desc,price,imageurl,uid);
    }

    }
    private void addData(String name,String desc,String price,String imageurl,String uid){
        TextView Name,Desc,Price;
        ImageView pic;
        Name=findViewById(R.id.text_name);
        Desc=findViewById(R.id.text_desc);
        Price=findViewById(R.id.Price);
        pic=findViewById(R.id.image_pic);
        Name.setText(name);
        Desc.setText(desc);
        Price.setText(price);
        Picasso.get().load(imageurl).fit().into(pic);
    }
}
