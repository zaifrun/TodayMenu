
package todaysmenu.pondar.org.todaysmenu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



/*
    This fragment uses the speech fragment to get user inputs about new choices from
    the user. Speech input is an important part of Android Wear platform.
 */
public class SpeechFragment extends Fragment implements OnClickListener {

    //just a number, so we can use this as the request code for the activity.
	private final int SPEECH_CODE = 1;
	private TextView textView;
	private String textInput = ""; //default input - nothing to start with

    /*
    * This method is called when the speech recognizer is done.
    * */
	@Override
	public void onActivityResult(int requestCode, int resultCode,
	        Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SPEECH_CODE && resultCode == Activity.RESULT_OK) {
	        List<String> results = data.getStringArrayListExtra(
	                RecognizerIntent.EXTRA_RESULTS);
	        String spokenText = results.get(0); //we just want the first word, nothing else!!
	        // Do something with spokenText - format it, just be sure we
            //have the first character as uppercase and the rest as lowercase.
	 		textInput = spokenText.substring(0,1).toUpperCase() + spokenText.substring(1).toLowerCase();
            //update the text field.
	 		textView.setText("Speech Input: "+textInput);

	    }
	}

    /*
     * Called when the users presses the button to save the input to our menu of choices.
     *
     */
	public void addData() {

        //First show a confirmation screen to the user.
		Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
 		intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
 		                ConfirmationActivity.SUCCESS_ANIMATION);
 		intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,getResources().getString(R.string.choiceAdded));
 		startActivity(intent);
 		//notify adapter of changes
		MenuFragment frag = ((SampleGridPagerAdapter) MainActivity.getPager().getAdapter()).getMenuFragment();
		frag.addData(textInput);
	}



	//method for starting the speech recognizer by using an Intent.
	private void displaySpeechRecognizer() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		// Start the activity, the intent will be populated with the speech text
		startActivityForResult(intent, SPEECH_CODE);
	}

    //setting up our listeners for the view.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.speech, container, false);
    	Button button = (Button) view.findViewById(R.id.speechButton);
    	button.setOnClickListener(this);
    	button = (Button) view.findViewById(R.id.addItemButton);
    	button.setOnClickListener(this);
    	textView = (TextView) view.findViewById(R.id.speechText);
		textInput = "";

		return view;
    }

    // Our onclick listener.
	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.speechButton) {
			displaySpeechRecognizer();
			//notice that on the emulator, the button
			//is actually still in the pressed state
			//after displaying the speech recognizer
			//this I have not seen on real hardware, must
			//be a bug.
		}
		else if (v.getId()==R.id.addItemButton) {
			//first check if we have some input
			if (textInput.length()>0)
				addData();
			else {
				//no input - show toast to the user.
				Toast toast = Toast.makeText(getActivity().getApplicationContext(),"No input to add",Toast.LENGTH_LONG);
				toast.show();;
			}
		}

		
	}
	
	
    
    
    
 
 	
}
