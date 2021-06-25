package estructuraTP.vista;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import estructuraTP.dao.ProblematicaDAO;
import estructuraTP.dao.ReferenteDAO;
import estructuraTP.modelo.Problematica;
import estructuraTP.modelo.Referente;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarProblematica extends JPanel{
	private JTextField textField;
	
	public ModificarProblematica(JFrame frame, Problematica p) {
		setLayout(null);
		frame.setTitle("Modificacion de datos: problematica");
		frame.setSize(340, 290);
		
		ReferenteDAO daoreferente = new ReferenteDAO();
		ProblematicaDAO daoproblematica = new ProblematicaDAO();
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 11, 46, 14);
		add(lblId);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setText(String.valueOf(p.getId())); 
		textField.setBounds(102, 8, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblReferente = new JLabel("Referente");
		lblReferente.setBounds(10, 47, 61, 14);
		add(lblReferente);
		
		JLabel lblObservaciones = new JLabel("Observaciones");
		lblObservaciones.setText("Observacion");
		lblObservaciones.setBounds(10, 83, 79, 14);
		add(lblObservaciones);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(102, 78, 180, 120);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setText(p.getObservacion());
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(99);
		//comboBox.addItem(p.getReferente().getNombre() + " " + p.getReferente().getApellido());
		
		ArrayList<Referente> referentes = daoreferente.obtenerTodas();
		for (Referente r : referentes) {
			//if(r.getId() != p.getId()) {
				comboBox.addItem(r.getId() + "@ " + r.getNombre() + " " + r.getApellido());
			//}
		}
		comboBox.getModel().setSelectedItem(p.getTematica().getId());
		comboBox.setBounds(102, 44, 180, 20);
		add(comboBox);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				daoproblematica.actualizar(Integer.parseInt(textField.getText()),comboBox.getSelectedIndex() + 1 , textArea.getText());				
				
				frame.setContentPane(new Inicio(frame));
			    frame.validate();
			}
		});
		btnGuardar.setBounds(40, 220, 89, 23);
		add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setContentPane(new Inicio(frame));
			    frame.validate();
			}
		});
		btnVolver.setBounds(176, 220, 89, 23);
		add(btnVolver);
	}
}
