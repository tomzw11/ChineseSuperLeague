package tom.chinesesuperleague;
import java.lang.*;

public class Ratings {

    //TODO:Find customized font to show ratings.
    //TODO:Improve rating algorithm.

    public static String getRating(
                                String score,String home_away,String appearance,String play_time,
                                String shot,String shot_on_target,String goal,String keypass,
                                String foul,String fouled,String clearance,String takeon,
                                String yellowcard,String redcard){

        double rating;
        double base_rating;
        double score_us = score.charAt(0);
        double score_them = score.charAt(2);
        double num_of_goal = Double.parseDouble(goal);
        double num_of_shot = Double.parseDouble(shot);
        double num_of_shot_on_target = Double.parseDouble(shot_on_target);
        double num_of_keypass = Double.parseDouble(keypass);
        double num_of_foul = Double.parseDouble(foul);
        double num_of_fouled = Double.parseDouble(fouled);
        double num_of_takeon = Double.parseDouble(takeon);
        double num_of_clearance = Double.parseDouble(clearance);
        double num_of_yellowcard = Double.parseDouble(yellowcard);
        double num_of_redcard = Double.parseDouble(redcard);

        if(score_us == score_them){

            base_rating = 6;
            rating = base_rating + num_of_goal*0.6 + num_of_shot*num_of_goal*0.3 + num_of_shot_on_target*0.1
            +num_of_keypass*0.2 + num_of_fouled*0.1 + num_of_foul*(-0.2) + num_of_takeon*0.2 + num_of_clearance*0.2
            +num_of_yellowcard*(-0.3) + num_of_redcard*(-2.5);

        }else if(score_us > score_them){


            base_rating = 7;
            rating = base_rating + num_of_goal*0.6 + num_of_shot*num_of_goal*0.3 + num_of_shot_on_target*0.1
                    +num_of_keypass*0.2 + num_of_fouled*0.1 + num_of_foul*(-0.2) + num_of_takeon*0.2 + num_of_clearance*0.2
                    +num_of_yellowcard*(-0.3) + num_of_redcard*(-2.5);
        }else{

            base_rating = 5.5;
            rating = base_rating + num_of_goal*0.6 + num_of_shot*num_of_goal*0.3 + num_of_shot_on_target*0.1
                    +num_of_keypass*0.2 + num_of_fouled*0.1 + num_of_foul*(-0.2) + num_of_takeon*0.2 + num_of_clearance*0.2
                    +num_of_yellowcard*(-0.3) + num_of_redcard*(-2.5);
        }

        if (rating > 10)rating = 10;
        if (rating < 0) rating = 0;

        return Double.toString(rating);
    }
}
