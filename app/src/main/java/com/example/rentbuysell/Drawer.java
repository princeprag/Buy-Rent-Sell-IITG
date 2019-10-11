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
    private ViewPager mViewPager;
    private ViewPagerAdapter mSectionsPagerAdapter;

    private productAdapter adapter;
    // Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        put_userdata(acct);


        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mSectionsPagerAdapter.addFragments(new AppliancesFragment(),"A");
        mSectionsPagerAdapter.addFragments(new BooksFragment(),"b");
        mSectionsPagerAdapter.addFragments(new SportsFragment(),"S");
        mSectionsPagerAdapter.addFragments(new FurnitureFragment(),"Furni");
        mSectionsPagerAdapter.addFragments(new FashionFragment(),"Fashion");
        mSectionsPagerAdapter.addFragments(new VehiclesFragment(),"Vehicles");




        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);


        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setViewPager(mViewPager);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //setContentView(R.layout.fragment_home);
//        setUpRecyclerview();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }*/


        if (savedInstanceState == null) {

        }


    }

    private CollectionReference userref = db.collection("users");

    private void put_profiledata(String name, String Email, ImageView pro_pic) {


    }



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new com.example.rentbuysell.HomeFragment()).commit();

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
                Intent s4 = new Intent(Drawer.this, sample.class);
                startActivity(s4);
                break;


            case R.id.nav_giveAway:
                Intent s5 = new Intent(Drawer.this, GiveAway.class);
                startActivity(s5);
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

    /*Recycler View showing Data From the data Base*/
   // private CollectionReference productref = db.collection("Product");

    /* private void setUpRecyclerview(){
         Query query=productref;
         FirestoreRecyclerOptions<product_part> options=new FirestoreRecyclerOptions.Builder<product_part>().setQuery(query,product_part.class).build();
         adapter=new productAdapter(options,this);
         RecyclerView recyclerView=findViewById(R.id.recyclerView);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(adapter);
*/

     @Override
     protected void onStart() {
         super.onStart();
         //adapter.startListening();
     }


     @Override
     protected void onStop() {
         super.onStop();
       //  adapter.stopListening();
     }
    private void signout() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(Drawer.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Drawer.this, "SignOut Succesfully", Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void put_userdata(GoogleSignInAccount acct) {
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



    }













