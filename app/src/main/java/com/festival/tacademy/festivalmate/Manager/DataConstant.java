package com.festival.tacademy.festivalmate.Manager;

import android.provider.BaseColumns;

/**
 * Created by Tacademy on 2016-06-01.
 */
public class DataConstant {
    public interface ChatUserTable extends BaseColumns {
        public static final String TABLE_NAME = "chat_user_table";
        public static final String COLUMN_SERVER_USER_ID = "sid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_LAST_MESSAGE_ID = "last_message";
    }

    public interface ChatTable extends BaseColumns {
        public static final int TYPE_SEND = 1;
        public static final int TYPE_RECEIVE = 2;

        public static final String TABL_NAME = "chat_table";
        public static final String COLUMN_USER_ID = "uid";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_DATE = "date_added";
    }
}