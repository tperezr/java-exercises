package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Binario.ArchivoBinario;
import ModeloCliente.HiloSocketCliente;
import Sockets.progredes.SocketClienteTCP;
import modeloMensajes.Mensaje;
import modeloMensajes.MensajeRegistro;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class RegistroCliente extends JPanel {
	private JTextField txtAlias;
	private JTextField txtUsername;
	private JButton btnRegistrarse;
	private JFrame frame2;
	private SocketClienteTCP socketCliente;
	private JPasswordField password;
	private JPasswordField repassword;
	private ArchivoBinario archivoBinario;

	/**
	 * Create the panel.
	 */
	public void registroUsuario(JFrame _frame) { // ANDA
		if (txtAlias.getText().trim().isEmpty() || txtUsername.getText().trim().isEmpty()
				|| password.getText().trim().length() < 0 || repassword.getText().trim().length() < 0) {
			btnRegistrarse.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Por favor, no deje campos vacios.", "Campos vacios",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			MensajeRegistro msgRegistrar = new MensajeRegistro(txtAlias.getText(), txtUsername.getText(),
					password.getText());
			Mensaje objetoMensaje = new Mensaje(2, msgRegistrar);
			String ip = "";
			String puerto;
			int puertoInteger = 0;
			try {
				archivoBinario.archivoParaLeer();
				try {
					ArrayList<Object> datos = archivoBinario.leerLista();
					ip = (String) datos.get(0);
					puerto = (String) datos.get(1);
					puertoInteger = Integer.parseInt(puerto);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			socketCliente.setPuertoEscucha(puertoInteger);
			socketCliente.setIpServer(ip);
			new Thread(new HiloSocketCliente(_frame, socketCliente, objetoMensaje, txtAlias, txtUsername, password))
					.start();
		}
	}

	public RegistroCliente(JFrame frame, SocketClienteTCP socket, ArchivoBinario ab) { // ANDA
		setLayout(null);
		socketCliente = socket;
		archivoBinario = ab;
		JLabel lblAlias = new JLabel("Alias");
		lblAlias.setBounds(63, 66, 46, 14);
		add(lblAlias);

		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario");
		lblNombreDeUsuario.setBounds(63, 91, 110, 14);
		add(lblNombreDeUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(63, 116, 73, 14);
		add(lblContrasea);

		JLabel lblReingreseContrasea = new JLabel("<html>Reingrese <BR>contrase\u00F1a</html>");
		lblReingreseContrasea.setBounds(63, 141, 73, 28);
		add(lblReingreseContrasea);

		txtAlias = new JTextField();
		txtAlias.setBounds(178, 63, 86, 20);
		add(txtAlias);
		txtAlias.setColumns(10);

		txtUsername = new JTextField();
		txtUsername.setBounds(178, 88, 86, 20);
		add(txtUsername);
		txtUsername.setColumns(10);

		JLabel lblNota = new JLabel("");
		lblNota.setBounds(268, 141, 130, 14);
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

		password.setBounds(178, 118, 86, 20);
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
					registroUsuario(frame);
				}
			}
		});
		repassword.setBounds(178, 149, 86, 20);
		add(repassword);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setEnabled(false);
		btnRegistrarse.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				registroUsuario(frame);
			}
		});
		btnRegistrarse.setBounds(153, 206, 120, 23);
		add(btnRegistrarse);

		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2 = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				frame2.setContentPane(new MainCliente(frame, socketCliente, archivoBinario));
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
		chckbxMostrar.setBounds(268, 112, 97, 23);
		add(chckbxMostrar);

	}
}
