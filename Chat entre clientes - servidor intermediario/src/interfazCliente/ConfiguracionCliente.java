package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Binario.ArchivoBinario;
import ModeloCliente.HiloSocketCliente;
import Sockets.progredes.SocketClienteTCP;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class ConfiguracionCliente extends JPanel {
	private JTextField txtIP;
	private JTextField txtPuerto;
	private JFrame frame2;
	private SocketClienteTCP socketCliente;
	private ArchivoBinario archivoBinario;

	/**
	 * Create the panel.
	 */
	public void mostrarValores () {
		String puerto;
		try {
			archivoBinario.archivoParaLeer();
			try {
				ArrayList<Object> datos = archivoBinario.leerLista();
				txtIP.setText((String) datos.get(0)); 
				txtPuerto.setText((String) datos.get(1)); 
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	public void guardarDatosEnBinario() {				//ANDA
		if (!txtPuerto.getText().trim().isEmpty() && !txtIP.getText().trim().isEmpty()) {
			String input = txtPuerto.getText();
			if (input.matches("-?\\d+")) {
				ArchivoBinario ab = new ArchivoBinario();
				String archivoBinario = "Memoria.bin";
				ab.setArchivo(archivoBinario);
				try {
					ab.archivoParaEscribir(false);
					ArrayList<String> listObjeto = new ArrayList<String>();
					listObjeto.add(txtIP.getText());
					listObjeto.add(txtPuerto.getText());
					ab.escribirObjeto(listObjeto);
					ab.cerrarEscribir();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Informacion guardada exitosamente", "Data saved",JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, reingrese el PUERTO.", "ERROR PUERTO  ~ CLIENTE",
						JOptionPane.INFORMATION_MESSAGE);
				txtPuerto.setText("");
				txtIP.setText("");
			}

			// NumberFormatException
		} else {
			txtPuerto.setText("");
			JOptionPane.showMessageDialog(null, "No dejes los campos vacios", "Empty field  ~ CLIENTE",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public ConfiguracionCliente(JFrame frame, SocketClienteTCP socket, ArchivoBinario ab) {			//ANDA
		setLayout(null);
		socketCliente = socket;
		archivoBinario = ab;
		JLabel lblipServer = new JLabel("IP servidor");
		lblipServer.setBounds(30, 43, 80, 14);
		add(lblipServer);

		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setBounds(30, 68, 55, 14);
		add(lblPuerto);

		txtIP = new JTextField();
		txtIP.setBounds(130, 40, 86, 20);
		add(txtIP);
		txtIP.setColumns(10);

		txtPuerto = new JTextField();
		txtPuerto.setText("");
		txtPuerto.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					guardarDatosEnBinario();
					
				}
			}
		});
		txtPuerto.setBounds(130, 65, 86, 20);
		add(txtPuerto);
		txtPuerto.setColumns(10);
		mostrarValores();
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatosEnBinario();
			}
		});
		btnGuardar.setBounds(95, 98, 89, 23);
		add(btnGuardar);

		JButton btnNewButton = new JButton("<");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2 = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				frame2.setContentPane(new MainCliente(frame, socketCliente, archivoBinario));
				frame2.validate();
			}
		});
		btnNewButton.setBounds(14, 14, 45, 23);
		add(btnNewButton);

	}
}
