package com.example.rentbuysell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class User_Description extends AppCompatActivity {
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    Button Deleteproduct;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Switch aSwitch;
    TextView warning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__description);
        Deleteproduct=findViewById(R.id.Delete);
        warning=findViewById(R.id.warning_text);
        aSwitch=findViewById(R.id.switch1);
        aSwitch.setTextOff("OFF");
        aSwitch.setTextOn("ON");
        getdata();
        Intent i = getIntent();
        final String uid=i.getStringExtra("UID");
        final String cat=i.getStringExtra("CATEGORY");
        String parentid=getIntent().getStringExtra("PARENTID");
        Boolean switchState = aSwitch.isChecked();
        if(switchState==true)
        {
          deleteproductpublicfeed(uid,cat);
          warning.setVisibility(View.VISIBLE);
          db.collection("users").document(mAuth.getUid()).collection("Product").document(parentid).get();
        }
        else{

        }


        Toast.makeText(this,cat, Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this,uid, Toast.LENGTH_SHORT).show();
        Deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deleteproduct.setVisibility(View.INVISIBLE);
                deleteproductpublic(cat,uid);

            }
        });




    }
    public void deleteproductpublicfeed(String cat,String uid)
    {
        db.collection(cat).document(uid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(User_Description.this, "Succesfully removed data from public feed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(User_Description.this, "Not able to delete data from public feed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void deleteproductpublic(String cat,String uid)
    {
        db.collection(cat).document(uid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(User_Description.this, "Succesfully deleted data from public feed", Toast.LENGTH_SHORT).show();
                        Deleteuserproduct();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(User_Description.this, "Not able to delete data from public feed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void Deleteuserproduct(){
        String parentid=getIntent().getStringExtra("PARENTID");
        String useruid = mAuth.getCurrentUser().getUid();
        db.collection("users").document(useruid).collection("Product").document(parentid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(User_Description.this, "Successfully deleted Data", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(User_Description.this,MyData.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(User_Description.this, "Not able to delete data from public feed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getdata()
    {
        if(getIntent().hasExtra("Name")&&getIntent().hasExtra("Price")&&getIntent().hasExtra("Shortdesc")&&getIntent().hasExtra("ImageURL")&&getIntent().hasExtra("Mobile_no")&&getIntent().hasExtra("CATEGORY"))
      {
        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String desc = i.getStringExtra("Shortdesc");
        String price = i.getStringExtra("Price");
        String imageurl=i.getStringExtra("ImageURL");
        addData(name,desc,price,imageurl);
      }

    }
    private void addData(String name,String desc,String price,String imageurl){
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
