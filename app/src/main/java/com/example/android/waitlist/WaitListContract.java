package com.example.android.waitlist;

/**
 * Created by Swapnil on 20-12-2016.
 */
import android.provider.BaseColumns;

public class WaitListContract {
    public static final class WaitListEntry implements BaseColumns {
        public static final String TABLE_NAME = "WaitList";
        public static final String COLUMN_GUEST_NAME = "GuestName";
        public static final String COLUMN_PARTY_SIZE = "PartySize";
        public static final String COLUMN_TIMESTAMP = "TimeStamp";


    }
}
