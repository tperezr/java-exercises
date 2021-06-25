package InterfazServidor;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Modelo.Usuario;
import ModeloServidor.hiloAceptarClientes;
import Sockets.progredes.SocketServidorTCP;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.EventObject;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class InicioServidor extends JPanel {
	private JTextField textPuerto;
	private SocketServidorTCP socket;
	private ArrayList<Usuario> usuarios;
	private DefaultTableModel model = new DefaultTableModel(new Object[][] {},
			new String[] { "Nombre", "IP", "Puerto" }) {
		boolean[] columnEditables = new boolean[] { false, false, false };

		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}
	};

	public void sendMessage(EventObject arg0) {
		try {
			Integer puerto = Integer.valueOf(textPuerto.getText());

			if (puerto >= 0 && puerto <= 65535) {
				try {
					usuarios = new ArrayList<Usuario>();
					socket = new SocketServidorTCP();
					socket.setPuertoEscucha(puerto);
					socket.escuchar();
					new Thread(new hiloAceptarClientes(socket, model, usuarios)).start();

					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) arg0.getSource());
					marco.setContentPane(new Principal(model, socket, usuarios));
					marco.validate();
				} catch (BindException e) {
					JOptionPane.showMessageDialog(null, "El puerto ya esta siendo utilizado", "Invalid port ~ SERVER",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un numero de puerto valido",
							"Invalid port ~ SERVER", JOptionPane.INFORMATION_MESSAGE);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "ERROR, puerto invalido. Ingrese un puerto VALIDO",
						"Invalid port ~ SERVER", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Debe ingresar un puerto válido", "Invalid port ~ SERVER",
					JOptionPane.INFORMATION_MESSAGE);
		}
		finally {
			textPuerto.setText("");
		}
	}

	/**
	 * Create the panel.
	 */
	public InicioServidor() {
		setLayout(null);

		textPuerto = new JTextField();
		textPuerto.setBounds(140, 100, 86, 20);
		textPuerto.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage(e);
				}

			}
		});
		add(textPuerto);
		textPuerto.setColumns(10);

		JLabel lblIngresePuerto = new JLabel("Ingrese puerto ");
		lblIngresePuerto.setBounds(140, 75, 86, 14);
		add(lblIngresePuerto);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMessage(arg0);
			}
		});
		btnAceptar.setBounds(140, 130, 86, 23);
		add(btnAceptar);
	}
}
