package ModeloServidor;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Modelo.Usuario;

public class hiloTablaClientes implements Runnable {
	private DefaultTableModel model;
	private ArrayList<Usuario> usuarios;

	public hiloTablaClientes(DefaultTableModel _model, ArrayList<Usuario> _usuarios) {
		// TODO Auto-generated constructor stub
		this.model = _model;
		this.usuarios = _usuarios;
	}

	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				model.setRowCount(0);
				for (Usuario s : usuarios) {
					model.addRow(new Object[] { s.getNombreUsuario(), s.getSocket().ipRemota(),
							s.getSocket().puertoRemoto() });
				}

			}
		});

	}

}
