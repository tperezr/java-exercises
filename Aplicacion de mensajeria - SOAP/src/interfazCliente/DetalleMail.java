package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import ws.GmailInterface;
import ws.Mail;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class DetalleMail extends JPanel {
	private JTextField txtAsunto;
	private JTextField txtEmisor;
	private JTextField txtFecha;
	private JTextArea textArea;
	private JTextField textReceptor;

	/**
	 * Create the panel.
	 */
	public DetalleMail(JFrame frame, Mail mail, String correoUser, GmailInterface gmail, DefaultTableModel dataModelInbox) {
		setLayout(null);
		System.out.println(mail.getAsunto());
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(75, 60, 400, 300);
		add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		scrollPane.setViewportView(textArea);
		Border border = BorderFactory.createMatteBorder(2, 2, 1 / 2, 1 / 2, new Color(64, 64, 64));
		textArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(4, 4, 4, 4)));

		txtAsunto = new JTextField();
		txtAsunto.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtAsunto.setEditable(false);
		scrollPane.setColumnHeaderView(txtAsunto);
		txtAsunto.setText("Asunto");
		txtAsunto.setColumns(10);

		txtEmisor = new JTextField();
		txtEmisor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtEmisor.setBounds(75, 29, 120, 20);
		txtEmisor.setEditable(false);
		add(txtEmisor);
		txtEmisor.setColumns(10);

		txtFecha = new JTextField();
		txtFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtFecha.setBounds(345, 29, 130, 20);
		txtFecha.setEditable(false);
		add(txtFecha);
		txtFecha.setColumns(10);

		textReceptor = new JTextField();
		textReceptor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textReceptor.setEditable(false);
		textReceptor.setBounds(215, 29, 120, 20);
		add(textReceptor);
		textReceptor.setColumns(10);
		
		// carga de data a los campos
		if (mail.getFromUser().equals(correoUser)) {
			txtEmisor.setText(correoUser);
		} else {
			txtEmisor.setText(mail.getFromUser());
		}
		textReceptor.setText(mail.getFromUser());
		txtAsunto.setText(mail.getAsunto());
		txtFecha.setText(mail.getFecha());
		textArea.setText(mail.getCuerpo());

		JButton buttonAtras = new JButton("<");
		buttonAtras.setBounds(10, 14, 45, 23);
		buttonAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new InicioCliente(frame, correoUser, gmail, dataModelInbox));
				frame.revalidate();
			}
		});
		add(buttonAtras);

		JLabel lblMailDe = new JLabel("Mail de:");
		lblMailDe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMailDe.setBounds(75, 14, 70, 16);
		add(lblMailDe);

		JLabel labelReceptor = new JLabel("Receptor:");
		labelReceptor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelReceptor.setBounds(215, 11, 70, 16);
		add(labelReceptor);

	}
}
