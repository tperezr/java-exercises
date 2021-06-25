package modelo;

import java.util.ArrayList;

public class GrupoDeUsuarios {

	private int id;
	private String nombreGrupo;
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	public GrupoDeUsuarios(int id, String nombreGrupo, ArrayList<Usuario> usuarios) {
		this.id = id;
		this.nombreGrupo = nombreGrupo;
		this.usuarios = usuarios;
	}
	

	public GrupoDeUsuarios(int id, String nombreGrupo) {
		this.id = id;
		this.nombreGrupo = nombreGrupo;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
