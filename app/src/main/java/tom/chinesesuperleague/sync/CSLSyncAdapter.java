package tom.chinesesuperleague.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;
import android.content.SyncRequest;
import android.os.Build;
import android.database.Cursor;
import android.net.Uri;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import tom.chinesesuperleague.R;
import tom.chinesesuperleague.Ratings;
import tom.chinesesuperleague.Roster;
import tom.chinesesuperleague.data.StatContract;

public class CSLSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = CSLSyncAdapter.class.getSimpleName();

    // Interval at which to sync, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180 * 5;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    //TODO: Research and reset SYNC interval.


    private String playerName;
    private String playerTag;

    public CSLSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

            ArrayList<String[]> playerStat = new ArrayList<>();
            playerTag = Roster.getPreferredPlayer(getContext());
            playerName = Roster.roster.get(playerTag);

            Document doc;
            Elements tableContentEles = null;
            int numberOfMatches = 0;

            if(playerTag.length()==0)return;

            String url = Roster.urlBuilder(playerTag);
            try {
                doc = Jsoup.connect(url).timeout(60000).get();
                tableContentEles = doc.select("td");
                numberOfMatches = tableContentEles.size() / 19 - 1;

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < numberOfMatches; j++) {
                String[] playerMatchStat = new String[19];
                for (int i = 0; i < 19; i++) {
                    playerMatchStat[i] = tableContentEles.get(i + j * 19).text();
                }
                playerStat.add(playerMatchStat);
            }
        addPlayerBio(playerTag);
        getPlayerStat(playerStat);

        }

    private void getPlayerStat(ArrayList<String[]> playerStat){

        Vector<ContentValues> statVector = new Vector<>(playerStat.size());

        for(int i=0;i<playerStat.size();i++){

            ContentValues statValues = new ContentValues();

            statValues.put(StatContract.StatEntry.COLUMN_PLAYER,playerTag);
            statValues.put(StatContract.StatEntry.COLUMN_CNAME,playerName);
            statValues.put(StatContract.StatEntry.COLUMN_DATE,playerStat.get(i)[0]);
            statValues.put(StatContract.StatEntry.COLUMN_GAME,playerStat.get(i)[1]);
            statValues.put(StatContract.StatEntry.COLUMN_TEAM,playerStat.get(i)[2]);
            statValues.put(StatContract.StatEntry.COLUMN_OPPONENT,playerStat.get(i)[3]);
            statValues.put(StatContract.StatEntry.COLUMN_HOME_AWAY,playerStat.get(i)[4]);
            statValues.put(StatContract.StatEntry.COLUMN_SCORE,playerStat.get(i)[5]);
            statValues.put(StatContract.StatEntry.COLUMN_APPREARANCE,playerStat.get(i)[6]);
            statValues.put(StatContract.StatEntry.COLUMN_PLAY_TIME,playerStat.get(i)[7]);
            statValues.put(StatContract.StatEntry.COLUMN_SHOT,playerStat.get(i)[8]);
            statValues.put(StatContract.StatEntry.COLUMN_SHOT_ON_TARGET,playerStat.get(i)[9]);
            statValues.put(StatContract.StatEntry.COLUMN_GOAL,playerStat.get(i)[10]);
            statValues.put(StatContract.StatEntry.COLUMN_KEY_PASS,playerStat.get(i)[11]);
            statValues.put(StatContract.StatEntry.COLUMN_FOUL,playerStat.get(i)[12]);
            statValues.put(StatContract.StatEntry.COLUMN_FOULED,playerStat.get(i)[13]);
            statValues.put(StatContract.StatEntry.COLUMN_CLEARANCE,playerStat.get(i)[14]);
            statValues.put(StatContract.StatEntry.COLUMN_TAKEON,playerStat.get(i)[15]);
            statValues.put(StatContract.StatEntry.COLUMN_SAVE,playerStat.get(i)[16]);
            statValues.put(StatContract.StatEntry.COLUMN_YELLOW_CARD,playerStat.get(i)[17]);
            statValues.put(StatContract.StatEntry.COLUMN_RED_CARD,playerStat.get(i)[18]);
            statValues.put(StatContract.StatEntry.COLUMN_RATING, Ratings.getRating(playerStat.get(i)[5],playerStat.get(i)[4],
                    playerStat.get(i)[6],playerStat.get(i)[7],playerStat.get(i)[8],playerStat.get(i)[9],playerStat.get(i)[10],
            playerStat.get(i)[11],playerStat.get(i)[12],playerStat.get(i)[13],playerStat.get(i)[14],playerStat.get(i)[15],
            playerStat.get(i)[17],playerStat.get(i)[18]));

            statVector.add(statValues);
        }

        // add to database
        int inserted =0;
        if ( statVector.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[statVector.size()];
            statVector.toArray(cvArray);
            inserted = getContext().getContentResolver().bulkInsert(StatContract.StatEntry.CONTENT_URI, cvArray);
        }
        Log.d(LOG_TAG, "SyncAdapter FetchStatTask Complete. " + inserted + " Inserted");
        return;

    }

    private String[] getPlayerBio(String playerTag){

        String[] playerBio = new String[11];

        Document doc_bio;
        Elements tableContentEles_bio = null;

        if(playerTag.length()==0) return null;

        String url = Roster.urlBioBuilder(playerTag);

        try {
            doc_bio = Jsoup.connect(url).timeout(60000).get();
            tableContentEles_bio = doc_bio.select(".t dd");


        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0;i < 11; i++){

            playerBio[i] = tableContentEles_bio.get(i).unwrap().toString();
        }
        return playerBio;
    }

    private void addPlayerBio(String playerTag) {

        ContentValues bioValues = new ContentValues();

        Cursor bioCursor = getContext().getContentResolver().query(
                //StatContract.BioEntry.CONTENT_URI,
                StatContract.BioEntry.buildBioUri(playerTag),
                new String[]{StatContract.BioEntry.COLUMN_TAG},
                StatContract.BioEntry.COLUMN_TAG + " = ?",
                new String[]{playerTag},
                null);

            if (bioCursor.moveToFirst()) {
                Log.d(LOG_TAG,"player bio exists.");
            } else {
                Log.d(LOG_TAG,"new player.");
                String[] playerBio = getPlayerBio(playerTag);
                // Now that the content provider is set up, inserting rows of data is pretty simple.
                // First create a ContentValues object to hold the data you want to insert.

                // Then add the data, along with the corresponding name of the data type,
                // so the content provider knows what kind of value is being inserted.
                bioValues.put(StatContract.BioEntry.COLUMN_TAG, playerTag);
                bioValues.put(StatContract.BioEntry.COLUMN_ENAME, playerBio[2]);
                bioValues.put(StatContract.BioEntry.COLUMN_CNAME, playerBio[0]);
                bioValues.put(StatContract.BioEntry.COLUMN_LNAME, playerBio[1]);
                bioValues.put(StatContract.BioEntry.COLUMN_AGE, playerBio[6]);
                bioValues.put(StatContract.BioEntry.COLUMN_HEIGHT, playerBio[4]);
                bioValues.put(StatContract.BioEntry.COLUMN_NATION, playerBio[8]);
                bioValues.put(StatContract.BioEntry.COLUMN_POSITION, playerBio[7]);

                // add to database
                Uri inserted = null;
                if ( bioValues.size() > 0 ) {

                    inserted = getContext().getContentResolver().insert(StatContract.BioEntry.buildBioUri(playerTag), bioValues);
                }
                Log.d(LOG_TAG, "SyncAdapter BioStatTask Complete. " + inserted + " Inserted Uri");
            }
            bioCursor.close();
            // Wait, that worked?  Yes!

        return;
        }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);

        }
        return newAccount;
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        CSLSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}