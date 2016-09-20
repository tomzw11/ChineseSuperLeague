package tom.chinesefootballtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoutAdapter extends CursorAdapter{


    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_SUMMARY = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    public static class ViewHolder {
        public final TextView nameView;


        public ViewHolder(View view) {

            nameView = (TextView) view.findViewById(R.id.listview_stat_name);

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

        String name = cursor.getString(FetchFragment.COL_STAT_LNAME);
        viewHolder.nameView.setText(name);

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
