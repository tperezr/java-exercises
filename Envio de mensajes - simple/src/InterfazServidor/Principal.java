package InterfazServidor;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import Modelo.Usuario;
import Sockets.progredes.SocketClienteTCP;
import Sockets.progredes.SocketServidorTCP;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class Principal extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public Principal(DefaultTableModel dataModel, SocketServidorTCP socket, ArrayList<Usuario> usuarios) {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(65, 39, 271, 162);
		add(scrollPane);

		table = new JTable();
		table.setModel(dataModel);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		scrollPane.setViewportView(table);

		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					socket.getServerSocket().close();
					
					for (Usuario user : usuarios) {
						
						user.getSocket().getSocket().close();
						
					}
					if(!usuarios.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Se han desconectado todos los cliente", "Server off ~ SERVER",
								JOptionPane.INFORMATION_MESSAGE);
					}
					usuarios.removeAll(usuarios);
					
					
					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) arg0.getSource());
					marco.setContentPane(new InicioServidor());
					marco.validate();
					
				} catch (NullPointerException e) {
				} catch (SocketException e) {
				} catch (IOException e) {
				}
			}
		});
		btnDesconectar.setBounds(135, 212, 110, 23);
		add(btnDesconectar);
	}
}
