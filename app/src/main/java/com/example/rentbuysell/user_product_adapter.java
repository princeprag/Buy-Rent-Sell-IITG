package com.example.rentbuysell;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class user_product_adapter extends FirestoreRecyclerAdapter<product_part,user_product_adapter.productHolder> {
    private Context mContext;


    public user_product_adapter(@NonNull FirestoreRecyclerOptions<product_part> options, Context mContext) {
        super(options);
        this.mContext = mContext;

    }

    //  public productAdapter(@NonNull FirestoreRecyclerOptions<Product_list> options) {
    //      super(options);
    //  }

    @Override
    protected void onBindViewHolder(@NonNull final productHolder productHolder, int i, @NonNull final product_part product_list) {
        productHolder.Name.setText(product_list.getNAME());
        productHolder.Shortdesc.setText(product_list.getDESCRIPTION());
        //String s=String.valueOf(product_list.getPrice());
        productHolder.pPrice.setText(product_list.getPRICE());
        String imURL=product_list.getIMAGEURL();
        Picasso.get().load(imURL).fit().into(productHolder.imageView);
        productHolder.listproduct.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // Toast.makeText(mContext,product_list.getname(),Toast.LENGTH_SHORT).show();
                Intent i=new Intent(mContext,User_Description.class);
                i.putExtra("Name",product_list.getNAME());
                i.putExtra("Shortdesc",product_list.getDESCRIPTION());
                i.putExtra("ImageURL",product_list.getIMAGEURL());
                i.putExtra("Price",product_list.getPRICE());
                i.putExtra("Mobile_no",product_list.getMOBILENO());
                i.putExtra("UID",product_list.getUID());
                mContext.startActivity(i);
            }
        });



    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_part,parent,false);
        return new productHolder(v);
    }

    public  class productHolder extends RecyclerView.ViewHolder {
        TextView Shortdesc;
        TextView Name;
        ImageView imageView;
        TextView pPrice;
        RelativeLayout listproduct;

        public productHolder(@NonNull View itemView) {
            super(itemView);
            Shortdesc=itemView.findViewById(R.id.textViewTitle);
            Name=itemView.findViewById(R.id.textViewShortDesc);
            pPrice=itemView.findViewById(R.id.textViewPrice);
            imageView=itemView.findViewById(R.id.imageView);
            listproduct=itemView.findViewById(R.id.list_product);


        }
    }
}
