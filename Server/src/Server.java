import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	public static void main(String[] args) throws IOException{
		ServerSocket ss = new ServerSocket(8088);
		while(true){
			Socket s = ss.accept();
			socketList.add(s);
			System.out.println("客户端连接");
			new Thread(new ServerThread(s)).start();
		}
	}
}

