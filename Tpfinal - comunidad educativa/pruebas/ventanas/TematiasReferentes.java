package estructuraTP.vista;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TematiasReferentes extends JPanel{
	private JTable table;
	public TematiasReferentes() {
		setLayout(null);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.setBounds(166, 228, 89, 23);
		add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(120, 72, 184, 145);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tematica"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblJuanaAsdadhadAsdlhasdljkashd = new JLabel("");
		lblJuanaAsdadhadAsdlhasdljkashd.setHorizontalAlignment(SwingConstants.CENTER);
		lblJuanaAsdadhadAsdlhasdljkashd.setBounds(104, 34, 215, 14);
		add(lblJuanaAsdadhadAsdlhasdljkashd);
	}
}
