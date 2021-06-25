package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Binario.ArchivoBinario;
import ModeloCliente.HiloEnviar;
import ModeloCliente.HiloRecibir;
import Sockets.progredes.SocketClienteTCP;
import modeloMensajes.Mensaje;
import modeloMensajes.MensajeChat;
import modeloMensajes.MensajeGrupo;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GrupoDeChat extends JPanel {
	private JTextField textMensaje;
	private JButton btnEnviarMensaje;
	private JTextArea textArea;
	private JLabel lblNombreGrupo;
	private JFrame frame2;
	/**
	 * Create the panel.
	 */
	public void enviarMensaje() {

	}

	public GrupoDeChat(String username, String aliasUser, String nombreGrupo, SocketClienteTCP socketCli, JFrame frame, ArrayList<String> grupos, ArchivoBinario ab) {
		setLayout(null);
		
		lblNombreGrupo = new JLabel(nombreGrupo);
		lblNombreGrupo.setBounds(150, 7, 100, 14);
		add(lblNombreGrupo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		scrollPane.setBounds(10, 30, 410, 230);
		add(scrollPane);

		textArea= new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);

		textMensaje = new JTextField();
		textMensaje.setBounds(10, 265, 366, 20);
		textMensaje.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (textMensaje.getText().trim().isEmpty() || socketCli.getSocket().isClosed()) {
					btnEnviarMensaje.setEnabled(false);
				} else {
					btnEnviarMensaje.setEnabled(true);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// enviar mensaje
					if (!textMensaje.getText().trim().isEmpty() && socketCli.socketIsConnected()) {
						MensajeChat msg = new MensajeChat(textMensaje.getText(), LocalDateTime.now(), aliasUser,
								nombreGrupo);
						Mensaje mensajeDeChat = new Mensaje(5, msg);
						new Thread(new HiloEnviar(socketCli, mensajeDeChat,frame,ab)).start();
						textMensaje.setText("");
						if (textArea.getText().isEmpty()) {
							textArea.setText(msg.toString() + " " + aliasUser + " " + msg.getMsj());
						} else {
							textArea.setText(textArea.getText() + "\n" + msg.toString() + " " + aliasUser + " " + msg.getMsj());
						}
					}
				}
			}
		});
		add(textMensaje);
		textMensaje.setColumns(10);

		btnEnviarMensaje = new JButton(">");
		btnEnviarMensaje.setEnabled(false);
		btnEnviarMensaje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!textMensaje.getText().trim().isEmpty() && socketCli.socketIsConnected()) {
						MensajeChat msg = new MensajeChat(textMensaje.getText(), LocalDateTime.now(), aliasUser,
								nombreGrupo);
						Mensaje mensajeDeChat = new Mensaje(5, msg);
						new Thread(new HiloEnviar(socketCli, mensajeDeChat,frame,ab)).start();
						textMensaje.setText("");
						if (textArea.getText().isEmpty()) {
							textArea.setText(msg.toString() + " " + aliasUser + " " + msg.getMsj());
						} else {
							textArea.setText(textArea.getText() + "\n" + msg.toString() + " " + aliasUser + " " + msg.getMsj());
						}
						btnEnviarMensaje.setEnabled(false);
				}else {
				}
			}
		});
		btnEnviarMensaje.setBounds(378, 265, 42, 20);
		add(btnEnviarMensaje);
		
		JButton btnAtras = new JButton("<");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MensajeGrupo msg = new MensajeGrupo(username, nombreGrupo);
				new Thread(new HiloEnviar(socketCli, new Mensaje(9, msg),frame,ab)).start();
				
				
				frame2 = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				frame2.setContentPane(new InicioCliente(frame, socketCli, username, aliasUser, grupos, ab));
				frame2.validate();
			}
		});
		btnAtras.setBounds(10, 3, 45, 23);
		add(btnAtras);

		Thread hiloRecibirChat =  new Thread(new HiloRecibir(socketCli, textArea, lblNombreGrupo, frame, ab,username,aliasUser,grupos));
		hiloRecibirChat.start();
		
	}
}
