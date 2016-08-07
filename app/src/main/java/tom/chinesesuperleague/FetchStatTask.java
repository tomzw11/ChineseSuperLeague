package tom.chinesesuperleague;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
        private String playerName;

        public FetchStatTask(Context context) {
        mContext = context;

        }

    @Override
    protected Void doInBackground(String... params) {

        playerName = params[0];
        if(playerName.length()==0)return null;
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
        getPlayerStat(playerStat);
      return null;
    }

    private void getPlayerStat(ArrayList<String[]> playerStat){

            Vector<ContentValues> statVector = new Vector<>(playerStat.size());

        for(int i=0;i<playerStat.size();i++){

                ContentValues statValues = new ContentValues();

                statValues.put(StatEntry.COLUMN_PLAYER,playerName);
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

