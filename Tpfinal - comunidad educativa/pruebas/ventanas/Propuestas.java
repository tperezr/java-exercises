package ventanas;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Propuestas extends JPanel {
	
	private JTable table;
	
	public Propuestas(JFrame frame) {
		setLayout(null);
		
		frame.setSize(950, 600);
		
		JCheckBox chckbxCaducadas = new JCheckBox("Caducadas");
		chckbxCaducadas.setBounds(119, 172, 97, 23);
		add(chckbxCaducadas);
		
		JCheckBox chckbxActivas = new JCheckBox("Activas");
		chckbxActivas.setBounds(119, 198, 97, 23);
		add(chckbxActivas);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(230, 228, 131, 23);
		add(btnVolver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 11, 694, 154);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Titulo", "Tematica", "Propuesto por", "Fecha de publicacion", "Fecha de caducidad", "Caduco"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(112);
		table.getColumnModel().getColumn(4).setPreferredWidth(105);
		table.getColumnModel().getColumn(5).setPreferredWidth(119);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.setBounds(26, 228, 131, 23);
		add(btnNewButton);
		
		JLabel lblFiltrarPorCaducidad = new JLabel("Filtrar por caducidad:");
		lblFiltrarPorCaducidad.setBounds(6, 176, 107, 14);
		add(lblFiltrarPorCaducidad);
	}

}
