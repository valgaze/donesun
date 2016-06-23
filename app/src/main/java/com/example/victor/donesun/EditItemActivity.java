package com.example.victor.donesun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class EditItemActivity extends AppCompatActivity {
    String payload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        payload = getIntent().getStringExtra("foo");
        System.out.println(payload);
    }

    public void saveEdits(View view) {
        System.out.println(Math.random());
        returnBack();
    }

    public void abortEdit (View view) {
        returnBack();
    }

    public void returnBack() {
        Intent payload = new Intent();

        payload.putExtra("foo", "himom!");

        setResult(RESULT_OK, payload);

        this.finish();
    }
}
