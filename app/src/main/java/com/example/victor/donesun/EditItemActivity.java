package com.example.victor.donesun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    TodoObj payload_from_main;
    EditText mlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        payload_from_main = (TodoObj) getIntent().getSerializableExtra("payload"); //MUST CAST!

        mlEditText = (EditText) findViewById(R.id.mlEditText);
        String todoText = payload_from_main.getContent();
        int todoPosition = payload_from_main.getPosition();

        mlEditText.setText(todoText);

        System.out.println("---------------------------");
        System.out.println("Todotext:" + todoText);
        System.out.println("Pos:" + todoPosition);

    }

    public void saveEdits(View view) {
        returnBack();
    }

    public void abortEdit (View view) {
        returnBack();
    }

    public void returnBack() {
        Intent payload = new Intent();
        String editedTodo = mlEditText.getText().toString();
        TodoObj payload_to_main = new TodoObj(payload_from_main.getPosition(), editedTodo);

        payload.putExtra("payload", payload_to_main);

        setResult(RESULT_OK, payload);

        this.finish();
    }
}
