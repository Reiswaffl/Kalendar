package Networking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author DigitalEye
 *
 */
public class ClientEmulator {

	// Singleton Implementation: In the context of this app,
	private static ClientEmulator _singletonInstance = null;

	// Default constructor is private to make sure it can't be accessed outside
	// the class
	private ClientEmulator() {
	}

	/**
	 * Returns single instance of ClientEmulator
	 * @return Instance of ClientEmulator
	 */
	public static ClientEmulator getSingleClientEmulator() {
		if (_singletonInstance == null) {
			_singletonInstance = new ClientEmulator();
		}
		return _singletonInstance;
	}

	/**
	 * Waits on response from server
	 * @param socket Server socket
	 */
	public void readServerResponse(Socket socket) {
		try {
			BufferedReader serverReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			String serverResponse = null;
			while ((serverResponse = serverReader.readLine()) != null) {
				System.out.println("Server Response: " + serverResponse);
			}
		} catch (IOException ex) {
			System.out.println("Error: Unable to read server response\n\t" + ex);
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		// Make sure command line arguments are valid
		if (args.length < 3) {
			System.out.println("Error: Please specify host name, port number and data file.\nExiting...");
			return;
		}
		try {

			// Open a socket to the server
			Socket socket = new Socket(args[0], Integer.parseInt(args[1]));

			// Wait for the server to accept connection before reading the xml file
			BufferedReader reader = new BufferedReader( new FileReader (args[2]));
			String line;
			StringBuilder  stringBuilder = new StringBuilder();
			while((line = reader.readLine() ) != null) {
				stringBuilder.append(line);
			}

			// Send xml data to server
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println(stringBuilder.toString());

			// Wait for server response
			getSingleClientEmulator().readServerResponse(socket);


		} catch (IOException | NumberFormatException ex) {
			System.out.println("Error: " + ex);
		}
	}

}