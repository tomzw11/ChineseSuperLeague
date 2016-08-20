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

            case "长春亚泰":
                return R.drawable.badge_changchun_yatai;

            case "广州富力":
                return R.drawable.badge_guangzhou_fuli;

            case "广州恒大":
                return R.drawable.badge_guangzhou_hengda;

            case "杭州绿城":
                return R.drawable.badge_hangzhou_lvcheng;

            case "河北华夏":
                return R.drawable.badge_heibei_huaxia;

            case "河南建业":
                return R.drawable.badge_henan_jianye;

            case "天津泰达":
                return R.drawable.badge_tianjin_taida;

            case "辽宁宏运":
                return R.drawable.badge_liaoning_hongyun;

            case "石家庄永昌":
                return R.drawable.badge_shijiazhuang_yongchang;

            case "山东鲁能":
                return R.drawable.badge_shandong_luneng;

            case "延边富德":
                return R.drawable.badge_yanbian_fude;

            case "江苏苏宁":
                return R.drawable.badge_jiangsu_suning;

            case "上海申花":
                return R.drawable.badge_shanghai_shenhua;

            default:return R.drawable.badge_default;
        }
    }

    public static int getImageForPlayer(String playerId){

        switch (playerId){

            case "61034":
                return R.drawable.icon_ralf;

            case "168101":
                return R.drawable.icon_fernando;

            case "116730":
                return R.drawable.icon_wulei;

            default:return R.drawable.icon_default;

        }
    }

    public static int getKitForTeam(String team){

        switch (team){

            case "北京国安":
                return R.drawable.kit_beijing_guoan;

            case "重庆力帆":
                return R.drawable.kit_chongqing_lifan;

            case "上海上港":
                return R.drawable.kit_shanghai_shanggang;

            case "长春亚泰":
                return R.drawable.kit_changchun_yatai;

            case "广州富力":
                return R.drawable.kit_guangzhou_fuli;

            case "广州恒大":
                return R.drawable.kit_guangzhou_hengda;

            case "杭州绿城":
                return R.drawable.kit_hangzhou_lvcheng;

            case "河北华夏":
                return R.drawable.kit_hebei_huaxia;

            case "河南建业":
                return R.drawable.kit_henan_jianye;

            case "天津泰达":
                return R.drawable.kit_tianjin_taida;

            case "辽宁宏运":
                return R.drawable.kit_liaoning_hongyun;

            case "石家庄永昌":
                return R.drawable.kit_shijiazhuang_yongchang;

            case "山东鲁能":
                return R.drawable.kit_shandong_luneng;

            case "延边富德":
                return R.drawable.kit_yanbian_fude;

            case "江苏苏宁":
                return R.drawable.kit_jiangsu_suning;

            case "上海申花":
                return R.drawable.kit_shanghai_shenhua;

            default:return R.drawable.kit_default;
        }

    }

    public static String getPlayerName(String playerId){

        switch (playerId){

            case "61034":
                return "拉尔夫";

            case "168101":
                return "费尔南多";

            case "116730":
                return "武磊";

            default:return "Unknown";

        }
    }



}
