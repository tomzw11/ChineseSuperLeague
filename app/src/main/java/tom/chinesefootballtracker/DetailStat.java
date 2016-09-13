package tom.chinesefootballtracker;

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
import android.support.v7.widget.Toolbar;

import tom.chinesefootballtracker.data.StatContract.StatEntry;

public class DetailStat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    public static class DetailFragment extends Fragment implements LoaderCallbacks<Cursor> {

        private static final int DETAIL_LOADER = 1;

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
                StatEntry.COLUMN_TAG,
                StatEntry.COLUMN_CNAME,
                StatEntry.COLUMN_FOUL,
                StatEntry.COLUMN_FOULED,
                StatEntry.COLUMN_CLEARANCE,
                StatEntry.COLUMN_DATE,
                StatEntry.COLUMN_GAME,
                StatEntry.COLUMN_SAVE

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
        private static final int COL_CLEARANCE = 17;
        private static final int COL_DATE = 18;
        private static final int COL_GAME = 19;
        private static final int COL_SAVE = 20;

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

            TextView tv_pt = (TextView)getView().findViewById(R.id.fragment_details_playtime);
            tv_pt.setText(data.getString(COL_PLAY_TIME)+"'");

            TextView tv_date = (TextView)getView().findViewById(R.id.detail_main_date);
            tv_date.setText(data.getString(COL_DATE));

            TextView tv_save = (TextView)getView().findViewById(R.id.fragment_details_save);
            tv_save.setText(data.getString(COL_SAVE));

            TextView tv_game = (TextView)getView().findViewById(R.id.detail_scoreview_game);
            tv_game.setText(Roster.translateGame(data.getString(COL_GAME)));

            TextView tv_goal = (TextView)getView().findViewById(R.id.fragment_details_goal);
            tv_goal.setText(data.getString(COL_GOAL));

            TextView tv_kp = (TextView)getView().findViewById(R.id.fragment_details_keypass);
            tv_kp.setText(data.getString(COL_KEY_PASS));

            TextView tv_shots = (TextView)getView().findViewById(R.id.fragment_details_shots);
            tv_shots.setText(data.getString(COL_SHOT)+"/"+data.getString(COL_SHOT_ON_TARGET));

            TextView tv_takeon = (TextView)getView().findViewById(R.id.fragment_details_takeon);
            tv_takeon.setText(data.getString(COL_TAKEON));

            TextView tv_cards = (TextView)getView().findViewById(R.id.fragment_details_cards);
            tv_cards.setText(data.getString(COL_YELLOW_CARD) +"/"+ data.getString(COL_RED_CARD));

            TextView tv_foul = (TextView) getView().findViewById(R.id.fragment_details_foul);
            tv_foul.setText(data.getString(COL_FOUL) + "/" + data.getString(COL_FOULED));

            TextView tv_home = (TextView) getView().findViewById(R.id.detail_main_home_club_name);
            tv_home.setText(Roster.translateClub(data.getString(COL_TEAM)));

            TextView tv_score = (TextView) getView().findViewById(R.id.detail_main_score);
            tv_score.setText(data.getString(COL_SCORE));

            TextView tv_away = (TextView) getView().findViewById(R.id.detail_main_away_club_name);
            tv_away.setText(Roster.translateClub(data.getString(COL_OPPONENT)));

            TextView tv_clearance = (TextView) getView().findViewById(R.id.fragment_details_clearance);
            tv_clearance.setText(data.getString(COL_CLEARANCE));

            ImageView home_team = (ImageView) getView().findViewById(R.id.detail_main_home_club);
            home_team.setImageResource(Roster.getBadgeForTeam(data.getString(COL_TEAM)));

            ImageView away_team = (ImageView) getView().findViewById(R.id.detail_main_away_club);
            away_team.setImageResource(Roster.getBadgeForTeam(data.getString(COL_OPPONENT)));

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) { }
    }
}