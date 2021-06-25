package estructuraTP.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import estructuraTP.dao.ProblematicaDAO;
import estructuraTP.dao.ReferenteDAO;
import estructuraTP.dao.TematicaDAO;
import estructuraTP.modelo.Problematica;
import estructuraTP.modelo.Referente;
import estructuraTP.modelo.Tematica;

public class CargarProblematica extends JPanel {
	private JTextField titulo;
	private JTextField nombre;
	private JTextField apellido;
	private JTextField detalle;
	private JCheckBox planteada;
	private JComboBox comboBox;

	/**
	 * Create the panel.
	 */
	public CargarProblematica(JFrame frame) {
		setLayout(null);
		frame.setTitle("Ingreso de datos: problematica");
		frame.setSize(450, 315);

		ProblematicaDAO daoproblematica = new ProblematicaDAO();
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

		comboBox = new JComboBox();
		comboBox.setBounds(189, 54, 192, 20);
		ArrayList<Tematica> tematicas = daotematica.obtenerTodos();
		for (Tematica t : tematicas) {
			comboBox.addItem(t.getNombre());
		}
		add(comboBox);

		JLabel label = new JLabel("Nombre");
		label.setBounds(51, 96, 106, 14);
		add(label);

		nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(189, 93, 192, 20);
		add(nombre);

		JLabel label_2 = new JLabel("Apellido");
		label_2.setBounds(51, 137, 106, 14);
		add(label_2);

		JLabel label_3 = new JLabel("Detalle");
		label_3.setBounds(51, 179, 46, 14);
		add(label_3);

		apellido = new JTextField();
		apellido.setColumns(10);
		apellido.setBounds(189, 134, 192, 20);
		add(apellido);

		detalle = new JTextField();
		detalle.setColumns(10);
		detalle.setBounds(189, 176, 192, 20);
		add(detalle);

		JButton button = new JButton("Guardar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fuePlanteada = 0;
				if (planteada.isSelected()) {
					fuePlanteada = 1;
				}
				
				Random random = new Random();
				ArrayList<Referente> referentes = daoreferente.obtenerTodasPorTematica(comboBox.getSelectedItem().toString());
				int index = random.nextInt(referentes.size());
				
				daoproblematica.guardar(new Problematica(0, nombre.getText(), apellido.getText(), titulo.getText(),
						LocalDate.now(), detalle.getText(), fuePlanteada == 0 ? false : true , true, "",
						daotematica.searchNombre(comboBox.getSelectedItem().toString()),
						daoreferente.searchId(referentes.get(index).getId())));
				frame.setContentPane(new Inicio(frame));
				frame.validate();
			}
		});
		button.setBounds(51, 243, 91, 23);
		add(button);

		JButton button_1 = new JButton("Volver");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new Inicio(frame));
				frame.validate();
			}
		});
		button_1.setBounds(290, 243, 91, 23);
		add(button_1);

		planteada = new JCheckBox("Fue planteada");
		planteada.setBounds(189, 213, 122, 23);
		add(planteada);

	}
}
