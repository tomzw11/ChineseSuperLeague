package tom.chinesefootballtracker;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import tom.chinesefootballtracker.data.StatContract;

public class ScoutFragment extends android.support.v4.app.DialogFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    int mNum;
    private ScoutAdapter mScoutAdapter;

    String[] SCOUT_COLUMNS = {

            StatContract.StatEntry.COLUMN_TAG,

            StatContract.StatEntry._ID
    };

    static final int COL_STAT_LNAME = 0;

    public ScoutFragment(){

    }

    static ScoutFragment newInstance() {
        ScoutFragment f = new ScoutFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle main events.
        mNum = getArguments().getInt("num");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_scout, container, false);

        mScoutAdapter = new ScoutAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_scout, container, false);

        View emptyView = rootView.findViewById(R.id.listview_fetch_empty);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_fragment_scout);
        listView.setEmptyView(emptyView);
        listView.setAdapter(mScoutAdapter);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String sortOrder = StatContract.StatEntry.COLUMN_DATE + " DESC";

        Uri statForPlayerUri = StatContract.BioEntry.buildBioUri(Roster.getPreferredPlayer(getActivity()));

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

//        updateEmptyView();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        mScoutAdapter.swapCursor(null);

    }


}
