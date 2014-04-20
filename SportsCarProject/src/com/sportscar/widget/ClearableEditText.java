/**
 * 
 * @Author : Mohammad Hasan Khan
 * @Date  : 
 * 
 * **/

package com.sportscar.widget;

import com.sportscar.app.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ClearableEditText extends LinearLayout {

	
	private ImageView clear;
	
	private EditText edit_text;
	
	private Context mContext;
	
	private int emailInputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
	private int pwdInputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

	
	public ClearableEditText(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		this.mContext = paramContext;
		LayoutInflater.from(paramContext).inflate(R.layout.clearable_edit_text, this, true);
		findViews();
		setOnFocusListener();
		setOnClearClicked();
		setValues(paramContext, paramAttributeSet);
	}

	private void setOnClearClicked() {
		ImageView crossView = this.clear;
		crossView.setTag(this.edit_text);
		crossView.setOnClickListener(clickListener);
    }
	
	private void setOnFocusListener() {
		EditText et = this.edit_text;
		et.setTag(this.clear);
		et.setOnFocusChangeListener(focusListener);
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			((EditText) ((ImageView) view).getTag()).setText("");
		}
	};
	
	private View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View focusedView, boolean hasFocus) {
		    if (hasFocus) {
		    	((ImageView)(focusedView).getTag()).setVisibility(VISIBLE);
		    	return;
		    } else {
		    	((ImageView)(focusedView).getTag()).setVisibility(GONE);
		    	return;
		    }
		}
	};

	@SuppressWarnings("deprecation")
	private void setValues(Context paramContext, AttributeSet paramAttributeSet) {
		int[] attrs = new int[] { R.attr.text, R.attr.hint, R.attr.textColor, R.attr.hintColor, R.attr.textPassword, R.attr.background, R.attr.max_chars };
		TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, attrs);
		String str1 = localTypedArray.getString(0);
		String str2 = localTypedArray.getString(1);
		int i = localTypedArray.getColor(2, paramContext.getResources().getColor(R.color.black));
		int j = localTypedArray.getColor(3, paramContext.getResources().getColor(R.color.gray_2));
		boolean bool = localTypedArray.getBoolean(4, false);
		Drawable localDrawable = localTypedArray.getDrawable(5);
		int k = localTypedArray.getInt(6, -1);
		if (k > 0) {
			EditText localEditText = this.edit_text;
			InputFilter[] arrayOfInputFilter = new InputFilter[1];
			arrayOfInputFilter[0] = new InputFilter.LengthFilter(k);
			localEditText.setFilters(arrayOfInputFilter);
		}
		
		this.edit_text.setText(str1);
		this.edit_text.setHint(str2);
		this.edit_text.setTextColor(i);
		this.edit_text.setHintTextColor(j);
		this.edit_text.setSingleLine(true);
		
		if(bool) {
			this.edit_text.setInputType(pwdInputType);
		} else {
			this.edit_text.setInputType(emailInputType);
		}
		((LinearLayout) findViewById(R.id.content)).setBackgroundDrawable(localDrawable);
		localTypedArray.recycle();
	}

	public boolean checkEmpty() {
		boolean bool = TextUtils.isEmpty(this.edit_text.getText().toString());
		if (bool) {
			setError(R.string.lg_input_email_empty);
		}
		return bool;
	}

	private void findViews() {
		try {
			this.edit_text = ((EditText) findViewById(R.id.edit_text));
			this.clear = ((ImageView) findViewById(R.id.clear));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EditText getEditText() {
		return this.edit_text;
	}

	public String getText() {
		return this.edit_text.getText().toString().trim();
	}

	public void hideInputMethod() {
		try {
			((InputMethodManager) this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(this.edit_text.getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void requestEditTextFocus() {
		try {
			this.edit_text.requestFocus();
			// showInputMethod();
			return;
		} catch (Exception localException) {
		}
	}

	public void setError(int paramInt) {
		setError(this.mContext.getString(paramInt));
	}

	public void setError(String paramString) {
		try {
			this.edit_text.setError(paramString);
			setSelectionToEnd();
			requestEditTextFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFocusable(boolean paramBoolean) {
		try {
			super.setFocusable(paramBoolean);
			this.edit_text.setFocusable(paramBoolean);
			this.edit_text.setEnabled(paramBoolean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSelection(int paramInt) {
		this.edit_text.setSelection(paramInt);
	}

	public void setSelectionToEnd() {
		setSelection(this.edit_text.getText().toString().length());
	}

	public void setText(String paramString) {
		this.edit_text.setText(paramString);
	}
	
	public void setHint(String paramString) {
		this.edit_text.setHint(paramString);
	}
	
	public void makeItTextView(){
		this.edit_text.setClickable(false);
		this.edit_text.setFocusable(false);
		this.edit_text.setFocusableInTouchMode(false);
	}

	protected void showInputMethod() {
		try {
			((InputMethodManager) this.mContext.getSystemService("input_method")).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}