package com.chhating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    String reciverimg,reciverUid,reciverName,SenderUid;
    CircleImageView profile;
    TextView reciverPName;
    CardView sndbtn;
    EditText msg_type;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    public static String senderImg;
    public static String receverImgg;

    String senderRoom;
    String reciverRoom;
    RecyclerView msgadapter_recyclerview;
    ArrayList<msgModelClass> messagesArrayList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        msgadapter_recyclerview=findViewById(R.id.chat_msgadapter_recycle);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);   //bottom se msg lene k lye
        msgadapter_recyclerview.setLayoutManager(linearLayoutManager);

        reciverName=getIntent().getStringExtra("nameeee");
        reciverimg=getIntent().getStringExtra("rcvImg");
        reciverUid=getIntent().getStringExtra("uIddd");

        messagesArrayList=new ArrayList<>();

        messageAdapter=new MessageAdapter(ChatActivity.this,messagesArrayList);
        msgadapter_recyclerview.setAdapter(messageAdapter);

        sndbtn=findViewById(R.id.chat_sendbtn);
        msg_type=findViewById(R.id.chat_msgtype);

        profile=findViewById(R.id.chat_profile_img);
        reciverPName=findViewById(R.id.chat_reciver_name);

        Picasso.get().load(reciverimg).into(profile);
        reciverPName.setText(""+reciverName);

        SenderUid=firebaseAuth.getUid();

        senderRoom=SenderUid+reciverUid;
        reciverRoom=reciverUid+SenderUid;


        DatabaseReference reference=database.getReference().child("user").child(firebaseAuth.getUid());

        DatabaseReference chatrefrence=database.getReference().child("chats").child(senderRoom).child("messages");


        // DatabaseReference  reference = database.getReference().child("user").child(firebaseAuth.getUid());
        //  DatabaseReference  chatreference = database.getReference().child("chats").child(senderRoom).child("messages");


        chatrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    msgModelClass messages=dataSnapshot.getValue(msgModelClass.class);
                    messagesArrayList.add(messages);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg=snapshot.child("profilepic").getValue().toString();
                receverImgg=reciverimg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sndbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=msg_type.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Messege is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                msg_type.setText("");   //enter(send) hone k bad empty box show hoga
                Date date=new Date();
                msgModelClass messagesss=new msgModelClass(message,SenderUid,date.getTime());

                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messagesss).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats")
                                        .child(reciverRoom)
                                        .child("messages").push()
                                        .setValue(messagesss).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
            }
        });


    }
}