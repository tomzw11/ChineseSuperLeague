package tom.chinesefootballtracker;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.content.Intent;
import android.widget.AdapterView;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.TextView;

import tom.chinesefootballtracker.data.StatContract;
import tom.chinesefootballtracker.sync.CSLSyncAdapter;

public class FetchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SharedPreferences.OnSharedPreferenceChangeListener {
//TODO:Use viewstub for hidden views in listitem/latestitem.
    private StatAdapter mStatAdapter;
    private String preferPlayer;
    private static final int STAT_LOADER_FETCH = 1;

    private static final String[] STAT_COLUMNS = {

            StatContract.StatEntry.COLUMN_DATE,
            StatContract.StatEntry.COLUMN_TEAM,
            StatContract.StatEntry.COLUMN_SCORE,
            StatContract.StatEntry.COLUMN_TAG,
            StatContract.StatEntry.COLUMN_OPPONENT,
            StatContract.StatEntry.COLUMN_LNAME,
            StatContract.StatEntry.COLUMN_HOME_AWAY,
            StatContract.StatEntry.COLUMN_GAME,
            StatContract.StatEntry.COLUMN_RATING,

            StatContract.StatEntry._ID
    };

    static final int COL_STAT_DATE = 0;
    static final int COL_STAT_TEAM = 1;
    static final int COL_STAT_SCORE = 2;
    static final int COL_STAT_PLAYER = 3;
    static final int COL_STAT_OPPONENT = 4;
    static final int COL_STAT_LNAME = 5;
    static final int COL_STAT_HOME_AWAY = 6;
    static final int COL_STAT_GAME = 7;
    static final int COL_STAT_RATING = 8;

    public FetchFragment() {
        //setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle main events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
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

        if (id == R.id.action_menu_settings) {

            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mStatAdapter = new StatAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_stat, container, false);

        View emptyView = rootView.findViewById(R.id.listview_fetch_empty);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_fragment_stat);
        listView.setEmptyView(emptyView);
        listView.setAdapter(mStatAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    Uri detailUri = StatContract.StatEntry.buildStatUriWithNameAndDate
                            (cursor.getString(COL_STAT_PLAYER), cursor.getString(COL_STAT_DATE));
                    Intent intent = new Intent(getActivity(), DetailStat.class)
                            .setData(detailUri);

                    ActivityOptionsCompat activityOptions =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
                    ActivityCompat.startActivity(getActivity(), intent, activityOptions.toBundle());
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        getLoaderManager().initLoader(STAT_LOADER_FETCH, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    void onPlayerChanged( ) {

        getLoaderManager().restartLoader(STAT_LOADER_FETCH, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String sortOrder = StatContract.StatEntry.COLUMN_DATE + " DESC";

        Uri statForPlayerUri = StatContract.StatEntry.buildStatUriWithName(Roster.getPreferredPlayer(getActivity()));

        return new CursorLoader(
                getActivity(),
                statForPlayerUri,
                STAT_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        mStatAdapter.swapCursor(cursor);

        updateEmptyView();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        mStatAdapter.swapCursor(null);

    }
    /*
    Updates the empty list view with contextually relevant information that the user can
    use to determine why they aren't seeing weather.
 */
    private void updateEmptyView() {
        if ( mStatAdapter.getCount() == 0 ) {
            TextView tv = (TextView) getView().findViewById(R.id.listview_fetch_empty);
            if ( null != tv ) {
                // if cursor is empty, why? do we have an invalid location
                int message = R.string.no_info_available;
                @CSLSyncAdapter.PlayerStatus int player = Roster.getPlayerStatus(getActivity());
                switch (player) {
                    case CSLSyncAdapter.PLAYER_STATUS_SERVER_DOWN:
                        message = R.string.no_info_available_SERVER_DOWN;
                        break;
                    case CSLSyncAdapter.PLAYER_STATUS_SERVER_INVALID:
                        message = R.string.no_info_available_SERVER_INVALID;
                        break;
                    default:
                        if (!Roster.isNetworkAvailable(getActivity()) ) {
                            message = R.string.no_info_available_NO_NETWORK;
                        }
                }
                tv.setText(message);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if ( key.equals(getString(R.string.pref_player_status_key)) ) {
            updateEmptyView();
        }
    }

}


