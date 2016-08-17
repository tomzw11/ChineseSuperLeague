package tom.chinesesuperleague;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MainFragment();
        }  else {
            return new FetchActivity.FetchFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}