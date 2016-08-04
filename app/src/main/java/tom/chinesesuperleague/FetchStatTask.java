package tom.chinesesuperleague;

import android.content.Context;
import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import android.content.ContentValues;
import android.util.Log;
import tom.chinesesuperleague.data.StatContract.StatEntry;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class FetchStatTask extends AsyncTask<String,Void,Void> {

        private final String LOG_TAG = FetchStatTask.class.getSimpleName();
        private ArrayList<String[]> playerStat = new ArrayList<>();
        private final Context mContext;

        public FetchStatTask(Context context) {
        mContext = context;

        }

    @Override
    protected Void doInBackground(String... params) {

        String playerId = params[0];
        if(playerId.length()==0)return null;
        Document doc = null;
        String url = Utility.urlBuilder(params[0]);
        try {
            doc = Jsoup.connect(url).timeout(10 * 1000).get();
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

    private void getPlayerStat(ArrayList<String[]> playerStat,String playerId){

        ContentValues statValues = new ContentValues();
        statValues.put(StatEntry.COLUMN_PLAYER_KEY,playerId);
        statValues.put(StatEntry.COLUMN_DATE,playerStat.get(0)[0]);
        statValues.put(StatEntry.COLUMN_GAME,playerStat.get(0)[1]);
        statValues.put(StatEntry.COLUMN_TEAM,playerStat.get(0)[2]);
        statValues.put(StatEntry.COLUMN_OPPONENT,playerStat.get(0)[3]);
        statValues.put(StatEntry.COLUMN_HOME_AWAY,playerStat.get(0)[4]);
        statValues.put(StatEntry.COLUMN_SCORE,playerStat.get(0)[5]);
        statValues.put(StatEntry.COLUMN_APPREARANCE,playerStat.get(0)[6]);
        statValues.put(StatEntry.COLUMN_PLAY_TIME,playerStat.get(0)[7]);
        statValues.put(StatEntry.COLUMN_SHOT,playerStat.get(0)[8]);
        statValues.put(StatEntry.COLUMN_SHOT_ON_TARGET,playerStat.get(0)[9]);
        statValues.put(StatEntry.COLUMN_GOAL,playerStat.get(0)[10]);
        statValues.put(StatEntry.COLUMN_KEY_PASS,playerStat.get(0)[11]);
        statValues.put(StatEntry.COLUMN_FOUL,playerStat.get(0)[12]);
        statValues.put(StatEntry.COLUMN_FOULED,playerStat.get(0)[13]);
        statValues.put(StatEntry.COLUMN_CLEARANCE,playerStat.get(0)[14]);
        statValues.put(StatEntry.COLUMN_TAKEON,playerStat.get(0)[15]);
        statValues.put(StatEntry.COLUMN_SAVE,playerStat.get(0)[16]);
        statValues.put(StatEntry.COLUMN_YELLOW_CARD,playerStat.get(0)[17]);
        statValues.put(StatEntry.COLUMN_RED_CARD,playerStat.get(0)[18]);

        Vector<ContentValues> statVector = new Vector<ContentValues>(statValues.size());
        statVector.add(statValues);

        // add to database
        int inserted =0;
        if ( statVector.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[statVector.size()];
            statVector.toArray(cvArray);
            inserted = mContext.getContentResolver().bulkInsert(StatEntry.CONTENT_URI, cvArray);
        }
        Log.d(LOG_TAG, "FetchWeatherTask Complete. " + inserted + " Inserted");
    }

    }

