package com.tanion.aston.rovery.moonfindr.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tanion.aston.rovery.moonfindr.R;
import com.tanion.aston.rovery.moonfindr.dialog.StoryDialog;
import com.tanion.aston.rovery.moonfindr.model.Story;

/**
 * Created by Aston Tanion on 24/04/2016.
 */
public class StoryFragment extends Fragment {
    private static final String ARG_POSITION = "POSITION";
    private int mPosition = 0;

    private Story[] mStoryBank;

    public static StoryFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        StoryFragment fragment = new StoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
        mStoryBank = new Story[] {
                new Story(R.drawable.img1, R.string.story1),
                new Story(R.drawable.img2, R.string.story2),
                new Story(R.drawable.img4, R.string.story4)
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_view_pager, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.fragment_image_view_container);
        img.setImageResource(mStoryBank[mPosition].getImageResId());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                StoryDialog dialog = StoryDialog.newInstance(mStoryBank[mPosition].getStoryResId());
                dialog.show(fm, StoryDialog.TAG);

            }
        });
        return view;
    }
}
