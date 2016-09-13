package tom.chinesefootballtracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.text.Spannable;

public class PagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] {"NEWS","PLAYER", "MATCH"};
    private Context context;
    private Fragment mCurrentFragment;

    private String main_tag,fetch_tag;

    private int[] imageResId = {
            R.drawable.ic_textsms_white_24dp,
            R.drawable.ic_person_white_24dp,
            R.drawable.ic_assessment_white_24dp
    };

    public PagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {


        if (position == 0) {
            return new TweetsFragment();
        }  else if(position == 1){
            return new MainFragment();
        }else return new FetchFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
//        // get the tags set by FragmentPagerAdapter
//        switch (position) {
//            case 0:
//                main_tag = createdFragment.getTag();
//                System.out.println("main_tag: " + main_tag);
//                break;
//            case 1:
//                fetch_tag = createdFragment.getTag();
//                System.out.println("fetch_tag: " + fetch_tag);
//
//                break;
//        }
//        // ... save the tags somewhere so you can reference them later
//        return createdFragment;
//    }
//
//    public String getMainPagerAdapterTag(){
//
//        return this.main_tag;
//    }
//
//    public String getFetchPagerAdapterTag(){
//
//        return this.fetch_tag;
//    }

}