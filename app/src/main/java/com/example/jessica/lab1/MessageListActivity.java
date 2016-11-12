package com.example.jessica.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.jessica.lab1.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

import static com.example.jessica.lab1.R.id.message;

/**
 * An activity representing a list of Messages. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MessageDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MessageListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    protected static final String CHAT_ACTIVITY = "ChatActivity";
    ;
    private Button send;
    private EditText edit;
    private ListView list;
    public ArrayList<String> messages;
    public SQLiteDatabase db;
    public ChatDatabaseHelper databaseHelper;

  //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
   // TextView message = (TextView) findViewById(R.id.message);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        FrameLayout layout = (FrameLayout) findViewById(R.id.frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Title");

       // super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_chat_window);
        edit = (EditText) findViewById(R.id.EditText);
        list = (ListView) findViewById(R.id.listView);
        send = (Button) findViewById(R.id.button3);
        messages = new ArrayList<String>();

        final MessageListActivity.ChatAdapter messageAdapter = new MessageListActivity.ChatAdapter(this);
        databaseHelper = new ChatDatabaseHelper(this);

        db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_COMMENTS, new String[]{});

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            messages.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(CHAT_ACTIVITY, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));


            cursor.moveToNext();
        }

        for (int x = 0; x < cursor.getColumnCount(); x++) {
            cursor.getColumnName(x);
        }

        Log.i(CHAT_ACTIVITY, "Cursor’s column count =" + cursor.getColumnCount());
        list.setAdapter(messageAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texts = edit.getText().toString();

                messages.add(texts);


                messageAdapter.notifyDataSetChanged();

                edit.setText("");

                ContentValues contentValues = new ContentValues();

                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, texts);
                db.insert(ChatDatabaseHelper.TABLE_COMMENTS, "Null Placeholder", contentValues);

            }
        });

        if (findViewById(R.id.message_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        String messageText = "";

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return messages.size();
        }

        public String getItem(int position) {
            return messages.get(position);

        }

        public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = MessageListActivity.this.getLayoutInflater();
            View result = null;


            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                TextView message1 = (TextView) result.findViewById(R.id.message);
                messageText = getItem(position);
                message1.setText(messageText);


            } else {

                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                TextView message2 = (TextView) result.findViewById(R.id.message);
                messageText = getItem(position);
                message2.setText(messageText);

            }


            //final String messageText = getItem(position);
          //  message.setText(messageText);

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MessageDetailFragment.ARG_ITEM_ID, messageText);
                        MessageDetailFragment fragment = new MessageDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.message_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MessageDetailActivity.class);
                        intent.putExtra(MessageDetailFragment.ARG_ITEM_ID, messageText);

                        context.startActivity(intent);
                    }
                }
            });
            return result;

        }
        /*
        private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
            recyclerView.setAdapter(new MessageListActivity.SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
        }

        public class SimpleItemRecyclerViewAdapter
                extends RecyclerView.Adapter<MessageListActivity.SimpleItemRecyclerViewAdapter.ViewHolder> {

            private final List<DummyContent.DummyItem> mValues;

            public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
                mValues = items;
            }

            @Override
            public void onBindViewHolder(final MessageListActivity.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
                holder.mItem = mValues.get(position);
                holder.mIdView.setText(mValues.get(position).id);
                holder.mContentView.setText(mValues.get(position).content);

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(MessageDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                            MessageDetailFragment fragment = new MessageDetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, MessageDetailActivity.class);
                            intent.putExtra(MessageDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                            context.startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return mValues.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                public final View mView;
                public final TextView mIdView;
                public final TextView mContentView;
                public DummyContent.DummyItem mItem;

                public ViewHolder(View view) {
                    super(view);
                    mView = view;
                    mIdView = (TextView) view.findViewById(R.id.id);
                    mContentView = (TextView) view.findViewById(R.id.content);
                }

                @Override
                public String toString() {
                    return super.toString() + " '" + mContentView.getText() + "'";
                }
    }*/

    }
}
