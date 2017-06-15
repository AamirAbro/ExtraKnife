package com.aamirabro.app;

import android.app.Activity;
import android.os.Bundle;

import com.aamirabro.extraknife.InjectExtra;

public class Main2Activity extends Activity {

    @InjectExtra("int_source")
    int intSource;
    @InjectExtra("long_source")
    long longSource;
    @InjectExtra("float_source")
    float floatSource;
    @InjectExtra("double_source")
    double doubleSource;

//    @InjectExtra("string_source")
//    String stringSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Main2Activity_ExtrasInjector.injectExtras(this);

//        this.getIntent().getIntExtra()
    }

//    static class Test {
//        public static void inject (Main2Activity main2Activity) {
//            main2Activity.booleanSource = main2Activity.getIntent().getBooleanExtra("boolean_source_2", false);
//        }
//    }
}
