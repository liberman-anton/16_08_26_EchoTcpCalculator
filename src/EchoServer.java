import java.io.*;
import java.net.*;
import java.util.HashMap;

public class EchoServer {
	
	public interface Calculator {
		public int calc(int a,int b);
		}
	private static HashMap<String,Calculator> operations = new HashMap<>();
	
	private static final int PORT = 2000;


	public static void main(String[] args) throws Exception{
		initOperations();
		
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("server started");
		while(true){
			try(
			Socket socket = serverSocket.accept();
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream writer = new PrintStream(socket.getOutputStream());
			){
			while (true) {
				String line;
				try {
					line = reader.readLine();
				} catch (Exception e) {
					break;
				}
				if (line == null)
					break;
				String[] operationAndNumbers = line.split(" ");
				String op = operationAndNumbers[0];
				if(!operations.containsKey(op)){
					writer.println("uncorrect format of operator");
					continue;
				}
							
				int res = -1;
				try {
					res = calculate(operations.get(op), 
							operationAndNumbers[1], operationAndNumbers[2]);
				} catch (Exception e) {
					writer.println("uncorrect format of numbers");
					continue;
				}
				
				writer.println(res);
			}
			}catch (Exception e) {System.out.println("Exception");}
		}
		
	}

	private static int calculate(Calculator calculator, String a, String b) {
		int	x = Integer.parseInt(a);
		int	y = Integer.parseInt(b);
		return calculator.calc(x, y);
	}

	private static void initOperations() {
		operations.put("+", (a,b) -> a + b);
		operations.put("-", (a,b) -> a - b);
		operations.put("*", (a,b) -> a * b);
		operations.put("/", (a,b) -> a / b);
	}

}
