package com.yizuche.loginRegister;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.example.yizuche.R;
import com.yizuche.MainActivity;
import com.yizuche.bean.Users;

public class AtyLogin extends Activity implements OnClickListener {

	private EditText userName,password;
	private Button btnLogin;
	private TextView tvRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		Bmob.initialize(AtyLogin.this, "61de156f4c125a5c66a5fbb97b670887");

		userName = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		tvRegister = (TextView) findViewById(R.id.tvRegister);
		btnLogin.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
		
		readUserNameAndPass();

	}

	private void readUserNameAndPass() {
		SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
		String name = sp.getString("userName", "");
		String pass = sp.getString("password", "");
		userName.setText(name);
		password.setText(pass);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnLogin:
			checkLogin();
			break;
		case R.id.tvRegister:
			Intent intent = new Intent(AtyLogin.this, AtyRegister.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void checkLogin() {
//		Intent intent = new Intent(AtyLogin.this, AtyMain.class);
//		startActivity(intent);
		final String userName = this.userName.getText().toString();
		final String password = this.password.getText().toString();
		if (userName.equals("") || password.equals("")){
			Toast.makeText(AtyLogin.this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
		} else{
			BmobQuery<Users> query = new BmobQuery<Users>();
			query.findObjects(new FindListener<Users>() {

				@Override
				public void done(List<Users> arg0, BmobException arg1) {
					int i=0;
					for (Users users : arg0) {
						i++;
						if (users.getUserName().equals(userName) && users.getPassword().equals(password)) {
							Toast.makeText(AtyLogin.this, "登陆成功", Toast.LENGTH_SHORT).show();
							rememberUserNameAndPass();
							Intent intent = new Intent(AtyLogin.this, MainActivity.class);
							startActivity(intent);
							finish();
							return;
						}
					}
					if(i==arg0.size())
						Toast.makeText(AtyLogin.this, "登录失败！请检查用户名和密码", Toast.LENGTH_SHORT).show();
				}

				private void rememberUserNameAndPass() {
					SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
					Editor ed = sp.edit();
					ed.putString("userName", userName);
					ed.putString("password", password);
					ed.commit();
				}
			});
		}
	}
}
