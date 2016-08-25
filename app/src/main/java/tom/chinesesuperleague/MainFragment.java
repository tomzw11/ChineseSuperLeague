package tom.chinesesuperleague;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.Menu;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.content.Intent;

import tom.chinesesuperleague.data.StatContract;
import tom.chinesesuperleague.sync.CSLSyncAdapter;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public MainFragment(){

    }

    private static final String[] STAT_COLUMNS = {

            StatContract.StatEntry.COLUMN_TEAM,
            StatContract.StatEntry.COLUMN_CNAME,
            StatContract.StatEntry.COLUMN_GOAL,
            StatContract.StatEntry.COLUMN_PLAYER,
            StatContract.StatEntry.COLUMN_RATING,
            StatContract.StatEntry._ID
    };

    public static final int STAT_LOADER_MAIN = 0;

    public static final int COL_TEAM = 0;
    public static final int COL_CNAME = 1;
    public static final int COL_GOAL = 2;
    public static final int COL_PLAYER = 3;
    public static final int COL_RATING = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle main events.
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mainfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu_settings) {

            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateStat(){

        CSLSyncAdapter.syncImmediately(getActivity());
    }

    // since we read the location when we create the loader, all we need to do is restart things
    void onPlayerChanged( ) {
        updateStat();
        getLoaderManager().restartLoader(STAT_LOADER_MAIN, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.displayGraph);
        fab.setSize(FloatingActionButton.SIZE_MINI);

        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(STAT_LOADER_MAIN, null, this);

        super.onActivityCreated(savedInstanceState);
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

        if (cursor == null || !cursor.moveToFirst()) { return; }

        String player = cursor.getString(COL_PLAYER);
        String team = cursor.getString(COL_TEAM);

        ImageView imageView  = (ImageView) getView().findViewById(R.id.main_player_icon);

        imageView.setImageResource(Roster.getImageForPlayer(player));

        TextView tv_cname = (TextView)getView().findViewById(R.id.main_name);
        tv_cname.setText(cursor.getString(COL_CNAME));

        TextView tv_team = (TextView)getView().findViewById(R.id.main_team);
        tv_team.setText(team);
        System.out.println("main frag team: "+ team);
        ImageView imageView1 = (ImageView) getView().findViewById(R.id.main_team_icon);
        imageView1.setImageResource(Roster.getBadgeForTeam(team));

        TextView tv_app = (TextView)getView().findViewById(R.id.main_appearance);
        String season_app = Integer.toString(cursor.getCount());
        tv_app.setText("Season Appearances: "+ season_app);


        int number_of_goals = 0;
        double rating_sum = 0;
        int rating_counter= 0;
        double[] recent_rating = new double[cursor.getCount()];

        try{
            cursor.moveToPosition(-1);
            while(cursor.moveToNext()){
                number_of_goals += Integer.valueOf(cursor.getString(COL_GOAL));
                rating_sum += Double.valueOf(cursor.getString(COL_RATING));
                recent_rating[rating_counter] = Double.valueOf(cursor.getString(COL_RATING));
                //System.out.println("counter"+rating_counter+"rating"+recent_rating[rating_counter]);
                rating_counter++;
            }
        }finally {
            TextView tv_goal = (TextView)getView().findViewById(R.id.main_goal);
            tv_goal.setText("Season Goals: " + Integer.toString(number_of_goals));

            String avg_rating = String.format("%.1f",rating_sum/rating_counter);
            RatingView main_rating = (RatingView) getView().findViewById(R.id.main_ratingView);
            main_rating.setText(avg_rating);

            if(Double.valueOf(avg_rating)>=8){

                main_rating.setSolidColor("#4CAF50");
            }else if (Double.valueOf(avg_rating)>=6){
                main_rating.setSolidColor("#FF9800");
            }else{
                main_rating.setSolidColor("#F44336");
            }

            cursor.close();
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        System.out.println("loader reset");
    }

}