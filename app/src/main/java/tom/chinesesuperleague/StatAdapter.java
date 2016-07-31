package tom.chinesesuperleague;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Tom on 7/30/16.
 */
public class StatAdapter extends Activity{

    private String RALF = "61034";
    private String FERNANDO = "168101";
    private String WULEI = "116730";

    private ArrayList<String[]> playerStat = new ArrayList<>();
    private Document doc = null;
    private CustomListViewAdapter mStatAdapter;

    ArrayList<listItem> inputData;

    public StatAdapter(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        //setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        Integer[] playerImage = {R.drawable.fernando,R.drawable.ralf2,R.drawable.wulei};
        String [] playerForm = {"9","8","7"};

        inputData = new ArrayList<>();
        for(int i=0;i<playerImage.length;i++){
            listItem listItem = new listItem(playerImage[i],playerForm[i]);
            inputData.add(listItem);
        }

        View rootView = inflater.inflate(R.layout.fragment_players, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        mStatAdapter = new CustomListViewAdapter(this,R.layout.fragment_players,inputData);

        listView.setAdapter(mStatAdapter);

        return rootView;
    }

    public ArrayList<String[]> getStat() {

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
        return playerStat;
    }


}


