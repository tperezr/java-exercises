package interfazCliente;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Binario.ArchivoBinario;
import ModeloCliente.HiloEnviar;
import Sockets.progredes.SocketClienteTCP;
import dao.GrupoDAO;
import modelo.GrupoDeUsuarios;
import modeloMensajes.Mensaje;
import modeloMensajes.MensajeGrupo;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class InicioCliente extends JPanel {
	private JTable table;

	private DefaultTableModel dataModel = new DefaultTableModel(new Object[][] {}, new String[] { "Mis Grupos" }) {
		boolean[] columnEditables = new boolean[] { false };

		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}
	};
	private JFrame frame2;
	private ArchivoBinario ab = new ArchivoBinario();
	//private ArrayList<GrupoDeUsuarios> grupos = new ArrayList<GrupoDeUsuarios>();
	private GrupoDAO gDao = new GrupoDAO();
	private SocketClienteTCP socketCliente;
	/**
	 * Create the panel.
	 */
	public void desconectarCliente (JFrame frame2, EventObject e2, ArchivoBinario ab2, String username) {
		try {
			Mensaje msj = new Mensaje(7, new MensajeGrupo(username, null));
			socketCliente.enviarObjeto(msj);
			
			socketCliente.getSocket().close();
			frame2 = (JFrame) SwingUtilities.getWindowAncestor((Component) e2.getSource());
			frame2.setContentPane(new MainCliente(frame2, socketCliente,ab2));
			frame2.validate();
		} catch (NullPointerException e1) {
		} catch (EOFException e1) {
		} catch (IOException e1) {
		}
	}
	public InicioCliente(JFrame frame, SocketClienteTCP socket, String username, String alias, ArrayList<String> nombreGruposUsuario, ArchivoBinario ab) {
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		setLayout(null);
		socketCliente = socket;
		frame.setTitle("Cliente ~ " + username + "/" + alias);
		
		JLabel lblMisGrupos = new JLabel("Mis grupos");
		lblMisGrupos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMisGrupos.setBounds(111, 60, 75, 17);
		add(lblMisGrupos);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 89, 195, 130);
		add(scrollPane);

		table = new JTable();
		table.setModel(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setResizable(false);
		for (String nombre : nombreGruposUsuario) {				//Cargar INFO a la tabla
			dataModel.addRow(new Object [] {nombre});
		}
		
		JButton btnEntrarAlGrupo = new JButton("Entrar al chat");
		btnEntrarAlGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (table.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Seleccione un grupo.", "Grupo no seleccionado.", JOptionPane.INFORMATION_MESSAGE);
				}else {
					//mandar al chat del grupo seleccioado
					String nombreGrupo = table.getModel().getValueAt(row, 0).toString();
					MensajeGrupo msg = new MensajeGrupo(username, nombreGrupo);
					new Thread(new HiloEnviar(socket, new Mensaje(6, msg),frame,ab)).start();
					frame.setContentPane(new GrupoDeChat(username,alias, nombreGrupo, socket,frame,nombreGruposUsuario,ab));
					frame.revalidate();
				}
			}
		});
		btnEntrarAlGrupo.setBounds(251, 106, 145, 23);
		add(btnEntrarAlGrupo);
		
		JButton btnUnirseAlGrupo = new JButton("Unirse a un grupo");
		btnUnirseAlGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				socketCliente.getSocket().toString();
					frame.setContentPane(new GruposDisponibles(username, alias,socketCliente,nombreGruposUsuario, ab, frame));
					frame.revalidate();
			}
		});
		btnUnirseAlGrupo.setBounds(251, 143, 145, 23);
		add(btnUnirseAlGrupo);

		JButton btnSalirDelGrupo = new JButton("Salir del grupo");
		btnSalirDelGrupo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (table.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Seleccione un grupo.", "Grupo no seleccionado.", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String grupoSeleccionado = (table.getModel().getValueAt(row, 0).toString());

					MensajeGrupo msg = new MensajeGrupo(username,grupoSeleccionado);
					Mensaje mensajeSalir = new Mensaje(4, msg);

					new Thread(new HiloEnviar(socketCliente, mensajeSalir,frame,ab)).start();
					nombreGruposUsuario.remove(grupoSeleccionado);
					dataModel.setRowCount(0);
					for (String nombre : nombreGruposUsuario) {				//Cargar INFO a la tabla
						dataModel.addRow(new Object [] {nombre});
					}
				}
			}
		});
		btnSalirDelGrupo.setBounds(251, 176, 145, 23);
		add(btnSalirDelGrupo);

		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desconectarCliente(frame, e, ab, username);
			}
		});
		btnDesconectar.setBounds(93, 233, 110, 23);
		add(btnDesconectar);
		
		

	}
}
