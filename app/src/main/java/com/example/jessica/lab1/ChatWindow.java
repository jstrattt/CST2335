package com.example.jessica.lab1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ChatWindow extends AppCompatActivity {
    protected static final String CHAT_ACTIVITY ="ChatActivity";;
    private Button send;
    private EditText edit;
    private ListView list;
    public ArrayList<String> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        edit = (EditText)findViewById(R.id.EditText);
        list = (ListView)findViewById(R.id.listView);
        send = (Button)findViewById(R.id.button3);
        messages = new ArrayList<String>();

        final ChatAdapter messageAdapter =new ChatAdapter( this );
        list.setAdapter (messageAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String texts =  edit.getText().toString();

                messages.add(texts);

                messageAdapter.notifyDataSetChanged();

                edit.setText("");

            }
        });




        }


    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }
        public int getCount(){
           return messages.size();
        }

        public String getItem(int position){
            return messages.get(position);

        }
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);}
            else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);}

            TextView messageText = (TextView)result.findViewById(R.id.message);
            messageText.setText(   getItem(position)  ); // get the string at position
            return result;

        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(CHAT_ACTIVITY, "In onResume()");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(CHAT_ACTIVITY, "In onStart()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(CHAT_ACTIVITY, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(CHAT_ACTIVITY, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(CHAT_ACTIVITY, "In onDestroy()");
    }
}