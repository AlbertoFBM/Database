package com.aserrano.database;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button create, view, delete;
    private EditText newName, newComment, name, comment;

    private Spinner spinner;
    private ArrayAdapter spinnerAdapter;

    private ArrayList<Comment> commentArrayList;
    private Comment c;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        play();
    }

    private void initComponents(){

        create = findViewById(R.id.create);
        create.setOnClickListener(this);
        view = findViewById(R.id.view);
        view.setOnClickListener(this);
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(this);

        newName = findViewById(R.id.newName);
        newComment = findViewById(R.id.newComment);
        name = findViewById(R.id.name);
        name.setEnabled(false);
        comment = findViewById(R.id.comment);
        comment.setEnabled(false);

        spinner = findViewById(R.id.spinner);

    }

    private void play(){
        db = new Database(this);
        commentArrayList = db.getComments();

        spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, commentArrayList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.create:
                db.insertComment(newName.getText().toString(), newComment.getText().toString());
                commentArrayList = db.getComments();

                spinnerAdapter = new ArrayAdapter(this,
                        android.R.layout.simple_spinner_dropdown_item, commentArrayList);
                spinner.setAdapter(spinnerAdapter);

                newName.setText("");
                newComment.setText("");
                Toast.makeText(getApplicationContext(), "COMMENT INSERTED", Toast.LENGTH_LONG).show();

                break;
            case R.id.view:
                if (c != null){
                    name.setText(c.getName());
                    comment.setText(c.getComment());
                    Toast.makeText(getApplicationContext(), "SEE COMMENT", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete:
                if (c != null){
                    db.deleteComment(c.getId());
                    commentArrayList = db.getComments();
                    spinnerAdapter = new ArrayAdapter(this,
                            android.R.layout.simple_spinner_dropdown_item, commentArrayList);
                    spinner.setAdapter(spinnerAdapter);
                    name.setText("");
                    comment.setText("");
                    c = null;
                    Toast.makeText(getApplicationContext(), "COMMENT DELETED", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinner){
            if (commentArrayList.size() > 0){
                c = commentArrayList.get(i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}