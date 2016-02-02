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
 * 1��д���Ͱ�ť��Ӧ�Ĵ��루������󣬷�ֹ���ִ��ݿ�ָ�룩���뵽��Ҫ�ȴ������߳�
 * 2���������̷߳��͹��������ݣ�����UI
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
		
		//����һ��Handler�������������̵߳���Ϣ������UI
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				//�յ�����Ϣ׷�ӵ��ı�����
				if(msg.what == 0x123){
					//System.out.println("show=" + msg.obj);
					show.append("\n" + msg.obj.toString());
				}
			}
			
		};
			
		//���������������߳�
		clientThread = new ClientThread(handler);
		new Thread(clientThread).start();
		
		//���Ͱ�ť��Ӧ
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//�û����·��Ͱ�ť�󣬽����ݷ�װ��Message��Ȼ���͸����̵߳�Handler
				Message msg = new Message();
				msg.what = 0x345;
				msg.obj = input.getText().toString();
				clientThread.handlerNet.sendMessage(msg);
				
				input.setText("");
			}
		});
	}

}
