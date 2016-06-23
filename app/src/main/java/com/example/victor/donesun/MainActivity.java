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
                String finishedTodo = items.get(position);
                String successMsg = "\"" + finishedTodo + "\" " + getString(R.string.todo_killed_success);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                toastMaker(successMsg);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String todoContent = items.get(position);
                TodoObj todopayload = new TodoObj(position, todoContent);
                launchComposeView(todopayload);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == INTENT_CODE) {
            TodoObj payload = (TodoObj) data.getSerializableExtra("payload"); //Again, cast it!!
            String originalTodo = items.get(payload.getPosition());
            String newTodo = payload.getContent();
            //Overwrite correct element
            items.set(payload.getPosition(), newTodo);
            //Notify the adapter!
            itemsAdapter.notifyDataSetChanged();
            //Persist the data
            writeItems();

            if (!originalTodo.equals(newTodo)) {
                String successMsg = "\"" + newTodo + "\" " + getString(R.string.todo_edited_success);
                toastMaker(successMsg);
            }

        }
    }

    public void launchComposeView(TodoObj todoinstance) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("payload", todoinstance);
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
            itemsAdapter.add(todoText); //Note add to adapter not the list!
            writeItems();
        }
    }


    public void toastMaker(String param) {
        Toast.makeText(this, param, Toast.LENGTH_SHORT).show();
    }
}
