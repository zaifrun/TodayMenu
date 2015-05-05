
package todaysmenu.pondar.org.todaysmenu;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;



/**
 * This fragment will show the stats screen of the various selected menus
 */
public class StatsFragment extends Fragment {

	LinearLayout parent;
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public StatsFragment() {

    }


	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.stats, container, false);
    	parent = (LinearLayout) view.findViewById(R.id.statslayout);
    	updateUI(); //make sure the UI is updated when the fragment is shown.
    	
    	return view;
    }


    //This method will update the UI by reading from the database and creating the stats view
    public void updateUI()
    {
    	Database db = new Database(context);
    	ArrayList<Item> items = db.getStats();
    	Collections.sort(items); //items are now sorted according to the frequency
    	//first delete all children of parent, except first one
    	//first child is the "statistics" textview, always there
    	int children = parent.getChildCount();
    	if (children>1) //do we have more than the "statistics" label, then remove them
    	{
    		parent.removeViews(1, children-1);
    	}

        //go through all the items and add them as individually text view.
    	for (Item item : items)
    	{
    		TextView text = new TextView(getActivity());
    		
    		String p = String.format("%.1f", item.getPercent());
    		text.setText(item.getName() + " : "+item.getFreq()+ " ("+p+" %)");
    		text.setTextColor(Color.WHITE);
    		text.setTextSize(18);
    		text.setLayoutParams(new LayoutParams(
    		        LayoutParams.WRAP_CONTENT,
    		        LayoutParams.WRAP_CONTENT));
    		parent.addView(text); //add the textview to the layout.
    	}
    }
    
 
 	
}
