package estructuraTP.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;

public class ModificarReferente extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;

	public ModificarReferente(JFrame frame) {
		setLayout(null);
		frame.setTitle("Modificacion de datos: referente");
		
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 61, 46, 14);
		add(lblId);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(66, 58, 105, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(66, 89, 105, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 92, 46, 14);
		add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(10, 123, 46, 14);
		add(lblApellido);
		
		textField_2 = new JTextField();
		textField_2.setBounds(66, 120, 105, 20);
		add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(66, 151, 105, 20);
		add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Rol");
		lblNewLabel.setBounds(10, 154, 46, 14);
		add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(214, 10, 184, 145);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Tematica"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(214, 166, 184, 20);
		add(comboBox);
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.setBounds(214, 197, 80, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Eliminar");
		btnNewButton_1.setBounds(318, 197, 80, 23);
		add(btnNewButton_1);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(158, 246, 89, 23);
		add(btnGuardar);
	}
}
