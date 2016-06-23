package com.example.victor.donesun;

import android.content.Intent;
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
    EditText mlEditText;
    private int INTENT_CODE = 200;

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

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toastMaker("Click listener!");
                launchComposeView();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == INTENT_CODE) {
            String str = data.getExtras().getString("foo");
            toastMaker(str);
        }
    }

    public void launchComposeView() {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("foo","barrrrr  you going to the mall later?");
        startActivityForResult(i, INTENT_CODE); // brings up the second activity
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
