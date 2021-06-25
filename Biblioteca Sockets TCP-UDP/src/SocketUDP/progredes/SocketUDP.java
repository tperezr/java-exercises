package SocketUDP.progredes;

import java.io.IOException;
import java.net.*;

public class SocketUDP {
	DatagramPacket datagramPacket;
	DatagramSocket datagramSocket;
	byte[] buffer;
	String msj;
	InetAddress address;
	int puertoDestino;
	
	//Cliente(emisor)
	public SocketUDP(String ip, int puerto, String msj) throws IOException {
		buffer = msj.getBytes();
		datagramSocket = new DatagramSocket();
		datagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), puerto);
		datagramSocket.send(datagramPacket);
	}
	
	//SV(receptor)
	public SocketUDP(int puertoEscucha) throws SocketException { 
		datagramSocket = new DatagramSocket(puertoEscucha);	
	}
	
	public String recibir() throws IOException {
		buffer = new byte[100];
		datagramPacket = new DatagramPacket(buffer, 100);	
		datagramSocket.receive(datagramPacket);
		return msj = (new String(datagramPacket.getData(),0,datagramPacket.getLength()));
	}
	
	public void enviar(String msj) throws IOException {
		buffer = msj.getBytes();
		address = datagramPacket.getAddress();
		puertoDestino = datagramPacket.getPort();
		datagramPacket = new DatagramPacket(buffer, buffer.length, address, puertoDestino);
		datagramSocket.send(datagramPacket);
	}
	
	public void close() {
		datagramSocket.close();
	}

	public int puertoRemoto() {
		return datagramPacket.getPort();
	}
	
	public String ipRemota() {
		return datagramPacket.getAddress().getHostAddress();
	}
	
	public int puertoLocal() {
		return datagramSocket.getLocalPort();
	}
	
	public String ipLocal() {
		return datagramSocket.getInetAddress().getHostAddress();
	}
}
