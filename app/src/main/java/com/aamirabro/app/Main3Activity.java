package com.aamirabro.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aamirabro.extraknife.AllowInjectExtras;
import com.aamirabro.extraknife.InjectExtra;

@AllowInjectExtras
public class Main3Activity extends AppCompatActivity {

    @InjectExtra("extra")
    public int extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }
}
