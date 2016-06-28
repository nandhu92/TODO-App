package com.testproject.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


public class EditFunctionality extends ActionBarActivity {

    private ArrayList todoItems;
    private ArrayAdapter aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_functionality);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        setupListViewListener();
        setupEditViewListener();
    }

  // Removes the selected item on long click
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        todoItems.remove(position);
                        aToDoAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }

 // Takes the user to Edit Activity for edit purpose
    private void setupEditViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        Intent editItemIntent = new Intent(EditFunctionality.this, EditItemAcivity.class);
                        String text = parent.getItemAtPosition(position).toString();
                        editItemIntent.putExtra("Item", text);
                        editItemIntent.putExtra("loc", position);
                        editItemIntent.putExtra("mode", 2);
                        startActivityForResult(editItemIntent, REQUEST_CODE);

                    }

                }

        );
    }



    private void readItems() {
        File filesDir = getFilesDir();
        File file  = new File(filesDir,"todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch( IOException e) {

            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file  = new File(filesDir,"todo.txt");
        try {
           FileUtils.writeLines(file,todoItems);
        } catch( IOException e) {
          e.printStackTrace();
        }
    }


    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_functionality, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Adds the user entered item
    public void onAddItem(View view) {

        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String modifiedItem = data.getExtras().getString("name");
            int modifiedItemLocation = data.getExtras().getInt("location");
            todoItems.set(modifiedItemLocation, modifiedItem);

            //updates the list
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
            int code = data.getExtras().getInt("code", 0);
        }
    }

}
