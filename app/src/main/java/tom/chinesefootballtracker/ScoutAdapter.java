package tom.chinesefootballtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ScoutAdapter extends CursorAdapter{

    public static class ViewHolder {

        public final TextView nameView;
        public final TextView teamView;
        public final TextView positionView;
        public final ImageView iconView;

        public ViewHolder(View view) {

            nameView = (TextView) view.findViewById(R.id.listview_scout_name);
            teamView = (TextView) view.findViewById(R.id.listview_scout_team);
            positionView = (TextView) view.findViewById(R.id.listview_scout_position);
            iconView = (ImageView) view.findViewById(R.id.listview_scout_icon);
        }
    }

    public ScoutAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        View view = LayoutInflater.from(context).inflate(R.layout.scout_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String tag = cursor.getString(ScoutFragment.COL_STAT_TAG);
        Glide.with(context).load(Roster.imageUrlBuilder(tag)).into(viewHolder.iconView);
        Glide.with(context).load(Roster.imageUrlBuilder(tag)).error(R.drawable.icon_default);

        String name = cursor.getString(ScoutFragment.COL_STAT_LNAME);
        viewHolder.nameView.setText(name);

        String team = cursor.getString(ScoutFragment.COL_STAT_TEAM);
        viewHolder.teamView.setText(team);

        String position = cursor.getString(ScoutFragment.COL_STAT_POSITION);
        viewHolder.positionView.setText(position);

    }

}