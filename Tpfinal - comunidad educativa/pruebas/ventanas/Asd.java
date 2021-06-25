package ventanas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

public class Asd extends JPanel {

	/**
	 * Create the panel.
	 */
	public Asd() {
		setLayout(null);
		
		JLabel label = new JLabel("Filtrar por caducidad:");
		label.setBounds(9, 89, 124, 14);
		add(label);
		
		JCheckBox checkBox = new JCheckBox("Caducadas");
		checkBox.setBounds(119, 110, 97, 23);
		add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("Activas");
		checkBox_1.setBounds(119, 136, 97, 23);
		add(checkBox_1);
		
		JLabel label_1 = new JLabel("Filtro para problematicas");
		label_1.setBounds(143, 90, 142, 14);
		add(label_1);
		
		JLabel label_2 = new JLabel("Filtrar por estado:");
		label_2.setBounds(222, 114, 142, 14);
		add(label_2);
		
		JCheckBox checkBox_2 = new JCheckBox("Abiertas");
		checkBox_2.setBounds(321, 110, 89, 23);
		add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("Cerradas");
		checkBox_3.setBounds(321, 136, 97, 23);
		add(checkBox_3);
		
		JLabel label_3 = new JLabel("Filtrar por caducidad:");
		label_3.setBounds(428, 114, 124, 14);
		add(label_3);
		
		JLabel label_4 = new JLabel("Filtro para propuestas");
		label_4.setBounds(496, 90, 142, 14);
		add(label_4);
		
		JCheckBox checkBox_4 = new JCheckBox("Caducadas");
		checkBox_4.setBounds(558, 110, 97, 23);
		add(checkBox_4);
		
		JCheckBox checkBox_5 = new JCheckBox("Activas");
		checkBox_5.setBounds(558, 136, 97, 23);
		add(checkBox_5);

	}
}
