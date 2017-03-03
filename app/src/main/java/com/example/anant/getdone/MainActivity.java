package com.example.anant.getdone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();

        lvItems=(ListView) findViewById(R.id.lvItems);
        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile= new File(filesDir,"todo.txt");
        try{
            items=new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items=new ArrayList<String>();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(MainActivity.this,EditGetDone.class);
                i.putExtra("position", position);
                i.putExtra("text",items.get(position));
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    public void onAddItem(View view){
        EditText etNewItem=(EditText) findViewById(R.id.etNewItem);
        String itemText= etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile= new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String action=data.getStringExtra("action");
            int pos=data.getIntExtra("position", items.size());
                if (action.equals("save")) {
                    items.set(pos, data.getStringExtra("text"));
                    writeItems();
                }
                else {
                    items.remove(pos);
                    writeItems();
                }
        }
        itemsAdapter.notifyDataSetChanged();
    }

}
