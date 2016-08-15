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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import tom.chinesesuperleague.R;
import tom.chinesesuperleague.Utility;
import tom.chinesesuperleague.data.StatContract;

public class CSLSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = CSLSyncAdapter.class.getSimpleName();

    // Interval at which to sync, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180 * 5;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;


    private ArrayList<String[]> playerStat = new ArrayList<>();
    private String playerName;
    private String playerTag;

    public CSLSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        //Log.d(LOG_TAG, "onPerformSync Called.");

            playerTag = Utility.getPreferredPlayer(getContext());
            playerName = Utility.getPlayerName(playerTag);
//            System.out.println("playerName"+ playerName+"playerTag"+playerTag);

            Document doc = null;
            Elements tableContentEles = null;

            if(playerTag.length()==0)return;

            String url = Utility.urlBuilder(playerTag);
            try {
                doc = Jsoup.connect(url).timeout(60000).get();
                tableContentEles = doc.select("td");


            } catch (IOException e) {
                e.printStackTrace();
            }

            int numberOfMatches = tableContentEles.size() / 19 - 1;

            for (int j = 0; j < numberOfMatches; j++) {
                String[] playerMatchStat = new String[19];
                for (int i = 0; i < 19; i++) {
                    playerMatchStat[i] = tableContentEles.get(i + j * 19).text();
                }
                playerStat.add(playerMatchStat);
            }
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