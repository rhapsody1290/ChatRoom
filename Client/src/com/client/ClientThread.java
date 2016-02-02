package com.client;

import java.io.*;
import java.net.*;

import android.os.*;
import android.widget.Toast;

/**
 * 1������socket���������������
 * 2��handlerNet����UI�̵߳����ݣ������������������
 * 3���������ݣ�������UI
 * @author Asus
 *
 */
public class ClientThread implements Runnable{
	private Socket s;
	//��UI�̷߳�����Ϣ��Handler����
	private Handler handlerUI;
	//����UI�̵߳���Ϣ��Handler����
	public Handler handlerNet;
	
	BufferedReader br = null;
	OutputStream os = null;
	
	public ClientThread(Handler handler){
		this.handlerUI = handler;	
	}
	@Override
	public void run() {
		//���ܷ���UI������
		try {
			System.out.println("����socket��");
			s = new Socket("192.168.1.101",8088);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = s.getOutputStream();
			System.out.println("socket" + s);
			//�½����߳�����ȡ��������Ӧ������
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						String content = null;
						while ((content = br.readLine()) != null) {
							System.out.println("clientRecv" + content);
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = content;
							handlerUI.sendMessage(msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}.start();
			
			//���������������
			Looper.prepare();
			handlerNet = new Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if(msg.what == 0x345){
						try {
							os.write((msg.obj.toString() + "\r\n").getBytes("utf-8"));
						} catch (Exception e){
							e.printStackTrace();
						}
					}
				}
				
			};
			Looper.loop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
