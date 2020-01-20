package com.example.rentbuysell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.rentbuysell.Notification.Data;
import com.example.rentbuysell.Notification.Myresponse;
import com.example.rentbuysell.Notification.Sender;
import com.example.rentbuysell.Notification.Token;
import com.example.rentbuysell.Notification.client;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class messageInterface extends AppCompatActivity {
    CircleImageView profile_pic;
    TextView username;
    EditText message;
    String senderId,receiverId;
    ImageView send,back;
    MessageAdapter adapter;
    List <getchats> mchatmessages;
    RecyclerView recyclerView;
    APIservices apIservices;
    boolean notify=false;


    //    private  DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference();
    FirebaseUser fuser;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_interface);
        Intent i=getIntent();
        receiverId=i.getStringExtra("UserId");


        recyclerView=findViewById(R.id.message_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        apIservices = client.getClient("https:fcm.googleapis.com/").create(APIservices.class);
        profile_pic=findViewById(R.id.iprofile_image);
        username=findViewById(R.id.iusername);
        message=findViewById(R.id.message_text);
        send=findViewById(R.id.send_btn);
        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(messageInterface.this,Chat.class);
                startActivity(i);
            }
        });
        String uid=i.getStringExtra("UserId");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify=true;
                sendmessage();
                message.setText("");
            }
        });

        //Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

         db.collection("users").document(mAuth.getUid()).collection("Chats").document(uid).
                 get().addOnCompleteListener(
                 new OnCompleteListener<DocumentSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                         if (task.isSuccessful()) {
                             DocumentSnapshot documentSnapshot = task.getResult();

                             if (documentSnapshot != null) {
                                 username.setText(documentSnapshot.getString("username"));
                                 String url_string = documentSnapshot.getString("imageUrl");
                                 Glide.with(messageInterface.this).load(url_string).into(profile_pic);
                                 readmessage(mAuth.getUid(),receiverId,url_string);
                             } else {
                                 Toast.makeText(messageInterface.this, "Document snapshot null", Toast.LENGTH_SHORT).show();
                             }
                         }else{

                             Toast.makeText(messageInterface.this, "Task is unsuccessfull because"+task.getException(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

    }
    public void sendmessage()
    {

        senderId=mAuth.getUid();
        final String message_txt=message.getText().toString();
        if(!message_txt.equals(""))
        {
            getnotidata(message_txt);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("chats").child(mAuth.getUid());
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("receiveR",receiverId);
        hashMap.put("message",message_txt);
        hashMap.put("sendeR",senderId);
        hashMap.put("time",ServerValue.TIMESTAMP);
        DatabaseReference messageId=ref.push();
        String pushid=messageId.getKey();
         ref.child(pushid).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                           // Toast.makeText(messageInterface.this, "Message Written Successfully11111111111111111", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(messageInterface.this, "Message Failed", Toast.LENGTH_SHORT).show();

                        }
                    });
            DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("chats").child(receiverId);
            HashMap<String,Object> hashMap1=new HashMap<>();
            hashMap1.put("receiveR",receiverId);
            hashMap1.put("message",message_txt);
            hashMap1.put("sendeR",senderId);
            hashMap1.put("time",ServerValue.TIMESTAMP);
            ref1.child(pushid).setValue(hashMap1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                           // Toast.makeText(messageInterface.this, "Message Written Successfully22222222222222", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(messageInterface.this, "Message Failed", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        else
            Toast.makeText(this, "You can't send empty Message", Toast.LENGTH_SHORT).show();


    }
    public void statuscheck(final String msg)
    {db.collection("users").document().collection("Chats").document(receiverId).
            get().addOnCompleteListener(
            new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        if (documentSnapshot != null) {
                            final String status=documentSnapshot.getString("status");
                            if(status.equals("Offline"))
                                getnotidata(msg);
                            else
                                Toast.makeText(messageInterface.this,status, Toast.LENGTH_SHORT).show();



                        } else {
                            Toast.makeText(messageInterface.this, "Document snapshot null", Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(messageInterface.this, "Task is unsuccessfull because"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void getnotidata(final String message_txt) {
        db.collection("users").document(mAuth.getUid()).
                get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            if (documentSnapshot != null) {
                                 final String myname=documentSnapshot.getString("Name");
                                 String imageurl = documentSnapshot.getString("ImageUrl");
                                 if(notify){
                                  //Toast.makeText(messageInterface.this, "204 notify true", Toast.LENGTH_SHORT).show();
                                 sendNotifications(myname,message_txt,imageurl,receiverId);}
                                 notify=false;

                            } else {
                                Toast.makeText(messageInterface.this, "Document snapshot null", Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            Toast.makeText(messageInterface.this, "Task is unsuccessfull because"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendNotifications(final String myname, final String message_txt, String imageurl, final String receiver) {
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Token");
        Query query=tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data=new Data(mAuth.getUid(),R.mipmap.ic_launcher,myname+": "+message_txt,"New Massage",receiver);
                    Sender sender=new Sender(data,token.getToken());
                    apIservices.sendNotification(sender)
                            .enqueue(new Callback<Myresponse>() {
                                @Override
                                public void onResponse(Call<Myresponse> call, Response<Myresponse> response) {
                                    if(response.code()==200)
                                    {
                                        if((response.body().success)!=1)
                                            Toast.makeText(messageInterface.this, "Notification Failed", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<Myresponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    void readmessage(final String myid,final String userid ,final String ImageUrl)
    { mchatmessages=new ArrayList<>();
     DatabaseReference ref=FirebaseDatabase.getInstance().getReference("chats").child(mAuth.getUid());
     ref.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             mchatmessages.clear();
             for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                 getchats mchats=snapshot.getValue(getchats.class);
                 if((mchats.getReceiveR().equals(myid) && mchats.getSendeR().equals(userid)) || (mchats.getReceiveR().equals(userid) && mchats.getSendeR().equals(myid)))
                 mchatmessages.add(mchats);
                 adapter=new MessageAdapter(messageInterface.this,mchatmessages,ImageUrl);
                 recyclerView.setAdapter(adapter);
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {
             Toast.makeText(messageInterface.this, "Not able to print messages", Toast.LENGTH_SHORT).show();

         }
     });


    }
    @Override
    public void onBackPressed() {
        finish();
        Intent i=new Intent(messageInterface.this,Chat.class);
        startActivity(i);
    }
}
