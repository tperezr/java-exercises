package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PantallaInicial extends JPanel {

	public PantallaInicial(JFrame marco) {
		setLayout(null);
		
		JButton btnVisualizarProblematicas = new JButton("Visualizar Problematicas");
		btnVisualizarProblematicas.setBounds(38, 200, 145, 23);
		add(btnVisualizarProblematicas);
		
		JButton btnVisualizarPropuestas = new JButton("Visualizar Propuestas");
		btnVisualizarPropuestas.setBounds(38, 237, 145, 23);
		add(btnVisualizarPropuestas);
		
		JButton btnNewButton = new JButton("Cargar Problematica");
		btnNewButton.setBounds(208, 200, 154, 23);
		add(btnNewButton);
		
		JButton btnCargarPropuesta = new JButton("Cargar Propuesta");
		btnCargarPropuesta.setBounds(208, 237, 154, 23);
		add(btnCargarPropuesta);
		
		JButton btnNewButton_1 = new JButton("Actores institucionales");
		btnNewButton_1.setBounds(38, 166, 145, 23);
		add(btnNewButton_1);

	}
}

