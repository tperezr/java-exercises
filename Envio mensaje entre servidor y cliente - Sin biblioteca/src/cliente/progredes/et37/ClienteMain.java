package cliente.progredes.et37;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClienteMain {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Socket socket;
		DataOutputStream enviar;
		DataInputStream recibir;
		String datosCliente;
		String datosServidor;
		String ip = null;
		int puerto = 0;
		boolean valido;
		boolean fin = false;

		do {

			valido = false;			

			try {
				
				System.out.println("Ingrese IP del servidor");
				ip = scanner.nextLine();						

				System.out.println("Ingrese puerto del servidor");

				while (valido == false) {

					puerto = Integer.parseInt(scanner.nextLine());

					if (puerto >= 0 && puerto <= 65535) {
						valido = true;
					} else {
						System.out.println("Puerto incorrecto \n");
						System.out.println("Ingrese nuevamente el puerto de escucha");
					}

				}
				
				socket = new Socket(ip, puerto);
				System.out
						.println("Conexión establecida con el servidor de IP " + socket.getInetAddress().getHostAddress() + " en el puerto " 
								+ socket.getPort() + " utilizando el puerto local " + socket.getLocalPort());

				enviar = new DataOutputStream(socket.getOutputStream());
				recibir = new DataInputStream(socket.getInputStream());

				System.out.println("\nEsperando mensaje del servidor...");
				datosServidor = recibir.readUTF();
				System.out.println("\nEl servidor dice: " + datosServidor);

				System.out.println("\nIngrese respuesta para enviar al servidor:");

				datosCliente = scanner.nextLine();
				enviar.writeUTF(datosCliente);

				socket.close();
				recibir.close();
				enviar.close();
				scanner.close();
				
				System.out.println("\n\nComunicacion exitosa");

				fin = true;

			} catch (InputMismatchException e) { // Ingresar una letra o simbolo
				scanner.next();
				System.out.println("ERROR - No ha ingresado un numero de puerto \n");
			} catch (NumberFormatException e) {
				System.out.println("ERROR - No ha ingresado un numero de puerto valido \n");
			} catch (NoSuchElementException e) { // Ingresar mensaje vacio
				System.out.println("ERROR - No ha ingresado nada");
			} catch (ConnectException e) {
				System.out.println("ERROR - El puerto ingresado es erróneo \n");
			} catch (UnknownHostException e) {
				System.out.println("No se ha podido encontra la IP del servidor, intente nuevamente \n");
			} catch (SocketException e) {
				System.out.println("El cliente se ha desconectado de la comunicacion\n\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (fin == false);
	}

}
