package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Binario.ArchivoBinario;
import ModeloCliente.HiloEnviar;
import Sockets.progredes.SocketClienteTCP;
import modeloMensajes.Mensaje;
import modeloMensajes.MensajeGrupo;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GruposDisponibles extends JPanel {
	private JTable table;
	private Mensaje mensajeUnirme;
	private ArrayList<String> gruposDisponibles = new ArrayList<String>();
	private JFrame frame2;
	// falta crear default table model
	DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Grupos disponibles" }) {

		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	/**
	 * Create the panel.
	 */
	public GruposDisponibles(String username, String aliasUser, SocketClienteTCP socketCliente,
			ArrayList<String> gruposDelUsuario, ArchivoBinario ab, JFrame frame1) {
		setLayout(null);

		try {
			MensajeGrupo msg = new MensajeGrupo(username, null);
			Mensaje msgGruposDisponibles = new Mensaje(8, msg);
			if (socketCliente.getSocket().isConnected()) {
				new Thread(new HiloEnviar(socketCliente, msgGruposDisponibles, frame1, ab)).start();
				gruposDisponibles = (ArrayList<String>) socketCliente.recibirObjeto();
			} else {
				System.out.println("dale bokita");
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (EOFException e2) {
		
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 80, 220, 140);
		add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setResizable(false);
		for (String nombre : gruposDisponibles) { // Cargar INFO a la tabla
			tableModel.addRow(new Object[] { nombre });
		}

		JLabel lblGruposDisponibles = new JLabel("Grupos Disponibles");
		lblGruposDisponibles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGruposDisponibles.setBounds(118, 51, 135, 17);
		add(lblGruposDisponibles);

		JButton btnUnirse = new JButton("Unirse");
		btnUnirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (table.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Seleccione un grupo.", "Grupo no seleccionado.",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					String grupoSeleccionado = table.getModel().getValueAt(row, 0).toString();
					MensajeGrupo unirse = new MensajeGrupo(username, grupoSeleccionado);
					mensajeUnirme = new Mensaje(3, unirse);
					new Thread(new HiloEnviar(socketCliente, mensajeUnirme, frame1, ab)).start();
					frame2 = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
					gruposDelUsuario.add(grupoSeleccionado);
					frame2.setContentPane(
							new InicioCliente(frame1, socketCliente, username, aliasUser, gruposDelUsuario, ab));
					frame2.revalidate();
				}
			}
		});
		btnUnirse.setBounds(140, 236, 89, 23);
		add(btnUnirse);

		JButton btnAtras = new JButton("<");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2 = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				frame2.setContentPane(
						new InicioCliente(frame1, socketCliente, username, aliasUser, gruposDelUsuario, ab));
				frame2.revalidate();
			}
		});
		btnAtras.setBounds(50, 24, 45, 23);
		add(btnAtras);

	}
}
