package com.client;

import java.io.*;
import java.net.*;

import android.os.*;
import android.widget.Toast;

/**
 * 1、创建socket，输入流，输出流
 * 2、handlerNet接受UI线程的数据，并向服务器发送数据
 * 3、接受数据，并更新UI
 * @author Asus
 *
 */
public class ClientThread implements Runnable{
	private Socket s;
	//向UI线程发送消息的Handler对象
	private Handler handlerUI;
	//接收UI线程的消息的Handler对象
	public Handler handlerNet;
	
	BufferedReader br = null;
	OutputStream os = null;
	
	public ClientThread(Handler handler){
		this.handlerUI = handler;	
	}
	@Override
	public void run() {
		//不能放在UI进程中
		try {
			System.out.println("连接socket中");
			s = new Socket("192.168.1.101",8088);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = s.getOutputStream();
			System.out.println("socket" + s);
			//新建子线程来读取服务器响应的数据
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
			
			//向服务器发送数据
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
