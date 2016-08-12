package tom.chinesesuperleague.data;

import android.provider.BaseColumns;
import android.content.ContentResolver;
import android.net.Uri;
import java.util.HashMap;

public class StatContract {

    public static final String CONTENT_AUTHORITY = "tom.chinesesuperleague.data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PLAYER = "player";

    /* Inner class that defines the contents of the stat table */
    public static final class StatEntry implements BaseColumns {


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLAYER).build();

        public static Uri buildDateUri(long id) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
        }

        public static Uri buildStatUriWithName(String name){

            return CONTENT_URI.buildUpon().appendPath(name).build();
        }


        public static String getPlayerSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }



        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER;

        public static final String TABLE_NAME = "stat";

        public static final String COLUMN_CNAME = "cname";
        public static final String COLUMN_PLAYER = "player";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_GAME = "game";
        public static final String COLUMN_TEAM = "team";
        public static final String COLUMN_OPPONENT = "opponent";
        public static final String COLUMN_HOME_AWAY = "home_away";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_APPREARANCE = "appearance";
        public static final String COLUMN_PLAY_TIME = "play_time";
        public static final String COLUMN_SHOT = "shot";
        public static final String COLUMN_SHOT_ON_TARGET = "shot_on_target";
        public static final String COLUMN_GOAL = "goal";
        public static final String COLUMN_KEY_PASS = "key_pass";
        public static final String COLUMN_FOUL = "foul";
        public static final String COLUMN_FOULED = "fouled";
        public static final String COLUMN_CLEARANCE = "clearance";
        public static final String COLUMN_TAKEON = "takeon";
        public static final String COLUMN_SAVE = "save";
        public static final String COLUMN_YELLOW_CARD = "yellow_card";
        public static final String COLUMN_RED_CARD = "red_card";

    }

//    public static final class PlayerEntry implements BaseColumns{
//
//        public static final String TABLE_NAME = "player";
//        public static final String COLUMN_PLAYER_NAME = "player_name";
//        public static final String COLUMN_PLAYER_ID = "player_id";
//
//        public static String getPlayerName(String playerId){
//
//
//
//            return null;
//        }
//
//    }


}
