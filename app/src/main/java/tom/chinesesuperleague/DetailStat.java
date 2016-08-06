package tom.chinesesuperleague;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tom.chinesesuperleague.data.StatContract.StatEntry;

public class DetailStat extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    public static class DetailFragment extends Fragment implements LoaderCallbacks<Cursor> {

        private String mForecast;

        private static final int DETAIL_LOADER = 0;

        private static final String[] STAT_COLUMNS = {
                StatEntry.TABLE_NAME + "." + StatEntry._ID,
                StatEntry.COLUMN_PLAYER_KEY,
                StatEntry.COLUMN_TEAM,
                StatEntry.COLUMN_GOAL,
                StatEntry.COLUMN_PLAY_TIME,
                StatEntry.COLUMN_KEY_PASS,
                StatEntry.COLUMN_YELLOW_CARD,
                StatEntry.COLUMN_RED_CARD
        };

        // these constants correspond to the projection defined above, and must change if the
        // projection changes
        private static final int COL_STAT_ID = 0;
        private static final int COL_PLAYER_KEY = 1;
        private static final int COL_TEAM = 2;
        private static final int COL_GOAL = 3;
        private static final int COL_PLAY_TIME = 4;
        private static final int COL_KEY_PASS = 5;
        private static final int COL_YELLOW_CARD = 6;
        private static final int COL_RED_CARD = 7;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_details, container, false);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detail_fragment, menu);

        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Intent intent = getActivity().getIntent();
            if (intent == null) {
                return null;
            }

            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    intent.getData(),
                    STAT_COLUMNS,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            if (!data.moveToFirst()) { return; }

            TextView tv_team = (TextView)getView().findViewById(R.id.fragment_details_name);
            tv_team.setText(getString(R.string.detail_item_name)+ " "+getString(R.string.p61034));

            TextView tv_name = (TextView)getView().findViewById(R.id.fragment_details_team);
            tv_name.setText(getString(R.string.detail_item_team) +" "+ data.getString(COL_TEAM));

            TextView tv_pt = (TextView)getView().findViewById(R.id.fragment_details_playtime);
            tv_pt.setText(getString(R.string.detail_item_pt)+" "+ data.getString(COL_PLAY_TIME));

            TextView tv_goal = (TextView)getView().findViewById(R.id.fragment_details_goal);
            tv_goal.setText(getString(R.string.detail_item_goal)+" "+ data.getString(COL_GOAL));

            TextView tv_kp = (TextView)getView().findViewById(R.id.fragment_details_keypass);
            tv_kp.setText(getString(R.string.detail_item_kp) +" "+ data.getString(COL_KEY_PASS));

            TextView tv_yc = (TextView)getView().findViewById(R.id.fragment_details_yellowcard);
            tv_yc.setText(getString(R.string.detail_item_yc) +" "+ data.getString(COL_YELLOW_CARD));

            TextView tv_rc = (TextView)getView().findViewById(R.id.fragment_details_redcard);
            tv_rc.setText(getString(R.string.detail_item_rc) +" "+ data.getString(COL_RED_CARD));
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) { }
    }
}