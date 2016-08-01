package tom.chinesesuperleague;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Tom on 7/31/16.
 */
public class DetailStat extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);

        Intent intent = this.getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {


            int playerId = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
            TextView textView = (TextView)this.findViewById(R.id.form_detail);
            textView.setText(playerId);

        }

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new DetailFragment())
//                    .commit();
//        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class DetailFragment extends Fragment {
//
//        public DetailFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//
//            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
//
//            Intent intent = getActivity().getIntent();
//            if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
//
//
//                int playerId = intent.getIntExtra(Intent.EXTRA_TEXT,0);
//                ((TextView) rootView.findViewById(R.id.form_detail)).setText(playerId);
//
//            }
//            return rootView;
//        }
//    }


}
