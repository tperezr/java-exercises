package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.WebServiceException;

import ws.*;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class LoginCliente extends JPanel {
	private JTextField txtCorreo;
	private JPasswordField passwordField;

	private GmailImplService gmailService;
	private GmailInterface gmail;
	
	private DefaultTableModel dataModel = new DefaultTableModel(new Object[][] {},
			new String[] { "Emisor", "Fecha", "Asunto" }) {
		boolean[] columnEditables = new boolean[] { false };

		public boolean isCellEditable(int row, int column) {
			// return columnEditables[column];
			return false;
		}
	};

	/**
	 * Create the panel.
	 */
	public void loguearUsuario(JFrame frame) {
		try {
			if (gmailService == null) {
				gmailService = new GmailImplService();
				gmail = gmailService.getGmailImplPort();
			}
			if (txtCorreo.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor, no deje campos vacios.", "Campos vacios",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				String correo = txtCorreo.getText();
				String contraseña = passwordField.getText();

				switch (gmail.iniciarSesion(correo, contraseña)) {
				case 0:
					JOptionPane.showMessageDialog(null, "500 Internal Server Error.", "500 Internal Server Error",
							JOptionPane.INFORMATION_MESSAGE);
					txtCorreo.setText("");
					passwordField.setText("");
					break;
				case 1:
					JOptionPane.showMessageDialog(null, "Usuario ya conectado", "User already connected",
							JOptionPane.INFORMATION_MESSAGE);
					txtCorreo.setText("");
					passwordField.setText("");
					break;
				case 2:
					JOptionPane.showMessageDialog(null, "Error de contraseña o email", "Bad entry",
							JOptionPane.INFORMATION_MESSAGE);
					txtCorreo.setText("");
					passwordField.setText("");
					break;
				case 3: // manda directo al inicio con las dos tablas de inbox y outbox
					new Thread(new hiloInbox(dataModel, gmail, correo)).start();
					frame.setContentPane(new InicioCliente(frame, txtCorreo.getText(), gmail, dataModel));
					frame.revalidate();
					break;
				default:
					break;
				}
			}
		} catch (WebServiceException e) {
			JOptionPane.showMessageDialog(null, "El servidor se encuentra apagado", "WS OFF",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public LoginCliente(JFrame frame) { // ANDA
		setLayout(null);

		JLabel lblEmail = new JLabel("Correo electronico");
		lblEmail.setBounds(105, 86, 118, 21);
		add(lblEmail);

		JLabel lblContraseña = new JLabel("Contrase\u00F1a");
		lblContraseña.setBounds(105, 118, 110, 21);
		add(lblContraseña);

		txtCorreo = new JTextField();
		txtCorreo.setBounds(233, 86, 86, 20);
		add(txtCorreo);
		txtCorreo.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// KeyEvent.VK_AT
				// KeyEvent.VK_PERIOD
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loguearUsuario(frame);
				}
			}
		});
		passwordField.setBounds(233, 118, 86, 20);
		add(passwordField);

		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				loguearUsuario(frame);
			}
		});
		btnIngresar.setBounds(232, 158, 89, 23);
		add(btnIngresar);

		JButton btnRegistrar = new JButton("Registrarme");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (gmailService == null) {
						gmailService = new GmailImplService();
						gmail = gmailService.getGmailImplPort();
					}
					frame.setContentPane(new RegistroCliente(frame, gmail));
					frame.revalidate();
				} catch (WebServiceException ee) {
					JOptionPane.showMessageDialog(null, "El servidor se encuentra apagado", "WS OFF",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnRegistrar.setBounds(307, 211, 115, 23);
		add(btnRegistrar);
		char caracter = passwordField.getEchoChar(); // char

		JCheckBox chckbxMostrar = new JCheckBox("Mostrar");
		chckbxMostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxMostrar.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar(caracter);
				}
			}
		});
		chckbxMostrar.setBounds(325, 117, 97, 23);
		add(chckbxMostrar);
		try {
			gmailService = new GmailImplService();
			gmail = gmailService.getGmailImplPort();
		} catch (WebServiceException e) {
			JOptionPane.showMessageDialog(null, "El servidor se encuentra apagado", "WS OFF",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
