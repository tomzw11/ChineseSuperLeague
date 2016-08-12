package tom.chinesesuperleague.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Vector;


import tom.chinesesuperleague.data.StatContract.StatEntry;

public class StatDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "stat.db";

    public StatDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_STAT_TABLE =
                "CREATE TABLE " + StatEntry.TABLE_NAME + " (" +

                StatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + StatEntry.COLUMN_PLAYER + " REAL NOT NULL, " +
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
                        // To assure the application have just one weather entry per day
                        // per location, it's created a UNIQUE constraint with REPLACE strategy

                        " UNIQUE (" + StatEntry.COLUMN_DATE + "," + StatEntry.COLUMN_PLAYER +
                         ") ON CONFLICT REPLACE);";

//        final String SQL_CREATE_PLAYER_TABLE = "CREATE TABLE " + StatContract.PlayerEntry.TABLE_NAME + " (" +
//                StatContract.PlayerEntry._ID + " INTEGER PRIMARY KEY," +
//                StatContract.PlayerEntry.COLUMN_PLAYER_ID + " TEXT UNIQUE NOT NULL, " +
//                StatContract.PlayerEntry.COLUMN_PLAYER_NAME + " TEXT NOT NULL, " +
//                " );";
//
//
//        final String SQL_PLAYER_ROSTER = "INSERT INTO player "+ "(" +
//                StatContract.PlayerEntry.COLUMN_PLAYER_NAME + "," +
//                StatContract.PlayerEntry.COLUMN_PLAYER_ID + ") VALUES (" +
//                Ralf + "," + iRalf +");";


        sqLiteDatabase.execSQL(SQL_CREATE_STAT_TABLE);

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
        onCreate(sqLiteDatabase);
    }

}
