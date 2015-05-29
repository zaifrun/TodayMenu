
package todaysmenu.pondar.org.todaysmenu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * This class is our fragment for the clear screen (where you can clear the
 * stats and reset the choices in the database
 */
public class ClearFragment extends Fragment implements OnClickListener {


	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.clear, container, false);

        //we set our click listeners for the buttons
    	Button button = (Button) view.findViewById(R.id.clearDataButton);
    	button.setOnClickListener(this);
    	button = (Button) view.findViewById(R.id.clearChoicesButton);
    	button.setOnClickListener(this);
    	
    	return view;
    }

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.clearDataButton) {
			//clear all stats data
			MyDialogFragment dialog = new MyDialogFragment() {
				@Override
				protected void positiveClick() {
					super.positiveClick();
					clearData();
				}

				@Override
				protected void negativeClick() {
					super.negativeClick();
				}
			};
			Bundle bundle = new Bundle();
			bundle.putString("title",getResources().getString(R.string.deleteStatsTitle));
			bundle.putString("message",getResources().getString(R.string.deleteStatsMessage));
			//passing arguments to our dialog instead of the default values
			dialog.setArguments(bundle);
			dialog.show(this.getFragmentManager(),"test"); //test is just a tag - not shown to the user
		}
		else if (v.getId()==R.id.clearChoicesButton)
            //reset choices to the default ones
			clearChoices();
		
	}

	//will clear all the lunch statistics
	public void clearData() {
		//Clear the database
		Database db = new Database(getActivity());
    	db.clearData();
        db.close();

    	//starting confirmation intent - the ConfirmationActivity is a standard wear class
    	Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
 		intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
 		                ConfirmationActivity.SUCCESS_ANIMATION);
 		intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,getResources().getString(R.string.statsDeleted));
 		startActivity(intent);
 		//notify adapter of changes - so the UI for the stats window is updated
        ((SampleGridPagerAdapter) MainActivity.getPager().getAdapter()).notifyStatsSetChanged();


    }

	//Will clear any user added choices and reset to default list.
	public void clearChoices() {
		//Clear the database
		Database db = new Database(getActivity());
    	db.clearChoices();
    	db.close();
    	//notify adapter of changes
 		((SampleGridPagerAdapter)MainActivity.getPager().getAdapter()).listViewDataSetChanged();

    	//starting confirmation intent to show to the user.
    	Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
 		intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
 		                ConfirmationActivity.SUCCESS_ANIMATION);
 		intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,getResources().getString(R.string.choicesDeleted));
 		startActivity(intent);
 	
	}
    
    
    
 
 	
}
