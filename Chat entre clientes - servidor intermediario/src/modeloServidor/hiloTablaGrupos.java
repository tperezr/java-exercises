package modeloServidor;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import modelo.Usuario;

public class hiloTablaGrupos implements Runnable{
	private ArrayList<Usuario> usuarios;
	private DefaultTableModel model;
	
	public hiloTablaGrupos(ArrayList<Usuario> usuarios, DefaultTableModel model) {
		this.usuarios = usuarios;
		this.model = model;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.setRowCount(0);
				for (Usuario s : usuarios) {
					if(s.getSocket() != null) {
						model.addRow(new Object[] { s.getNombreUsuario(), s.getSocket().ipRemota(),
								s.getSocket().puertoRemoto() });
					}else {
						model.addRow(new Object[] { s.getNombreUsuario(),"","" });
					}					
				}
			}
		});
	}

}
