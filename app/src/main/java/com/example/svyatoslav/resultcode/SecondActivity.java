package com.example.svyatoslav.resultcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText Name, Surname, Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Name = (EditText) findViewById(R.id.et_second_name);
        Surname = (EditText) findViewById(R.id.et_second_surname);
        Phone = (EditText) findViewById(R.id.et_second_phone);
        Button response = (Button) findViewById(R.id.btn_second_responce);
        response.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(Config.KEY_NAME, Name.getText().toString());
        intent.putExtra(Config.KEY_SURNAME, Surname.getText().toString());
        intent.putExtra(Config.KEY_PHONE, Phone.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
