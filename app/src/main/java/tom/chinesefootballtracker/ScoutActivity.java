package tom.chinesefootballtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ScoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_scout, new ScoutFragment())
                    .commit();
        }

    }

}
