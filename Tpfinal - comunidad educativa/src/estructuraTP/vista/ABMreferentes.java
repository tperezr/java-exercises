package estructuraTP.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ABMreferentes extends JPanel {
	private JTable table;
	
	
	public ABMreferentes(JFrame frame) {
		setLayout(null);
		frame.setTitle("Referentes");
		frame.setSize(490, 320);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setContentPane(new Inicio(frame));
			    frame.validate();
			}
		});
		btnVolver.setBounds(25, 205, 104, 23);
		add(btnVolver);

		/*
		if(table.getSelectedRow() == -1) {
			
			btnEliminar.setEnabled(false);
		}
		else {
			btnEliminar.setEnabled(true);
		}*/
			
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
			}
		});
		btnEliminar.setBounds(25, 239, 104, 23);
		add(btnEliminar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(139, 205, 126, 23);
		add(btnModificar);

		JButton btnVerTtodos = new JButton("Limpiar campos");
		btnVerTtodos.setBounds(139, 239, 126, 23);
		add(btnVerTtodos);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 21, 394, 149);
		add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nombre", "Apellido", "Rol"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);

		JButton btnMostrarTodos = new JButton("Mostrar todos");
		btnMostrarTodos.setBounds(275, 205, 144, 23);
		add(btnMostrarTodos);

		JButton btnMostrarTematica = new JButton("Mostrar tematicas");
		btnMostrarTematica.setBounds(275, 239, 144, 23);
		add(btnMostrarTematica);

	}
}