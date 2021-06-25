package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.xml.ws.WebServiceException;

import ws.*;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class RegistroCliente extends JPanel {
	private JTextField txtAlias;
	private JTextField txtCorreo;
	private JButton btnRegistrarse;
	private JFrame frame2;
	private JPasswordField password;
	private JPasswordField repassword;
	private JTextField txtApellido;
	private JLabel lblNota;
	private JLabel lblLabelco;

	/**
	 * Create the panel.
	 */
	public void registroUsuario(JFrame _frame, GmailInterface gmail) { // ANDA
		String regex = "^[a-zA-Z]+$";
		try {
		if (txtAlias.getText().trim().isEmpty() || txtCorreo.getText().trim().isEmpty()
				|| password.getText().trim().length() < 0 || repassword.getText().trim().length() < 0) {
			btnRegistrarse.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Por favor, no deje campos vacios.", "Campos vacios",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			if (txtAlias.getText().matches(regex) && txtApellido.getText().matches(regex)) {
				btnRegistrarse.setEnabled(true);
				// mandar al inicio
				switch (gmail.registrarse(txtAlias.getText(), txtApellido.getText(), txtCorreo.getText(),
						password.getText())) {
				case 1:
					JOptionPane.showMessageDialog(null, "Dirección de correo ya en uso", "Mail already in use",
							JOptionPane.INFORMATION_MESSAGE);
					txtCorreo.setText("");
					break;
				case 2: // registro exitoso
					_frame.setContentPane(new LoginCliente(_frame));
					_frame.revalidate();
					break;
				default:
					break;
				}

			} else {
				JOptionPane.showMessageDialog(null, "Ingrese valores validos en Nombre y Apellido.", "ERROR",
						JOptionPane.INFORMATION_MESSAGE);
				txtAlias.setText("");
				txtCorreo.setText("");
			}
		}
		} catch (WebServiceException e) {
			_frame.setContentPane(new LoginCliente(_frame));
			_frame.revalidate();
		}
	}

	public RegistroCliente(JFrame frame, GmailInterface gmail) { // ANDA

		setLayout(null);
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(112, 38, 46, 14);
		add(lblNombre);

		JLabel lblCorreoElectronico = new JLabel("Correo electronico");
		lblCorreoElectronico.setBounds(112, 98, 110, 14);
		add(lblCorreoElectronico);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(112, 123, 73, 14);
		add(lblContrasea);

		JLabel lblReingreseContrasea = new JLabel("<html>Reingrese <BR>contrase\u00F1a</html>");
		lblReingreseContrasea.setBounds(112, 148, 73, 28);
		add(lblReingreseContrasea);

		txtAlias = new JTextField();
		txtAlias.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		txtAlias.setBounds(232, 38, 86, 20);
		add(txtAlias);
		txtAlias.setColumns(10);

		lblLabelco = new JLabel("");
		lblLabelco.setBounds(328, 104, 100, 14);
		add(lblLabelco);

		txtCorreo = new JTextField(); // realizar validaciones del . y el @
		txtCorreo.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
				String regex3 = "^([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?)";
				if (txtCorreo.getText().contains("@")) {
					if (txtCorreo.getText().matches(regex)) {
						lblLabelco.setText("Valido");
						btnRegistrarse.setEnabled(true);
					} else {
						lblLabelco.setText("NO valido");
						btnRegistrarse.setEnabled(false);
					}
				} else {
					if (txtCorreo.getText().contains(".")) {
						if (txtCorreo.getText().matches(regex3)) {
							lblLabelco.setText("Valido");
							btnRegistrarse.setEnabled(true);
						} else {
							lblLabelco.setText("NO valido");
							btnRegistrarse.setEnabled(false);
						}
					} else {
						lblLabelco.setText("NO valido");
						btnRegistrarse.setEnabled(false);
					}

				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		txtCorreo.setBounds(232, 98, 86, 20);
		add(txtCorreo);
		txtCorreo.setColumns(10);

		lblNota = new JLabel("");
		lblNota.setBounds(328, 148, 130, 14);
		add(lblNota);

		password = new JPasswordField();
		password.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@SuppressWarnings("deprecation")
			@Override
			public void keyReleased(KeyEvent e) {
				if (password.getText().equals(repassword.getText()) && !password.getText().trim().isEmpty()) {
					btnRegistrarse.setEnabled(true);
					lblNota.setText("Coinciden");
				} else {
					btnRegistrarse.setEnabled(false);
					lblNota.setText("No hay coincidencia");
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		password.setBounds(232, 128, 86, 20);
		add(password);

		repassword = new JPasswordField();
		repassword.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					btnRegistrarse.setEnabled(false);
					lblNota.setText("No ingrese espacios");
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_SPACE) {
					if (password.getText().equals(repassword.getText()) && !password.getText().trim().isEmpty()) {
						btnRegistrarse.setEnabled(true);
						lblNota.setText("Coinciden");
					} else {
						btnRegistrarse.setEnabled(false);
						lblNota.setText("No hay coincidencia");
					}
				} else {
					if (password.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
						btnRegistrarse.setEnabled(false);
						lblNota.setText("No ingrese espacios");
					}
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// se registra
				}
			}
		});
		repassword.setBounds(232, 159, 86, 20);
		add(repassword);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setEnabled(false);
		btnRegistrarse.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(lblLabelco.getText().equals("Valido")) {
					registroUsuario(frame, gmail);
				} else {
					JOptionPane.showMessageDialog(null, "Dirección de mail no valida.", "Error e-mail",
							JOptionPane.INFORMATION_MESSAGE);
					txtCorreo.setText("");
					btnRegistrarse.setEnabled(false);
				}
			}
		});
		btnRegistrarse.setBounds(215, 216, 120, 23);
		add(btnRegistrarse);

		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2 = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				frame2.setContentPane(new LoginCliente(frame));
				frame2.validate();
			}
		});
		button.setBounds(10, 14, 45, 23);
		add(button);
		char caracter = password.getEchoChar();
		char caracterdos = repassword.getEchoChar();
		JCheckBox chckbxMostrar = new JCheckBox("Mostrar");
		chckbxMostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxMostrar.isSelected()) {
					password.setEchoChar((char) 0);
					repassword.setEchoChar((char) 0);
				} else {
					password.setEchoChar(caracter);
					repassword.setEchoChar(caracterdos);
				}
			}
		});
		chckbxMostrar.setBounds(324, 122, 97, 23);
		add(chckbxMostrar);

		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(232, 70, 86, 20);
		add(txtApellido);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(112, 70, 46, 14);
		add(lblApellido);

	}
}
