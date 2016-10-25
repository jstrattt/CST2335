package com.example.jessica.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    public SQLiteDatabase db;
    public ChatDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        edit = (EditText)findViewById(R.id.EditText);
        list = (ListView)findViewById(R.id.listView);
        send = (Button)findViewById(R.id.button3);
        messages = new ArrayList<String>();

        final ChatAdapter messageAdapter =new ChatAdapter( this );
        databaseHelper = new ChatDatabaseHelper(this);

        db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_COMMENTS, new String[]{});

        cursor.moveToFirst();

        while(!cursor.isAfterLast() ){

         messages.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(CHAT_ACTIVITY, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ));


        cursor.moveToNext();
        }

        for (int x = 0; x < cursor.getColumnCount(); x++ ){
            cursor.getColumnName(x);
        }

        Log.i(CHAT_ACTIVITY, "Cursor’s column count =" + cursor.getColumnCount() );
        list.setAdapter (messageAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String texts =  edit.getText().toString();

                messages.add(texts);


                messageAdapter.notifyDataSetChanged();

                edit.setText("");

                ContentValues contentValues = new ContentValues();

                    contentValues.put(ChatDatabaseHelper.KEY_MESSAGE , texts);
                    db.insert(ChatDatabaseHelper.TABLE_COMMENTS ,"Null Placeholder", contentValues);

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
        db.close();
        Log.i(CHAT_ACTIVITY, "In onDestroy()");
    }
}