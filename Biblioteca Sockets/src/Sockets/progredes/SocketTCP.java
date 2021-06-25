package Sockets.progredes;

import java.io.*;
import java.net.*;

public abstract class SocketTCP {
	
	private Socket socket;
	private DataInputStream recibir;
	private DataOutputStream enviar;
	private ObjectInputStream recibirObjeto;
	private ObjectOutputStream enviarObjeto;
	private int puertoEscucha;
	
	public Boolean recibirBoolean() throws IOException {
		recibir = new DataInputStream(socket.getInputStream());
		return recibir.readBoolean();
	}
	
	public void enviar(Boolean v) throws IOException {
		enviar = new DataOutputStream(socket.getOutputStream());
		enviar.writeBoolean(v);
	}
	
	public void enviar(String mensaje) throws IOException {
		enviar = new DataOutputStream(socket.getOutputStream());
		enviar.writeUTF(mensaje);	
	}
	
	public String recibir() throws IOException {
		recibir = new DataInputStream(socket.getInputStream());
		return recibir.readUTF();
	}
	
	public void enviarObjeto(Object obj) throws IOException {
		enviarObjeto = new ObjectOutputStream(socket.getOutputStream());
		enviarObjeto.flush();
		enviarObjeto.writeObject(obj);	
	}
	
	public Object recibirObjeto() throws IOException, ClassNotFoundException {
		recibirObjeto = new ObjectInputStream(socket.getInputStream());
		return recibirObjeto.readObject();
	}

	public String ipLocal() {
		return socket.getLocalAddress().getHostAddress();
	}
	
	public String ipRemota() {
		return socket.getInetAddress().getHostAddress();
		
	}
	
	public int puertoLocal() {
		return socket.getLocalPort();
	}
	
	public int puertoRemoto() {
		return socket.getPort();
	}
	
    public void cerrar() throws IOException {
    	try{socket.close();}catch(SocketException e){}
    	try{recibir.close();}catch(SocketException e){}
    	try{recibirObjeto.close();}catch(SocketException e){}
    	try{enviarObjeto.close();}catch(SocketException e){}
    	try{enviar.close();}catch(SocketException e){}
		
	}   
    
    public boolean socketIsConnected() {
    	return socket.isConnected();
    }
    
	public int getPuertoEscucha() {
		return puertoEscucha;
	}
	public void setPuertoEscucha(int puertoEscucha) {
		this.puertoEscucha = puertoEscucha;
	}
	
	public Socket getSocket() {
		return socket;
	}	
    public void setSocket(Socket socket) {
		this.socket = socket;
	}

	
}
