package com.example.rentbuysell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Rent extends AppCompatActivity {


    Button Choose,Upload,Submit;
    EditText text_name,text_description,text_price,txt_number,txt_period;
    TextView txt_path;
    private static final String KEY_NAME="NAME";
    private static final String KEY_DESCRIPTION="DESCRIPTION";
    private static final String KEY_PRICE="PRICE";
    private static final String KEY_URL="IMAGEURL";
    private static final String KEY_MODE="MODE";
    private static final String KEY_CONTACT="MOBILENO";
    private static final String KEY_PERIOD="DURATION_OF_RENT";
    private static final String KEY_UID="UID";
    private static final String KEY_CATEGORY="CATEGORY";
    private static final String KEY_PARENT_ID="PARENTID";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private  String uid = mAuth.getCurrentUser().getUid();

    Spinner category;
    public String s="null",t="null1";



    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef= storage.getReference();
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    ImageView imageView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // ProgressDialog progressDialog=new ProgressDialog(this);






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);




        Choose=(Button)findViewById(R.id.btn_choose);
        Upload=(Button)findViewById(R.id.btn_upload);
        //  Submit=(Button)findViewById(R.id.btn_submit);
        text_name=(EditText)findViewById(R.id.edt_name);
        text_description=(EditText)findViewById(R.id.edt_description);
        text_price=(EditText)findViewById(R.id.edt_price);
        category=(Spinner)findViewById(R.id.category);
        txt_number=(EditText)findViewById(R.id.edt_number);
        txt_period=(EditText)findViewById(R.id.edt_rent);
        txt_path=(TextView) findViewById(R.id.filepath);




        String url_string;


        Upload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*progressDialog.setMessage("Uploading.....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();*/
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

      /*  Submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uploadtext(t);
                    }
                }
        );*/


    }
    public void onBackPressed() {
        Intent i=new Intent(Rent.this,Drawer.class);
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
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            txt_path.setText(filePath.toString());
            Toast.makeText(Rent.this, "Image successfully Choosen!!!!", Toast.LENGTH_SHORT).show();
           /* try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }*/
        }else {
            Toast.makeText(Rent.this, "Error in Choosing Image!!!!", Toast.LENGTH_SHORT).show();
        }
    }



    public String UploadButtonClicked(){


        String name = text_name.getText().toString();
        String description = text_description.getText().toString();
        String price= text_price.getText().toString();
        String cat= category.getSelectedItem().toString();
        String number=txt_number.getText().toString();
        String period=txt_period.getText().toString();



        if (filePath!= null  &&  (!(TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(period) || TextUtils.isEmpty(price) || TextUtils.isEmpty(number)))) {

            final StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());
            Log.d("prince",ref.toString());
            ref.putFile(filePath)

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            Toast.makeText(Rent.this, "Failed " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            Toast.makeText(Rent.this, "Uploaded", Toast.LENGTH_SHORT).show();



                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    s= uri.toString();
                                    Toast.makeText(Rent.this, s, Toast.LENGTH_SHORT).show();
                                    Uploadtext(s);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(Rent.this, "Error is"+exception.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("pra",exception.toString());

                                }
                            });


                        }
                    });










        }else{
            Toast.makeText(this,"Image not selected or Field left Empty!!!!!!!!!",Toast.LENGTH_LONG).show();
        }
        Log.d("Str",s);
        return s;
    }


    public void Uploadtext(final String s1){


        String name = text_name.getText().toString();
        String description = text_description.getText().toString();
        String price= text_price.getText().toString();
        final String cat= category.getSelectedItem().toString();
        String number=txt_number.getText().toString();
        String period=txt_period.getText().toString();


        //  if (!(TextUtils.isEmpty(name) && TextUtils.isEmpty(description) && TextUtils.isEmpty(period) && TextUtils.isEmpty(price) && TextUtils.isEmpty(number))){


        final Map<String, Object> data = new HashMap<>();
        data.put(KEY_CATEGORY,cat);
        data.put(KEY_NAME,name);
        data.put(KEY_DESCRIPTION,description);
        data.put(KEY_PRICE,price);
        data.put(KEY_URL,s1);
        data.put(KEY_MODE,"ON RENT");
        data.put(KEY_CONTACT,number);
        data.put(KEY_PERIOD,period);
        data.put(KEY_UID,uid);
        db.collection("productid").document("CsRaWRYVoTQF0mNR1fv6").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                final String n = document.getString("productid");
                                int m=Integer.valueOf(n);
                                data.put("Myid",String.valueOf(m+1));
                                data.put("Myidint",m+1);
                                DocumentReference ref= db.collection(cat).document(String.valueOf(m+1));
                                ref.set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Rent.this,"Successful",Toast.LENGTH_LONG).show();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Rent.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                String id=ref.getId();
                                putuserdata(s1,id);


                            } else {
                                Toast.makeText(Rent.this, "Getted both choice", Toast.LENGTH_SHORT).show();
                            }
                        } else {


                        }
                    }
                });
       /* DocumentReference dref= db.collection(cat).document();
        dref.set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Rent.this,"Successful",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Rent.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        String id= dref.getId();
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        putuserdata(s1,id);*/
    }
    public void putuserdata(String s1,String id){
        String name = text_name.getText().toString();
        String description = text_description.getText().toString();
        String price= text_price.getText().toString();
        String cat= category.getSelectedItem().toString();
        String number=txt_number.getText().toString();
        String period=txt_period.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put(KEY_CATEGORY,cat);
        data.put(KEY_NAME,name);
        data.put(KEY_DESCRIPTION,description);
        data.put(KEY_PRICE,price);
        data.put(KEY_URL,s1);
        data.put(KEY_MODE,"ON RENT");
        data.put(KEY_CONTACT,number);
        data.put(KEY_PERIOD,period);
        data.put(KEY_UID,id);
        Map<String,Object> upid=new HashMap<>();
        upid.put("productid",id);
        db.collection("productid").document("CsRaWRYVoTQF0mNR1fv6").set(upid);
        final DocumentReference userdref= db.collection("users").document(uid).collection("Product").document();
        data.put(KEY_PARENT_ID,userdref.getId());
        Toast.makeText(Rent.this,userdref.getId()+" Parent id",Toast.LENGTH_LONG).show();
        userdref.set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Toast.makeText(Rent.this,userdref.getId(),Toast.LENGTH_LONG).show();

                        text_name.setText("");
                        text_description.setText("");
                        text_price.setText("");
                        txt_number.setText("");
                        txt_period.setText("");
                        txt_path.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Rent.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }




}








