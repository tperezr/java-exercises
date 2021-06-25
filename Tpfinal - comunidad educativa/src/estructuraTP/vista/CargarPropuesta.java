package estructuraTP.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import estructuraTP.dao.PropuestaDAO;
import estructuraTP.dao.ReferenteDAO;
import estructuraTP.dao.TematicaDAO;
import estructuraTP.modelo.Propuestas;
import estructuraTP.modelo.Tematica;

import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class CargarPropuesta extends JPanel {
	private JTextField titulo;
	private JTextField nombre;
	private JTextField detalle;
	private JTextField apellido;
	private JComboBox comboBox;

	/**
	 * Create the panel.
	 */
	public CargarPropuesta(JFrame frame) {
		setLayout(null);
		frame.setTitle("Ingreso de datos: propuesta");
		frame.setSize(422, 295);
		
		PropuestaDAO daopropuesta = new PropuestaDAO();
		ReferenteDAO daoreferente = new ReferenteDAO();
		TematicaDAO daotematica = new TematicaDAO();

		JLabel label_1 = new JLabel("Ingrese titulo");
		label_1.setBounds(51, 14, 90, 14);
		add(label_1);

		titulo = new JTextField();
		titulo.setColumns(10);
		titulo.setBounds(189, 11, 192, 20);
		add(titulo);

		JLabel lblSeleccioneTematica = new JLabel("Seleccione tematica");
		lblSeleccioneTematica.setBounds(51, 57, 128, 14);
		add(lblSeleccioneTematica);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(51, 100, 106, 14);
		add(lblNombre);

		nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(189, 97, 192, 20);
		add(nombre);

		JButton button = new JButton("Guardar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Propuestas p = new Propuestas();
				
				Date fecha = new Date();
				
				
                Calendar cal = Calendar.getInstance(); 
                cal.add(Calendar.DATE, 15);
                cal.add(Calendar.MONTH, 1);
                fecha = cal.getTime();
                
                LocalDate date = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
				daopropuesta.guardar(p = new Propuestas(0,nombre.getText(),apellido.getText(),titulo.getText(),LocalDate.now(),
						detalle.getText(),false,date,daotematica.searchNombre(comboBox.getSelectedItem().toString())));
				frame.setContentPane(new Inicio(frame));
			    frame.validate();
			}
		});
		button.setBounds(51, 222, 91, 23);
		add(button);

		JButton button_1 = new JButton("Volver");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new Inicio(frame));
			    frame.validate();
			}
		});
		button_1.setBounds(290, 222, 91, 23);
		add(button_1);
		
		JLabel lblDetalle = new JLabel("Detalle");
		lblDetalle.setBounds(51, 183, 46, 14);
		add(lblDetalle);
		
		detalle = new JTextField();
		detalle.setBounds(189, 180, 192, 20);
		add(detalle);
		detalle.setColumns(10);
		
		comboBox = new JComboBox();
		ArrayList<Tematica> tematicas = daotematica.obtenerTodos();
		for (Tematica t : tematicas) {
			comboBox.addItem(t.getNombre());
		}
		comboBox.setBounds(189, 54, 192, 20);
		add(comboBox);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(51, 141, 106, 14);
		add(lblApellido);
		
		apellido = new JTextField();
		apellido.setColumns(10);
		apellido.setBounds(189, 138, 192, 20);
		add(apellido);

	}
}
