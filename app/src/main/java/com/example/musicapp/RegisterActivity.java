package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private Button registerBtn;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private EditText regName,regPhone,regEmail,regPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        openHelper = new DatabaseHelper(this);

        registerBtn = findViewById(R.id.regButton);
        regName = findViewById(R.id.Name);
        regPhone = findViewById(R.id.mob_number);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPass);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String fname = regName.getText().toString().trim();
                String fPhone = regPhone.getText().toString().trim();
                String fGmail = regEmail.getText().toString().trim();
                String fPassword = regPassword.getText().toString().trim();
                if (fname.isEmpty() || fPassword.isEmpty() || fGmail.isEmpty() || fPhone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    insertData(fname,fPhone,fGmail,fPassword);
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
    public void insertData(String fname,String fPhone,String fEmail,String fPassword){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2,fname);
        contentValues.put(DatabaseHelper.COL_3,fPhone);
        contentValues.put(DatabaseHelper.COL_4,fEmail);
        contentValues.put(DatabaseHelper.COL_5,fPassword);

        long id = db.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
    }
}
//public class RegisterActivity extends AppCompatActivity {
//
//    private EditText name, mobile_no, email, pass, conf_pass;
//    private Button regButton;
//    private DBHelper db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        name = findViewById(R.id.Name);
//        mobile_no = findViewById(R.id.mob_number);
//        email = findViewById(R.id.regEmail);
//        pass = findViewById(R.id.regPass);
//        conf_pass = findViewById(R.id.confPass);
//        regButton = findViewById(R.id.regButton);
//        db = new DBHelper(this);
//        insertUser();
//    }
//
//        private void insertUser(){
//            regButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean var=db.insert(name.getText().toString(),mobile_no.getText().toString(),email.getText().toString(),pass.getText().toString());
//                    if (var){
//                        Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//}