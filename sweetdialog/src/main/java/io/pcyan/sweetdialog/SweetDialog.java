package io.pcyan.sweetdialog;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * 作者：err0r <br/>
 * 日期: 2016/8/3 17:12 <br/>
 * 描述:
 */
@SuppressWarnings("unused")
public class SweetDialog extends BaseDialog implements View.OnClickListener {

    //property
    private int mGravity = Gravity.CENTER;
    private CharSequence mTitle;
    private
    @StringRes
    int mTitleRes = -1;
    private CharSequence mContent;
    private
    @StringRes
    int mContentRes = -1;
    private boolean autoCancel = true;
    private CharSequence mConfirmText;
    private
    @StringRes
    int mConfirmTextRes = -1;
    private CharSequence mCancelText;
    private
    @StringRes
    int mCancelTextRes = -1;
    private
    @DrawableRes
    int mCustomImage = -1;
    private boolean isLoading = false;

    private
    @LayoutRes
    int mCustomLayout = -1;

    private OnSweetClickListener mOnConfirmListener;
    private OnSweetClickListener mOnCancelListener;


    public interface OnSweetClickListener {
        void onClick(SweetDialog sweetDialog);
    }


    private CustomViewCallBack customViewCallBack;


    public interface CustomViewCallBack {
        void onViewCreated(View view);
    }

    //View
    private TextView title_tv;
    private TextView content_tv;
    private LinearLayout root_rl;
    private Button cancel_btn;
    private Button confirm_btn;
    private ImageView image_iv;
    private MaterialProgressBar loading_mpb;

    private @SweetType int currentType = NORMAL_TYPE;

