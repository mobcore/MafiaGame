package ClientServer;

import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) throws IOException {

		String serverHostname = new String("127.0.0.1");

		if (args.length > 0) {
			serverHostname = args[0];
		}

		System.out.println("Attemping to connect to host " + serverHostname
				+ " on port 1008.");

		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(serverHostname, 10008);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + serverHostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to: "
					+ serverHostname);
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));

		String userInput;
		String line;
		StringBuilder serverOutput;

		System.out.print("input: ");
		while ((userInput = stdIn.readLine()) != null) {
			out.println(userInput);

			serverOutput = new StringBuilder();
			line = in.readLine();
			serverOutput.append(line);
			while ((line = in.readLine()) != null) {
				if (line.equals("break")) {
					break;
				} else {
					serverOutput.append("\n");
				}
				serverOutput.append(line);
			}

			System.out.println("reply: " + serverOutput.toString());
			System.out.print("input: ");
		}

		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
	}
}
