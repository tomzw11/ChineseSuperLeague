package tom.chinesefootballtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoutAdapter extends CursorAdapter{

    public static class ViewHolder {
        public final TextView nameView;


        public ViewHolder(View view) {

            nameView = (TextView) view.findViewById(R.id.listview_scout_name);

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

        String name = cursor.getString(ScoutFragment.COL_STAT_TEAM);
        viewHolder.nameView.setText(name);

    }

}