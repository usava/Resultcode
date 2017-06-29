package com.example.svyatoslav.resultcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private TextView Name, Surname, Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (TextView) findViewById(R.id.tv_main_name);
        Surname = (TextView) findViewById(R.id.tv_main_surname);
        Phone = (TextView) findViewById(R.id.tv_main_phone);
        Button request = (Button) findViewById(R.id.btn_main_request);
        Button chooseImage = (Button) findViewById(R.id.btn_main_chooseImage);
        request.setOnClickListener(this);
        chooseImage.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Config.REQUEST_CODE){
                String name = data.getStringExtra(Config.KEY_NAME);
                String surname = data.getStringExtra(Config.KEY_SURNAME);
                String phone = data.getStringExtra(Config.KEY_PHONE);

                Name.setText(name);
                Surname.setText(surname);
                Phone.setText(phone);
            }
            CircleImageView CirleImage = (CircleImageView) findViewById(R.id.civ_main_cirleImage);
            if(requestCode == Config.REQUEST_IMAGE){
                Uri uri = data.getData();
                Log.d("URI", "Uri: " + uri.toString());
                Picasso.with(this)
                        .load(uri.toString())
                        .into(CirleImage);
            }
        }
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_main_request:
                intent = new Intent(this, SecondActivity.class);
                startActivityForResult(intent, Config.REQUEST_CODE);
                break;
            case R.id.btn_main_chooseImage:
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, Config.REQUEST_IMAGE);
               // performFileSearch();
                break;
            case R.id.btn_main_requestPlaces:
                setupPlacePicker();
        }

    }

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, Config.REQUEST_IMAGE);
    }

    public void setupPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try{
            startActivityForResult(builder.build(this), Config.REQUEST_PLACES);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("OnConnnectionFailed", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
