package tom.chinesesuperleague;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import tom.chinesesuperleague.data.StatContract.StatEntry;

public class DetailStat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //TODO:Debug toolbar. DetailFragment disappears when toolbar is enabled.
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_detail_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment implements LoaderCallbacks<Cursor> {

        private static final int DETAIL_LOADER = 1;

        private String mPlayer;

        private static final String[] STAT_COLUMNS = {

                StatEntry.TABLE_NAME + "." + StatEntry._ID,
                StatEntry.COLUMN_TEAM,
                StatEntry.COLUMN_OPPONENT,
                StatEntry.COLUMN_HOME_AWAY,
                StatEntry.COLUMN_PLAY_TIME,
                StatEntry.COLUMN_SHOT,
                StatEntry.COLUMN_SHOT_ON_TARGET,
                StatEntry.COLUMN_TAKEON,
                StatEntry.COLUMN_GOAL,
                StatEntry.COLUMN_SCORE,
                StatEntry.COLUMN_KEY_PASS,
                StatEntry.COLUMN_YELLOW_CARD,
                StatEntry.COLUMN_RED_CARD,
                StatEntry.COLUMN_PLAYER,
                StatEntry.COLUMN_CNAME,
                StatEntry.COLUMN_FOUL,
                StatEntry.COLUMN_FOULED
        };

        // these constants correspond to the projection defined above, and must change if the
        // projection changes
        private static final int COL_TABLE_NAME = 0;
        private static final int COL_TEAM = 1;
        private static final int COL_OPPONENT = 2;
        private static final int COL_HOME_AWAY = 3;
        private static final int COL_PLAY_TIME = 4;
        private static final int COL_SHOT = 5;
        private static final int COL_SHOT_ON_TARGET = 6;
        private static final int COL_TAKEON = 7;
        private static final int COL_GOAL = 8;
        private static final int COL_SCORE = 9;
        private static final int COL_KEY_PASS = 10;
        private static final int COL_YELLOW_CARD = 11;
        private static final int COL_RED_CARD = 12;
        private static final int COL_PLAYER = 13;
        private static final int COL_CNAME = 14;
        private static final int COL_FOUL = 15;
        private static final int COL_FOULED = 16;



        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView =  inflater.inflate(R.layout.fragment_details, container, false);

            return rootView;
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

//            ImageView tv_image = (ImageView)getView().findViewById(R.id.detail_player_icon);
//            tv_image.setImageResource(Utility.getImageForPlayer(data.getString(COL_PLAYER)));

            TextView tv_pt = (TextView)getView().findViewById(R.id.fragment_details_playtime);
            tv_pt.setText(data.getString(COL_PLAY_TIME)+"'");

//TODO:Modify team/player name strings to English.

            TextView tv_goal = (TextView)getView().findViewById(R.id.fragment_details_goal);
            tv_goal.setText(data.getString(COL_GOAL));
//
            TextView tv_kp = (TextView)getView().findViewById(R.id.fragment_details_keypass);
            tv_kp.setText(data.getString(COL_KEY_PASS));

            TextView tv_shots = (TextView)getView().findViewById(R.id.fragment_details_shots);
            tv_shots.setText(data.getString(COL_SHOT)+"/"+data.getString(COL_SHOT_ON_TARGET));

            TextView tv_takeon = (TextView)getView().findViewById(R.id.fragment_details_takeon);
            tv_takeon.setText(data.getString(COL_TAKEON));
//
            TextView tv_cards = (TextView)getView().findViewById(R.id.fragment_details_cards);
            tv_cards.setText(data.getString(COL_YELLOW_CARD) +"/"+ data.getString(COL_RED_CARD));

            TextView tv_foul = (TextView) getView().findViewById(R.id.fragment_details_foul);
            tv_foul.setText(data.getString(COL_FOUL) + "/" + data.getString(COL_FOULED));

            TextView tv_home = (TextView) getView().findViewById(R.id.main_grid_home);
            tv_home.setText(data.getString(COL_TEAM));

            TextView tv_score = (TextView) getView().findViewById(R.id.main_grid_score);
            tv_score.setText(data.getString(COL_SCORE));

            TextView tv_away = (TextView) getView().findViewById(R.id.main_grid_away);
            tv_away.setText(data.getString(COL_OPPONENT));

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) { }
    }
}