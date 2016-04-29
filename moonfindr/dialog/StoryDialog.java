package com.tanion.aston.rovery.moonfindr.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.tanion.aston.rovery.moonfindr.R;

/**
 * Created by Aston Tanion on 29/04/2016.
 */
public class StoryDialog extends DialogFragment {
    public static final String TAG = "StoryDialog";
    private static final String ARG_RES_ID = "resid";

    private int mTextResId;

    public static StoryDialog newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ARG_RES_ID, id);
        StoryDialog fragment = new StoryDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextResId = getArguments().getInt(ARG_RES_ID);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_story, null, false);

        EditText editText = (EditText) view.findViewById(R.id.story_edit_text);
        editText.setText(getResources().getString(mTextResId));

        return new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.display_menu_story))
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }
}