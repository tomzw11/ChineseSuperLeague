package tom.chinesefootballtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import tom.chinesefootballtracker.data.StatContract;

public class ScoutFragment extends Fragment {

    //TODO:Remove scout player by swiping listitem.

    private ScoutAdapter mScoutAdapter;
    public static final String SCOUT = "Scout";
    SharedPreferences sp_scout;


    String[] SCOUT_COLUMNS = {

            StatContract.BioEntry.COLUMN_TAG,
            StatContract.BioEntry._ID
    };

    static final int COL_STAT_TAG = 0;

    public ScoutFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        String[] set_scout = {Roster.getPreferredPlayer(getContext())};
        sp_scout = getContext().getSharedPreferences(SCOUT,Context.MODE_PRIVATE);
        sp_scout.edit().putStringSet(SCOUT, new HashSet<String>(Arrays.asList(set_scout))).commit();

        mScoutAdapter = new ScoutAdapter(getContext(),new ArrayList<String>(Arrays.asList(set_scout)));

        for(String s:set_scout){

            mScoutAdapter.add(s);
            System.out.println("set_scout " + s);
        }

        View rootView = inflater.inflate(R.layout.fragment_scout, container, false);

//        View emptyView = rootView.findViewById(R.id.listview_fetch_empty);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_fragment_scout);
//        listView.setEmptyView(emptyView);
        listView.setAdapter(mScoutAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {

        super.onStart();
    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//
//        String sortOrder = StatContract.StatEntry.COLUMN_DATE + " DESC";
//
//        Uri statForPlayerUri = StatContract.BioEntry.buildBioUri(Roster.getPreferredPlayer(getActivity()));
//
//        return new CursorLoader(
//                getActivity(),
//                statForPlayerUri,
//                SCOUT_COLUMNS,
//                null,
//                null,
//                sortOrder);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//
//        mScoutAdapter.swapCursor(cursor);
//
////        updateEmptyView();
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//        mScoutAdapter.swapCursor(null);
//
//    }


}
