package com.chhating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView recyclerView;
    UserAdapter adapter;
    FirebaseDatabase database;

    ArrayList<Users> usersArrayList;

    ImageView img_logout;
    ImageView cambtn,settingbtn,cal_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        cambtn=findViewById(R.id.cam_btn);
        settingbtn=findViewById(R.id.setting_btn);
        cal_btn=findViewById(R.id.main_callbtn);


        DatabaseReference reference=database.getReference().child("user");

        usersArrayList=new ArrayList<>();

        recyclerView=findViewById(R.id.main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new UserAdapter(MainActivity.this,usersArrayList);
        recyclerView.setAdapter(adapter);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Users users=dataSnapshot.getValue(Users.class);
                    usersArrayList.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        img_logout=findViewById(R.id.main_logout);

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(MainActivity.this,R.style.dialoge);
                dialog.setContentView(R.layout.dialog_layout);

                Button btn_no,btn_yes;

                btn_yes=dialog.findViewById(R.id.yes_btn);
                btn_no=dialog.findViewById(R.id.no_btn);

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SettingAct.class);
                startActivity(intent);

            }
        });

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Calling.class);
                startActivity(intent);
            }
        });
        cambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,102);
            }
        });


        if (auth.getCurrentUser()==null){
            Intent intent=new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }

    }
}