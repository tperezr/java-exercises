package SocketTCP.progredes;

import java.io.*;
import java.net.*;

public class SocketServidorTCP extends SocketTCP{
    
    private ServerSocket serverSocket;
    
    public void aceptar() throws IOException {
    	setSocket(serverSocket.accept());
    }
    
    public void escuchar() throws IOException {
    	serverSocket = new ServerSocket(getPuertoEscucha());
    }
    
    public void cerrar() throws IOException {
    	super.cerrar();
    	serverSocket.close();   	
    }

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
    
    
}
