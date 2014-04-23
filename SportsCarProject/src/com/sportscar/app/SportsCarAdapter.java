package com.sportscar.app;

/********************************************************
 * 
 * 
 * @author: Mohammad Hasan Khan email: rajibhasan11@gmail.com
 * @date: 19/04/2014
 * 
 * ********************************************/
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SportsCarAdapter extends ArrayAdapter implements ISportsCar {
	
	private LayoutInflater mInflater;
	private final int mRowLayout;
	private final Context mContext;
	private CopyOnWriteArrayList<ConcurrentHashMap<String, String>> mDataSet = new CopyOnWriteArrayList<ConcurrentHashMap<String, String>>();
	
	private static class ViewHolder {
		TextView manufacturerModel;
		TextView origin;
		RelativeLayout rowContainer;
	}
	
	public SportsCarAdapter(Context context, int res, CopyOnWriteArrayList<ConcurrentHashMap<String, String>> dataSet) {
		super(context, res, dataSet);
		this.mContext = context;
		this.mDataSet = dataSet;
		this.mRowLayout = res;
		initialize();
	}
	
	private void initialize() {
	    this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getViewTypeCount(){
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		return ((position%2)==0) ? 0 : 1;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ConcurrentHashMap<String, String> currentItem = (ConcurrentHashMap<String, String>) mDataSet.get(position);
		if (convertView == null) {
			view = newView(parent);
		} else {
			view = convertView;
		}
		int type = getItemViewType(position);
		setCustomView(view, type, currentItem);
		return view;
	}
	
	private int[] colors = {Color.WHITE, Color.parseColor("#f7f7f7")};

	protected void setCustomView(View v, int type, Map<String, String> currentItem) {
		try {
			ViewHolder holder = (ViewHolder) v.getTag();
			holder.rowContainer.setBackgroundColor(colors[type]);
			bindView(v, currentItem);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	protected View newView(ViewGroup parent) {
		View v = mInflater.inflate(mRowLayout, parent, false);
		ViewHolder holder = new ViewHolder();
		holder.manufacturerModel = (TextView) v.findViewById(R.id.manufacturerModel);
		holder.origin = (TextView) v.findViewById(R.id.origin);
		holder.rowContainer = (RelativeLayout) v.findViewById(R.id.rowContainer);
		v.setTag(holder);
		return v;
	}

	protected void bindView(View view, Map<String, String> currentItem) {
		ViewHolder holder = (ViewHolder) view.getTag();
		String manufacturer = currentItem.get(MANUFACTURER);
		String model = currentItem.get(MODEL);
		holder.manufacturerModel.setText(manufacturer + " (" + model + ")");
		String origin = currentItem.get(ORIGIN);
		holder.origin.setText(origin);
		
		view.setTag(R.id.id, currentItem.get(ID));
	}
	
}

