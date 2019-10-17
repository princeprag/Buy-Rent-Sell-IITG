package com.example.rentbuysell;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentuser = mAuth.getCurrentUser();
    private GoogleSignInClient mGoogleSignInClient;
    ImageView pro_pic;
    TextView name, Email;
    public  users myuser ;
    static String choice1,choice2;
    productAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar = findViewById(R.id.toolbar);
        putnewsfeed();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        //put_userdata_header(acct);
        put_userdata_header(acct);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

        }




    }

    private CollectionReference userref = db.collection("users");
/////////////////


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new com.example.rentbuysell.HomeFragment()).commit();

                break;
            case R.id.nav_buy:
              Intent s=new Intent(Drawer.this,Buy.class);
              startActivity(s);
              break;
            case R.id.nav_sell:
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GalleryFragment()).commit();*/
                Intent s1 = new Intent(Drawer.this, Sell.class);
                startActivity(s1);
                break;
            case R.id.nav_rent:
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SlideshowFragment()).commit();*/
                Intent s2 = new Intent(Drawer.this, Rent.class);
                startActivity(s2);
                break;
            case R.id.nav_swap:
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ToolsFragment()).commit();*/
                Intent s3 = new Intent(Drawer.this, Swap.class);
                startActivity(s3);
                break;

            case R.id.nav_aboutUs:
                /*Toast.makeText(this, mAuth.getCurrentUser().getUid().toString(), Toast.LENGTH_SHORT).show();*/

                break;


            case R.id.nav_giveAway:
                Intent s5 = new Intent(Drawer.this, GiveAway.class);
                startActivity(s5);
                break;
            case R.id.nav_myinfo:
                Intent s6=new Intent(Drawer.this,MyData.class);
                startActivity(s6);
                break;
            case R.id.signOut:
                signout();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    public void putnewsfeed() {
        DocumentReference docref = db.collection("users").document(mAuth.getCurrentUser().getUid());
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        choice1 = document.getString("Choice1");
                        choice2 = document.getString("Choice2");
                        putinrecyclerview(choice1,choice2);

                    } else {
                        Toast.makeText(Drawer.this, "Getted both choice", Toast.LENGTH_SHORT).show();
                    }
                } else {


                }
            }
        });
        Toast.makeText(Drawer.this, "Choice1" + choice1, Toast.LENGTH_SHORT).show();
    }
    public void putinrecyclerview(String ch1,String ch2)
    {   db = FirebaseFirestore.getInstance();
        Toast.makeText(Drawer.this, "Choice1" + ch1, Toast.LENGTH_SHORT).show();

        CollectionReference productref = db.collection(ch1);
        Query query = productref;
        FirestoreRecyclerOptions<product_part> options = new FirestoreRecyclerOptions.Builder<product_part>().setQuery(query, product_part.class).build();
        adapter = new productAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.drawer_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        productref=db.collection(ch2);
        query=productref;
        FirestoreRecyclerOptions<product_part> options1 = new FirestoreRecyclerOptions.Builder<product_part>().setQuery(query, product_part.class).build();
        adapter = new productAdapter(options1, this);
        //RecyclerView recyclerView = findViewById(R.id.drawer_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



    @Override
    protected void onStart() {
        super.onStart();
        get_put_userDetails();

    }
//
//
//     @Override
//     protected void onStop() {
//         super.onStop();
//       //  adapter.stopListening();
//     }
    private void signout() {
       /* mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(Drawer.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Drawer.this, "SignOut Succesfully", Toast.LENGTH_LONG).show();

                    }
                });*/
        myuser.logout();
        Intent it= new Intent(Drawer.this,MainActivity.class);
        startActivity(it);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

    }

    private void put_userdata_header(GoogleSignInAccount acct) {
        navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        pro_pic = headerview.findViewById(R.id.pic);
        name = headerview.findViewById(R.id.name_user);
        Email = headerview.findViewById(R.id.email_user);

        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            // String Url=personPhoto.toString();
            name.setText(personName);
            Email.setText(personEmail);
            // Picasso.get().load(String.valueOf(personPhoto)).fit().into(pro_pic);
            Glide.with(this).load(String.valueOf(personPhoto)).into(pro_pic);
        }
//       name.setText(currentuser.getDisplayName());
//       Email.setText(currentuser.getEmail());
//       Glide.with(this).load(currentuser.getPhotoUrl()).into(pro_pic);

    }


    private void get_put_userDetails(){
        final String uid= mAuth.getCurrentUser().getUid();
        DocumentReference docref= db.collection("users").document(uid);
        docref.get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            if (documentSnapshot != null) {

                                navigationView = findViewById(R.id.nav_view);
                                View headerview = navigationView.getHeaderView(0);
                                pro_pic = headerview.findViewById(R.id.pic);
                                name = headerview.findViewById(R.id.name_user);
                                Email = headerview.findViewById(R.id.email_user);
                                name.setText(documentSnapshot.getString("Name"));
                                Email.setText(documentSnapshot.getString("Email"));
                                String url_string = documentSnapshot.getString("Image Url");
                                Glide.with(Drawer.this).load(url_string).into(pro_pic);
                            } else {
                                Toast.makeText(Drawer.this, "Document snapshot null", Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            Toast.makeText(Drawer.this, "Task is unsuccessfull because"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }







}













