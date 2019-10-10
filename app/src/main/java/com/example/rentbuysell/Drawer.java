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
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentuser=mAuth.getCurrentUser();
    private GoogleSignInClient mGoogleSignInClient;
    ImageView pro_pic;
    TextView name,Email;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
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
    private CollectionReference userref= db.collection("users");

    private void put_profiledata(String name,String Email,ImageView pro_pic)
    {




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
                Intent s1 = new Intent(Drawer.this,Sell.class);
                startActivity(s1);
                break;
            case R.id.nav_rent:
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SlideshowFragment()).commit();*/
                Intent s2 = new Intent(Drawer.this,Rent.class);
                startActivity(s2);
                break;
            case R.id.nav_swap:
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ToolsFragment()).commit();*/
                Intent s3 = new Intent(Drawer.this,Swap.class);
                startActivity(s3);
                break;

            case R.id.nav_aboutUs:
                Intent s4 = new Intent(Drawer.this,AboutUs.class);
                startActivity(s4);
                break;



            case R.id.nav_giveAway:
                Intent s5 = new Intent(Drawer.this,GiveAway.class);
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
    private CollectionReference productref= db.collection("Product");
   /* private void setUpRecyclerview(){
        Query query=productref;
        FirestoreRecyclerOptions<product_part> options=new FirestoreRecyclerOptions.Builder<product_part>().setQuery(query,product_part.class).build();
        adapter=new productAdapter(options,this);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/
    private void signout() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       Intent intent = new Intent(Drawer.this,MainActivity.class);
                       startActivity(intent);
                        finish();
                        Toast.makeText(Drawer.this, "SignOut Succesfully", Toast.LENGTH_LONG).show();

                    }
                });
    }
    private void put_userdata(GoogleSignInAccount acct)
    { navigationView = findViewById(R.id.nav_view);
      View headerview=navigationView.getHeaderView(0);
        pro_pic=headerview.findViewById(R.id.pic);
        name=headerview.findViewById(R.id.name_user);
        Email=headerview.findViewById(R.id.email_user);

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



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ELECTRONICS";
                case 1:
                    return "BOOKS";
                case 2:
                    return "Sports";
                case 3:
                    return "Furniture";
                case 4:
                    return "Fashion";

            }
            return null;
        }
    }








    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View xyz = null;
            View z=null;
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);


            TextView textView = (TextView) rootView.findViewById(R.id.section_label);



            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                xyz = inflater.inflate(R.layout.activity_electronics, container, false);



            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                xyz = inflater.inflate(R.layout.activity_books, container, false);



            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                xyz = inflater.inflate(R.layout.activity_sports, container, false);



            }
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                xyz = inflater.inflate(R.layout.activity_furniture, container, false);



            }
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 5) {
                xyz = inflater.inflate(R.layout.activity_wearing, container, false);



            }


//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return xyz;
        }}



}
