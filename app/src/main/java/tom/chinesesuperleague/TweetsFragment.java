package tom.chinesesuperleague;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.SearchTimeline;

import io.fabric.sdk.android.Fabric;

public class TweetsFragment extends ListFragment{

    //TODO: Your consumer key and secret should be obfuscated in your source code before shipping.
    //TODO: refresh tweet fragment when initial load fails (work in OnResume in mainactivity.
    private static final String TWITTER_KEY = "tLJBanpU29SUx1hatvYOorAYT";
    private static final String TWITTER_SECRET = "jGDYPILQ27vEq0nd8pGitIbc0uXlog5AEBk9JqJdjsm7fwn00d";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getContext(), new Twitter(authConfig));

        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#chinesesuperleague")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(searchTimeline)
                .build();
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }

}
