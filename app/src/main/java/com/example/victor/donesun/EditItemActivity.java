package com.example.victor.donesun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

    }

    public void saveEdits(View view) {
        System.out.println(Math.random());
        returnBack();
    }

    public void abortEdit (View view) {
        returnBack();
    }

    public void returnBack() {
        this.finish();
    }
}
