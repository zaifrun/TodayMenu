package todaysmenu.pondar.org.todaysmenu;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by makn on 07-05-2015.
 */
public class MyDialog  extends DialogFragment implements DelayedConfirmationView.DelayedConfirmationListener {

    DelayedConfirmationView delayedView;
    String input = "";


    public static MyDialog newInstance(String input) {
        MyDialog frag = new MyDialog();
        Bundle args = new Bundle();
        args.putString("input", input);
        frag.setArguments(args);
        return frag;
    }


    // User didn't cancel, perform the action

    @Override
    public void onTimerFinished(View view) {
        MenuFragment frag = ((SampleGridPagerAdapter) MainActivity.getPager().getAdapter()).getMenuFragment();
        //add data to the Wearablelistview
        frag.addData(input);
       // frag.addData(textInput);
        dismiss();
    }

    @Override
    public void onTimerSelected(View view) {
        // User canceled, abort the action
        //do nothing - just exit dialog
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),"timer selected",Toast.LENGTH_LONG);
        toast.show();;
        dismiss();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        input = getArguments().getString("input");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog, container, false);
      //  View tv = v.findViewById(R.id.text);
        delayedView =
                (DelayedConfirmationView) v.findViewById(R.id.delayed_confirm);
        delayedView.setListener(this);
        // Two seconds to cancel the action
        delayedView.setTotalTimeMs(2000);
        // Start the timer
        delayedView.start();

        return v;
    }
}

