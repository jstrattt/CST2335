package com.example.jessica.lab1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class StartActivity extends AppCompatActivity {
    protected static final String Start_Activity ="StartActivity"; //change to all caps
    private Button button1;
    private Button chatbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        button1 = (Button)findViewById(R.id.button);
        chatbutton = (Button)findViewById(R.id.chatbutton);



        Log.i(Start_Activity, "In onCreate()");
        Log.i(Start_Activity, "User Clicked Start Chat");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
               startActivityForResult(intent, 5);



            }


        });



        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);

            }
        });

        }



    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 5) {
            Log.i(Start_Activity, "Returned to StartActivity.onActivityResult");
        }
        if (responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            CharSequence text = "ListItemsActivity passed:." + messagePassed;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(StartActivity.this, text, duration);
            toast.show();
        }



    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(Start_Activity, "In onResume()");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(Start_Activity, "In onStart()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(Start_Activity, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(Start_Activity, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(Start_Activity, "In onDestroy()");
    }
}
