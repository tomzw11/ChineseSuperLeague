package tom.chinesesuperleague;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by Tom on 7/31/16.
 */
public class CustomListViewAdapter extends ArrayAdapter<ListItem>{

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<ListItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView playerImage;
        TextView playerForm;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ListItem ListItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_players, null);
            holder = new ViewHolder();
            holder.playerForm = (TextView) convertView.findViewById(R.id.form);
            holder.playerImage = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.playerForm.setText(ListItem.getPlayerForm());
        holder.playerImage.setImageResource(ListItem.getPlayerId());

        return convertView;
    }
}



