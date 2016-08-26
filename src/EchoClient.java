import java.io.*;
import java.net.*;

public class EchoClient {
	
	private static final int PORT = 2000;

	public static void main(String[] args) throws UnknownHostException, IOException {
		try(
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			Socket socket = new Socket("localhost", PORT);
			BufferedReader socketFrom = 
				new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream socketTo = new PrintStream(socket.getOutputStream());
		){
		while(true){
			System.out.println("enter operator and two numbers or exit");
			String line = console.readLine();
			if(line == null || line.equals("exit")){
				socket.close();
				break;
			}
			socketTo.println(line);
			String outLine = socketFrom.readLine();
			if(outLine == null)
				break;
			System.out.println(outLine);
		}
		}catch (Exception e) {
			
		}
	}
}