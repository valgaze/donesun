package com.example.victor.donesun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items; //Set the type
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems); //This ID is specified in XML
        lvItems.setAdapter(itemsAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText); //We're casting findViewById, which returns a VIEW, into an EditText

    }

    public void populateArrayItems() {
        items = new ArrayList<String>();
        items.add("Task1");
        items.add("Task2");
        items.add("Task3");
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }

    public void addItem(View view) {
        String todoText = etEditText.getText().toString();
        if (todoText.length() == 0) {
            Toast.makeText(this, getString(R.string.todo_added_error), Toast.LENGTH_SHORT).show();
        } else {
            etEditText.setText("");
            String successMsg = todoText + " " + getString(R.string.todo_added_success);

            itemsAdapter.add(todoText); //Note add to adapter not the list!
            Toast.makeText(this, successMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
