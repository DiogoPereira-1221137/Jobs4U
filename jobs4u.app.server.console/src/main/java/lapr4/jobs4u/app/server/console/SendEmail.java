package lapr4.jobs4u.app.server.console;

import java.io.*;
import java.net.*;
import java.util.Properties;

public class SendEmail {
	static InetAddress serverIP;
	static Socket sock;

	private static Properties loadProperties() throws IOException {
		Properties properties = new Properties();
		try (InputStream input = SendEmail.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return properties;
			}
			properties.load(input);
		}
		return properties;
	}

	public static boolean sendEmail(String from, String to, String message) throws Exception {
		Properties properties = loadProperties();
		String serverAddress = properties.getProperty("server.address");
		int serverPort = Integer.parseInt(properties.getProperty("server.port"));


		try {
			serverIP = InetAddress.getByName(serverAddress);
		} catch (UnknownHostException ex) {
			System.out.println("Invalid server specified: " + serverAddress);
			System.exit(1);
		}

		try {
			sock = new Socket(serverIP, serverPort); // Porta SMTP padrão é a 25 para SMTP
		} catch (IOException ex) {
			System.out.println("Failed to establish TCP connection");
			System.exit(1);
		}

		BufferedReader sIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		DataOutputStream sOut = new DataOutputStream(sock.getOutputStream());

		// Ler a mensagem de saudação do servidor
		System.out.println(sIn.readLine());



		boolean value = false;

		sOut.writeBytes("HELO frodo.isep.ipp.pt" + "\r\n");
		System.out.println("Server: " + sIn.readLine());
		sOut.writeBytes("MAIL FROM: <"+ from +"> \r\n");
		System.out.println("Server: " + sIn.readLine());
		sOut.writeBytes("RCPT TO: <"+ to +"> \r\n");
		System.out.println("Server: " + sIn.readLine());
		sOut.writeBytes("DATA \r\n");
		System.out.println("Server: " + sIn.readLine());
		sOut.writeBytes(message + " \r\n");
		sOut.writeBytes(".\r\n");
		String response = sIn.readLine();
		System.out.println("Server: " + response);

		if (response.startsWith("500")) {
			System.out.println("Failed to send the email. Server response: " + response);
			value=false;
		} else {
			System.out.println("Email successfully sent!");
			value=true;
		}

		sOut.writeBytes("QUIT\r\n");
		if(value) System.out.println("Server: " + sIn.readLine());

		sock.close();
		return value;
	}
}
