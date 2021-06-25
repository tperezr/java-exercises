package Servidor.progredes.et37;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ServidorMain {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Socket socket;
		ServerSocket serverSocket;
		DataOutputStream enviar;
		DataInputStream recibir;
		String mensajeCliente;
		String mensajeServidor;
		boolean puertoValido = false;
		boolean mensajeValido = false;
		boolean fin = false;
		int puerto = 0;

		do {

			try {

				System.out.println("Ingrese puerto de escucha");

				while (puertoValido == false) {

					puerto = Integer.parseInt(scanner.nextLine());

					if (puerto >= 0 && puerto <= 65535) {
						puertoValido = true;
					} else {
						System.out.println("ERROR - Numero de puerto fuera del rango establecido (0 - 65535) \n");
						System.out.println("Ingrese nuevamente el puerto de escucha");
					}
				}
				serverSocket = new ServerSocket(puerto);

				System.out.println("\nPuerto " + serverSocket.getLocalPort() + " seleccionado correctamente \n");

				System.out.println("Esperando la conexion con el cliente...\n");

				socket = serverSocket.accept();

				System.out.println("Conexion establecida");

				System.out.println(
						"Cliente de IP " + socket.getInetAddress().getHostAddress() + " y puerto " + socket.getPort());

				enviar = new DataOutputStream(socket.getOutputStream());
				recibir = new DataInputStream(socket.getInputStream());

				System.out.println("\nIngresar mensaje");

				do {
					mensajeServidor = scanner.nextLine();
					if (mensajeServidor.trim().length() == 0) {
						System.out.println("Por favor, ingrese un mensaje");
					} else {
						mensajeValido = true;
					}

				} while (mensajeValido == false);
				enviar.writeUTF(mensajeServidor);

				System.out.println("Esperando respuesta del cliente... \n");

				mensajeCliente = recibir.readUTF();
				System.out.println("Mensaje del cliente: " + mensajeCliente);

				socket.close();
				serverSocket.close();
				recibir.close();
				enviar.close();
				scanner.close();

				System.out.println("\n\nComunicacion exitosa");

				fin = true;

			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.out.println("ERROR - No ha ingresado un numero de puerto \n");
			} catch (NumberFormatException e) {
				System.out.println("ERROR - No ha ingresado un numero de puerto valido \n");
			} catch (BindException e) {
				puertoValido = false;
				System.out.println("ERROR - El puerto se encuentra en uso \n");
			} catch (SocketException e) {
				System.out.println("El cliente se ha desconectado de la comunicacion\n\n");
				fin = true;
			} catch (IOException e) {
				e.printStackTrace();
			}

		} while (fin == false);

	}

}

/*
 * do { puerto = scanner.nextInt();
 * 
 * if(puerto >= 0 && puerto <= 65535) { valido = true; } else {
 * System.out.println("Puerto incorrecto");
 * System.out.println("Ingrese nuevamente el puerto de escucha"); }
 * 
 * } while (valido == false);
 * 
 * 
 * 
 * catch (NoSuchElementException e) { // Ingresar mensaje vacio
 * System.out.println("ERROR - No ha ingresado nada"); }
 */
