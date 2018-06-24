package com.example.adilelbourki.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper DBHelper;
    ArrayAdapter<String > mAdapter;
    private ListView lstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper = new DBHelper(this);
    lstTask = findViewById(R.id.taskList);

    loadtasklist();

    }

    private void loadtasklist() {
        ArrayList<String> taskList= DBHelper.getTaskList();
        if(mAdapter==null)
        {
            mAdapter=new ArrayAdapter<String>(this, R.layout.newl,R.id.taskList,taskList);
            lstTask.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_add_task :
            final EditText taskEdiText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("ajouter une nouvelle tache")
                    .setMessage("What is next ?").setView(taskEdiText).setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = String.valueOf(taskEdiText.getText());
                            DBHelper.insertNewTask(task);
                            loadtasklist();
                        }
                    }).setNegativeButton("Annuler",null).create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view)
    {
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)findViewById(R.id.taskText);
        String task = String.valueOf(taskTextView.getText());
        DBHelper.deleteTask(task);
        loadtasklist();
    }
}
