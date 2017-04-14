package com.yizuche;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.example.yizuche.R;
import com.yizuche.bean.BikeInfo;

public class AddActivity extends BaseActivity implements
		OnCheckedChangeListener, OnClickListener {
	private RadioGroup rgGroupAdd;

	EditText edit_title, edit_photo, edit_describe;
	Button btn_back, btn_true;

	// 设置Activity的布局
	@Override
	public void setContentView() {
		setContentView(R.layout.layout_add);
	}

	// 初始化布局
	@Override
	public void initViews() {
		rgGroupAdd = (RadioGroup) findViewById(R.id.rg_group_add);
		rbAdd1 = (RadioButton) findViewById(R.id.rb_add1);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_true = (Button) findViewById(R.id.btn_true);
		edit_photo = (EditText) findViewById(R.id.edit_photo);
		edit_describe = (EditText) findViewById(R.id.edit_describe);
		edit_title = (EditText) findViewById(R.id.edit_title);
	}

	// 初始化监听
	@Override
	public void initListeners() {
		rgGroupAdd.setOnCheckedChangeListener(this);
		btn_back.setOnClickListener(this);
		btn_true.setOnClickListener(this);

	}

	// 初始化数据
	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		if (v == btn_true) {
			addByType();
		} else if (v == btn_back) {
			rbAdd1.setChecked(true);
			finish();
		}
	}

	String title = "";
	String describe = "";
	String photo = "";

	private RadioButton rbAdd1;

	/**
	 * 根据类型添加失物/招领 addByType
	 * 
	 * @Title: addByType
	 * @throws
	 */
	private void addByType() {
		title = edit_title.getText().toString();
		describe = edit_describe.getText().toString();
		photo = edit_photo.getText().toString();

		if (TextUtils.isEmpty(title)) {
			ShowToast("请填写标题");
			return;
		}
		if (TextUtils.isEmpty(describe)) {
			ShowToast("请填写描述");
			return;
		}
		if (TextUtils.isEmpty(photo)) {
			ShowToast("请填写手机");
			return;
		} else
			addLost();
	}

	private void addLost() {
		BikeInfo bike = new BikeInfo();
		bike.setDescribe(describe);
		bike.setPhone(photo);
		bike.setTitle(title);
		bike.save(new SaveListener<String>() {

			@Override
			public void done(String arg0, BmobException arg1) {
				Toast.makeText(AddActivity.this, "保存成功！", Toast.LENGTH_SHORT)
						.show();
//				setResult(RESULT_OK);
				Intent intent1 = new Intent(AddActivity.this, MainActivity.class);
				startActivity(intent1);
				rbAdd1.setChecked(true);
				finish();
			}
		});
	}

	// RadioGroup的监听方法
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_add1:
			Intent intent1 = new Intent(this, MainActivity.class);
			startActivity(intent1);
			// finish();
			break;
		case R.id.rb_add2:

			break;
		case R.id.rb_add3:
			Intent intent3 = new Intent(this, MeActivity.class);
			startActivity(intent3);
			// finish();
			break;

		default:
			break;
		}

	}
}
