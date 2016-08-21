package io.pcyan.sweetdialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 作者：err0r <br/>
 * 日期: 2016/8/10 17:46 <br/>
 * 描述:
 */
public abstract class BaseDialog extends DialogFragment {

    protected Activity mActivity;

    private boolean isCancelable = true;

    protected View rootView;


    private static volatile BaseDialog mInstance;


    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mActivity = getActivity();

        rootView = inflater.inflate(R.layout.sd_dialog_base, container, false);
        FrameLayout root_rl = (FrameLayout) rootView.findViewById(R.id.root_rl);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCancelable) {
                    dismiss();
                }
            }
        });
        View containView = inflater.inflate(getLayoutId(), root_rl, false);
        containView.setClickable(true);
        root_rl.addView(containView);

        initView(containView);
        initListener();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void initView(View rootView);

    protected abstract void initListener();

    protected abstract void initData();

    public void showDialog(FragmentManager fm, String fragmentTag){
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(fragmentTag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.commit();

        show(fm, fragmentTag);
    }

}
