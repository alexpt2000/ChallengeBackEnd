package game;

public class Main {
	public static void main(String argv[]) throws Exception {
		Server tcpServer = new Server(22222);
		tcpServer.runServer();
	}
}
