package com.example.support;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.pstruct.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StructA structA = new StructA();
        byte[] source = new byte[structA.length()];
        structA.setSource(source);
    }
}
