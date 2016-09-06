package tom.chinesesuperleague.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Vector;


import tom.chinesesuperleague.data.StatContract.StatEntry;
import tom.chinesesuperleague.data.StatContract.BioEntry;

public class StatDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 9;

    static final String DATABASE_NAME = "stat.db";

    public StatDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_BIO_TABLE = "CREATE TABLE " + BioEntry.TABLE_NAME + " (" +
                BioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BioEntry.COLUMN_TAG + " REAL NOT NULL," +
                BioEntry.COLUMN_ENAME + " REAL NOT NULL, " +
                BioEntry.COLUMN_CNAME + " REAL NOT NULL, " +
                BioEntry.COLUMN_LNAME + " REAL NOT NULL, " +
                BioEntry.COLUMN_NATION + " REAL NOT NULL, " +
                BioEntry.COLUMN_AGE + " REAL NOT NULL, " +
                BioEntry.COLUMN_POSITION + " REAL NOT NULL, " +
                BioEntry.COLUMN_HEIGHT + " REAL NOT NULL, " +
                BioEntry.COLUMN_NUMBER + " REAL NOT NULL " +

                " );";

        final String SQL_CREATE_STAT_TABLE =

                "CREATE TABLE " + StatEntry.TABLE_NAME + " (" +

                        StatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        StatEntry.COLUMN_TAG + " REAL NOT NULL, " +
                        StatEntry.COLUMN_CNAME + " REAL NOT NULL, " +
                        StatEntry.COLUMN_DATE + " REAL NOT NULL, " +
                        StatEntry.COLUMN_GAME + " REAL NOT NULL, " +
                        StatEntry.COLUMN_TEAM + " REAL NOT NULL, " +
                        StatEntry.COLUMN_OPPONENT + " REAL NOT NULL, " +
                        StatEntry.COLUMN_HOME_AWAY + " REAL NOT NULL, " +
                        StatEntry.COLUMN_SCORE + " REAL NOT NULL, " +
                        StatEntry.COLUMN_APPREARANCE + " REAL NOT NULL, " +
                        StatEntry.COLUMN_PLAY_TIME + " REAL NOT NULL, " +
                        StatEntry.COLUMN_SHOT + " REAL NOT NULL, " +
                        StatEntry.COLUMN_SHOT_ON_TARGET + " REAL NOT NULL, " +
                        StatEntry.COLUMN_GOAL + " REAL NOT NULL, " +
                        StatEntry.COLUMN_KEY_PASS + " REAL NOT NULL, " +
                        StatEntry.COLUMN_FOUL + " REAL NOT NULL, " +
                        StatEntry.COLUMN_FOULED + " REAL NOT NULL, " +
                        StatEntry.COLUMN_CLEARANCE + " REAL NOT NULL, " +
                        StatEntry.COLUMN_TAKEON + " REAL NOT NULL, " +
                        StatEntry.COLUMN_SAVE + " REAL NOT NULL, " +
                        StatEntry.COLUMN_YELLOW_CARD + " REAL NOT NULL, " +
                        StatEntry.COLUMN_RED_CARD + " REAL NOT NULL," +
                        StatEntry.COLUMN_RATING + " REAL NOT NULL," +

                        StatEntry.COLUMN_LNAME + " REAL," +
                        StatEntry.COLUMN_NATION + " REAL," +
                        StatEntry.COLUMN_AGE + " REAL," +
                        StatEntry.COLUMN_POSITION + " REAL," +
                        StatEntry.COLUMN_HEIGHT + " REAL," +
                        StatEntry.COLUMN_NUMBER + " REAL," +

                        // To assure the application have just one match per day
                        // per player, it's created a UNIQUE constraint with REPLACE strategy

                        " UNIQUE (" + StatEntry.COLUMN_DATE + "," + StatEntry.COLUMN_TAG +
                        ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_STAT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BIO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StatEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BioEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}