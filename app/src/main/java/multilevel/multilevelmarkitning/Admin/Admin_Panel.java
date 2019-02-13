package multilevel.multilevelmarkitning.Admin;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import multilevel.multilevelmarkitning.R;

public class Admin_Panel extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__panel);

        tabLayout = (TabLayout) findViewById(R.id.H1_tabLayout);
        pager = (ViewPager) findViewById(R.id.H1_viewPager);



        tabLayout.addTab(tabLayout.newTab().setText("Profile")); //set tab layout name
        tabLayout.addTab(tabLayout.newTab().setText("User Show"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter adapter = new PagerAdapter (getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);  //set tablayout in screen

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));  //swipe with figure



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                pager.setCurrentItem(tab.getPosition());   //it is going to H1 pageadapter class
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
}
