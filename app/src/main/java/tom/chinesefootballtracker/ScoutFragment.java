package tom.chinesefootballtracker;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import tom.chinesefootballtracker.data.StatContract;

public class ScoutFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //TODO:Remove scout player by swiping listitem.

    private CursorAdapter mScoutAdapter;
    private String url_scout = "SCOUT";
    private static final int SCOUT_LOADER = 1;

    String[] SCOUT_COLUMNS = {

            //StatContract.BioEntry.COLUMN_TAG,
            StatContract.StatEntry.COLUMN_TEAM,

            StatContract.BioEntry.TABLE_NAME + "." + StatContract.BioEntry._ID
    };

    static final int COL_STAT_TAG = 0;
    static final int COL_STAT_TEAM = 0;

    public ScoutFragment(){

    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mScoutAdapter = new ScoutAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_scout, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_fragment_scout);
        listView.setAdapter(mScoutAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SCOUT_LOADER, null, this);

    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String sortOrder = StatContract.StatEntry.COLUMN_DATE + " DESC";

        Uri statForPlayerUri = StatContract.BioEntry.buildBioUri(url_scout);

        return new CursorLoader(
                getActivity(),
                statForPlayerUri,
                SCOUT_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        mScoutAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        mScoutAdapter.swapCursor(null);

    }


}
