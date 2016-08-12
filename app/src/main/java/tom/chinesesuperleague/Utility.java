package tom.chinesesuperleague;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Utility {

    public static String urlBuilder(String id){

        String url = "http://match.sports.sina.com.cn/football/player_iframe.php?id="+ id + "&season=2016&dpc=1";
        return url;
    }

    public static String getPreferredPlayer(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_name_key),
                context.getString(R.string.pref_player_default));
    }

    public static String getPlayerName(String playerId){

        switch (playerId){

            case "61034":
                return "拉尔夫";

            case "168101":
                return "武磊";

            case "116730":
                return "费尔南多";

            default:return null;

        }
    }



}
