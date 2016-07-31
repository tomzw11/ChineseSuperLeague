package tom.chinesesuperleague;

/**
 * Created by Tom on 7/30/16.
 */
public class Utility {

    public static String urlBuilder(String id){

        String url = "http://match.sports.sina.com.cn/football/player_iframe.php?id="+ id + "&season=2016&dpc=1";
        return url;
    }




}
