package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import game.Game;
import player.Player;

public class Server {
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Starts a King of Tokyo: Power Up server with the specified number of players
	 * @param args the number of players
	 * @throws Exception no specified amount of players
	 */
	public static void main(String[] args) throws Exception {
		int numPlayers;
		try {
			numPlayers = Integer.parseInt(args[0]);
		} catch (Exception e) {
			throw new Exception("Please supply the server with number the of players as an argument: \'java Server <num players>\'");
		}
		
		if (numPlayers < 2) {
			throw new Exception("The number of players needs to be at least 2");
		}
		new Server(numPlayers);
	}

	public Server(int numPlayers) throws Exception {
		// Server stuffs
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player());
		}

		try {
			ServerSocket aSocket = new ServerSocket(2048);
			for (int onlineClient = 0; onlineClient < numPlayers; onlineClient++) {
				Socket connectionSocket = aSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				players.get(onlineClient).connection = connectionSocket;
				players.get(onlineClient).inFromClient = inFromClient;
				players.get(onlineClient).outToClient = outToClient;
				System.out.println("Connected to player" + onlineClient);
			}
		} catch (Exception e) {
			throw e;
		}

		new Game(players);
	}

	public static String sendMessage(Player recipient, String message) {
		String response = "";
		if (recipient.connection != null) {
			try {
				recipient.outToClient.writeBytes(message);
				response = recipient.inFromClient.readLine();
			} catch (Exception e) {
			}
		} else {
			String[] theMessage = message.split(":");
			for (int i = 0; i < theMessage.length; i++) {
				System.out.println(theMessage[i].toString());
			}
			if (!(theMessage[0].equals("ATTACKED") || theMessage[0].equals("ROLLED")
					|| theMessage[0].equals("PURCHASE")))
				System.out.println("Press [ENTER]");
			response = sc.nextLine();
		}
		return response;
	}

}