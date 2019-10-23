package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import game.Game;
import monster.Monster;

public class Server {
	private static Scanner sc = new Scanner(System.in);
	private ArrayList<Monster> monsters = new ArrayList<Monster>();
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		// https://www.youtube.com/watch?v=HqdOaAzPtek
		// https://boardgamegeek.com/thread/1408893/categorizing-cards
		new Server();
	}

	public Server() {
		Monster kong = new Monster("Kong");
		Monster gigazaur = new Monster("Gigazaur");
		Monster alien = new Monster("Alienoid");
		monsters.add(kong);
		monsters.add(gigazaur);
		monsters.add(alien);

		// Server stuffs
		try {
			ServerSocket aSocket = new ServerSocket(2048);
			// assume two online clients
			for (int onlineClient = 0; onlineClient < 2; onlineClient++) {
				Socket connectionSocket = aSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				outToClient.writeBytes("You are the monster: " + monsters.get(onlineClient).name + "\n");
				monsters.get(onlineClient).connection = connectionSocket;
				monsters.get(onlineClient).inFromClient = inFromClient;
				monsters.get(onlineClient).outToClient = outToClient;
				System.out.println("Connected to " + monsters.get(onlineClient).name);
			}
		} catch (Exception e) {
		}

		new Game(monsters);
	}

	public static String sendMessage(Monster recipient, String message) {
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