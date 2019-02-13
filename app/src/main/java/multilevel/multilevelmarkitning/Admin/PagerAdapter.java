package multilevel.multilevelmarkitning.Admin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter
{
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                Admin_Profile tab1 = new Admin_Profile();
                return tab1;
            case 1:
                Admin_Show tab2 = new Admin_Show();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return mNumOfTabs;
    }
}

