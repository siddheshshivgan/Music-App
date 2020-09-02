package com.example.musicapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private DrawerLayout drawerLayout;
    Uri image_uri;
    private NavigationView navigationView;
    CircleImageView profile;
    private Toolbar toolbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        profile = navigationView.inflateHeaderView(R.layout.nav_header).findViewById(R.id.profile);
        profile.setImageResource(R.drawable.ic_baseline_person_24);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNav,
                R.string.closeNav
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        profile.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                camPermission();
            }
        });
    }

    private void camPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                cameraIntent();
            }

            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
            }

            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    /* access modifiers changed from: private */
    private void cameraIntent() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "New Photo");
        contentValues.put("description", "From Camera");
        image_uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        Toast.makeText(this, "Camera Opened", Toast.LENGTH_SHORT).show();
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra("output",image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            this.profile.setImageURI(this.image_uri);
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
        return true;
    }

    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
