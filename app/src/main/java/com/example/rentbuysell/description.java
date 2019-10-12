package com.example.rentbuysell;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class description extends AppCompatActivity {
    Button callseller,messageseller;
    TextView reportAdmin;
    private static final int Request_Call=2;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        callseller=findViewById(R.id.call_seller);
        messageseller=findViewById(R.id.message_seller);
        reportAdmin=findViewById(R.id.ReportA);
        callseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mobile_No=getIntent().getStringExtra("Mobile_no");
                makephonecall(Mobile_No);
            }
        });
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
        String useruid=mAuth.getCurrentUser().getUid();



    }
    private void makephonecall(String mobile_No){
        if(mobile_No.trim().length()>0) {
            if(ContextCompat.checkSelfPermission(description.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(description.this,new String[]{Manifest.permission.CALL_PHONE},Request_Call);
            }
            else
            {
                String dial="tel:"+mobile_No;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }


        }
        else
            Toast.makeText(this,"Mobile Number is Not Valid",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==Request_Call)
        {    String Mobile_No=getIntent().getStringExtra("Mobile_no");

            if(grantResults.length >0 &&  grantResults[0]==PackageManager.PERMISSION_GRANTED)
                makephonecall(Mobile_No);
            else
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }

    }
}

