package todaysmenu.pondar.org.todaysmenu;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets;



//This is the main activity for our app, where we setup the GridViewPager
public class MainActivity extends Activity
{

	private static GridViewPager pagerGlobal;
	
	public static GridViewPager getPager() {
		return pagerGlobal;
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);
		
		
		 final Resources res = getResources();
	     final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
	     pager.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
	            @Override
	            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
	                // Adjust page margins:
	                //   A little extra horizontal spacing between pages looks a bit
	                //   less crowded on a round display.
					// following lines are from Google.
	                final boolean round = insets.isRound();
	                int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
	                int colMargin = res.getDimensionPixelOffset(round ?
	                        R.dimen.page_column_margin_round : R.dimen.page_column_margin);
	                pager.setPageMargins(rowMargin, colMargin);

	                // GridViewPager relies on insets to properly handle
	                // layout for round displays. They must be explicitly
	                // applied since this listener has taken them over.
	                pager.onApplyWindowInsets(insets);
	                return insets;
	            }
	        });
            //create a new adapter
	        pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager()));
	        pagerGlobal = pager;
            //setup our DotsPageIndicator to use this pagerAdapter
	        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
	        dotsPageIndicator.setPager(pager);
	        Database db = new Database(this);
	        db.readChoices(); //when the app starts we need to read the choices from the database file
		
	}
	
}
