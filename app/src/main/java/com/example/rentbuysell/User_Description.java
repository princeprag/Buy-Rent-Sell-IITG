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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
        /*aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                deleteproductpublic(cat,uid);
                String parentid=getIntent().getStringExtra("PARENTID");
                String useruid = mAuth.getCurrentUser().getUid();
                db.collection("users").document(useruid).collection("Product").document(parentid).update("UID","");
                }
                else{
                    String parentid=getIntent().getStringExtra("PARENTID");
                    String useruid = mAuth.getCurrentUser().getUid();
                    db.collection("users").document(useruid).collection("Product").document(parentid).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(User_Description.this, "connected with firebase", Toast.LENGTH_SHORT).show();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                product_part temp=new product_part();
                                            String NAME=document.getString("NAME");
                                            String DESCRIPTION=document.getString("DESCRIPTION");
                                            String IMAGEURL=document.getString("IMAGEURL");
                                            String MODE=document.getString("MODE");
                                            String PRICE=document.getString("PRICE");
                                            String MOBILENO=document.getString("MOBILENO");
                                            String UID=document.getString("UID");
                                            int Myid=Integer.valueOf(document.getString("Myid"));
                                            String DURATION_OF_RENT=document.getString("DURATION_OF _RENT");
                                            String PARENTID=document.getString("PARENTID");
                                            String CATAGORY=document.getString("CATEGORY");
                                            product_part temp=new product_part(PARENTID,MODE,IMAGEURL,MOBILENO,CATAGORY, NAME, PRICE, DESCRIPTION,UID, DURATION_OF_RENT,Myid);
                                        }
                                    }
                                });

                             else {
                        Toast.makeText(User_Description.this, "Error getting documents: ", Toast.LENGTH_SHORT).show();
                    }

            });

                }

            }
        });*/



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
  /*  public void deleteproductpublicfeed(String cat,String uid)
    {
        db.collection(cat).document(uid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(User_Description.this, "Succesfully removed data from public feed", Toast.LENGTH_SHORT).show();
                        warning.setVisibility(View.VISIBLE);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(User_Description.this, "Not able to delete data from public feed", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
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
