package com.chhating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calling extends AppCompatActivity {

    private static final int CONTANTS_REQUEST_CODE=1;
    private TextView calling_edit;
    private Button call_btn,contact_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        calling_edit=findViewById(R.id.calling_edit);
        contact_btn=findViewById(R.id.calling_contact_btn);
        call_btn=findViewById(R.id.calling_cal_btn);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},1);

        contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent,CONTANTS_REQUEST_CODE);

            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_CALL);
                String number=calling_edit.getText().toString();

                if (number.trim().isEmpty()){
                    Toast.makeText(Calling.this,"Please Enter Number",Toast.LENGTH_LONG).show();
                }
                else {
                    intent.setData(Uri.parse("tel:"+number));
                }
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"please grant your premission ",Toast.LENGTH_LONG).show();
                    requestPermissions();
                }
                else {
                    startActivity(intent);
                }
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTANTS_REQUEST_CODE:
                    contactPicked(data);
                    break;
            }
        } else {
            Toast.makeText(this, "Failed to pick contact", Toast.LENGTH_SHORT).show();
        }
    }
    private void contactPicked(Intent data){
        Cursor cursor=null;

        try {
            String phoneNo=null;
            Uri uri=data.getData();
            cursor=getContentResolver().query (uri,null,null,null,null);
            cursor.moveToFirst();
            int phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo=cursor.getString(phoneIndex);

            calling_edit.setText(phoneNo);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void requestPermissions(){
        ActivityCompat.requestPermissions(Calling.this,new String[]{
                Manifest.permission.CALL_PHONE
        },1);

    }
}


