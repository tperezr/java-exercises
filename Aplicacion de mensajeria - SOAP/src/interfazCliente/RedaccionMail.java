package interfazCliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import ws.GmailInterface;

import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RedaccionMail extends JPanel {
	private JTextField txtAsunto;
	private JTextField txtDestinatario;
	private JLabel lblDestinatario;
	private JButton btnEnviarMail;
	private JScrollPane scrollPane;
	private JTextArea txtAreaCuerpo;

	/**
	 * Create the panel.
	 */

	public RedaccionMail(JFrame frame, String correo, GmailInterface gmail, DefaultTableModel dataModelInbox) {
		setLayout(null);

		txtAsunto = new JTextField();
		txtAsunto.setToolTipText("");
		txtAsunto.setBounds(72, 37, 200, 20);
		add(txtAsunto);
		txtAsunto.setColumns(10);

		txtDestinatario = new JTextField();
		txtDestinatario.setBounds(72, 88, 200, 20);
		add(txtDestinatario);
		txtDestinatario.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(65, 119, 420, 180);
		add(scrollPane);

		txtAreaCuerpo = new JTextArea();
		scrollPane.setViewportView(txtAreaCuerpo);
		txtAreaCuerpo.setLineWrap(true);
		txtAreaCuerpo.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		Border border = BorderFactory.createMatteBorder(2, 2, 1 / 2, 1 / 2, new Color(64, 64, 64));
		txtAreaCuerpo
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(4, 4, 4, 4)));

		JLabel lblAsunto = new JLabel("Asunto");
		lblAsunto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAsunto.setBounds(74, 17, 50, 14);
		add(lblAsunto);

		lblDestinatario = new JLabel("Destinatario");
		lblDestinatario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDestinatario.setBounds(62, 68, 80, 14);
		add(lblDestinatario);

		btnEnviarMail = new JButton("Enviar mail");
		btnEnviarMail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEnviarMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtAsunto.getText().trim().isEmpty() || txtDestinatario.getText().trim().isEmpty()
						|| txtAreaCuerpo.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, no deje campos vacios.", "Empty fields",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int op = gmail.enviarMail(txtAsunto.getText(), txtAreaCuerpo.getText(), txtDestinatario.getText(),
							correo);
					if (op == 0) {
						JOptionPane.showMessageDialog(null, "No existe el email destinatario", "Invalid e-mail",
								JOptionPane.INFORMATION_MESSAGE);
						txtDestinatario.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Mail enviado", "Successful operation",
								JOptionPane.INFORMATION_MESSAGE);
						frame.setContentPane(new InicioCliente(frame, correo, gmail, dataModelInbox));
						frame.revalidate();
					}
				}
			}
		});
		btnEnviarMail.setEnabled(true);
		btnEnviarMail.setBounds(225, 310, 100, 22);
		add(btnEnviarMail);

		JButton btnAtras = new JButton("<");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new InicioCliente(frame, correo, gmail, dataModelInbox));
				frame.revalidate();
			}
		});
		btnAtras.setBounds(10, 14, 45, 23);
		add(btnAtras);

	}
}
