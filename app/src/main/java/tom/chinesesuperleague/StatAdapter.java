package tom.chinesesuperleague;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TextView;

import tom.chinesesuperleague.data.StatContract;

public class StatAdapter extends CursorAdapter{

    public StatAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
    }

    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.stat_item, parent, false);
        return view;
    }


    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

        ImageView iconView = (ImageView) view.findViewById(R.id.listview_stat_icon);
        iconView.setImageResource(R.drawable.ralf2);

        String date = cursor.getString(FetchFragment.COL_STAT_DATE);
        TextView dateView = (TextView)view.findViewById(R.id.listview_stat_date);
        dateView.setText(date);

        String team = cursor.getString(FetchFragment.COL_STAT_TEAM);
        TextView teamView = (TextView)view.findViewById(R.id.listview_stat_team);
        teamView.setText(team);

        String score = cursor.getString(FetchFragment.COL_STAT_SCORE);
        TextView scoreView = (TextView)view.findViewById(R.id.listview_stat_score);
        scoreView.setText(score);

    }
}
