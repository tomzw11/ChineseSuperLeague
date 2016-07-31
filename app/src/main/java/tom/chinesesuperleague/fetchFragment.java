package tom.chinesesuperleague;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tom on 7/31/16.
 */
public class fetchFragment extends Fragment{






        public class fetchStatTask extends AsyncTask<String,Void,ArrayList<String[]>> {


            private Document doc = null;
            private String RALF = "61034";
            private String FERNANDO = "168101";
            private String WULEI = "116730";
            private ArrayList<String[]> playerStat = new ArrayList<>();

            @Override
            protected ArrayList<String[]> doInBackground(String... params) {

                String url = Utility.urlBuilder(RALF);

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
                return null;
            }
        }

}
