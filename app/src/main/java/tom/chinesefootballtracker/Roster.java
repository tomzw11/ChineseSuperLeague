package tom.chinesefootballtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import tom.chinesefootballtracker.sync.CSLSyncAdapter;

public class Roster {

    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @SuppressWarnings("ResourceType")
    static public @CSLSyncAdapter.PlayerStatus
    int getPlayerStatus(Context c){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getInt(c.getString(R.string.pref_player_status_key), CSLSyncAdapter.PLAYER_STATUS_UNKNOWN);
    }

    public static String imageUrlBuilder(String tag){

        String url = "http://www.sinaimg.cn/ty/opta/players/" + tag + ".jpg";
        return url;
    }

    public static String urlBuilder(String id){

        String url = "http://match.sports.sina.com.cn/football/player_iframe.php?id=" + id + "&season=2016&dpc=1";
        return url;
    }

    public static String urlBioBuilder(String id){

        String url = "http://match.sports.sina.com.cn/football/player.php?id="+id+"&dpc=1";
        return url;
    }

    public static String getPreferredPlayer(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_name_key),
                context.getString(R.string.pref_player_default));
    }

    public static String translatePosition(String position){

        switch(position){

            case("门将"): return "Goadkeeper";
            case("后卫"): return "Defender";
            case("中场"): return "Midfielder";
            case("前锋"): return "Forward";
            default:return position;

        }
    }

    public static String translateGame(String game){

        switch (game){

            case("中超"):return "Chinese Super League";
            case("亚冠"):return "Asian Super Cup";
            default:return game;
        }
    }

    public static String translateClub(String club){

        switch (club){

            case("北京国安"): return "Beijing Guoan";
            case("重庆力帆"): return "Chongqing Lifan";
            case("上海上港"): return "Shanghai SIPG";
            case("长春亚泰"): return "Changchun Yatai";
            case("广州富力"): return "Guangzhou R&F";
            case("广州恒大"): return "Guangzhou Evergrande";
            case("杭州绿城"): return "Hangzhou Greentown";
            case("河北华夏"): return "Hebei China Fortune";
            case("河南建业"): return "Henan Jianye";
            case("天津泰达"): return "Tianjin Teda";
            case("辽宁宏运"): return "Liaoning Whowin";
            case("石家庄永昌"): return "Shijiazhuang Ever Bright";
            case("山东鲁能"): return "Shandong Luneng";
            case("延边富德"): return "Yanbian Funde";
            case("江苏苏宁"): return "Jiangsu Suning";
            case("上海申花"): return "Shanghai Shenhua";
            case("全北现代"): return "Jeonbuk Hyundai";
            case("东京FC"): return "F.C.Tokyo";
            case("越南平阳"): return "Becamex Bình Dương";
            case("首尔FC"): return "FC Seoul";
            case("广岛三箭"): return "Sanfrecce Hiroshima";
            case("武里南联"): return "Buriram United";
            case("墨尔本胜利"): return "Melbourne Victory";
            case("水原三星"): return "Suwon Samsung";
            case("大阪钢巴"): return "Gamba Osaka";
            case("悉尼FC"): return "Sydney FC";
            case("浦和红钻"): return "Urawa Red Diamonds";
            case("浦项制铁"): return "Pohang Steelers";

            default:return club;

        }
    }

    public static String translateNation(String nation){

        switch (nation){

            case "中国":
                return "China";

            case "中华台北":
                return "Taiwan";

            case "中国香港":
                return "Hong Kong";

            case "乌兹别克":
                return "Uzbekistan";

            case "菲律宾":
                return "Philippines";

            case "法国":
                return "France";

            case "意大利":
                return "Italy";

            case "葡萄牙":
                return "Portugal";

            case "以色列":
                return "Israel";

            case "塞尔维亚":
                return "Serbia";

            case "波黑":
                return "Bosnia and Herzegovina";

            case "瑞典":
                return "Sweden";

            case "丹麦":
                return "Denmark";

            case "韩国":
                return "South Korea";

            case "朝鲜":
                return "North Korea";

            case "克罗地亚":
                return "Croatia";

            case "斯洛文尼亚":
                return "Slovenia";

            case "巴西":
                return "Brazil";

            case "阿根廷":
                return "Argentina";

            case "Venezuela":
                return "Venezuela";

            case "委内瑞拉":
                return "Venezuela";

            case "玻利维亚":
                return "Bolivia";

            case "智利":
                return "Chile";

            case "Senegal":
                return "Senegal";

            case "塞内加尔":
                return "Senegal";

            case "刚果":
                return "Congo DR";

            case "Congo DR":
                return "Congo DR";

            case "Gambia":
                return "Gambia";

            case "赞比亚":
                return "Gambia";

            case "Mozambique":
                return "Mozambique";

            case "莫桑比克":
                return "Mozambique";

            case "Gabon":
                return "Gabon";

            case "加蓬":
                return "Gabon";

            case "喀麦隆":
                return "Cameroon";

            case "科特迪瓦":
                return "Ivory Coast";

            case "哥伦比亚":
                return "Columbia";

            case "尼日利亚":
                return "Nigeria";

            case "澳大利亚":
                return "Australia";

            case "土耳其":
                return "Turkey";

            default:return nation;

        }
    }

    public static String convertHeight(String height){

        if(height=="Unknown"||height==null) return height;
        Double height_parse = Double.valueOf(height.substring(0,3));

        int feetPart = 0;
        int inchesPart = 0;

        if (String.valueOf(height_parse).trim().length() != 0) {
            feetPart = (int) Math.floor((height_parse / 2.54) / 12);
            inchesPart = (int) Math.ceil((height_parse / 2.54) - (feetPart * 12));

        }
        return String.format("%d' %d''", feetPart, inchesPart);

    }

    public static String convertAge(String age){

        if(age=="Unknown"||age==null)return age;
        return age.substring(0,2);
    }

    public static String convertNumber(String number){

        if(number=="Unknown"||number==null)return number;
        return number.substring(0,number.length()-1);
    }

    public static int getFlagForNation(String nation){

        switch (nation){

            case "中国":
                return R.drawable.flag_china;

            case "中华台北":
                return R.drawable.flag_taiwan;

            case "中国香港":
                return R.drawable.flag_hongkong;

            case "乌兹别克":
                return R.drawable.flag_uzbekistan;

            case "菲律宾":
                return R.drawable.flag_philippines;

            case "法国":
                return R.drawable.flag_france;

            case "意大利":
                return R.drawable.flag_italy;

            case "葡萄牙":
                return R.drawable.flag_portugal;

            case "以色列":
                return R.drawable.flag_israel;

            case "塞尔维亚":
                return R.drawable.flag_serbia;

            case "波黑":
                return R.drawable.flag_boznia;

            case "瑞典":
                return R.drawable.flag_sweden;

            case "丹麦":
                return R.drawable.flag_denmark;

            case "韩国":
                return R.drawable.flag_southkorea;

            case "朝鲜":
                return R.drawable.flag_northkorea;

            case "克罗地亚":
                return R.drawable.flag_crotia;

            case "斯洛文尼亚":
                return R.drawable.flag_slovenia;

            case "巴西":
                return R.drawable.flag_brazil;

            case "阿根廷":
                return R.drawable.flag_argentina;

            case "Venezuela":
                return R.drawable.flag_venezuela;

            case "委内瑞拉":
                return R.drawable.flag_venezuela;

            case "玻利维亚":
                return R.drawable.flag_bolivia;

            case "智利":
                return R.drawable.flag_chile;

            case "Senegal":
                return R.drawable.flag_senegal;

            case "塞内加尔":
                return R.drawable.flag_senegal;

            case "刚果":
                return R.drawable.flag_congodr;

            case "Congo DR":
                return R.drawable.flag_congodr;

            case "Gambia":
                return R.drawable.flag_gambia;

            case "赞比亚":
                return R.drawable.flag_gambia;

            case "Mozambique":
                return R.drawable.flag_mozambique;

            case "莫桑比克":
                return R.drawable.flag_mozambique;

            case "Gabon":
                return R.drawable.flag_gabon;

            case "加蓬":
                return R.drawable.flag_gabon;

            case "喀麦隆":
                return R.drawable.flag_cameroon;

            case "科特迪瓦":
                return R.drawable.flag_ivorycoast;

            case "哥伦比亚":
                return R.drawable.flag_columbia;

            case "尼日利亚":
                return R.drawable.flag_nigeria;

            case "澳大利亚":
                return R.drawable.flag_australia;

            case "土耳其":
                return R.drawable.flag_turkey;




            default:return R.drawable.flag_default;

        }
    }

    public static int getImageForMatchType(String game){

        switch (game){

            case "中超":
                return R.drawable.icon_league;

            case "亚冠":
                return R.drawable.icon_cup;

            default:return R.drawable.match_default;

        }
    }

    public static String getStringForMatchType(String game){

        switch (game){

            case "中超":
                return "LEAGUE";

            case "亚冠":
                return "AFC CUP";

            default:return "UNKNOWN";

        }
    }

    public static String getResultFromScore(String score){

        if(Integer.valueOf(score.charAt(0))>Integer.valueOf(score.charAt(2))){

            return "Win";

        }else if(Integer.valueOf(score.charAt(0))<Integer.valueOf(score.charAt(2))){

            return "Lose";

        }else return "Draw";

    }

    public static int getBadgeForTeam(String team){

        switch (team){

            case "北京国安":
                return R.drawable.badge_beijing_guoan;
//
//            case "重庆力帆":
//                return R.drawable.badge_chongqing_lifan;

            case "上海上港":
                return R.drawable.badge_shanghai_shanggang;

            case "长春亚泰":
                return R.drawable.badge_changchun_yatai;
//
//            case "广州富力":
//                return R.drawable.badge_guangzhou_fuli;

            case "广州恒大":
                return R.drawable.badge_guangzhou_hengda;

            case "杭州绿城":
                return R.drawable.badge_hangzhou_lvcheng;

            case "河北华夏":
                return R.drawable.badge_heibei_huaxia;
//
//            case "河南建业":
//                return R.drawable.badge_henan_jianye;

            case "天津泰达":
                return R.drawable.badge_tianjin_taida;
//
//            case "辽宁宏运":
//                return R.drawable.badge_liaoning_hongyun;

            case "石家庄永昌":
                return R.drawable.badge_shijiazhuang_yongchang;

            case "山东鲁能":
                return R.drawable.badge_shandong_luneng;

            case "延边富德":
                return R.drawable.badge_yanbian_fude;

            case "江苏苏宁":
                return R.drawable.badge_jiangsu_suning;
//
//            case "上海申花":
//                return R.drawable.badge_shanghai_shenhua;

            case "全北现代":
                return R.drawable.badge_jeonbuk;
//
//            case "东京FC":
//                return R.drawable.badge_fctokyo;

            case "越南平阳":
                return R.drawable.badge_becamex;
//
//            case "首尔FC":
//                return R.drawable.badge_fcseoul;
//
//            case "广岛三箭":
//                return R.drawable.badge_sanfrecce;
//
//            case "武里南联":
//                return R.drawable.badge_buriram;

            case "墨尔本胜利":
                return R.drawable.badge_melbourne;

            case "水原三星":
                return R.drawable.badge_suwon;
//
//            case "大阪钢巴":
//                return R.drawable.badge_gamba;
//
//            case "悉尼FC":
//                return R.drawable.badge_sydney;
//
//            case "浦和红钻":
//                return R.drawable.badge_urawareddiamond;

            case "浦项制铁":
                return R.drawable.badge_pohang;

            default:return R.drawable.badge_default;
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

}
