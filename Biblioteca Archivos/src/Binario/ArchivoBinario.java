package Binario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ArchivoBinario {
	
	FileOutputStream fos;
	ObjectOutputStream oos;
	FileInputStream fis;
	ObjectInputStream ois;
	String archivo;
	
	public void archivoParaEscribir(boolean append) throws FileNotFoundException, IOException {
		fos = new FileOutputStream(archivo,append);
		oos = new ObjectOutputStream(fos);
	}
	public void archivoParaLeer() throws FileNotFoundException, IOException {
		fis = new FileInputStream(archivo);
		ois = new ObjectInputStream(fis);
	}
	
	public void escribirObjeto(Object obj) throws IOException {
		oos.writeObject(obj);
	}
	public Object leerObjeto() throws ClassNotFoundException, IOException {
		return ois.readObject();
	}
	
	public void escribirLista(ArrayList<Object> list) throws IOException {
		oos.writeObject(list);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Object> leerLista() throws ClassNotFoundException, IOException{
		return (ArrayList<Object>) ois.readObject();
	}
}
