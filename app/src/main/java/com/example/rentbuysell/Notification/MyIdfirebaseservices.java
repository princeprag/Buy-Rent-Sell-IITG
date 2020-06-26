package com.example.rentbuysell.Notification;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.rentbuysell.Drawer;
import com.example.rentbuysell.description;
import com.example.rentbuysell.messageInterface;
import com.example.rentbuysell.signIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyIdfirebaseservices extends FirebaseMessagingService {


    private String ch1,ch2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String refreshToken = instanceIdResult.getToken();

                if (refreshToken != null){
                    updateToken(refreshToken);

                }
            }
        });
    }



    private void updateToken(String refreshToken) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String uid=firebaseUser.getUid();
            DocumentReference ref=db.collection("users").document(uid);
            ref.get().addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task)
                                                {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                        Toast.makeText(getApplicationContext(), "Task is successfull", Toast.LENGTH_SHORT).show();

                                                        if (documentSnapshot != null) {
                                                             ch1=documentSnapshot.getString("Choice1");
                                                             ch2=documentSnapshot.getString("Choice2");


                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Document snapshot null", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else{

                                                        Toast.makeText(getApplicationContext(), "Task is unsuccessful because"+task.getException(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                    });


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Token");
            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("products"); //.child(firebaseUser.getUid()).child("Token");
            Token tokens = new Token(refreshToken);
            reference.child(firebaseUser.getUid()).setValue(tokens);
            reference1.child(ch2).child(firebaseUser.getUid()).setValue(tokens);
            reference1.child(ch1).child(firebaseUser.getUid()).setValue(tokens);

        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        SharedPreferences sp=getSharedPreferences("SP_USER",MODE_PRIVATE);
        String savedCurrent=sp.getString("Current_USERID","None");
        String sented= remoteMessage.getData().get("sented"); // sented is for chat NOTIFICATION
        //String sented2= remoteMessage.getData().get("sented2"); // sented2 is for A prod added notification
        Log.d("size",remoteMessage.getData().toString());

        if(sented!=null)
            Log.d("sented"," sented not null");
        else
            Log.d("sented","sented null");



//        Toast.makeText(this,sented, Toast.LENGTH_SHORT).show();
        FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        //&& sented.equals(FirebaseAuth.getInstance().getUid())
        if(sented!= null && fuser!=null && sented.equals(FirebaseAuth.getInstance().getUid())){
         if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            sendOreoNotification(remoteMessage,messageInterface.class);
         }
         else{
             sendNotification(remoteMessage,messageInterface.class);
         }

        }
        else if(sented!= null && fuser!=null && sented.equals("Message for all")) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                sendOreoNotification2(remoteMessage, description.class);
            }
            else{
                sendNotification2(remoteMessage,description.class);
            }

        }

    }
    private void sendOreoNotification(RemoteMessage remoteMessage,Class clss){
        String user=remoteMessage.getData().get("user");
        String icon=remoteMessage.getData().get("icon");
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        RemoteMessage.Notification notification=remoteMessage.getNotification();
//        int j= Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent i= new Intent(this, clss);
        Bundle bundle=new Bundle();
        bundle.putString("UID",user);
        i.putExtras(bundle);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultsound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        oreonotification oreonotification=new oreonotification(this);
        Notification.Builder builder=oreonotification.getOreoNotification(title,body,pendingIntent,defaultsound,icon);
//        int p=0;
//        if(j>0){
//            p=j;
//        }
        oreonotification.getManager().notify(0,builder.build());
    }

    private void sendOreoNotification2(RemoteMessage remoteMessage,Class clss){
         String user=remoteMessage.getData().get("user");
         String icon=remoteMessage.getData().get("icon");
         String title=remoteMessage.getData().get("title");
         String body=remoteMessage.getData().get("body");
         String prod_name=remoteMessage.getData().get("prod_name");
         String prod_id=remoteMessage.getData().get("prod_id");
         String prod_des=remoteMessage.getData().get("prod_des");
         String prod_price=remoteMessage.getData().get("prod_price");
         String mobile=remoteMessage.getData().get("mobile");
         String imageURL=remoteMessage.getData().get("imageURL");
         String category=remoteMessage.getData().get("category");
         String price=remoteMessage.getData().get("user");
         String modd=remoteMessage.getData().get("modd");
        RemoteMessage.Notification notification=remoteMessage.getNotification();
//        int j= Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent i= new Intent(this, clss);
        Bundle bundle=new Bundle();
        bundle.putString("UID",user);
        bundle.putString("Mode",modd);
        bundle.putString("Mobile_no",mobile);
        bundle.putString("Name",prod_name);
        bundle.putString("Price",prod_price);
        bundle.putString("Shortdesc",prod_des);
        bundle.putString("ImageURL",imageURL);

        i.putExtras(bundle);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultsound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        oreonotification oreonotification=new oreonotification(this);
        Notification.Builder builder=oreonotification.getOreoNotification(title,body,pendingIntent,defaultsound,icon);
//        int p=0;
//        if(j>0){
//            p=j;
//        }
        oreonotification.getManager().notify(0,builder.build());
    }


    private void sendNotification(RemoteMessage remoteMessage,Class clss)
    {   String user=remoteMessage.getData().get("user");
        String icon=remoteMessage.getData().get("icon");
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        RemoteMessage.Notification notification=remoteMessage.getNotification();
        //int j= Integer.parseInt(user.replaceAll("[\\D]",""));



        Intent i= new Intent(this, clss);
        Bundle bundle=new Bundle();
        bundle.putString("UID",user);
        i.putExtras(bundle);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultsound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultsound)
                .setContentIntent(pendingIntent);
        NotificationManager noti=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
          int p=0;
//         if(j>0){
//             p=j;
//         }
         noti.notify(0,builder.build());


    }

    private void sendNotification2(RemoteMessage remoteMessage,Class clss)
    {   String user=remoteMessage.getData().get("user");
        String icon=remoteMessage.getData().get("icon");
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        String prod_name=remoteMessage.getData().get("prod_name");
        String prod_id=remoteMessage.getData().get("prod_id");
        String prod_des=remoteMessage.getData().get("prod_des");
        String prod_price=remoteMessage.getData().get("prod_price");
        String mobile=remoteMessage.getData().get("mobile");
        String imageURL=remoteMessage.getData().get("imageURL");
        String category=remoteMessage.getData().get("category");
        String modd=remoteMessage.getData().get("modd");

        RemoteMessage.Notification notification=remoteMessage.getNotification();
        //int j= Integer.parseInt(user.replaceAll("[\\D]",""));



        Intent i= new Intent(this, clss);
        Bundle bundle=new Bundle();
        bundle.putString("UID",user);
        bundle.putString("Mode",modd);
        bundle.putString("Mobile_no",mobile);
        bundle.putString("Name",prod_name);
        bundle.putString("Price",prod_price);
        bundle.putString("Shortdesc",prod_des);
        bundle.putString("ImageURL",imageURL);


        i.putExtras(bundle);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultsound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultsound)
                .setContentIntent(pendingIntent);
        NotificationManager noti=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int p=0;
//         if(j>0){
//             p=j;
//         }
        noti.notify(0,builder.build());


    }
}
