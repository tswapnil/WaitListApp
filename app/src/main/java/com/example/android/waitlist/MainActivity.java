package com.example.android.waitlist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.database.Cursor;

import android.util.*;

public class MainActivity extends AppCompatActivity {
    private WaitListAdapter mAdapter;
    private EditText mName;
    private EditText mPartySize;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        // Set local attributes to corresponding views
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        WaitListDBHelper waitListDBHelper = new WaitListDBHelper(this);
        mDb = waitListDBHelper.getWritableDatabase();
        // Create an adapter for that cursor to display the data
        Cursor cursor = getAllGuests();
        mAdapter = new WaitListAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);
        mName = (EditText) findViewById(R.id.person_name_edit_text);
        mPartySize = (EditText) findViewById(R.id.party_count_edit_text);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            /**
             * Called when ItemTouchHelper wants to move the dragged item from its old position to
             * the new position.
             * <p>
             * If this method returns true, ItemTouchHelper assumes {@code viewHolder} has been moved
             * to the adapter position of {@code target} ViewHolder
             * ( ViewHolder#getAdapterPosition()
             * ViewHolder#getAdapterPosition()}).
             * <p>
             * If you don't support drag & drop, this method will never be called.
             *
             * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to.
             * @param viewHolder   The ViewHolder which is being dragged by the user.
             * @param target       The ViewHolder over which the currently active item is being
             *                     dragged.
             * @return True if the {@code viewHolder} has been moved to the adapter position of
             * {@code target}.
             * see #onMoved(RecyclerView, ViewHolder, int, ViewHolder, int, int, int)
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * Called when a ViewHolder is swiped by the user.
             * <p>
             * If you are returning relative directions ({link #START} , {link #END}) from the
             * {link #getMovementFlags(RecyclerView, ViewHolder)} method, this method
             * will also use relative directions. Otherwise, it will use absolute directions.
             * <p>
             * If you don't support swiping, this method will never be called.
             * <p>
             * ItemTouchHelper will keep a reference to the View until it is detached from
             * RecyclerView.
             * As soon as it is detached, ItemTouchHelper will call
             * {link #clearView(RecyclerView, ViewHolder)}.
             *
             * @param viewHolder The ViewHolder which has been swiped by the user.
             * @param direction  The direction to which the ViewHolder is swiped. It is one of
             *                   {link #UP}, {link #DOWN},
             *                   {link #LEFT} or {link #RIGHT}. If your
             *                   {link #getMovementFlags(RecyclerView, ViewHolder)}
             *                   method
             *                   returned relative flags instead of {link #LEFT} / {link #RIGHT};
             *                   `direction` will be relative as well. ({link #START} or {link
             *                   #END}).
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               long id = (long)viewHolder.itemView.getTag();
                removeGuest(id);
                //refresh the list
                mAdapter.swapCursor(getAllGuests());
            }
        }).attachToRecyclerView(waitlistRecyclerView);



    }
    public void addToWaitlist(View view) {
        if(mName.getText().length() == 0 || mPartySize.getText().length()==0){
            return;
        }
         String name = mName.getText().toString();
        int partySize = 1;
        try {
            partySize = Integer.parseInt(mPartySize.getText().toString());
        }catch (Exception e){

            Log.e("AddToWaitList",e.getMessage());
        }
        addNewGuest(name, partySize);
        Cursor cursor = getAllGuests();
        mAdapter.swapCursor(cursor);
     //   mAdapter.setData(name,count);
    }
    private long addNewGuest(String name, int partySize){
        ContentValues cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME,name);
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE,partySize);
        return mDb.insert(WaitListContract.WaitListEntry.TABLE_NAME,null,cv);
    }
    private boolean removeGuest(long id){
       return mDb.delete(WaitListContract.WaitListEntry.TABLE_NAME, WaitListContract.WaitListEntry._ID +"="+ id,null) > 0;
    }

    private Cursor getAllGuests(){
         return mDb.query(WaitListContract.WaitListEntry.TABLE_NAME,
                 null,
                 null,
                 null,
                 null,
                 null, WaitListContract.WaitListEntry.COLUMN_TIMESTAMP);
    }
}
