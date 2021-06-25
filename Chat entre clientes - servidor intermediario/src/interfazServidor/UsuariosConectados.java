package interfazServidor;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import Sockets.progredes.SocketServidorTCP;
import Texto.ArchivoTexto;
import modelo.GrupoDeUsuarios;
import modelo.Usuario;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class UsuariosConectados extends JPanel {
	
	private JTable table;
	
	public UsuariosConectados(SocketServidorTCP socket, ArrayList<Usuario> usuarios, ArrayList<Usuario> usuariosConectados, ArchivoTexto txt, ArrayList<GrupoDeUsuarios> grupos, DefaultTableModel model) {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(65, 39, 271, 162);
		add(scrollPane);
		
		table = new JTable();
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		scrollPane.setViewportView(table);
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new GruposActivos(socket, usuariosConectados, txt, grupos, model));
				marco.validate();
			}
		});
		btnRegresar.setBounds(145, 221, 89, 23);
		add(btnRegresar);
		
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
}
