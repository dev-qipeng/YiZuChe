package com.yizuche.loginRegister;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.example.yizuche.R;
import com.yizuche.bean.Users;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AtyRegister extends Activity implements OnClickListener {

	private EditText etPhone,etYZM,etPassword;
	private ImageButton btnBacgToLogin;
	private Button btnGetYZM,btnRegister;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);
		//初始化Bmob
		Bmob.initialize(AtyRegister.this, "61de156f4c125a5c66a5fbb97b670887");
		BmobSMS.initialize(AtyRegister.this, "61de156f4c125a5c66a5fbb97b670887");
		//初始化布局
		etPhone = (EditText) findViewById(R.id.etPhone);
		etYZM = (EditText) findViewById(R.id.etYZM);
		etPassword = (EditText) findViewById(R.id.etPassword);
		btnBacgToLogin = (ImageButton) findViewById(R.id.btnBackToLogin);
		btnGetYZM = (Button) findViewById(R.id.btn_get_YZM);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnBacgToLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		btnGetYZM.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnBackToLogin:
			intent = new Intent(AtyRegister.this, AtyLogin.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_get_YZM:
			getYZM();
			break;
		case R.id.btnRegister:
			register();
			break;

		default:
			break;
		}
		
	}
	
	//获取验证码按钮事件
	private void getYZM() {
		String phone = etPhone.getText().toString();  
		Log.e("MESSAGE:", "2");  
        if (phone.length() != 11) {  
            Toast.makeText(this, "请输入11位有效手机号码", Toast.LENGTH_SHORT).show();  
        }  
        else {  
            Log.e("MESSAGE:", "3");  
            //进行获取验证码操作和倒计时1分钟操作  
            BmobSMS.requestSMSCode(this, phone, "校园易租车", new RequestSMSCodeListener() {  
				@Override
				public void done(Integer integer,
						cn.bmob.sms.exception.BmobException e) {
					if (e == null) {  
                        //发送成功时，让获取验证码按钮不可点击，且为灰色  
                        btnGetYZM.setClickable(false);  
                        btnGetYZM.setBackgroundColor(Color.GRAY);  
                        Toast.makeText(AtyRegister.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();  
                        /** 
                         * 倒计时1分钟操作 
                         * 说明： 
                         * new CountDownTimer(60000, 1000),第一个参数为倒计时总时间，第二个参数为倒计时的间隔时间 
                         * 单位都为ms，其中必须要实现onTick()和onFinish()两个方法，onTick()方法为当倒计时在进行中时， 
                         * 所做的操作，它的参数millisUntilFinished为距离倒计时结束时的时间，以此题为例，总倒计时长 
                         * 为60000ms,倒计时的间隔时间为1000ms，然后59000ms、58000ms、57000ms...该方法的参数 
                         * millisUntilFinished就等于这些每秒变化的数，然后除以1000，把单位变成秒，显示在textView 
                         * 或Button上，则实现倒计时的效果，onFinish()方法为倒计时结束时要做的操作，此题可以很好的 
                         * 说明该方法的用法，最后要注意的是当new CountDownTimer(60000, 1000)之后，一定要调用start() 
                         * 方法把该倒计时操作启动起来，不调用start()方法的话，是不会进行倒计时操作的 
                         */  
                        new CountDownTimer(60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                btnGetYZM.setText(millisUntilFinished / 1000 + "秒");
                            }

                            @Override
                            public void onFinish() {
                            	btnGetYZM.setClickable(true);
                            	btnGetYZM.setBackgroundColor(Color.BLUE);
                            	btnGetYZM.setText("重新发送");
                            }
                        }.start();
                        Log.e("MESSAGE:", "4");
                    }
                    else {  
                        Toast.makeText(AtyRegister.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();  
                    }
				}
            });
        }
	}
	//注册按钮事件
	private void register() {
		final String phone = this.etPhone.getText().toString();
		String yzm = this.etYZM.getText().toString();
		final String password = this.etPassword.getText().toString();
		//
		 Log.e("MESSAGE:", "5");  
         if (phone.length() == 0 ||  phone.length() != 11) {  
             Log.e("MESSAGE:", "6");  
             Toast.makeText(this, "请输入11位有效手机号码", Toast.LENGTH_SHORT).show();  
         } else if(yzm.length() == 0 ){
        	 Toast.makeText(this, "验证码输入不合法", Toast.LENGTH_SHORT).show();  
         } else if(password.length()==0||password.length()<6||password.length()>16){
        	 Toast.makeText(this, "密码在6~16位之间", Toast.LENGTH_SHORT).show(); 
         } else {  
             BmobSMS.verifySmsCode(this, phone, yzm, new VerifySMSCodeListener() {  
				@Override
				public void done(cn.bmob.sms.exception.BmobException e) {
					if (e == null) {  
                        Log.e("MESSAGE:", "7");  
                        System.out.println("验证码发送成功");
                        Users userObj = new Users();
                        userObj.setUserName(phone);
                        userObj.setPassword(password);
                        userObj.save(new SaveListener<String>() {
							@Override
							public void done(String arg0, BmobException arg1) {
								if (arg1==null) {
									Toast.makeText(AtyRegister.this, "注册成功！", Toast.LENGTH_SHORT).show();
								}else {
									Toast.makeText(AtyRegister.this, "注册失败"+arg1.getMessage(), Toast.LENGTH_SHORT).show();
								}
							}
						});
                    }  
                    else {  
                        Log.e("MESSAGE:", "8");  
                        Toast.makeText(AtyRegister.this, "验证码错误", Toast.LENGTH_SHORT).show();  
                    } 
				}  
             });  
         }  
	}
	
}
