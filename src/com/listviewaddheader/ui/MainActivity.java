package com.listviewaddheader.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.listviewaddheader.R;
import com.listviewaddheader.adapter.ListViewAdapter;
import com.listviewaddheader.adapter.TopViewPagerAdapter;
import com.listviewaddheader.model.Information;
import com.listviewaddheader.view.PullDownRefreshListView;
import com.listviewaddheader.view.PullDownRefreshListView.IXListViewListener;

public class MainActivity extends Activity implements OnItemClickListener,
		OnPageChangeListener, IXListViewListener {

	private Context mContext = this;
	private PullDownRefreshListView mListView;

	private LayoutInflater mInflater;
	private View mHeaderView;
	private ListViewAdapter mAdapter;
	private List<Information> mInformationList;
	private List<String> mImageList;
	private ViewPager mTopViewPager;
	private TopViewPagerAdapter mTopViewPagerAdapter;

	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getData();
		getView();
		setListener();
	}

	private void getView() {
		if (mInflater == null) {
			mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		mListView = (PullDownRefreshListView) this.findViewById(R.id.lv);
		mListView.setPullLoadEnable(true);
		mHeaderView = mInflater.inflate(R.layout.viewpager_main, null);
		mTopViewPagerAdapter = new TopViewPagerAdapter(mContext, mImageList);
		mTopViewPager = (ViewPager) mHeaderView.findViewById(R.id.viewpager);
		mTopViewPager.setAdapter(mTopViewPagerAdapter);
		mListView.addHeaderView(mHeaderView);
		//////////////////////////////
		mAdapter = new ListViewAdapter(mContext, mInformationList);
		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		mListView.setOnItemClickListener(this);
		mTopViewPager.setOnPageChangeListener(this);
	}

	public void getData() {
		mInformationList = new ArrayList<Information>();
		for (int i = 0; i < 20; i++) {
			Information information = new Information();
			information.setDesc("第" + i + "条描述");
			information.setTitle("第" + i + "条标题");
			mInformationList.add(information);
		}
		mImageList = new ArrayList<String>();
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/0111174780.jpg");
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/01111959pp.jpg");
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/011121360w.jpg");
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/01112258p9.jpg");
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/01112527zp.jpg");

	}

	public void getData2() {
		mInformationList = new ArrayList<Information>();
		for (int i = 0; i < 10; i++) {
			Information information = new Information();
			information.setDesc("第" + i + i + "条描述");
			information.setTitle("第" + i + i + "条标题");
			mInformationList.add(information);
		}
		mImageList = new ArrayList<String>();
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/0111174780.jpg");
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/01111959pp.jpg");
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/011121360w.jpg");
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/01112258p9.jpg");
		mImageList
				.add("http://ys.rili.com.cn/images/image/201401/01112527zp.jpg");

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub
		dosomething(parent.getAdapter().getItem(position));
	}

	private void dosomething(Object item) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, ((Information) item).getTitle(), 1000).show();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		Date mDate = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String date = format.format(mDate);
		mListView.setRefreshTime(date);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
//				mInformationList.clear();
//				mImageList.clear();
				mImageList = Arrays.asList(Constant.BANNER_URLS);
				mTopViewPagerAdapter.setAdapterData(mImageList);
				
				Information mInformation = new Information();
				mInformation.setDesc("123"+start);
				mInformation.setTitle("456"+start);
				mInformationList.add(mInformation);
				mAdapter.setadapterData(mInformationList);
				
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Information mInformation = new Information();
				mInformation.setDesc("123"+start);
				mInformation.setTitle("456"+start);
				mInformationList.add(mInformation);
//				getData2();
//				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

}
