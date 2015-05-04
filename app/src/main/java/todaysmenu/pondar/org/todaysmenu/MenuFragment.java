
package todaysmenu.pondar.org.todaysmenu;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/*
    This class represents the fragment where we will select an item .
 */
public class MenuFragment extends Fragment implements WearableListView.ClickListener {
	
	WearableListView listView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.select, container, false);
    	
		listView =(WearableListView) view.findViewById(R.id.wearable_list);
				
		// Assign an adapter to the list
		if (listView!=null)
		{
			listView.setAdapter(new Adapter(getActivity().getApplicationContext(), Choices.ELEMENTS));
				
			// Set a click listener - using this activity
			listView.setClickListener(this);
			listView.setGreedyTouchMode(true);
		}
    	return view;
    }

    //This is called when we want to add data to our choices and the wearable
    //listview should be updated.
    public void addData(String data) {
    	int len = Choices.ELEMENTS.length;
    	String[] newElements = new String[len+1];
    	System.arraycopy(Choices.ELEMENTS, 0, newElements, 0, len);
    	newElements[len]=data;
    	Choices.ELEMENTS = newElements;
        //update our adapter with the new choices.
    	listView.setAdapter(new Adapter(getActivity().getApplicationContext(), Choices.ELEMENTS));
    	//redraw our list
        listView.invalidate();
    	Database db = new Database(getActivity());
        //put our new choice in the database.
    	db.addChoice(data);
    }

    //reset our list of choices.
    public void resetList()
    { 
    	int len = Choices.ELEMENTS_RESET.length;
    	String[] newElements = new String[len];
        //copy our original choices to elements
    	System.arraycopy(Choices.ELEMENTS_RESET, 0, newElements, 0, len);
    	Choices.ELEMENTS = newElements;
    	//redraw our list.
        listView.setAdapter(new Adapter(getActivity().getApplicationContext(), Choices.ELEMENTS));
    	listView.invalidate();
    }
    
 // WearableListView click listener
 	@Override
 	public void onClick(WearableListView.ViewHolder v) {
 		Integer tag = (Integer) v.itemView.getTag();
 		int index = tag.intValue();
 		
 		// use this data to complete some action ...
 		String chosen = Choices.ELEMENTS[index];
 		Database db = new Database(getActivity());
		db.addFood(chosen);
		
 		Intent intent = new Intent(getActivity().getApplicationContext(), ConfirmationActivity.class);
 		intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
 		                ConfirmationActivity.SUCCESS_ANIMATION);
 		intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,getResources().getString(R.string.saved)+" "+chosen);
 		startActivity(intent);
 		//update the adapter with our stats.
 	
        ((SampleGridPagerAdapter) MainActivity.getPager().getAdapter()).notifyStatsSetChanged();
 	}
 	
 	@Override
 	public void onTopEmptyRegionClick() {
 	}
 	
 	private static final class Adapter extends WearableListView.Adapter {
 	    private String[] mDataset;
 	    private final Context mContext;
 	    private final LayoutInflater mInflater;

 	    // Provide a suitable constructor (depends on the kind of dataset)
 	    public Adapter(Context context, String[] dataset) {
 	        mContext = context;
 	        mInflater = LayoutInflater.from(context);
 	        mDataset = dataset;
 	    }

 	    // Provide a reference to the type of views you're using
 	    public static class ItemViewHolder extends WearableListView.ViewHolder {
 	        private TextView textView;
 	        public ItemViewHolder(View itemView) {
 	            super(itemView);
 	            // find the text view within the custom item's layout
 	            textView = (TextView) itemView.findViewById(R.id.name);
 	        }
 	    }

 	    // Create new views for list items
 	    // (invoked by the WearableListView's layout manager)
 	    @Override
 	    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
 	                                                          int viewType) {
 	        // Inflate our custom layout for list items
 	        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
 	    }

 	    // Replace the contents of a list item
 	    // Instead of creating new views, the list tries to recycle existing ones
 	    // (invoked by the WearableListView's layout manager)
 	    @Override
 	    public void onBindViewHolder(WearableListView.ViewHolder holder,
 	                                 int position) {
 	        // retrieve the text view
 	        ItemViewHolder itemHolder = (ItemViewHolder) holder;
 	        TextView view = itemHolder.textView;
 	        // replace text contents
 	        view.setText(mDataset[position]);
 	        // replace list item's metadata

 	        holder.itemView.setTag(position);
 	    }

 	    // Return the size of your dataset
 	    @Override
 	    public int getItemCount() {
 	        return mDataset.length;
 	    }
 	}
 	
}
