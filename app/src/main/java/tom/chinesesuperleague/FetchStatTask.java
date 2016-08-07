package tom.chinesesuperleague;

import android.content.Context;
import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import android.content.ContentValues;
import android.util.Log;
import tom.chinesesuperleague.data.StatContract.StatEntry;
import android.database.Cursor;
import android.content.ContentUris;
import android.net.Uri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import tom.chinesesuperleague.data.StatContract;

public class FetchStatTask extends AsyncTask<String,Void,Void> {

        private final String LOG_TAG = FetchStatTask.class.getSimpleName();
        private ArrayList<String[]> playerStat = new ArrayList<>();
        private final Context mContext;

        public FetchStatTask(Context context) {
        mContext = context;

        }

    long addPlayer(String playerSetting, String playerName) {

        long playerId;

        Cursor playerCursor = mContext.getContentResolver().query(
                StatContract.PlayerEntry.CONTENT_URI,
                new String[]{StatContract.PlayerEntry._ID},
                StatContract.PlayerEntry.COLUMN_PLAYER_NAME + " = ?",
                new String[]{playerSetting},
                null);


        if (playerCursor.moveToFirst()) {

            int playerIdIndex = playerCursor.getColumnIndex(StatContract.PlayerEntry._ID);

            playerId = playerCursor.getLong(playerIdIndex);
        } else {

            // Now that the content provider is set up, inserting rows of data is pretty simple.
            // First create a ContentValues object to hold the data you want to insert.
            ContentValues playerValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.
            System.out.println("playerName:"+playerName);
            playerValues.put(StatContract.PlayerEntry.COLUMN_PLAYER_NAME, playerName);

            // Finally, insert location data into the database.
            Uri insertedUri = mContext.getContentResolver().insert(
                    StatContract.PlayerEntry.CONTENT_URI,
                    playerValues
            );
            System.out.println("Uri:"+insertedUri);

            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            playerId = ContentUris.parseId(insertedUri);
        }

        playerCursor.close();
        // Wait, that worked?  Yes!
        return playerId;
    }

    @Override
    protected Void doInBackground(String... params) {

        String playerId = params[0];
        if(playerId.length()==0)return null;
        Document doc = null;
        String url = Utility.urlBuilder(params[0]);
        try {
            doc = Jsoup.connect(url).timeout(60000).get();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements tableContentEles = doc.select("td");
        int numberOfMatches = tableContentEles.size() / 19 - 1;

        for (int j = 0; j < numberOfMatches; j++) {
            String[] playerMatchStat = new String[19];
            for (int i = 0; i < 19; i++) {
                playerMatchStat[i] = tableContentEles.get(i + j * 19).text();
            }
            playerStat.add(playerMatchStat);
        }
        getPlayerStat(playerStat,playerId);
      return null;
    }

    private void getPlayerStat(ArrayList<String[]> playerStat,String playerSetting){

            Vector<ContentValues> statVector = new Vector<>(playerStat.size());

        String playerName = "RALF";
        long playerId = addPlayer(playerSetting, playerName);

        for(int i=0;i<playerStat.size();i++){

                ContentValues statValues = new ContentValues();

                statValues.put(StatEntry.COLUMN_PLAYER_KEY,playerId);
                statValues.put(StatEntry.COLUMN_DATE,playerStat.get(i)[0]);
                statValues.put(StatEntry.COLUMN_GAME,playerStat.get(i)[1]);
                statValues.put(StatEntry.COLUMN_TEAM,playerStat.get(i)[2]);
                statValues.put(StatEntry.COLUMN_OPPONENT,playerStat.get(i)[3]);
                statValues.put(StatEntry.COLUMN_HOME_AWAY,playerStat.get(i)[4]);
                statValues.put(StatEntry.COLUMN_SCORE,playerStat.get(i)[5]);
                statValues.put(StatEntry.COLUMN_APPREARANCE,playerStat.get(i)[6]);
                statValues.put(StatEntry.COLUMN_PLAY_TIME,playerStat.get(i)[7]);
                statValues.put(StatEntry.COLUMN_SHOT,playerStat.get(i)[8]);
                statValues.put(StatEntry.COLUMN_SHOT_ON_TARGET,playerStat.get(i)[9]);
                statValues.put(StatEntry.COLUMN_GOAL,playerStat.get(i)[10]);
                statValues.put(StatEntry.COLUMN_KEY_PASS,playerStat.get(i)[11]);
                statValues.put(StatEntry.COLUMN_FOUL,playerStat.get(i)[12]);
                statValues.put(StatEntry.COLUMN_FOULED,playerStat.get(i)[13]);
                statValues.put(StatEntry.COLUMN_CLEARANCE,playerStat.get(i)[14]);
                statValues.put(StatEntry.COLUMN_TAKEON,playerStat.get(i)[15]);
                statValues.put(StatEntry.COLUMN_SAVE,playerStat.get(i)[16]);
                statValues.put(StatEntry.COLUMN_YELLOW_CARD,playerStat.get(i)[17]);
                statValues.put(StatEntry.COLUMN_RED_CARD,playerStat.get(i)[18]);

                statVector.add(statValues);
            }

            // add to database
            int inserted =0;
            if ( statVector.size() > 0 ) {
                ContentValues[] cvArray = new ContentValues[statVector.size()];
                statVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(StatEntry.CONTENT_URI, cvArray);
            }
            Log.d(LOG_TAG, "FetchStatTask Complete. " + inserted + " Inserted");
        }
    }

