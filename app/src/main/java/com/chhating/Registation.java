package com.chhating;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registation extends AppCompatActivity {

    TextView loginbt;
    EditText rg_username,rg_email,rg_password,rg_repassword;
    Button rg_signup;
    CircleImageView rg_profileImg;
    FirebaseAuth auth;

    Uri imageURI;
    String imageuri;

    FirebaseDatabase database;
    FirebaseStorage storage;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setCancelable(false);

        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();

        loginbt=findViewById(R.id.rej_login);

        rg_email=findViewById(R.id.rej_email);
        rg_password=findViewById(R.id.rej_password);
        rg_repassword=findViewById(R.id.rej_re_password);
        rg_username=findViewById(R.id.rej_name);

        rg_signup=findViewById(R.id.rej_signup);
        rg_profileImg=findViewById(R.id.rej_profile);

        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Registation.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        rg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namee=rg_username.getText().toString();
                String emaill=rg_email.getText().toString();
                String Password=rg_password.getText().toString();
                String cPassword=rg_repassword.getText().toString();
                String status="Hey I'm using This Application";


                if (TextUtils.isEmpty(namee)||TextUtils.isEmpty(emaill)||TextUtils.isEmpty(Password)
                        ||TextUtils.isEmpty(cPassword)){
                    progressDialog.dismiss();
                    Toast.makeText(Registation.this,"Please Enter Valid Information",Toast.LENGTH_SHORT).show();
                } else if (!emaill.matches(emailPattern)) {
                    progressDialog.dismiss();
                    rg_email.setError("Type Valid Email");
                } else if (Password.length()<6) {
                    progressDialog.dismiss();
                    rg_password.setError("Password must be six charecter or more");
                } else if (!Password.equals(cPassword)) {
                    progressDialog.dismiss();
                    rg_password.setError("Password not Matched");
                }
                else {
                    auth.createUserWithEmailAndPassword(emaill,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //excess token banta hai,har ek user ki unique id=id
                                String id=task.getResult().getUser().getUid();
                                DatabaseReference reference=database.getReference().child("user").child(id);
                                StorageReference storageReference=storage.getReference().child("upload").child(id);

                                if (imageURI!=null){
                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageuri=uri.toString();
                                                        Users users=new Users(id,namee,emaill,Password,imageuri,status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    progressDialog.show();
                                                                    Toast.makeText(Registation.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                                                                    Intent intent=new Intent(Registation.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }else {
                                                                    Toast.makeText(Registation.this, "Error in creating User", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }else {
                                    String status="Hey I'm using This Application";
                                    imageuri="https://firebasestorage.googleapis.com/v0/b/chhating-a081d.appspot.com/o/men.png?alt=media&token=2b4b1392-954f-4967-bc7b-c63f2cdc209c";
                                    Users users=new Users(id,namee,emaill,Password,imageuri,status);

                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressDialog.show();
                                                Intent intent=new Intent(Registation.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                Toast.makeText(Registation.this, "Error in creating the User", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }else {
                                Toast.makeText(Registation.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });




        rg_profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select picture"),101);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==101){
            if (data!=null){
                imageURI=data.getData();
                rg_profileImg.setImageURI(imageURI);
            }
        }
    }
}