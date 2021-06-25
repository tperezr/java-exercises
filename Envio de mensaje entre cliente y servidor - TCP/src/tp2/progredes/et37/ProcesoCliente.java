package tp2.progredes.et37;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Sockets.progredes.*;

public class ProcesoCliente {

	public static void main(String[] args) {
		SocketClienteTCP socketCliente = new SocketClienteTCP();

		Scanner scanner = new Scanner(System.in);
		String mensajeCliente;
		String mensajeServidor;
		String ip = null;
		boolean puertoValido;
		boolean mensajeValido = false;
		boolean fin = false;
		int puerto = 0;
		boolean ipValida;
		
		do {
			
			ipValida = false;
			puertoValido = false;
			
			try {
				System.out.println("Ingrese IP del servidor");
				while (ipValida == false) {
					ip = scanner.nextLine();
					if (ip.trim().length() == 0) {
						System.out.println("Por favor, ingrese una IP");
					} else {
						ipValida = true;
					}
				}

				System.out.println("Ingrese puerto del servidor");
				while (puertoValido == false) {
					puerto = Integer.parseInt(scanner.nextLine());
					if (puerto >= 0 && puerto <= 65535) {
						puertoValido = true;
					} else {
						System.out.println("ERROR, puerto invalido");
						System.out.println("Ingrese un puerto VALIDO");
					}
				}
				socketCliente.setPuertoEscucha(puerto);
				socketCliente.setIpServer(ip);

				socketCliente.conectar();

				System.out.println("Conexión establecida con el servidor. IP: " + socketCliente.ipRemota()
						+ "\nPuerto del Servidor:" + socketCliente.puertoRemoto());
				// Linea de mensajes entre cl y sv

				System.out.println("\nEsperando mensaje del servidor...");
				mensajeServidor = socketCliente.recibir();
				System.out.println("\nMensaje del Servidor: " + mensajeServidor);
				// recibido mensaje sv

				// ahora redactar msg cliente y enviar

				System.out.println("\nIngresar mensaje");
				do {
					mensajeCliente = scanner.nextLine();
					if (mensajeServidor.trim().length() == 0) {
						System.out.println("Por favor, ingrese un mensaje.");
					} else {
						mensajeValido = true;
					}

				} while (mensajeValido == false);
				socketCliente.enviar(mensajeCliente);

				// Cerrando socket y demás
				socketCliente.cerrar();
				scanner.close();
				fin = true;

				System.out.println("Comunicación exitosa!!!");

			} catch (NumberFormatException e) {
				System.out.println("ERROR - No ha ingresado un numero de puerto valido. \n");
			} catch (ConnectException e) {
				System.out.println(
						"ERROR - No se ha podido establecer la conexion con el servidor. Los datos son incorrectos. \n");
			} catch(SocketException e){
				if(e.getMessage().contains("Network is unreachable: connect")) {
					System.out.println(
							"ERROR - No se ha podido establecer la conexion con el servidor. Los datos son incorrectos. \n");
				}
				else {
					System.out.println("El servidor se ha desconectado de la comunicacion\n\n");
					fin = true;
				}
			} catch (UnknownHostException e) {
				System.out.println("No se ha podido encontra la IP del servidor, intente nuevamente. \n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} while (fin == false);

	}

}
