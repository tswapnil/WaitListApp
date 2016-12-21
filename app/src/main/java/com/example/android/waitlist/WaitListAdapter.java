package com.example.android.waitlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.database.*;
/**
 * Created by Swapnil on 20-12-2016.
 */

public class WaitListAdapter extends RecyclerView.Adapter<WaitListAdapter.WaitViewHolder>{

    private Context mContext;
    private String nameData;
    private String countData;
    private Cursor mCursor;
    public WaitListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public WaitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new WaitViewHolder(view);

    }


    @Override
    public void onBindViewHolder(WaitViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME));
        int partySize = mCursor.getInt(mCursor.getColumnIndex(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE));
        long ID = mCursor.getLong(mCursor.getColumnIndex(WaitListContract.WaitListEntry._ID));
        holder.tvName.setText(name);
        holder.tvPartSize.setText(String.valueOf(partySize));
        //Hidden Data Stored Here
        holder.itemView.setTag(ID);
    }
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public void setData(String name, String count){
        nameData = name;
        countData = count;
        notifyDataSetChanged();
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class WaitViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvPartSize;

        public WaitViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name_text_view);
            tvPartSize = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }
    }
}
