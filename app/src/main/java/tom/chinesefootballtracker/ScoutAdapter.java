package tom.chinesefootballtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import tom.chinesefootballtracker.data.StatContract;

public class ScoutAdapter extends CursorAdapter{

    public static class ViewHolder {

        public final TextView nameView;
        public final TextView teamView;
        public final TextView positionView;
        public final ImageView iconView;
        public final Switch switchView;

        public ViewHolder(View view) {

            nameView = (TextView) view.findViewById(R.id.listview_scout_name);
            teamView = (TextView) view.findViewById(R.id.listview_scout_team);
            positionView = (TextView) view.findViewById(R.id.listview_scout_position);
            iconView = (ImageView) view.findViewById(R.id.listview_scout_icon);
            switchView = (Switch) view.findViewById(R.id.switch_scout);
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
    public void bindView(View view, final Context context, final Cursor cursor) {

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

        viewHolder.switchView.setChecked(true);
        viewHolder.switchView.setTag(ScoutFragment.COL_STAT_TAG);

        viewHolder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (!bChecked) {
                    Log.d("bindview","onchecked");
                    int deleteCount = context.getContentResolver().delete(StatContract.BioEntry.buildBioUri(cursor.getString(ScoutFragment.COL_STAT_TAG)),null,null);
                    //mScoutAdapter.notifyDataSetChanged();
                    Toast.makeText(context,"Player Deleted",Toast.LENGTH_SHORT);

                }
            }
        });


    }

}