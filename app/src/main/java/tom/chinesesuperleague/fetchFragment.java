package tom.chinesesuperleague;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.content.Intent;


/**
 * Created by Tom on 7/31/16.
 */
public class fetchFragment extends Fragment{

    private ArrayList<listItem> inputData = new ArrayList<>();
    private CustomListViewAdapter mStatAdapter;
    private ArrayList<String[]> playerStat = new ArrayList<>();

    private String RALF = "61034";
    private String FERNANDO = "168101";
    private String WULEI = "116730";

    Integer[] playerImage = {R.drawable.fernando,R.drawable.ralf2,R.drawable.wulei};
    String[] playerForm = new String[3];

    public fetchFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fetchfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            fetchStatTask statTask = new fetchStatTask();
            statTask.execute(RALF);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inputData = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fragment_players, container, false);
        final ListView listView = (ListView)getActivity().findViewById(R.id.list);

        mStatAdapter = new CustomListViewAdapter(getContext(),R.layout.fragment_players,inputData);

        listView.setAdapter(mStatAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String message = "great player";
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                int playerId = mStatAdapter.getItem(position).getPlayerId();
                Intent intent = new Intent(getActivity(),DetailStat.class).putExtra(Intent.EXTRA_TEXT,playerId);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class fetchStatTask extends AsyncTask<String,Void,ArrayList<String[]>> {

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

            if(result!=null){
                mStatAdapter.clear();
                inputData.clear();

                for(int i=0;i<playerImage.length;i++){
                    playerForm[i] = result.get(1)[i];
                    listItem listItem = new listItem(playerImage[i],playerForm[i]);
                    inputData.add(listItem);
                }
            }

        }
        }

}
