package tom.chinesesuperleague;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.content.Intent;
import android.widget.AdapterView;

import tom.chinesesuperleague.data.StatContract;


public class FetchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FetchFragment())
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

    public static class FetchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

        private StatAdapter mStatAdapter;
        private static final int STAT_LOADER = 0;

        private static final String[] STAT_COLUMNS = {

                StatContract.StatEntry.COLUMN_DATE,
                StatContract.StatEntry.COLUMN_TEAM,
                StatContract.StatEntry.COLUMN_SCORE,
                StatContract.StatEntry.COLUMN_PLAYER,
                StatContract.StatEntry.COLUMN_OPPONENT,
                StatContract.StatEntry.COLUMN_CNAME,
                StatContract.StatEntry._ID
        };

        static final int COL_STAT_DATE = 0;
        static final int COL_STAT_TEAM = 1;
        static final int COL_STAT_SCORE = 2;
        static final int COL_STAT_PLAYER = 3;
        static final int COL_STAT_OPPONENT = 4;
        static final int COL_STAT_CNAME = 5;


        public FetchFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Add this line in order for this fragment to handle main events.
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.fetchfragment, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.

            int id = item.getItemId();
            if (id == R.id.action_refresh) {

                updateStat();
                return true;
            }

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_menu_settings) {

                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            mStatAdapter = new StatAdapter(getActivity(), null, 0);

            View rootView = inflater.inflate(R.layout.fragment_stat, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.listview_fragment_stat);
            listView.setAdapter(mStatAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // CursorAdapter returns a cursor at the correct position for getItem(), or null
                    // if it cannot seek to that position.
                    Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                    if (cursor != null) {
                        Uri detailUri = StatContract.StatEntry.buildStatUriWithNameAndDate
                                (cursor.getString(COL_STAT_PLAYER), cursor.getString(COL_STAT_DATE));
                        Intent intent = new Intent(getActivity(), DetailStat.class)
                                .setData(detailUri);
                        startActivity(intent);
                    }
                }
            });

            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            getLoaderManager().initLoader(STAT_LOADER, null, this);

            super.onActivityCreated(savedInstanceState);
        }

        private void updateStat() {

            FetchStatTask statTask = new FetchStatTask(getActivity());
            String player = Utility.getPreferredPlayer(getActivity());

            getLoaderManager().restartLoader(STAT_LOADER, null, this);
            statTask.execute(player);
        }

        @Override
        public void onStart() {

            super.onStart();
            updateStat();
        }

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

            String sortOrder = StatContract.StatEntry.COLUMN_DATE + " DESC";
            Uri statForPlayerUri = StatContract.StatEntry.buildStatUriWithName(Utility.getPreferredPlayer(getActivity()));
            System.out.println("Main Page onCreateLoader uri: " + statForPlayerUri);

            return new CursorLoader(getActivity(),
                    statForPlayerUri,
                    STAT_COLUMNS,
                    null,
                    null,
                    sortOrder);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

            mStatAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> cursorLoader) {

            mStatAdapter.swapCursor(null);
        }

    }

}