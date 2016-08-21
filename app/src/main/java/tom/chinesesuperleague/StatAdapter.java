package tom.chinesesuperleague;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.IntegerRes;
import android.support.v4.content.ContextCompat;
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
        public final ImageView kitView;
        public final TextView nameView;
        public final TextView dateView;
        public final TextView teamView;
        public final TextView scoreView;
        public final TextView oppoView;
        public final TextView ratingView;

        public ViewHolder(View view) {

            iconView = (ImageView) view.findViewById(R.id.listview_stat_icon);
            kitView = (ImageView) view.findViewById(R.id.listview_stat_kit);
            nameView = (TextView) view.findViewById(R.id.listview_stat_name);
            dateView = (TextView) view.findViewById(R.id.listview_stat_date);
            teamView = (TextView) view.findViewById(R.id.listview_stat_team);
            scoreView = (TextView) view.findViewById(R.id.listview_stat_score);
            oppoView = (TextView) view.findViewById(R.id.listview_stat_oppo);
            ratingView = (TextView) view.findViewById(R.id.listview_stat_rating);

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

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String match_type = cursor.getString(FetchFragment.COL_STAT_GAME);
        viewHolder.iconView.setImageResource(Roster.getImageForMatchType(match_type));

        String name = cursor.getString(FetchFragment.COL_STAT_CNAME);
        viewHolder.nameView.setText(name);

        String date = cursor.getString(FetchFragment.COL_STAT_DATE);
        viewHolder.dateView.setText(date);

        String team = cursor.getString(FetchFragment.COL_STAT_TEAM);
        viewHolder.teamView.setText(team);

        viewHolder.kitView.setImageResource(Roster.getKitForTeam(team));

        String score = cursor.getString(FetchFragment.COL_STAT_SCORE);
        viewHolder.scoreView.setText(score);

        String opponent;
        if(cursor.getString(FetchFragment.COL_STAT_HOME_AWAY).equals("主场")){
            opponent = "Home vs. "+ cursor.getString(FetchFragment.COL_STAT_OPPONENT);
        }else if(cursor.getString(FetchFragment.COL_STAT_HOME_AWAY).equals("客场")){
            opponent = "Away vs. "+ cursor.getString(FetchFragment.COL_STAT_OPPONENT);
        }else opponent = "Unknown";
        viewHolder.oppoView.setText(opponent);

        String rating = cursor.getString(FetchFragment.COL_STAT_RATING);
        viewHolder.ratingView.setText(rating);
        if(Double.valueOf(rating)>=8){
            viewHolder.ratingView.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
        }else if (Double.valueOf(rating)>=6){
            viewHolder.ratingView.setBackgroundColor(ContextCompat.getColor(context,R.color.orange));
        }else{
            viewHolder.ratingView.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
        }
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
