package estructuraTP.vista;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JTextField;

import estructuraTP.dao.PropuestaDAO;
import estructuraTP.dao.TematicaDAO;
import estructuraTP.modelo.Propuestas;
import estructuraTP.modelo.Referente;
import estructuraTP.modelo.Tematica;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarPropuesta extends JPanel {
	private JTextField id;
	private JTextField titulo;
	private JTextField detalle;
	private JTextField nombre;
	private JTextField apellido;
	private JComboBox comboBox;

	public ModificarPropuesta(JFrame frame, Propuestas p) {
		setLayout(null);
		frame.setTitle("Modificacion de datos: propuesta");

		PropuestaDAO daopropuesta = new PropuestaDAO();
		TematicaDAO daotematica = new TematicaDAO();

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(10, 11, 46, 14);
		add(lblNewLabel);

		id = new JTextField();
		id.setText(String.valueOf(p.getId()));
		id.setEditable(false);
		id.setBounds(66, 8, 140, 20);
		add(id);
		id.setColumns(10);

		JLabel lblTematica = new JLabel("Tematica");
		lblTematica.setBounds(10, 42, 46, 14);
		add(lblTematica);

		JLabel lblNewLabel_1 = new JLabel("Titulo");
		lblNewLabel_1.setBounds(10, 73, 46, 14);
		add(lblNewLabel_1);

		JLabel lblDetalle = new JLabel("Detalle");
		lblDetalle.setBounds(10, 104, 46, 14);
		add(lblDetalle);

		titulo = new JTextField();
		titulo.setBounds(66, 70, 140, 20);
		titulo.setText(p.getTitulo());
		add(titulo);
		titulo.setColumns(10);

		detalle = new JTextField();
		detalle.setBounds(66, 101, 140, 20);
		detalle.setText(p.getDetalle());
		add(detalle);
		detalle.setColumns(10);

		nombre = new JTextField();
		nombre.setBounds(66, 132, 140, 20);
		nombre.setText(p.getPersonaQuePosteoNombre());
		add(nombre);
		nombre.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 135, 46, 14);
		add(lblNombre);

		JLabel lblNewLabel_2 = new JLabel("Apellido");
		lblNewLabel_2.setBounds(10, 166, 46, 14);
		add(lblNewLabel_2);

		apellido = new JTextField();
		apellido.setBounds(66, 163, 140, 20);
		apellido.setText(p.getPersonaQuePosteoApellido());
		add(apellido);
		apellido.setColumns(10);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new Inicio(frame));
				frame.validate();
			}
		});
		btnVolver.setBounds(10, 205, 89, 23);
		add(btnVolver);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.setTitulo(titulo.getText());
				p.setTematica(daotematica.searchId(comboBox.getSelectedIndex()));
				p.setPersonaQuePosteoNombre(nombre.getText());
				p.setPersonaQuePosteoApellido(apellido.getText());
				p.setDetalle(detalle.getText());
				daopropuesta.actualizar(p);
				frame.setContentPane(new Inicio(frame));
				frame.validate();

			}
		});
		btnGuardar.setBounds(117, 205, 89, 23);
		add(btnGuardar);

		comboBox = new JComboBox();
		comboBox.setMaximumRowCount(99);
		ArrayList<Tematica> tematicas = daotematica.obtenerTodos();
		for (Tematica t : tematicas) {
			comboBox.addItem(t.getId() + "@ " + t.getNombre());
		}
		comboBox.getModel().setSelectedItem(p.getTematica().getId());
		comboBox.setBounds(66, 39, 140, 20);
		add(comboBox);
	}
}
