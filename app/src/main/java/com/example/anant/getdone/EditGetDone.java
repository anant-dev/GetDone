package com.example.anant.getdone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditGetDone extends AppCompatActivity {
    Button save,delete;
    EditText editItem;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_get_done);
        init();
        position = getIntent().getIntExtra("position",0);
        editItem.setText(getIntent().getStringExtra("text"));
    }

    public void  onSaveItem(View view) {
        Intent result= new Intent();
        result.putExtra("position", position);
        result.putExtra("text", editItem.getText().toString());
        result.putExtra("action","save");
        setResult(RESULT_OK,result);
        finish();
    }

    public void  onDeleteItem(View view) {
        Intent result= new Intent();
        result.putExtra("position", position);
        result.putExtra("text", editItem.getText().toString());
        result.putExtra("action","delete");
        setResult(RESULT_OK,result);
        finish();
    }



    private void init() {
        save=(Button) findViewById(R.id.save);
        delete=(Button) findViewById(R.id.delete);
        editItem=(EditText) findViewById(R.id.editItem);
    }
}
