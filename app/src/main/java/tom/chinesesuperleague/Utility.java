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

    public static int getImageForMatchType(String game){

        switch (game){

            case "中超":
                return R.drawable.csl;

            case "亚冠":
                return R.drawable.afc;

            default:return R.drawable.match_default;
//TODO:Adjust size of images in listitem.xml.
        }
    }

    public static int getBadgeForTeam(String team){

        switch (team){

            case "北京国安":
                return R.drawable.badge_beijing_guoan;

            case "重庆力帆":
                return R.drawable.badge_chongqing_lifan;

            case "上海上港":
                return R.drawable.badge_shanghai_shanggang;

            default:return R.drawable.badge_default;
        }
    }

    public static int getImageForPlayer(String playerId){

        switch (playerId){

            case "61034":
                return R.drawable.ralf;

            case "168101":
                return R.drawable.wulei;

            case "116730":
                return R.drawable.fernando;

            default:return 0;

        }
    }

    public static String getPlayerName(String playerId){

        switch (playerId){

            case "61034":
                return "拉尔夫";

            case "168101":
                return "武磊";

            case "116730":
                return "费尔南多";

            default:return "Unknown";

        }
    }



}
