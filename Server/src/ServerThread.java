import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;


public class ServerThread implements Runnable {
	Socket s = null;
	BufferedReader br = null;
	public ServerThread(Socket s) throws UnsupportedEncodingException, IOException {
		// TODO Auto-generated constructor stub
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream(),"utf-8"));
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String content = null;
		while((content = readFromClient()) != null){
			//将读到的内容向每个Socket广播
			for(Socket s : Server.socketList){
				try {
					//System.out.println("Server=" + content);
					OutputStream os = s.getOutputStream();
					os.write((content + "\n").getBytes("utf-8"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private String readFromClient(){
		try {
			return br.readLine();
		} catch (Exception e) {
			// TODO: handle exception
			Server.socketList.remove(s);
			e.printStackTrace();
		}
		return null;
		
	}

}
