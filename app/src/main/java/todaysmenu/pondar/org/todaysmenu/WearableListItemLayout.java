package todaysmenu.pondar.org.todaysmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Most of the code in this class is from a Google sample and then modified.
 * This controls the layout of our weareableListItems.
 */

public class WearableListItemLayout extends LinearLayout
	implements WearableListView.OnCenterProximityListener { // implements a listener, so we can change the lookout of center items.

	private ImageView mCircle;
	private TextView mName;

	//color for the faded text
	private final float mFadedTextAlpha;
	//color for the circle not chosen
	private final int mFadedCircleColor;
	//color for the chosen circle in the listview
	private final int mChosenCircleColor;
	
	public WearableListItemLayout(Context context) {
		this(context, null);
	}
	
	public WearableListItemLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	//initializing colors
	public WearableListItemLayout(Context context, AttributeSet attrs,
	                     int defStyle) {
		super(context, attrs, defStyle);
		
		mFadedTextAlpha = 0.5f; //make the non-selected text a bit transparent.
		mFadedCircleColor = Color.GRAY; //So when the circle is not in the center, it should be gray
		mChosenCircleColor = Color.BLUE;  //when it is in the center it should be blue - to indicate selection
	}
	
	// Get references to the icon and text in the item layout definition
	// that we will use for later.
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// These are defined in the layout file for list items
		mCircle = (ImageView) findViewById(R.id.circle);
		mName = (TextView) findViewById(R.id.name);
	}

    //This method will specify the layout of the items in the wearablelistview
    //when they are selected (in the center position of the watch)
	@Override
	public void onCenterPosition(boolean animate) {
		mName.setAlpha(1f); //no transparency in center
		mName.setTextSize(35); //Bigger text size when in center
		mName.setTextColor(Color.BLUE); //the text is blue
		((GradientDrawable)mCircle.getDrawable()).setColor(mChosenCircleColor);
	}

    //This method will specify the layout of NON-centered items in the wearablelistview
    //for items that are not selected.
	@Override
	public void onNonCenterPosition(boolean animate) {
		((GradientDrawable) mCircle.getDrawable()).setColor(mFadedCircleColor); //set gray color
		mName.setAlpha(mFadedTextAlpha); //more alpha, more faded
		mName.setTextColor(Color.BLUE); //same text color as in non-selected ones, but smaller and faded a bit.
		mName.setTextSize(25);//smaller text size when NOT in center


	}
}