package todaysmenu.pondar.org.todaysmenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.FragmentGridPagerAdapter;


/**
 * Used for navigating between our different fragments.
 */
public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

    MenuFragment menuFragment;
    ClearFragment clearFragment;
    StatsFragment statsFragment;
    SpeechFragment speechFragment;

    //getters - not all of them are actually used.
    public SpeechFragment getSpeechFragment()
    {
    	return speechFragment;
    }
    
    public ClearFragment getClearFragment()
    {
    	return clearFragment;
    }
    
    public StatsFragment getStatsFragment()
    {
    	return statsFragment;
    }
    
    public MenuFragment getMenuFragment()
    {
    	return menuFragment;
    }

    public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        menuFragment = new MenuFragment();
        clearFragment = new ClearFragment();
        statsFragment = new StatsFragment();
        statsFragment.setContext(ctx); //we need the context in this class
        speechFragment = new SpeechFragment();
    }
    
    
    //update our stats.
    public void notifyStatsSetChanged() {
        statsFragment.updateUI();
    }

    //this we will call when the list of choices has been reset
    public void listViewDataSetChanged()  {
    	menuFragment.resetList();
    }
    
 

    //get the correct view depending on where we are in the UI
    //so screen 1 = the menu, screen 2 = stats, screen 3 = speech input, screen 4 = clearing options
    @Override
    public Fragment getFragment(int row, int col) {
        if (col==0)
        	return menuFragment;  
        else if (col==1)
        	return statsFragment; 
        else if (col==2)
        	return speechFragment;
        else 
        	return clearFragment;
    }

   

    @Override
    public int getRowCount() {
    	return 1;  //we just have 1 row of pages, meaning scrolling horizontally
    }

    @Override
    public int getColumnCount(int rowNum) {
    	return 4; //we just have 4 columns - fixed in this app.
    }
}
