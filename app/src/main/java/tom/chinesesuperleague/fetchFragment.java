package tom.chinesesuperleague;

import android.support.v4.app.Fragment;
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

public class FetchFragment extends Fragment{

    private CustomListViewAdapter mStatAdapter;
    private String RALF = "61034";
    private String FERNANDO = "168101";
    private String WULEI = "116730";

    public FetchFragment(){
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
            updateStat();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mStatAdapter = new CustomListViewAdapter(getActivity(),R.layout.fragment_players,new ArrayList<ListItem>());

        View rootView = inflater.inflate(R.layout.fragment_players, container, false);
        ListView listView = (ListView)getActivity().findViewById(R.id.list);
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

    private void updateStat(){

        FetchStatTask statTask = new FetchStatTask(getActivity(),mStatAdapter);
        statTask.execute(RALF);
    }

    @Override
    public void onStart(){

        super.onStart();
        updateStat();
    }

}
