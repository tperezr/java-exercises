package InterfazCliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import Modelo.Mensaje;
import Modelo.Usuario;
import ModeloCliente.HiloEnviar;
import ModeloCliente.HiloSocketCliente;
import Sockets.progredes.SocketClienteTCP;

public class Cliente extends JPanel {

	private JTextField textPuerto;
	private JTextField textNick;
	private JTextField textIp;
	private JTextField txtDesconectado;
	private JTextField textMsj;
	private JButton btnSend;
	private JTextArea textArea;
	private Mensaje mensajeCliente = new Mensaje();
	private JButton btnDesconectar;
	private SocketClienteTCP socket = new SocketClienteTCP();
	private Thread thread;

	public Cliente() {
		setLayout(null);

		JLabel lblPuertoDeEscucha = new JLabel("IP del servidor");
		lblPuertoDeEscucha.setBounds(10, 36, 107, 14);
		add(lblPuertoDeEscucha);

		textPuerto = new JTextField();
		textPuerto.setBounds(127, 58, 86, 20);
		add(textPuerto);
		textPuerto.setColumns(10);

		JLabel lblNick = new JLabel("Nick");
		lblNick.setBounds(10, 11, 46, 14);
		add(lblNick);

		textNick = new JTextField();
		textNick.setBounds(127, 8, 86, 20);
		add(textNick);
		textNick.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(10, 132, 428, 227);
		add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(278, 36, 46, 14);
		add(lblStatus);

		txtDesconectado = new JTextField();
		txtDesconectado.setHorizontalAlignment(SwingConstants.CENTER);
		txtDesconectado.setText("OFF");
		txtDesconectado.setForeground(Color.RED);
		txtDesconectado.setEditable(false);
		txtDesconectado.setBounds(254, 58, 86, 20);
		add(txtDesconectado);
		txtDesconectado.setColumns(10);

		textMsj = new JTextField();
		textMsj.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (textMsj.getText().trim().isEmpty() || socket.getSocket().isClosed()
							|| textMsj.getText().trim() == "") {
						btnSend.setEnabled(false);
					} else {
						btnSend.setEnabled(true);
					}
				} catch (Exception E) {
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						if (!textMsj.getText().trim().isEmpty() && socket.socketIsConnected()) {
							try {
								mensajeCliente = new Mensaje(textMsj.getText().trim(), LocalDateTime.now(),
										textNick.getText());
								textMsj.setText("");
								HiloEnviar hiloEnviar = new HiloEnviar(socket, mensajeCliente, textArea, textMsj);
								Thread threadEnviar = new Thread(hiloEnviar);
								threadEnviar.start();

								textArea.setText(textArea.getText() + "\n" + mensajeCliente.toString() + " "
										+ mensajeCliente.getEmisor() + ": " + mensajeCliente.getMsj());

							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					} catch (Exception E) {
					}
				}
			}
		});
		textMsj.setBounds(10, 370, 363, 20);
		add(textMsj);
		textMsj.setColumns(10);

		textIp = new JTextField();
		textIp.setColumns(10);
		textIp.setBounds(127, 33, 86, 20);
		add(textIp);

		JLabel label = new JLabel("Puerto servidor");
		label.setBounds(10, 61, 107, 14);
		add(label);

		JButton btnNewButton = new JButton("Conectar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textPuerto.getText().trim().isEmpty() && !textNick.getText().trim().isEmpty()
						&& !textIp.getText().trim().isEmpty()) {
					textNick.setEditable(false);
					textIp.setEditable(false);
					textPuerto.setEditable(false);
					textArea.setText("");
					btnDesconectar.setEnabled(false);
					String input = textPuerto.getText();

					if (input.matches("-?\\d+")) {
						socket.setPuertoEscucha(Integer.parseInt(textPuerto.getText()));
						socket.setIpServer(textIp.getText());
						HiloSocketCliente hiloSocket = new HiloSocketCliente(socket, mensajeCliente, textArea,
								btnNewButton, btnDesconectar, txtDesconectado, textNick, textPuerto, textIp);

						thread = new Thread(hiloSocket);
						thread.start();
						mensajeCliente.setFechayhora(LocalDateTime.now());
					} else {
						JOptionPane.showMessageDialog(null, "Por favor, reingrese el PUERTO.",
								"ERROR PUERTO  ~ CLIENTE", JOptionPane.INFORMATION_MESSAGE);
						textPuerto.setEditable(true);
						textIp.setEditable(true);
						textIp.setText("");
						textPuerto.setText("");

					}

					// NumberFormatException
				} else {
					textPuerto.setText("");
					textNick.setText("");
					JOptionPane.showMessageDialog(null, "No dejes los campos vacios", "Empty field  ~ CLIENTE",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		btnNewButton.setBounds(116, 89, 107, 23);
		add(btnNewButton);
		btnSend = new JButton(">");// send
		btnSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					if (!textMsj.getText().trim().isEmpty() && socket.socketIsConnected()) {
						mensajeCliente = new Mensaje(textMsj.getText().trim(), LocalDateTime.now(), textNick.getText());
						textMsj.setText("");
						HiloEnviar hiloEnviar = new HiloEnviar(socket, mensajeCliente, textArea, textMsj);
						Thread threadEnviar = new Thread(hiloEnviar);
						threadEnviar.start();

						textArea.setText(textArea.getText() + "\n" + mensajeCliente.toString() + " "
								+ mensajeCliente.getEmisor() + ": " + mensajeCliente.getMsj());

					} else {
						btnSend.setEnabled(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSend.setEnabled(false);
		btnSend.setBounds(383, 370, 55, 20);
		add(btnSend);

		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setEnabled(false);
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textNick.setEditable(true);
				textPuerto.setEditable(true);
				textIp.setEditable(true);
				btnNewButton.setEnabled(true);

				textNick.setText("");
				textPuerto.setText("");
				textIp.setText("");
				btnNewButton.setEnabled(true);
				txtDesconectado.setText("OFF");
				txtDesconectado.setForeground(Color.RED);
				btnDesconectar.setEnabled(false);
				try {
					socket.cerrar();
				} catch (NullPointerException e) {
				} catch (EOFException e) {
				} catch (IOException e) {
				}

			}
		});
		btnDesconectar.setBounds(242, 89, 107, 23);
		add(btnDesconectar);

		textIp.setText("localhost");
		textNick.setText("z");
		textPuerto.setText(String.valueOf(5000));

	}
}
