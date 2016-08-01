package tom.chinesesuperleague.data;

import android.provider.BaseColumns;
import android.text.format.Time;

public class StatContract {

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    /*
        Inner class that defines the contents of the location table
     */
    public static final class PlayerEntry implements BaseColumns {

        public static final String TABLE_NAME = "player";

        public static final String COLUMN_PLAYER_SETTING = "player_setting";

        // Human readable location string, provided by the API.  Because for styling,
        // "Mountain View" is more recognizable than 94043.
        public static final String PLAYER_NAME = "player_name";

    }

    /* Inner class that defines the contents of the stat table */
    public static final class StatEntry implements BaseColumns {

        public static final String TABLE_NAME = "stat";

        // Column with the foreign key into the location table.
        public static final String COLUMN_PLAYER_KEY = "player_id";
        // Date, stored as long in milliseconds since the epoch
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


}
