package io.pcyan.sweetdialog;

import android.support.annotation.DrawableRes;
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
    private @StringRes int mTitleRes = -1;
    private CharSequence mContent;
    private @StringRes int mContentRes = -1;
    private boolean autoCancel = true;
    private CharSequence mConfirmText;
    private @StringRes int mConfirmTextRes = -1;
    private CharSequence mCancelText;
    private @StringRes int mCancelTextRes = -1;
    private @DrawableRes int mCustomImage = -1;

    private @LayoutRes int mCustomLayout = -1;

    private OnSweetClickListener mOnConfirmListener;
    private OnSweetClickListener mOnCancelListener;


    public interface OnSweetClickListener {
        void onClick(SweetDialog sweetDialog);
    }


    private CustomViewCallBack customViewCallBack;


    public interface CustomViewCallBack{
        void onViewCreated(View view);
    }

    /*private @SweetType int currentType = NORMAL_TYPE;

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
    public final static int CUSTOM_LAYOUT_TYPE = 6;*/

    //View
    private TextView title_tv;
    private TextView content_tv;
    private LinearLayout root_rl;
    private Button cancel_btn;
    private Button confirm_btn;
    private ImageView image_iv;

    /*private static class Builder{

        protected final Context context;

        //property
        protected int mGravity = Gravity.CENTER;
        protected CharSequence mTitle;
        protected CharSequence mContent;
        protected boolean autoCancel = true;
        protected CharSequence mConfirmText;
        protected CharSequence mCancelText;
        protected  @DrawableRes int mCustomImage = -1;

        protected boolean cancelAble;

        protected OnSweetClickListener mOnConfirmListener;
        protected OnSweetClickListener mOnCancelListener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setGravity(int gravity) {
            mGravity = gravity;
            return this;
        }

        public Builder setTitle(@StringRes int title) {
            return setTitle(this.context.getText(title));
        }

        public Builder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        public Builder setContent(@StringRes int content) {
            return setContent(this.context.getText(content));
        }

        public Builder setContent(CharSequence content) {
            mContent = content;
            return this;
        }

        public Builder setConfirmText(@StringRes int confirmText) {
            return setConfirmText(this.context.getText(confirmText));
        }

        public Builder setConfirmText(CharSequence confirmText) {
            mConfirmText = confirmText;
            return this;
        }

        public Builder setCancelText(@StringRes int cancelText) {
            return setCancelText(this.context.getText(cancelText));
        }

        public Builder setCancelText(CharSequence cancelText) {
            mCancelText = cancelText;
            return this;
        }

        public Builder autoCancel(boolean autoCancel) {
            this.autoCancel = autoCancel;
            return this;
        }

        public Builder setOnConfirmListener(OnSweetClickListener listener) {
            mOnConfirmListener = listener;
            return this;
        }

        public Builder setOnCancelListener(OnSweetClickListener listener) {
            mOnCancelListener = listener;
            return this;
        }

        public Builder setCancelAble(boolean cancelAble) {
            this.cancelAble = cancelAble;
            return this;
        }

        public Builder setCustomImage(@DrawableRes int image){

            mCustomImage = image;
            return this;
        }

        public SweetDialog build(){

            SweetDialog sweetDialog = new SweetDialog();

            sweetDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.SweetDialogStyle);
            sweetDialog.setCancelable(this.cancelAble);

            return sweetDialog;
        }
    }
    */

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
        mTitleRes = title;
        return mInstance;
    }

    public SweetDialog setTitle(CharSequence title) {
        mTitle = title;
        return mInstance;
    }

    public SweetDialog setContent(@StringRes int content) {
        mContentRes = content;
        return mInstance;
    }

    public SweetDialog setContent(CharSequence content) {
        mContent = content;
        return mInstance;
    }

    public SweetDialog setConfirmText(@StringRes int confirmText) {
        mConfirmTextRes = confirmText;
        return mInstance;
    }

    public SweetDialog setConfirmText(CharSequence confirmText) {
        mConfirmText = confirmText;
        return mInstance;
    }

    public SweetDialog setCancelText(@StringRes int cancelText) {
        mCancelTextRes = cancelText;
        return mInstance;
    }

    public SweetDialog setCancelText(CharSequence cancelText) {
        mCancelText = cancelText;
        return mInstance;
    }

    public SweetDialog autoCancel(boolean autoCancel) {
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

    public SweetDialog setCustomImage(@DrawableRes int image){

        mCustomImage = image;
        return mInstance;
    }

    public SweetDialog setCustomView(@LayoutRes int layout){
        this.mCustomLayout = layout;
        return mInstance;
    }

    public SweetDialog setCustomView(@LayoutRes int layout, CustomViewCallBack customViewCallBack){
        this.mCustomLayout = layout;
        this.customViewCallBack = customViewCallBack;
        return mInstance;
    }

    @Override
    protected int getLayoutId() {
        if(mCustomLayout!=-1){
            return mCustomLayout;
        }else {
            return R.layout.dialog_sweet_normal;
        }
    }

    @Override
    protected void initView(View rootView) {
        if(mCustomLayout!=-1){
            if (this.customViewCallBack != null) {
                //init root view before use it
                rootView.setBackgroundResource(R.drawable.bg_round_fff);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rootView.getLayoutParams();
                layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.sweet_dialog_margin);
                layoutParams.rightMargin = (int) getResources().getDimension(R.dimen.sweet_dialog_margin);
                rootView.setLayoutParams(layoutParams);
                customViewCallBack.onViewCreated(rootView);
            }
        }else {
            title_tv = (TextView) rootView.findViewById(R.id.title_tv);
            content_tv = (TextView) rootView.findViewById(R.id.content_tv);
            root_rl = (LinearLayout) rootView.findViewById(R.id.root_rl);
            cancel_btn = (Button) rootView.findViewById(R.id.cancel_btn);
            confirm_btn = (Button) rootView.findViewById(R.id.confirm_btn);
            image_iv = (ImageView) rootView.findViewById(R.id.image_iv);
        }
    }

    @Override
    protected void initListener() {
        if(mCustomLayout!=-1){
            return;
        }
        cancel_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if(mCustomLayout!=-1){
            return;
        }
        setProperty();
    }

    private void setProperty() {
        //gravity
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) root_rl.getLayoutParams();
        layoutParams.gravity = mGravity;
        root_rl.setLayoutParams(layoutParams);

        //title
        if (mTitleRes != -1) {
            title_tv.setText(mTitleRes);
            title_tv.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mTitle)) {
            title_tv.setText(mTitle);
            title_tv.setVisibility(View.VISIBLE);
        }else{
            title_tv.setVisibility(View.GONE);
        }
        //content
        if (mContentRes != -1) {
            content_tv.setText(mContentRes);
            content_tv.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mContent)) {
            content_tv.setText(mContent);
            content_tv.setVisibility(View.VISIBLE);
        }else{
            content_tv.setVisibility(View.GONE);

        }

        //custom image
        if(mCustomImage!=-1){
            image_iv.setImageResource(mCustomImage);
            image_iv.setVisibility(View.VISIBLE);
        }

        //confirm btn
        if (mConfirmTextRes != -1) {
            confirm_btn.setText(mConfirmTextRes);
            confirm_btn.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mConfirmText)) {
            confirm_btn.setText(mConfirmText);
            confirm_btn.setVisibility(View.VISIBLE);
        } else {
            confirm_btn.setVisibility(View.GONE);
        }

        //cancel btn
        if (mCancelTextRes != -1) {
            cancel_btn.setText(mCancelTextRes);
            cancel_btn.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mCancelText)) {
            cancel_btn.setText(mCancelText);
            cancel_btn.setVisibility(View.VISIBLE);
        } else {
            cancel_btn.setVisibility(View.GONE);
        }

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
