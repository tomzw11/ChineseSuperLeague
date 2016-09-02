package tom.chinesesuperleague.data;


import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import tom.chinesesuperleague.Roster;

public class StatProvider extends ContentProvider{

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private StatDBHelper mOpenHelper;

    static final int DATE = 300;
    static final int PLAYER = 100;
    static final int PLAYER_WITH_DATE = 101;
    static final int BIO = 200;

    private static final String sPlayerSettingSelection =
            StatContract.StatEntry.TABLE_NAME+
                    "." + StatContract.StatEntry.COLUMN_PLAYER + " = ? ";

    private static final String sBioSettingSelection =
            StatContract.BioEntry.TABLE_NAME+
                    "." + StatContract.BioEntry.COLUMN_TAG + " = ? ";

    private static final String sPlayerSettingAndDateSelection =
            StatContract.StatEntry.TABLE_NAME+
                    "." + StatContract.StatEntry.COLUMN_PLAYER + " = ? AND "
            + StatContract.StatEntry.COLUMN_DATE + " = ? ";

    static UriMatcher buildUriMatcher() {

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = StatContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, StatContract.PATH_PLAYER + "/*", PLAYER);
        matcher.addURI(authority, StatContract.PATH_PLAYER + "/*/*", PLAYER_WITH_DATE);
        matcher.addURI(authority, StatContract.PATH_BIO + "/*", BIO);


        return matcher;
    }

    private Cursor getBioByPlayer(Uri uri, String[] projection, String sortOrder) {

        System.out.println(uri+" getbio uri " + projection);
        String bioSetting = StatContract.BioEntry.getBioSettingFromUri(uri);

        String[] selectionArgs;
        selectionArgs = new String[]{bioSetting};

        String selection = sBioSettingSelection;

        //System.out.println("StatProvider player selection: "+selection);

        return mOpenHelper.getReadableDatabase().query(StatContract.BioEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getStatByPlayer(Uri uri, String[] projection, String sortOrder) {

        String playerSetting = StatContract.StatEntry.getPlayerSettingFromUri(uri);
        //System.out.println("playerSetting: "+playerSetting);

        String[] selectionArgs;
        selectionArgs = new String[]{playerSetting};

        String selection = sPlayerSettingSelection;

        //System.out.println("StatProvider player selection: "+selection);

        return mOpenHelper.getReadableDatabase().query(StatContract.StatEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getStatByPlayerAndDate(Uri uri, String[] projection, String sortOrder) {

        String playerSetting = StatContract.StatEntry.getPlayerSettingFromUri(uri);
        String dateSetting = StatContract.StatEntry.getDateFromUri(uri);

        String[] selectionArgs;
        selectionArgs = new String[]{playerSetting,dateSetting};

        String selection = sPlayerSettingAndDateSelection;

        return mOpenHelper.getReadableDatabase().query(StatContract.StatEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new StatDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case DATE:
                return StatContract.StatEntry.CONTENT_TYPE;

            case PLAYER_WITH_DATE:
                return StatContract.StatEntry.CONTENT_TYPE;

            case BIO:
                return StatContract.BioEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            case PLAYER:{
                retCursor = getStatByPlayer(uri,projection,sortOrder);
                break;
            }
            case BIO:{
                retCursor = getBioByPlayer(uri,projection,sortOrder);
                break;
            }
            case PLAYER_WITH_DATE:{
                retCursor = getStatByPlayerAndDate(uri,projection,sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {

            case BIO: {
                long _id = db.insert(StatContract.BioEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = StatContract.BioEntry.buildBioUri(Roster.getPreferredPlayer(getContext()));
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        String countQuery = "SELECT  * FROM " + StatContract.BioEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        System.out.println("player bio database size: "+cnt);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case DATE:
                rowsDeleted = db.delete(
                        StatContract.StatEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {

            case DATE:
                rowsUpdated = db.update(StatContract.StatEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(StatContract.StatEntry.TABLE_NAME, null, value);

                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

//        String countQuery = "SELECT  * FROM " + StatContract.StatEntry.TABLE_NAME;
//        Cursor cursor = db.rawQuery(countQuery, null);
//        int cnt = cursor.getCount();
//        cursor.close();
//        System.out.println("database size: "+cnt);

        return returnCount;
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
