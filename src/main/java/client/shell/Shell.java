package client.shell;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import client.utils.ProtocolCommands;
import client.utils.ServerRequest;

public class Shell {
	
	private Scanner sc;
	
	public Shell() {
		sc = new Scanner(System.in);
	}
	
	public void run() {
		while(true) {
			String command = sc.nextLine();
			ServerRequest request = parseCommand(command);
			if (request != null) {
				request.execute();
			}
			else {
				System.out.println("Error occured");
			}
		}
	}

	private ServerRequest parseCommand(String command) { //TODO error handling
		if (command.startsWith("list")) {
			return new ServerRequest(ProtocolCommands.LIST, "");
		}
		else {
			if (command.startsWith("put")) {
				String filename = command.split(" ")[1];
				if (Files.exists(Paths.get(filename))) {
					return new ServerRequest(ProtocolCommands.GET, filename);					
				}
			}
			else if (command.startsWith("get")) {
				String fileId = command.split(" ")[1];
				return new ServerRequest(ProtocolCommands.GET, fileId);
			}
			
		}
		
		System.err.println("Bad command: " + command);
		return null;
	}
	
}
