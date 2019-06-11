
package com.danggui.publishsystem.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.danggui.publishsystem.R;
import com.danggui.publishsystem.mylistener.FragmentCallBack;

/**
 * 首页
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View contentView;
    private Context context;
    private FragmentCallBack fragmentCallBack;
    private LinearLayout check_linearLayout, publish_linearLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        check_linearLayout = contentView.findViewById(R.id.check_linearLayout);
        publish_linearLayout = contentView.findViewById(R.id.publish_linearLayout);
        check_linearLayout.setOnClickListener(this);
        publish_linearLayout.setOnClickListener(this);
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallBack) {
            fragmentCallBack = (FragmentCallBack) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentCallBack");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallBack = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_linearLayout:
                fragmentCallBack.setPosition(1);
                break;
            case R.id.publish_linearLayout:
                fragmentCallBack.setPosition(2);
                break;
            default:
                break;
        }
    }
}
