package com.example.rentbuysell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.rentbuysell.adapter.productAdapter;
import com.example.rentbuysell.model.product_part;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListGiveAwayItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_give_away_items);
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        CollectionReference productref= db.collection("Giveaway");
        Query query=productref.orderBy("Servertime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<product_part> options=new FirestoreRecyclerOptions.Builder<product_part>().setQuery(query,product_part.class).build();
        productAdapter adapter=new productAdapter(options,ListGiveAwayItems.this);
        RecyclerView recyclerView= findViewById(R.id.giveawayitems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListGiveAwayItems.this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
