package com.example.rentbuysell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rentbuysell.Notification.Token;
import com.example.rentbuysell.adapter.userchat_Adapter;
import com.example.rentbuysell.model.chat_users;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String ch1,ch2;

    private userchat_Adapter cadapter;
    private ArrayList<chat_users> muser;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(Chat.this,Drawer.class);
                startActivity(i);
            }
        });

        muser= new ArrayList<>();
        setUpRecyclerview();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String uid = firebaseUser.getUid();
            Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
            DocumentReference ref = db.collection("users").document(uid);
            ref.get().addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                Toast.makeText(getApplicationContext(), "Task is successfull", Toast.LENGTH_SHORT).show();

                                if (documentSnapshot != null) {
                                    ch1 = documentSnapshot.getString("Choice1");
                                    ch2 =  documentSnapshot.getString("Choice2");
                                    updToken(FirebaseInstanceId.getInstance().getToken());


                                    //Toast.makeText(Chat.this, ch1+ch2 + "insidesnap", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(getApplicationContext(), "Document snapshot null", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(getApplicationContext(), "Task is unsuccessful because" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }



    }
    Intent i=getIntent();
    private DocumentReference docref;
    private void setUpRecyclerview() {

        RecyclerView recyclerView=findViewById(R.id.user_chat_recyclerview);
        Query query=db.collection("users").document(mAuth.getUid()).collection("Chats").orderBy("Servertime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<chat_users> options;
        options=new FirestoreRecyclerOptions.Builder<chat_users>().setQuery(query, chat_users.class).build();
        cadapter=new userchat_Adapter(options,this,true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cadapter);
    }



    @Override
    protected void onStart() {
        super.onStart();
        cadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cadapter.stopListening();
    }


    private void updToken(String token) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Token");
            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("products");
            Token token1 = new Token(token);
            reference.child(mAuth.getUid()).setValue(token1);
           // Toast.makeText(this, ch1+ch2+"outside", Toast.LENGTH_SHORT).show();
            reference1.child(ch2).child(mAuth.getUid()).setValue(token1);
            reference1.child(ch1).child(mAuth.getUid()).setValue(token1);
    }





    @Override
    public void onBackPressed() {
        Intent i=new Intent(Chat.this,Drawer.class);
        startActivity(i);
    }

}

