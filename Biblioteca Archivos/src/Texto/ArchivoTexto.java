package Texto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoTexto {
	FileWriter fw;
	BufferedWriter bw;
	FileReader fr;
	BufferedReader br;
	String archivo;
	ArrayList<String> lineas;
	
	public void archivoParaEscribir(boolean append) throws IOException {
		fw = new FileWriter(archivo,append);
		bw = new BufferedWriter(fw);
	}
	public void archivoParaLeer() throws FileNotFoundException {
		fr = new FileReader(archivo);
		br = new BufferedReader(fr);
	}
	
	public ArrayList<String> leerArchivo() throws IOException {
		String linea;
		lineas = new ArrayList<String>();
		while((linea = br.readLine()) != null) {
			lineas.add(linea);
		}
		return lineas;
	}
	public void escribirArchivo(String linea) throws IOException {
		bw.write(linea);
		bw.newLine();
	}
	
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
	public void cerrarEscribir() throws IOException {
		bw.close();
	}
	public void cerrarLeer() throws IOException{
		br.close();
	}
}
