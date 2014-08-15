import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class SimpleServer implements Runnable {
	
	public static ArrayList<String> messageList = new ArrayList<String>();

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(10000);
			Socket socket = serverSocket.accept();
			System.out.println("ACCEPTED!");
			OutputStream outputStream = socket.getOutputStream();
			
			while(true) {
				 
				synchronized (messageList) {
					while (messageList.size() > 0) {
						String message = messageList.get(0);
						System.out.println("message:"+message);
						messageList.remove(0);
						
						
						outputStream.write(message.getBytes());
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
					
					if(!GameClient.isGamePlaying && messageList.size() == 0 ) {
						break;
					}
				}
				
				
			}
			
		} catch (IOException e) {
			System.out.println("error!");
			e.printStackTrace();
		}
		
	}
}
