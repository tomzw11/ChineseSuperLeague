package tom.chinesesuperleague;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.Uri;

import tom.chinesesuperleague.data.StatContract;

public class FetchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private StatAdapter mStatAdapter;
    private static final int STAT_LOADER = 0;
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

        mStatAdapter = new StatAdapter(getActivity(),null,0);

        View rootView = inflater.inflate(R.layout.fragment_players, container, false);
        ListView listView = (ListView)getActivity().findViewById(R.id.list);
        listView.setAdapter(mStatAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(STAT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void updateStat(){

        FetchStatTask statTask = new FetchStatTask(getActivity());
        statTask.execute(RALF);
    }

    @Override
    public void onStart(){

        super.onStart();
        updateStat();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        // Sort order:  Ascending, by date.
        String sortOrder = StatContract.StatEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = StatContract.StatEntry.buildWeatherLocation();

        return new CursorLoader(getActivity(),
                weatherForLocationUri,
                null,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mStatAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        mStatAdapter.swapCursor(null);
    }

}
