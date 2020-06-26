package com.example.rentbuysell;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rentbuysell.Notification.Data;
import com.example.rentbuysell.Notification.Data2;
import com.example.rentbuysell.Notification.Sender;
import com.example.rentbuysell.Notification.Sender2;
import com.example.rentbuysell.Notification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sell extends AppCompatActivity {

    Button Choose, Upload;
    ProgressDialog pd1;
    ImageView prodimg;
    TextView txt_path;
    EditText text_name, text_description, text_price, txt_number;
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRICE = "price";
    private static final String KEY_URL = "imageUrl";
    private static final String KEY_MODE = "mode";
    private static final String KEY_CONTACT = "mobileNo";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_UID = "uid";
    private static final String KEY_PARENT_ID = "parentid";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String uid = mAuth.getCurrentUser().getUid();
    private boolean notify=false;

    Spinner category;
    public String s = "null", t = "null1";


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private final int PICK_IMAGE_REQUEST = 71;
    private RequestQueue requestQueue;
    private Uri filePath;
    ImageView imageView, back;
    TextView welcome;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        Choose = (Button) findViewById(R.id.btn_choose);
        Upload = (Button) findViewById(R.id.btn_upload);
        text_name = (EditText) findViewById(R.id.edt_name);
        text_description = (EditText) findViewById(R.id.edt_description);
        text_price = (EditText) findViewById(R.id.edt_price);
        category = (Spinner) findViewById(R.id.category);
        txt_number = (EditText) findViewById(R.id.edt_number);
        prodimg = (ImageView) findViewById(R.id.regitemPhoto);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pd1 = new ProgressDialog(this);
//        txt_path=(TextView) findViewById(R.id.filepath);
        welcome = (TextView) findViewById(R.id.welcome);
        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        String url_string;
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(Sell.this, Drawer.class);
                startActivity(i);
            }
        });


        Upload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pd1.setMessage("Uploading...");
                        pd1.show();
                        pd1.setCancelable(false);
                        notify=true;
                        t = UploadButtonClicked();


                    }
                }
        );
        Choose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseImage();
                    }
                }
        );


    }

    public void onBackPressed() {
        Intent i = new Intent(Sell.this, Drawer.class);
        startActivity(i);
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            prodimg.setImageURI(filePath);
            // txt_path.setText(filePath.toString());

        }
    }


    //UUID.randomUUID().toString()

    public String UploadButtonClicked() {       // on clicking the upload button uploads the image to storage and gets the image url in string format

        String name = text_name.getText().toString();
        String description = text_description.getText().toString();
        String price = text_price.getText().toString();
        String cat = category.getSelectedItem().toString();
        String number = txt_number.getText().toString();


        if (filePath != null && (!(TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(number) || TextUtils.isEmpty(price) || TextUtils.isEmpty(cat)))) {

            final StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());
            Log.d("prince", ref.toString());
            ref.putFile(filePath)

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Toast.makeText(Sell.this, "Failed " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            pd1.dismiss();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    //Toast.makeText(Sell.this, "Uploaded", Toast.LENGTH_SHORT).show();

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            s = uri.toString();
                            Toast.makeText(Sell.this, s, Toast.LENGTH_SHORT).show();
                            Uploadtext(s);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Sell.this, "Error is" + exception.toString(), Toast.LENGTH_SHORT).show();
                            pd1.dismiss();
                            Log.d("pra", exception.toString());

                        }
                    });


                }
            });
        } else {
            Toast.makeText(this, "Image not selected or any field left empty!!!!!!!!!", Toast.LENGTH_LONG).show();
        }
        Log.d("Str", s);
        return s;
    }


    public void Uploadtext(final String s1) {    // using the image url stores everything in the database

        String name = text_name.getText().toString();
        String description = text_description.getText().toString();
        String price = text_price.getText().toString();
        final String cat = category.getSelectedItem().toString();
        String number = txt_number.getText().toString();


        final Map<String, Object> data = new HashMap<>();
        data.put(KEY_NAME, name);
        data.put(KEY_DESCRIPTION, description);
        data.put(KEY_PRICE, price);
        data.put(KEY_URL, s1);
        data.put(KEY_MODE, "ON SALE");
        data.put(KEY_CONTACT, number);
        data.put(KEY_CATEGORY, cat);
        data.put(KEY_UID, uid);
        data.put("public_feed", "tr");
        db.collection("productid").document("CsRaWRYVoTQF0mNR1fv6").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                final String n = document.getString("productid");   //EVERY PRODUCT HAS A UNIQUE PRODUCT ID, the current product ID being
                                // the total number of products
                                int m = Integer.valueOf(n);
                                data.put("myid", String.valueOf(m + 1));
                                data.put("myidint", m + 1);
                                DocumentReference ref = db.collection(cat).document(String.valueOf(m + 1)); // stores the product in its CATEGORY with document name as product id
                                ref.set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Sell.this, "Successful in uploading to database", Toast.LENGTH_LONG).show();
                                                pd1.dismiss();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Sell.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                pd1.dismiss();

                                            }
                                        });
                                String id = ref.getId();
                                putuserdtata(s1, id); // updating the id of the document in users  product data,


                            } else {
                                //Toast.makeText(Sell.this, "Getted both choice", Toast.LENGTH_SHORT).show();
                            }
                        } else {


                        }
                    }
                });


    }

    public void putuserdtata(String s1, String id) {
        final String name = text_name.getText().toString();
        final String description = text_description.getText().toString();
        final String price = text_price.getText().toString();
        final String cat = category.getSelectedItem().toString();
        final String s11 = s1;
        final String pd_id = id;
        final String number = txt_number.getText().toString();
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_NAME, name);
        data.put(KEY_DESCRIPTION, description);
        data.put(KEY_PRICE, price);
        data.put(KEY_URL, s1);
        data.put(KEY_MODE, "ON SALE");
        data.put(KEY_CONTACT, number);
        data.put(KEY_CATEGORY, cat);
        data.put(KEY_UID, id);
        data.put("public_feed", "tr");
        Map<String, Object> upid = new HashMap<>();
        upid.put("productid", id);
        db.collection("productid").document("CsRaWRYVoTQF0mNR1fv6").set(upid); // updating the total cnt in database
        DocumentReference refuser = db.collection("users").document(uid).collection("Product").document();
        data.put(KEY_PARENT_ID, refuser.getId());
        refuser.set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Sell.this,"Successful in uplaoding the prod to user",Toast.LENGTH_LONG).show();

                        //get data for notification
                        getdatas(name,pd_id,description,price,number,s11,category.getSelectedItem().toString(),"ON SALE");


                        text_name.setText("");
                        text_description.setText("");
                        text_price.setText("");
                        txt_number.setText("");
