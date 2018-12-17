/**
* 
*
* @author  Alexander Souza 
*/

package game;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	/**
	 * This is the method main, this will create new socket and a thread for this
	 * socket.
	 */

	public static void main(String[] args) throws Exception {

		// Creating a new socket
		ServerSocket m_ServerSocket = new ServerSocket(2004, 10);

		// This will give one ID for each socket created
		int id = 0;

		// Stay in loop waiting for conections (client)
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();

			// Creating a thread and give a socket and ID
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);

			// Start the thread
			cliThread.start();
		}
	}
}
