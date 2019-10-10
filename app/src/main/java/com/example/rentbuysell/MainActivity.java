package com.example.rentbuysell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/*public class MainActivity extends AppCompatActivity {
  private  Button btn;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    Button Gmail;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gmail=(Button)findViewById(R.id.gmail_btn);
        Gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.gmail_btn:
                        signIn();
                        break;
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already signed in
        //we will close this activity
        //and take the user to profile activity
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, Drawer.class));
        }
    }
    LogIn With Google
    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        //Toast.makeText(this, "67", Toast.LENGTH_SHORT).show();
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Line 69", Toast.LENGTH_SHORT).show();

            if(account!=null){

               Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
               firebaseAuthWithGoogle(account);
                putgmail_data(account);

              }

            else{
                Toast.makeText(this,"Login authantication failed",Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        AuthCredential credential= GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            Toast.makeText(MainActivity.this, "SignIn suceesfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user=mAuth.getCurrentUser();


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void putgmail_data(GoogleSignInAccount acct){
        if (acct != null) {
            Toast.makeText(this, "Put Data", Toast.LENGTH_SHORT).show();
            String Name = acct.getDisplayName();
            String Email = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Map<String, Object> data = new HashMap<>();
            data.put("Name",Name);
            data.put("Email",Email);
            data.put("ImageUrl",personPhoto);
            data.put("Id",personId);
            db= FirebaseFirestore.getInstance();
            Toast.makeText(this, "Put Data new", Toast.LENGTH_SHORT).show();
            db.collection("user").document(mAuth.getUid()).set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Toast.makeText(signIn.this, "", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(MainActivity.this,Drawer.class);
                            startActivity(intent);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


        }}
}*/
public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    Button outlook;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseFirestore  db= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outlook=(Button)findViewById(R.id.gmail_btn);
        outlook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.gmail_btn:
                        signIn();
                        break;
                }
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    /* @Override
     protected void onStart() {
         super.onStart();
         if (mAuth.getCurrentUser() != null) {
             finish();
             startActivity(new Intent(this, Home.class));
         }
     }*/
    /*LogIn With Google*/
    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account!=null){
                Toast.makeText(this, "235", Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account);
                putdata(account);
                Intent intent= new Intent(MainActivity.this,Drawer.class);
                startActivity(intent);
                Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Login authantication failed",Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this,e.getMessage()+" line 246", Toast.LENGTH_SHORT).show();

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           // putdata(acct,user);
                            Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
    private void putdata(GoogleSignInAccount acct){
        if (acct != null) {
            String Name = acct.getDisplayName();
            String Email = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            String ImageUrl=personPhoto.toString();
            Map<String, Object> data = new HashMap<>();
            data.put("Name",Name);
            data.put("Email",Email);
            data.put("ImageUrl",ImageUrl);
            data.put("Id",personId);
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Toast.makeText(this, "Firestore returning Null", Toast.LENGTH_SHORT).show();
            }
            else {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                db.collection("users").document(uid).set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Successful Putted Data", Toast.LENGTH_LONG).show();


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }

        }}
}
