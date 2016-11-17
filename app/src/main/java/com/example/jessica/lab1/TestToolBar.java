package com.example.jessica.lab1;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.jessica.lab1.R.id.action_one;
import static com.example.jessica.lab1.R.id.action_two;

public class TestToolBar extends AppCompatActivity{
    EditText sail;
    Snackbar snack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        snack = Snackbar.make(findViewById(R.id.toolbar), "You Selected Campfire, You wanna camp?", Snackbar.LENGTH_LONG)
                .setAction("Action", null);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Baseball" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu m){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, m );
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem mi) {

        switch (mi.getItemId()) {
            case R.id.action_one:
                //Log.d("Toolbar", "Option 1 selected");
                snack.show();
                break;
            case R.id.action_two:

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TestToolBar.this);
// 2. Chain together various setter methods to set the dialog characteristics

                        builder.setMessage(R.string.toolbar_dialog) //Add a dialog message to strings.xml

                                .setTitle(R.string.toolbar_dialogMessage)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent resultIntent = new Intent(  );
                                        resultIntent.putExtra("Response", "My information to share");
                                        setResult(ListItemsActivity.RESULT_OK, resultIntent);
                                        finish();

                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                })
                                .show();

                break;

            case R.id.action_three:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                LayoutInflater inflater =  this.getLayoutInflater();
                final View v = inflater.inflate(R.layout.content_custom_layout, null);
                builder1.setView(v)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sail = (EditText) v.findViewById(R.id.newMessage);
                                snack.setText(sail.getText());

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;

            case R.id.action_four:
                Toast.makeText(this, "Version 1.0 by Jessica Stratton" , Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
            return true;
    }

}