//                        txt_path.setText("");
                        prodimg.setImageResource(R.drawable.splashscreen);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Sell.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



    }


//    public void addinrbase(String name,int price, int id)
//    {
//        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference(category.getSelectedItem().toString());
//        Map<String, Object> data = new HashMap<>();
//        data.put(KEY_NAME, name);
//        data.put(KEY_PRICE, price);
//        data.put(KEY_UID, id); //product id
//        data.put("SellersId",uid);
//
//        ref1.child(String.valueOf(id)).setValue(data)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Toast.makeText(messageInterface.this, "Message Written Successfully22222222222222", Toast.LENGTH_SHORT).show();
//
//                        sendNotific();
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Sell.this, "Message Failed", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//
//    }


    private void getdatas(final String pd_nm, final String pd_id, final String pd_des, final String pd_pr, final String my_mb, final String pd_imgrl, final String cg, final String md) {
        db.collection("users").document(mAuth.getUid()).
                get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Toast.makeText(Sell.this, "Task is successfull", Toast.LENGTH_SHORT).show();

                            if (documentSnapshot != null) {
                                final String myname=documentSnapshot.getString("Name");
                                String my_imrl = documentSnapshot.getString("ImageUrl");
                                if(notify)
                                {
                                    sendNots(myname,my_imrl,pd_nm,pd_id,pd_des,pd_pr,my_mb,pd_imgrl,cg,md); // calling function here
                                }
                                notify=false;

                            } else {
                                Toast.makeText(Sell.this, "Document snapshot null", Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            Toast.makeText(Sell.this, "Task is unsuccessful because"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendNots(final String myname,final String user_img,final String pd_nm, final String pd_id, final String pd_des, final String pd_pr, final String my_mb, final String pd_imgrl, final String categ, final String md) {
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("products").child(categ);
        //Query query=tokens;
        Toast.makeText(this, categ, Toast.LENGTH_SHORT).show();
        tokens.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    Toast.makeText(Sell.this, "inside", Toast.LENGTH_SHORT).show();
                    Token token = snapshot.getValue(Token.class); // getting the token corresponding to the id of the receiver

                    Data data  =  new Data(mAuth.getUid(),R.mipmap.ic_launcher,categ+":A new product is added by"+myname,"A new product is added","Message for all", pd_nm, pd_id, pd_des, pd_pr, my_mb, pd_imgrl, categ, md);

//                    Toast.makeText(Sell.this,mAuth.getUid()+categ+":A new product is added by"+myname, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Sell.this,"A new product is added"+"Message for all", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(Sell.this,pd_nm+ pd_id+ pd_des, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Sell.this,pd_pr+ my_mb+pd_imgrl+categ+ md , Toast.LENGTH_SHORT).show();

               //     Toast.makeText(Sell.this,mAuth.getUid()+categ+":A new product is added by"+myname+"A new product is added"+"Message for all"+pd_nm+ pd_id+ pd_des+ pd_pr+ my_mb+pd_imgrl+categ+ md , Toast.LENGTH_SHORT).show();








                    Sender sender= new Sender(data,token.getToken());
//                    apIservices.sendNotification(sender)
//                            .enqueue(new Callback<Myresponse>() {
//                                @Override
//                                public void onResponse(Call<Myresponse> call, Response<Myresponse> response) {
//                                    if(response.code()==200)
//                                    {
//                                        if((response.body().success)!=1)
//                                            Toast.makeText(messageInterface.this, "Notification Failed", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Myresponse> call, Throwable t) {
//
//                                }
//                            });
                    try {
                        JSONObject senderJsonObj=new JSONObject(new Gson().toJson(sender));
                        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", senderJsonObj,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("Json_Response","Response"+response.toString());
                                        Toast.makeText(Sell.this, "Response"+response.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Json_Response","Response"+error.toString());
                                // Toast.makeText(messageInterface.this, "Response"+error.toString(), Toast.LENGTH_SHORT).show();



                            }
                        }){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String ,String > headers=new HashMap<>();
                                headers.put("Content-Type","application/json");
                                headers.put("Authorization","key=AAAAaNwEVdo:APA91bGJAEDteYgvTowqwqLqz_VnxA6ILBPNkvUiq9mt301Jb4cCH_voKpn-A9tGCvZZyw6BYwCR91Y2RnXGDBX6aWO1Gb865R_2stVWPHh75X0VONOmngKPHeYscC2tls-Y8WtyITql");
                                return headers;
                            }
                        };
                        requestQueue.add(jsonObjectRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}