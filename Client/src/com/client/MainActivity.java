package com.client;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 1、写发送按钮响应的代码（放在最后，防止出现传递空指针），想到需要先创建子线程
 * 2、接受子线程发送过来的数据，更新UI
 * @author Asus
 *
 */
public class MainActivity extends Activity {
	
	EditText input;
	TextView show;
	Button send;
	
	Handler handler;
	ClientThread clientThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		input = (EditText) findViewById(R.id.input);
		show = (TextView) findViewById(R.id.show);
		send = (Button) findViewById(R.id.send);
		
		//定义一个Handler来处理来自子线程的消息，更新UI
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				//收到的消息追加到文本框中
				if(msg.what == 0x123){
					//System.out.println("show=" + msg.obj);
					show.append("\n" + msg.obj.toString());
				}
			}
			
		};
			
		//启动网络请求子线程
		clientThread = new ClientThread(handler);
		new Thread(clientThread).start();
		
		//发送按钮响应
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//用户按下发送按钮后，将数据封装成Message，然后发送给子线程的Handler
				Message msg = new Message();
				msg.what = 0x345;
				msg.obj = input.getText().toString();
				clientThread.handlerNet.sendMessage(msg);
				
				input.setText("");
			}
		});
	}

}