    @IntDef({
            NORMAL_TYPE,
            ERROR_TYPE,
            SUCCESS_TYPE,
            WARNING_TYPE,
            PROGRESS_TYPE,
            CUSTOM_IMAGE_TYPE,
            CUSTOM_LAYOUT_TYPE
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface SweetType {}

    public final static int NORMAL_TYPE = 0;
    public final static int ERROR_TYPE = 1;
    public final static int SUCCESS_TYPE = 2;
    public final static int WARNING_TYPE = 3;
    public final static int PROGRESS_TYPE = 4;
    public final static int CUSTOM_IMAGE_TYPE = 5;
    public final static int CUSTOM_LAYOUT_TYPE = 6;


    private static volatile SweetDialog mInstance;

    public static SweetDialog build() {
        mInstance = new SweetDialog();
        mInstance.setStyle(DialogFragment.STYLE_NORMAL, R.style.SweetDialogStyle);
        return mInstance;
    }

    public SweetDialog setGravity(int gravity) {
        this.mGravity = gravity;
        return mInstance;
    }

    public SweetDialog setTitle(@StringRes int title) {
        checkIsNormal("setTitle()");
        mTitleRes = title;
        return mInstance;
    }

    public SweetDialog setTitle(CharSequence title) {
        checkIsNormal("setTitle()");
        mTitle = title;
        return mInstance;
    }

    public SweetDialog setContent(@StringRes int content) {
        checkIsNormal("setContent()");
        mContentRes = content;
        return mInstance;
    }

    public SweetDialog setContent(CharSequence content) {
        checkIsNormal("setContent()");
        mContent = content;
        return mInstance;
    }

    public SweetDialog setConfirmText(@StringRes int confirmText) {
        checkIsNormal("setConfirmText()");
        mConfirmTextRes = confirmText;
        return mInstance;
    }

    public SweetDialog setConfirmText(CharSequence confirmText) {
        checkIsNormal("setConfirmText()");
        mConfirmText = confirmText;
        return mInstance;
    }

    public SweetDialog setCancelText(@StringRes int cancelText) {
        checkIsNormal("setCancelText()");
        mCancelTextRes = cancelText;
        return mInstance;
    }

    public SweetDialog setCancelText(CharSequence cancelText) {
        checkIsNormal("setCancelText()");
        mCancelText = cancelText;
        return mInstance;
    }

    public SweetDialog autoCancel(boolean autoCancel) {
        checkIsNormal("autoCancel()");
        this.autoCancel = autoCancel;
        return mInstance;
    }

    public SweetDialog setOnConfirmListener(OnSweetClickListener listener) {
        mOnConfirmListener = listener;
        return mInstance;
    }

    public SweetDialog setOnCancelListener(OnSweetClickListener listener) {
        mOnCancelListener = listener;
        return mInstance;
    }

    public SweetDialog setCancelAble(boolean cancelAble) {
        super.setCancelable(cancelAble);
        return mInstance;
    }

    public SweetDialog setCustomImage(@DrawableRes int image) {

        mCustomImage = image;
        return mInstance;
    }

    public SweetDialog setCustomView(@LayoutRes int layout) {
        return setCustomView(layout, null);
    }

    public SweetDialog setCustomView(@LayoutRes int layout, CustomViewCallBack customViewCallBack) {
        if (mTitleRes != -1 || mContentRes != -1 || !TextUtils.isEmpty(mTitle) || !TextUtils.isEmpty(mContent)) {
            throw new IllegalStateException("You cannot use setCustomView() when you have content set.");
        } else if (isLoading) {
            throw new IllegalStateException("ou cannot use setCustomView() with a progress dialog");
        }
        this.mCustomLayout = layout;
        this.customViewCallBack = customViewCallBack;
        return mInstance;
    }

    public SweetDialog isLoading(boolean isLoading,@StringRes int tipRes) {
        if (mCustomLayout != -1) {
            throw new IllegalStateException("You cannot use isLoading() when you are use a custom view.");
        }
        this.mTitleRes = tipRes;
        this.isLoading = true;

        return mInstance;
    }

    public SweetDialog isLoading(boolean isLoading,String tip) {
        if (mCustomLayout != -1) {
            throw new IllegalStateException("You cannot use isLoading() when you are use a custom view.");
        }
        this.mTitle = tip;
        this.isLoading = true;

        return mInstance;
    }

    private boolean checkIsNormal(String methodName) {
        if (mCustomLayout != -1) {
            throw new IllegalStateException("You cannot use "+methodName+" when you are use a custom view.");

        } else if (isLoading) {
            throw new IllegalStateException("You cannot use "+methodName+" with a progress dialog");
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        if (mCustomLayout != -1) {
            return mCustomLayout;
        } else {
            return R.layout.sd_dialog_normal;
        }
    }

    @Override
    protected void initView(View rootView) {
        if (mCustomLayout != -1) {
                rootView.setBackgroundResource(R.drawable.bg_round_fff);
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rootView.getLayoutParams();
//                layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.sweet_dialog_margin);
//                layoutParams.rightMargin = (int) getResources().getDimension(R.dimen.sweet_dialog_margin);
//                rootView.setLayoutParams(layoutParams);
            if (this.customViewCallBack != null) {
                //init root view before use it
                customViewCallBack.onViewCreated(rootView);
            }
        } else {
            title_tv = (TextView) rootView.findViewById(R.id.title_tv);
            content_tv = (TextView) rootView.findViewById(R.id.content_tv);
            root_rl = (LinearLayout) rootView.findViewById(R.id.root_rl);
            cancel_btn = (Button) rootView.findViewById(R.id.cancel_btn);
            confirm_btn = (Button) rootView.findViewById(R.id.confirm_btn);
            image_iv = (ImageView) rootView.findViewById(R.id.image_iv);
            loading_mpb = (MaterialProgressBar) rootView.findViewById(R.id.loading_mpb);
        }

        setGravity(rootView);
    }

    @Override
    protected void initListener() {
        if (mCustomLayout != -1) {
            return;
        }
        cancel_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (mCustomLayout != -1) {
            return;
        }
        setProperty();
    }

    private void setProperty() {

        //title
        if (mTitleRes != -1) {
            title_tv.setText(mTitleRes);
            title_tv.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mTitle)) {
            title_tv.setText(mTitle);
            title_tv.setVisibility(View.VISIBLE);
        } else {
            title_tv.setVisibility(View.GONE);
        }

        //content
        if (mContentRes != -1) {
            content_tv.setText(mContentRes);
            content_tv.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mContent)) {
            content_tv.setText(mContent);
            content_tv.setVisibility(View.VISIBLE);
        } else {
            content_tv.setVisibility(View.GONE);

        }

        //custom image
        if (mCustomImage != -1) {
            image_iv.setImageResource(mCustomImage);
            image_iv.setVisibility(View.VISIBLE);
        }

        //confirm btn
        if (mConfirmTextRes != -1) {
            confirm_btn.setText(mConfirmTextRes);
        } else if (!TextUtils.isEmpty(mConfirmText)) {
            confirm_btn.setText(mConfirmText);
        }
        if (mOnConfirmListener != null) {
            confirm_btn.setVisibility(View.VISIBLE);
        } else {
            confirm_btn.setVisibility(View.GONE);
        }

        //cancel btn
        if (mCancelTextRes != -1) {
            cancel_btn.setText(mCancelTextRes);
        } else if (!TextUtils.isEmpty(mCancelText)) {
            cancel_btn.setText(mCancelText);
        }
        if (mOnCancelListener != null) {
            cancel_btn.setVisibility(View.VISIBLE);
        } else {
            cancel_btn.setVisibility(View.GONE);
        }

        if(isLoading){
            loading_mpb.setVisibility(View.VISIBLE);
        }else{
            loading_mpb.setVisibility(View.GONE);
        }

    }

    private void setGravity(View rootView) {
        //gravity
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        layoutParams.gravity = mGravity;
        rootView.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.cancel_btn) {
            // Cancel
            if (mOnCancelListener != null) {
                mOnCancelListener.onClick(this);
            }
        } else if (i == R.id.confirm_btn) {
            //Confirm
            if (mOnConfirmListener != null) {
                mOnConfirmListener.onClick(this);
            }
        }

        if (autoCancel) {
            dismiss();
        }
    }

}
