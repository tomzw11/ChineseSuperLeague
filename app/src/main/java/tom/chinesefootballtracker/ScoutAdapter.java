package tom.chinesefootballtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class ScoutAdapter extends ArrayAdapter<String>{


    public ScoutAdapter(Context context, ArrayList<String> s) {

        super(context, 0, s);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Get the data item for this position
        String s = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.scout_item, parent, false);
        }
        TextView tv_scout = (TextView)view.findViewById(R.id.listview_scout_name);
        tv_scout.setText(s);

        // Return the completed view to render on screen
        return view;
    }

//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//
//
//        View view = LayoutInflater.from(context).inflate(R.layout.scout_item, parent, false);
//
//        ViewHolder viewHolder = new ViewHolder(view);
//        view.setTag(viewHolder);
//
//        return view;
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//
//        ViewHolder viewHolder = (ViewHolder) view.getTag();
//
//        String name = cursor.getString(ScoutFragment.COL_STAT_TAG);
//        viewHolder.nameView.setText(name);
//
//    }

}
