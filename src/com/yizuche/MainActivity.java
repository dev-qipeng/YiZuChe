package com.yizuche;

import static com.example.yizuche.R.id.tv_describe;
import static com.example.yizuche.R.id.tv_photo;
import static com.example.yizuche.R.id.tv_time;
import static com.example.yizuche.R.id.tv_title;

import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.example.yizuche.R;
import com.yizuche.adapter.BaseAdapterHelper;
import com.yizuche.adapter.QuickAdapter;
import com.yizuche.bean.BikeInfo;

public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private RadioGroup rgGroup;

	protected QuickAdapter<BikeInfo> lvAdapter;
	private ListView lv;

	// 设置Activity的布局
	@Override
	public void setContentView() {
		setContentView(R.layout.layout_main);
	}

	// 初始化布局
	@Override
	public void initViews() {
		lv = (ListView) findViewById(R.id.lv);
		rgGroup = (RadioGroup) findViewById(R.id.rg_group);
	}

	// 初始化监听
	@Override
	public void initListeners() {
		rgGroup.setOnCheckedChangeListener(this);

	}

	// 初始化数据
	@Override
	public void initData() {
		if (lvAdapter == null) {
			lvAdapter = new QuickAdapter<BikeInfo>(this, R.layout.item_list) {
				@Override
				protected void convert(BaseAdapterHelper helper,
						BikeInfo bikeinfo) {
					helper.setText(tv_title, bikeinfo.getTitle())
							.setText(tv_describe, bikeinfo.getDescribe())
							.setText(tv_time, bikeinfo.getCreatedAt())
							.setText(tv_photo, bikeinfo.getPhone());
				}
			};
		}
		lv.setAdapter(lvAdapter);
		// 默认加载失物界面
		queryBikeInfo();

	}

	// 查询
	private void queryBikeInfo() {
		BmobQuery<BikeInfo> query = new BmobQuery<BikeInfo>();
		query.order("-createdAt");
		query.findObjects(new FindListener<BikeInfo>() {

			@Override
			public void done(List<BikeInfo> arg0, BmobException arg1) {
				lvAdapter.clear();
				if (arg1 == null) {
					// progress.setVisibility(View.GONE);
					lvAdapter.addAll(arg0);
					lv.setAdapter(lvAdapter);
				} else {
					Log.i("bmob",
							"失败：" + arg1.getMessage() + ","
									+ arg1.getErrorCode());
				}
			}
		});
	}

	// RadioGroup的监听方法
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.rb_zuche1:

			break;
		case R.id.rb_zuche2:
			Intent intent2 = new Intent(this, AddActivity.class);
			startActivity(intent2);
			// finish();
			break;
		case R.id.rb_zuche3:
			Intent intent3 = new Intent(this, MeActivity.class);
			startActivity(intent3);
			// finish();
			break;

		default:
			break;
		}

	}
}
