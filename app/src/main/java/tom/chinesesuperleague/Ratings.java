package tom.chinesesuperleague;
import java.lang.*;

public class Ratings {

    public static String getRating(
                                String score,String home_away,String appearance,String play_time,
                                String shot,String shot_on_target,String goal,String keypass,
                                String foul,String fouled,String clearance,String takeon,
                                String yellowcard,String redcard,String position,String save){

        double rating;
        double base_rating;
        double score_us = score.charAt(0);
        double score_them = score.charAt(2);
        double num_of_goal = Double.parseDouble(goal);
        double num_of_save = Double.parseDouble(save);
        double num_of_shot = Double.parseDouble(shot);
        double num_of_shot_on_target = Double.parseDouble(shot_on_target);
        double num_of_keypass = Double.parseDouble(keypass);
        double num_of_foul = Double.parseDouble(foul);
        double num_of_fouled = Double.parseDouble(fouled);
        double num_of_takeon = Double.parseDouble(takeon);
        double num_of_clearance = Double.parseDouble(clearance);
        double num_of_yellowcard = Double.parseDouble(yellowcard);
        double num_of_redcard = Double.parseDouble(redcard);

        //Drawing.
        if(score_us == score_them){

            base_rating = 6;

            switch (position){

                case "门将":

                    rating = base_rating +
                            num_of_save*0.25 +
                            num_of_keypass*0.2 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.2) +
                            num_of_clearance*0.2 +
                            num_of_yellowcard*(-0.1) +
                            num_of_redcard*(-2);
                    if(score_them==0) rating+=1;
                    if(score_them>=3) rating-=0.5;
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "后卫":

                    rating = base_rating +
                            num_of_goal*0.25 +
                            num_of_takeon*0.1 +
                            num_of_shot_on_target*0.1 +
                            num_of_keypass*0.1 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.2) +
                            num_of_clearance*0.25 +
                            num_of_yellowcard*(-0.2) +
                            num_of_redcard*(-2);
                    if(score_them==0) rating+=0.3;
                    if(score_them>=3) rating-=0.3;
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "中场":

                    rating = base_rating +
                            num_of_goal*0.25 +
                            num_of_takeon*0.25 +
                            num_of_shot_on_target*0.25 +
                            num_of_keypass*0.25 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.2) +
                            num_of_clearance*0.1 +
                            num_of_yellowcard*(-0.25) +
                            num_of_redcard*(-2);
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "前锋":

                    rating = base_rating +
                            num_of_goal*0.2 +
                            num_of_takeon*0.1 +
                            num_of_shot_on_target*0.2 +
                            num_of_keypass*0.2 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.1) +
                            num_of_yellowcard*(-0.2) +
                            num_of_redcard*(-2);
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                default:return "0";

            }
        //Winning.
        }else if(score_us > score_them){


            base_rating = 6.25;

            switch (position){

                case "门将":

                    rating = base_rating +
                            num_of_save*0.3 +
                            num_of_keypass*0.2 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.2) +
                            num_of_clearance*0.2 +
                            num_of_yellowcard*(-0.1) +
                            num_of_redcard*(-1.5);
                    if(score_them==0) rating+=1;
                    if(score_them>=3) rating-=0.3;
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "后卫":

                    rating = base_rating +
                            num_of_goal*0.25 +
                            num_of_takeon*0.1 +
                            num_of_shot_on_target*0.1 +
                            num_of_keypass*0.1 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.2) +
                            num_of_clearance*0.3 +
                            num_of_yellowcard*(-0.1) +
                            num_of_redcard*(-1.5);
                    if(score_them==0) rating+=0.5;
                    if(score_them>=3) rating-=0.3;
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "中场":

                    rating = base_rating +
                            num_of_goal*0.3 +
                            num_of_takeon*0.3 +
                            num_of_shot_on_target*0.3 +
                            num_of_keypass*0.3 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.2) +
                            num_of_clearance*0.1 +
                            num_of_yellowcard*(-0.25) +
                            num_of_redcard*(-1.5);
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "前锋":

                    rating = base_rating +
                            num_of_goal*0.25 +
                            num_of_takeon*0.1 +
                            num_of_shot_on_target*0.2 +
                            num_of_keypass*0.2 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.1) +
                            num_of_yellowcard*(-0.2) +
                            num_of_redcard*(-1.5);
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                default:return "0";

            }
            //Losing.
        }else{

            base_rating = 5.5;
            switch (position){

                case "门将":

                    rating = base_rating +
                            num_of_save*0.2 +
                            num_of_keypass*0.1 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.2) +
                            num_of_clearance*0.1 +
                            num_of_yellowcard*(-0.1) +
                            num_of_redcard*(-2.5);
                    if(score_them>=3) rating-=0.3;
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "后卫":

                    rating = base_rating +
                            num_of_goal*0.2 +
                            num_of_takeon*0.1 +
                            num_of_shot_on_target*0.1 +
                            num_of_keypass*0.1 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.25) +
                            num_of_clearance*0.2 +
                            num_of_yellowcard*(-0.2) +
                            num_of_redcard*(-2.5);
                    if(score_them>=3) rating-=0.5;
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "中场":

                    rating = base_rating +
                            num_of_goal*0.2 +
                            num_of_takeon*0.2 +
                            num_of_shot_on_target*0.2 +
                            num_of_keypass*0.2 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.2) +
                            num_of_clearance*0.1 +
                            num_of_yellowcard*(-0.2) +
                            num_of_redcard*(-2.5);
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                case "前锋":

                    rating = base_rating +
                            num_of_goal*0.2 +
                            num_of_takeon*0.1 +
                            num_of_shot_on_target*0.1 +
                            num_of_keypass*0.2 +
                            num_of_fouled*0.1 +
                            num_of_foul*(-0.1) +
                            num_of_yellowcard*(-0.2) +
                            num_of_redcard*(-2.5);
                    if (rating > 10)rating = 10;
                    if (rating < 0) rating = 0;
                    return Double.toString(rating);

                default:return "0";

            }
        }
    }
}
