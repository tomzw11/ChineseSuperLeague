package tom.chinesesuperleague;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.content.Intent;


import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import io.fabric.sdk.android.Fabric;
import tom.chinesesuperleague.data.StatContract;
import tom.chinesesuperleague.sync.CSLSyncAdapter;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //TODO: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "tLJBanpU29SUx1hatvYOorAYT";
    private static final String TWITTER_SECRET = "jGDYPILQ27vEq0nd8pGitIbc0uXlog5AEBk9JqJdjsm7fwn00d";

    public MainFragment(){

    }

    private static final String[] STAT_COLUMNS = {

            StatContract.StatEntry.COLUMN_TEAM,
            StatContract.StatEntry.COLUMN_CNAME,
            StatContract.StatEntry.COLUMN_GOAL,
            StatContract.StatEntry.COLUMN_PLAYER,
            StatContract.StatEntry._ID
    };

    public static final int STAT_LOADER_MAIN = 0;

    public static final int COL_TEAM = 0;
    public static final int COL_CNAME = 1;
    public static final int COL_GOAL = 2;
    public static final int COL_PLAYER = 3;

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

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getContext(), new Twitter(authConfig));

        final LinearLayout mLinearLayout = (LinearLayout) rootView.findViewById(R.id.twitter_view);
        if(mLinearLayout==null)System.out.println("mLinearLayout null");

        // TODO: Base this Tweet ID on some data from elsewhere in your app
        long tweetId = 631879971628183552L;
        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                TweetView tweetView = new TweetView(getActivity(),result.data);

                mLinearLayout.addView(tweetView);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Load Tweet failure", exception);
            }
        });

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
        Uri statForPlayerUri = StatContract.StatEntry.buildStatUriWithName(Utility.getPreferredPlayer(getActivity()));

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

        imageView.setImageResource(Utility.getImageForPlayer(player));

        TextView tv_cname = (TextView)getView().findViewById(R.id.main_name);
        tv_cname.setText(cursor.getString(COL_CNAME));

        TextView tv_team = (TextView)getView().findViewById(R.id.main_team);
        tv_team.setText(team);
        System.out.println("main frag team: "+ team);
        ImageView imageView1 = (ImageView) getView().findViewById(R.id.main_team_icon);
        imageView1.setImageResource(Utility.getBadgeForTeam(team));

        TextView tv_app = (TextView)getView().findViewById(R.id.main_appearance);
        String season_app = Integer.toString(cursor.getCount());
        tv_app.setText("Season Appearances: "+ season_app);

        int number_of_goals = 0;
        try{
            while(cursor.moveToNext()){
                number_of_goals += Integer.valueOf(cursor.getString(COL_GOAL));
            }
        }finally {
            TextView tv_goal = (TextView)getView().findViewById(R.id.main_goal);
            tv_goal.setText("Season Goals: " + Integer.toString(number_of_goals));
            cursor.close();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        System.out.println("loader reset");
    }


}
