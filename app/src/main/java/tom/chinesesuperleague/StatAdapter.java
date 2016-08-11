package tom.chinesesuperleague;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

public class StatAdapter extends CursorAdapter{



    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_SUMMARY = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView teamView;
        public final TextView scoreView;



        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.listview_stat_icon);
            dateView = (TextView) view.findViewById(R.id.listview_stat_date);
            teamView = (TextView) view.findViewById(R.id.listview_stat_team);
            scoreView = (TextView) view.findViewById(R.id.listview_stat_score);
        }
    }

    public StatAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

    // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_SUMMARY: {
                layoutId = R.layout.stat_item_latest;
                break;
            }
            case VIEW_TYPE_ITEM: {
                layoutId = R.layout.stat_item;
                break;
            }
        }
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }


    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.iconView.setImageResource(R.drawable.ralf2);

        String date = cursor.getString(FetchFragment.COL_STAT_DATE);
        viewHolder.dateView.setText(date);

        String team = cursor.getString(FetchFragment.COL_STAT_TEAM);
        viewHolder.teamView.setText(team);

        String score = cursor.getString(FetchFragment.COL_STAT_SCORE);
        viewHolder.scoreView.setText(score);

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_SUMMARY : VIEW_TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }
}
