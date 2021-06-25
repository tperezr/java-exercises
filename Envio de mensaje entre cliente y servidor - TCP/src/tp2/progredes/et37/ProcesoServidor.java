package tp2.progredes.et37;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Sockets.progredes.*;

public class ProcesoServidor {
	
	
	
	public static void main(String[] args) {
		SocketServidorTCP serverLibreria = new SocketServidorTCP();
		
		Scanner scanner = new Scanner(System.in);		
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
				
				serverLibreria.setPuertoEscucha(puerto);
				serverLibreria.escuchar();

				System.out.println("\nPuerto "+ puerto + " seleccionado correctamente \n" );

				System.out.println("Esperando la conexion con el cliente...\n");

				serverLibreria.aceptar();
				
				System.out.println("Conexion establecida");

				System.out.println(
						"Cliente de IP " + serverLibreria.ipRemota() + " y puerto " + serverLibreria.puertoRemoto());
			
				System.out.println("\nIngresar mensaje");					
						
				do {
					mensajeServidor = scanner.nextLine();
					if (mensajeServidor.trim().length() == 0) {
						System.out.println("Por favor, ingrese un mensaje");
					} else {
						mensajeValido = true;
					}

				} while (mensajeValido == false);
				serverLibreria.enviar(mensajeServidor);

				System.out.println("Esperando respuesta del cliente... \n");

				mensajeCliente = serverLibreria.recibir();
				System.out.println("* Mensaje del cliente *\n" + mensajeCliente);

				serverLibreria.cerrar();
				scanner.close();
				
				System.out.println("\n\nComunicacion exitosa");

				fin = true;

			} catch (NumberFormatException e) { // Funcionando correctamente
				System.out.println("ERROR - No ha ingresado un numero de puerto valido \n");
			} catch (BindException e) { // Funcionando correctamente
				puertoValido = false;
				System.out.println("ERROR - El puerto se encuentra en uso \n");
			} catch (SocketException e) { // Funcionando correctamente
				System.out.println("El cliente se ha desconectado de la comunicacion\n\n");
				fin = true;
			} catch (IOException e) {
				e.printStackTrace();
			}

		} while (fin == false);


	}

}
