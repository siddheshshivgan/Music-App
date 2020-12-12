package com.example.musicapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvRegister;
    private EditText etLoginEmail,etLoginPassword;
    private Button loginButton;

    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Cursor cursor;
    private String username,mail_id,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        tvRegister = findViewById(R.id.textView2);
        etLoginEmail = findViewById(R.id.logEmail);
        etLoginPassword = findViewById(R.id.logPass);
        loginButton = findViewById(R.id.logButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginEmail.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter your Email and Password to login", Toast.LENGTH_SHORT).show();
                } else {
                    cursor = db.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", new String[]{email, password});
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            cursor = db.rawQuery("SELECT "+DatabaseHelper.COL_2+" , "+DatabaseHelper.COL_4+" , "+DatabaseHelper.COL_3+" FROM "+DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=?",new String[]{email});
                            if (cursor.moveToFirst()){
                                username = cursor.getString(0);
                                mail_id = cursor.getString(1);
                                phone = cursor.getString(2);
                            }
                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("email",mail_id);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });



        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });

    }
}

