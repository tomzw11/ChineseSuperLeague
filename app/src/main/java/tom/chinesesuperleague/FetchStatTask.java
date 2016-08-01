package tom.chinesesuperleague;

import android.content.Context;
import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FetchStatTask extends AsyncTask<String,Void,ArrayList<String[]>> {

        private CustomListViewAdapter mStatAdapter;
        private ArrayList<String[]> playerStat = new ArrayList<>();
        private ArrayList<ListItem> inputData = new ArrayList<>();
        private final Context mContext;
        Integer[] playerImage = {R.drawable.fernando,R.drawable.ralf2,R.drawable.wulei};
        String[] playerForm = new String[3];

        public FetchStatTask(Context context,CustomListViewAdapter inputAdapter) {
        mContext = context;
        mStatAdapter = inputAdapter;
        }

        @Override
        protected ArrayList<String[]> doInBackground(String... params) {

            if(params.length==0)return null;
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
            return playerStat;
        }

        @Override
        protected void onPostExecute(ArrayList<String[]> result){
            if(result!=null && mStatAdapter!=null){
                mStatAdapter.clear();

                for(int i=0;i<playerImage.length;i++){
                    playerForm[i] = result.get(i)[0];
                    ListItem ListItem = new ListItem(playerImage[i],playerForm[i]);
                    mStatAdapter.add(ListItem);
                }
            }

        }
    }

