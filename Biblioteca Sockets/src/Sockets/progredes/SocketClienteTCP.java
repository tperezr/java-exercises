package Sockets.progredes;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClienteTCP extends SocketTCP {
	private String ipServer;
	
	public void conectar() throws UnknownHostException, IOException {
		setSocket(new Socket(ipServer, getPuertoEscucha()));
	}

	public String getIpServer() {
		return ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	};
	
	
		
}
