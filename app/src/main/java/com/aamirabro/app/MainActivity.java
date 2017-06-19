package com.aamirabro.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("some text");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain2();
            }
        });
    }

    private void openMain2 () {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("int_source", 2);
        intent.putExtra("long_source", 3L);
        intent.putExtra("float_source", 6.7F);
        intent.putExtra("double_source", 8.9D);
        startActivity(intent);
    }
}
