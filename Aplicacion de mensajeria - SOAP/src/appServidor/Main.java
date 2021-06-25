package appServidor;

import javax.xml.ws.Endpoint;

import com.sun.xml.internal.ws.server.ServerRtException;

public class Main {

	public static void main(String[] args) {

		String url = "http://localhost:8080/WS/Gmail/V1.0";

		System.out.println("Publicando web service...");
		try {
			Endpoint.publish(url, new GmailImpl());
			System.out.println("URL -> " + url);
			System.out.println("WSDL -> " + url + "?wsdl");
		} catch (ServerRtException e) {
			System.out.println("\nYa existe un servidor corriendo\n");
			System.out.println("Ejecucion finalizada");
		}
		

	}

}
