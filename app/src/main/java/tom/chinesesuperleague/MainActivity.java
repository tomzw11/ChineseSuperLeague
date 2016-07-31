package tom.chinesesuperleague;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import java.util.ArrayList;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private CustomListViewAdapter mStatAdapter;
    ArrayList<listItem> inputData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        //setHasOptionsMenu(true);
        setContentView(R.layout.activity_main);

        Integer[] playerImage = {R.drawable.fernando,R.drawable.ralf2,R.drawable.wulei};
        String[] playerForm = {"9","8","7"};

        inputData = new ArrayList<>();
        for(int i=0;i<playerImage.length;i++){
            listItem listItem = new listItem(playerImage[i],playerForm[i]);
            inputData.add(listItem);
        }

        ListView listView = (ListView) findViewById(R.id.list);

        mStatAdapter = new CustomListViewAdapter(this,R.layout.fragment_players,inputData);

        listView.setAdapter(mStatAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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



}
