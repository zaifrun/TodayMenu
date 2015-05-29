package todaysmenu.pondar.org.todaysmenu;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Martin on 29-05-2015.
 */
public class MyWearDialog  extends DialogFragment implements View.OnClickListener {
        static MyWearDialog newInstance() {
            return new MyWearDialog();
        }

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


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.cancel_btn) {
            dismiss(); //just do nothing
        }
        else if (v.getId()==R.id.ok_btn) {
            clearChoices();//do something
            dismiss(); //then dismiss
        }
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.weardialog, container, false);
            View button = (View) view.findViewById(R.id.cancel_btn);
            button.setOnClickListener(this);
            button = (View) view.findViewById(R.id.ok_btn);
            button.setOnClickListener(this);
           // View tv = v.findViewById(R.id.text);
            // ((TextView)tv).setText("This is an instance of MyDialogFragment");
            return view;
        }
}
