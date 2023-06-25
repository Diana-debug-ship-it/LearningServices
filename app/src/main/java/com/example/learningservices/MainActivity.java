package com.example.learningservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.learningservices.databinding.ActivityMainBinding;

import io.reactivex.rxjava3.core.Observable;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStartSimpleService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(MyService.newIntent(MainActivity.this));
            }
        });
    }

}