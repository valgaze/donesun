package com.example.victor.donesun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
//                toastMaker(Integer.toString(position));
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch(IOException e) {

            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, items);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void populateArrayItems() {
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }

    public void addItem(View view) {
        String todoText = etEditText.getText().toString();
        if (todoText.length() == 0) {
            toastMaker(getString(R.string.todo_added_error));
        } else {
            etEditText.setText("");
            String successMsg = todoText + " " + getString(R.string.todo_added_success);

            itemsAdapter.add(todoText); //Note add to adapter not the list!
            writeItems();
            toastMaker(todoText);
        }
    }

    public void toastMaker(String param) {
        Toast.makeText(this, param, Toast.LENGTH_SHORT).show();
    }
}
