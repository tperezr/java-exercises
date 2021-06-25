package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.JTextField;

import Binario.ArchivoBinario;
import ModeloCliente.HiloEnviar;
import ModeloCliente.HiloSocketCliente;
import Sockets.progredes.SocketClienteTCP;
import modeloMensajes.Mensaje;
import modeloMensajes.MensajeLogin;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

public class MainCliente extends JPanel {
	private JTextField txtNombre;
	private JPasswordField passwordField;
	private SocketClienteTCP socketCliente;
	private ArchivoBinario archivoBinario;

	/**
	 * Create the panel.
	 */

	public void loguearUsuario(JFrame frame) {			//ANDA
		if (txtNombre.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, no deje campos vacios.", "Campos vacios",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			String username = txtNombre.getText();
			String contraseña = passwordField.getText();
			MensajeLogin msgLogin = new MensajeLogin(username, contraseña);
			Mensaje mensajeLogin = new Mensaje(1, msgLogin);
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
			socketCliente.setIpServer(ip);
			socketCliente.setPuertoEscucha(puertoInteger);
			new Thread(new HiloSocketCliente(frame, socketCliente, mensajeLogin, txtNombre, passwordField, archivoBinario)).start();
		}
	}

	public MainCliente(JFrame frame, SocketClienteTCP socketCli, ArchivoBinario ab) {		//ANDA
		setLayout(null);
		socketCliente = socketCli;
		archivoBinario = ab;
		frame.setTitle("Cliente");

		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario");
		lblNombreDeUsuario.setBounds(72, 60, 118, 21);
		add(lblNombreDeUsuario);

		JLabel lblContraseña = new JLabel("Contrase\u00F1a");
		lblContraseña.setBounds(72, 92, 110, 21);
		add(lblContraseña);

		txtNombre = new JTextField();
		txtNombre.setBounds(187, 60, 86, 20);
		add(txtNombre);
		txtNombre.setColumns(10);

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
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loguearUsuario(frame);
				}
			}
		});
		passwordField.setBounds(187, 92, 86, 20);
		add(passwordField);

		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				loguearUsuario(frame);
			}
		});
		btnIngresar.setBounds(164, 132, 89, 23);
		add(btnIngresar);

		JButton btnConfiguracion = new JButton("Configuracion");
		btnConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new ConfiguracionCliente(frame, socketCliente, archivoBinario));
				frame.revalidate();
			}
		});
		btnConfiguracion.setBounds(72, 200, 120, 23);
		add(btnConfiguracion);

		JButton btnRegistrar = new JButton("Registrarme");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new RegistroCliente(frame, socketCliente, archivoBinario));
				frame.revalidate();
			}
		});
		btnRegistrar.setBounds(240, 200, 115, 23);
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
		chckbxMostrar.setBounds(279, 91, 97, 23);
		add(chckbxMostrar);

	}
}
