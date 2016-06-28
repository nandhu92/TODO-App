package com.testproject.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemAcivity extends ActionBarActivity {

    private EditText etaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_acivity);
        etaItems = (EditText)findViewById(R.id.etaItems);
        String selectedItem = getIntent().getStringExtra("Item");
        etaItems.setText(selectedItem);

        // Moves cursor to the end of the text
        etaItems.setSelection(etaItems.getText().length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item_acivity, menu);
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

    public void onSubmitEdit(View v) {
        Intent data = new Intent();
        int locationOfItem = getIntent().getIntExtra("loc", 0);
        data.putExtra("name", etaItems.getText().toString());
        data.putExtra("location", locationOfItem);
        data.putExtra("code", 200);
        setResult(RESULT_OK, data);
        // closes the activity and returns to first screen
        this.finish();

    }


}
