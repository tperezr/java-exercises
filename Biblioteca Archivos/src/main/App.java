package main;

import java.util.ArrayList;

import Binario.ArchivoBinario;
import Texto.ArchivoTexto;

public class App {

	public static void main(String[] args) {
		ArchivoTexto at = new ArchivoTexto();
		//ArchivoBinario ab = new ArchivoBinario();
		String archivo = "test.txt";
		try {
			at.setArchivo(archivo);
			at.archivoParaEscribir(false);
			
			at.escribirArchivo("Hola");
			at.escribirArchivo("Chau");
			at.cerrarEscribir();
			at.archivoParaLeer();
			ArrayList<String> a = at.leerArchivo();
			at.cerrarLeer();
			for (String s : a) {
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
