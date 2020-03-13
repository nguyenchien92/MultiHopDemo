package com.example.nguyen.multihopdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.nguyen.multihopdemo.fragments.FragmentContent;


public class MainActivity extends AppCompatActivity {

    private FrameLayout frameMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameMain = findViewById(R.id.frame_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.frame_main,new FragmentContent(),FragmentContent.class.getSimpleName())
                .commit();

    }
}
