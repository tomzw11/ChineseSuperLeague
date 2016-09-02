package tom.chinesesuperleague;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.SeekBar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
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

    public static final String MAIN_RATING_PREF = "MainRatingPreferences";
    public static final String COIN = "Coin";
    public static final String SIZE = "Size";

    SharedPreferences sharedPreferences_size;
    SharedPreferences sharedPreferences_rating;
    SharedPreferences sharedPreferences_coin;

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

        final String player = cursor.getString(COL_PLAYER);
        String team = cursor.getString(COL_TEAM);
        String rating_last = (cursor.getString(COL_RATING));

        final RatingView main_predict_rating = (RatingView) getView().findViewById(R.id.main_predict_ratingView);
        main_predict_rating.setSolidColor("#FF9800");

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.displayGraph);
        fab.setSize(FloatingActionButton.SIZE_MINI);

        ImageView imageView  = (ImageView) getView().findViewById(R.id.main_player_icon);

        imageView.setImageResource(Roster.getImageForPlayer(player));

        TextView tv_cname = (TextView)getView().findViewById(R.id.main_name);
        tv_cname.setText(cursor.getString(COL_CNAME));

        TextView tv_team = (TextView)getView().findViewById(R.id.main_team);
        tv_team.setText(team);

        ImageView imageView1 = (ImageView) getView().findViewById(R.id.main_team_icon);
        imageView1.setImageResource(Roster.getBadgeForTeam(team));

        TextView tv_app = (TextView)getView().findViewById(R.id.fragment_main_appearance);
        String season_app = Integer.toString(cursor.getCount());
        tv_app.setText(season_app);

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
            TextView tv_goal = (TextView)getView().findViewById(R.id.fragment_main_goal);
            tv_goal.setText(Integer.toString(number_of_goals));

            String avg_rating = String.format("%.1f",rating_sum/rating_counter);
            RatingView main_rating = (RatingView) getView().findViewById(R.id.main_ratingView);
            main_rating.setText(avg_rating);

            if(Double.valueOf(avg_rating)>=7){

                main_rating.setSolidColor("#4CAF50");
            }else if (Double.valueOf(avg_rating)>=5.5){
                main_rating.setSolidColor("#FF9800");
            }else{
                main_rating.setSolidColor("#F44336");
            }

            cursor.close();
        }
        //TODO:Clear size when player setting switches.
        sharedPreferences_size = getContext().getSharedPreferences(SIZE, Context.MODE_PRIVATE);
        sharedPreferences_rating = getContext().getSharedPreferences(MAIN_RATING_PREF, Context.MODE_PRIVATE);
        sharedPreferences_coin = getContext().getSharedPreferences(COIN, Context.MODE_PRIVATE);

        if(rating_counter < sharedPreferences_size.getInt(SIZE,0)){

            Toast.makeText(getActivity(),"New Match Updated",Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor_size = sharedPreferences_size.edit();
            editor_size.putInt(SIZE,rating_counter);
            editor_size.commit();

            if(sharedPreferences_rating.getString(player,null) != null &&
                    sharedPreferences_rating.getString(player,null).equals(rating_last)){

                double saved_rating_double = Double.parseDouble(sharedPreferences_rating.getString(player,null));
                double current_rating_double = Double.parseDouble(rating_last);

                if(Math.abs(saved_rating_double - current_rating_double) < 1){

                    //                Toast.makeText(getActivity(),"Congratulations! Prediction Correct",Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("Congratulations! You have earned 20 coins.");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
//TODO:Restore saved rating.             
                    int current_coin = sharedPreferences_coin.getInt(COIN,0);
                    SharedPreferences.Editor editor_coin = sharedPreferences_coin.edit();
                    editor_coin.putInt(COIN,20 + current_coin);
                    editor_coin.commit();
                    TextView tv_coin = (TextView)getView().findViewById(R.id.main_coin);
                    tv_coin.setText(String.valueOf(sharedPreferences_coin.getInt(COIN,0)));

                }

            }

        }

        SeekBar seekBar = (SeekBar) getView().findViewById(R.id.main_predict_input);
        final Button ratingButton = (Button) getView().findViewById(R.id.main_predict_button);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgress = progress;

                double seekBarProgress_double = seekBarProgress;
                String current_saved_rating = String.valueOf(seekBarProgress_double/10);

                main_predict_rating.setText(String.valueOf(current_saved_rating));

                if(Double.valueOf(current_saved_rating)>=7){

                    main_predict_rating.setSolidColor("#4CAF50");
                }else if (Double.valueOf(current_saved_rating)>=5.5){
                    main_predict_rating.setSolidColor("#FF9800");
                }else{
                    main_predict_rating.setSolidColor("#F44336");
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        ratingButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                SharedPreferences.Editor editor_player = sharedPreferences_rating.edit();

                editor_player.putString(player,String.valueOf(ratingButton.getText()));
                editor_player.commit();

                Toast.makeText(getActivity(),"Rating Saved",Toast.LENGTH_LONG).show();

            }

        });

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

}