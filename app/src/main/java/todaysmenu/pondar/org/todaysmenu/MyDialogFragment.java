package todaysmenu.pondar.org.todaysmenu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class MyDialogFragment extends DialogFragment {
	String title = "Confirmation";
	String messsage = "Are you sure";

	public MyDialogFragment()
	{

	}


	@Override
	public Dialog onCreateDialog(Bundle savedBundle) {

		//Here we create a new dialogbuilder;
		System.out.println("Creating dialog");
		AlertDialog.Builder alert = new AlertDialog.Builder(
				getActivity());
		Bundle bundle = getArguments();
		alert.setTitle("Confirmation");
		alert.setMessage("Are you sure?");
		if (bundle!=null)
		{
			String confirmation = bundle.getString("title");
			if (confirmation!=null)
				alert.setTitle(confirmation);
			String message = bundle.getString("message");
			if (message!=null)
				alert.setMessage(message);
		}


	    alert.setPositiveButton("Yes", pListener);
		alert.setNegativeButton("No", nListener);

		return alert.create();
	}

	//This is our positive listener for when the user presses
	//the yes button
	DialogInterface.OnClickListener pListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// these will be executed when user click Yes button
			positiveClick();
		}
	};

	//This is our negative listener for when the user presses
	//the no button.
	DialogInterface.OnClickListener nListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// these will be executed when user click No button
			negativeClick();
		}
	};
	
    //These two methods are empty, because they will
	//be overridden
	protected void positiveClick() 
	{
		
	}
	protected void negativeClick()
	{
		
	}
}
