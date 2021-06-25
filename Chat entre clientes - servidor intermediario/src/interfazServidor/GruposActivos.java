package interfazServidor;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import Sockets.progredes.SocketServidorTCP;
import dao.GrupoDAO;
import modelo.GrupoDeUsuarios;
import modelo.Usuario;
import modeloMensajes.Mensaje;
import Texto.ArchivoTexto;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class GruposActivos extends JPanel {
	private JTable table;
	private JFrame frame;
	private GrupoDAO gDao = new GrupoDAO();
	private JButton btnVerUsuarios;
	private JButton btnBorrarGrupo;
	private ArrayList<GrupoDeUsuarios> grupos;

	private DefaultTableModel dataModel = new DefaultTableModel(new Object[][] {}, new String[] { "Grupos" }) {
		boolean[] columnEditables = new boolean[] { false };

		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}
	};

	/**
	 * Create the panel.
	 */
	public GruposActivos(SocketServidorTCP socket, ArrayList<Usuario> usuariosConectados, ArchivoTexto txt,
			ArrayList<GrupoDeUsuarios> _grupos, DefaultTableModel modelUsuarios) {
		setLayout(null);
		grupos = _grupos;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 39, 188, 162);
		add(scrollPane);

		try {
			txt.archivoParaEscribir(true);

			txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ')
					+ " [Server ON]: Se ha encendido el servidor correctamente en el puerto "
					+ socket.getPuertoEscucha());
		} catch (IOException e) {
		}

		table = new JTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRow() > -1) {
					btnVerUsuarios.setEnabled(true);
					btnBorrarGrupo.setEnabled(true);
				}

			}
		});
		table.setModel(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getColumn(0).setResizable(false);
		scrollPane.setViewportView(table);

		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					socket.getServerSocket().close();

					for (Usuario user : usuariosConectados) {
						user.getSocket().getSocket().close();
					}
					if (!usuariosConectados.isEmpty()) {
						txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ')
								+ " [Server OFF]: Se han desconectado todos los clientes");
					}
					txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ')
							+ " [Server OFF]: Se ha apagado el servidor correctamente");
					txt.escribirArchivo(
							"------------------------------------------------------------------------------------------------");
					txt.cerrarEscribir();

					usuariosConectados.removeAll(usuariosConectados);
					frame = (JFrame) SwingUtilities.getWindowAncestor((Component) arg0.getSource());
					frame.setContentPane(new InicioServidor((MainServidor) frame));
					frame.validate();

				} catch (NullPointerException e) {
				} catch (SocketException e) {
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnDesconectar.setBounds(68, 212, 110, 23);
		add(btnDesconectar);

		JButton btnCrearGrupo = new JButton("Crear grupo");
		btnCrearGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boolean validacion = true;
				frame = (JFrame) SwingUtilities.getWindowAncestor((Component) arg0.getSource());

				String grupoNombre = null;

				try {
					grupoNombre = JOptionPane.showInputDialog(frame, "Nombre del grupo, minimo 3 caracteres",
							"Nuevo Grupo", JOptionPane.PLAIN_MESSAGE).trim();
				} catch (NullPointerException e) {
				}

				if (grupoNombre != null && grupoNombre.length() > 2) {
					for (GrupoDeUsuarios g : grupos) {
						if (g.getNombreGrupo() == grupoNombre) {
							validacion = false;
							break;
						}
					}
				} else {
					validacion = false;
				}
				if (validacion) {
					gDao.crearGrupo(grupoNombre);
					dataModel.setRowCount(0);
					try {
						grupos = gDao.seleccionarTodosGrupos();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					for (GrupoDeUsuarios gdu : grupos) {
						dataModel.addRow(new Object[] { gdu.getNombreGrupo() });
					}
					try {
						txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ')
								+ " [Server Action]: Se ha creado un nuevo grupo con el nombre: " + grupoNombre);
					} catch (IOException e) {
					}
				}

			}
		});
		btnCrearGrupo.setBounds(250, 64, 130, 23);
		add(btnCrearGrupo);

		btnVerUsuarios = new JButton("Ver integrantes");
		btnVerUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = table.getSelectedRow();
				String nombre = dataModel.getValueAt(index, 0).toString();
				for (GrupoDeUsuarios gdu : grupos) {
					if (gdu.getNombreGrupo().equals(nombre)) {
						frame = (JFrame) SwingUtilities.getWindowAncestor((Component) arg0.getSource());
						frame.setContentPane(new UsuariosConectados(socket, gdu.getUsuarios(), usuariosConectados, txt,
								grupos, modelUsuarios));
						frame.validate();
					}
				}
			}
		});
		btnVerUsuarios.setEnabled(false);
		btnVerUsuarios.setBounds(250, 105, 130, 23);
		add(btnVerUsuarios);

		btnBorrarGrupo = new JButton("Borrar grupo");
		btnBorrarGrupo.setEnabled(false);
		btnBorrarGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				String nombre = dataModel.getValueAt(index, 0).toString();
				gDao.eliminarGrupoPorNombre(nombre);
				for (GrupoDeUsuarios g : grupos) {
					if (g.getNombreGrupo().equals(nombre)) {
						grupos.remove(g);
						dataModel.setRowCount(0);
						for (GrupoDeUsuarios gdu : grupos) {
							dataModel.addRow(new Object[] { gdu.getNombreGrupo() });
						}
						break;
					}
				}

				for (Usuario us : usuariosConectados) {
					try {
						if (us.getGrupo().equals(nombre)) {
							try {
								us.getSocket().enviarObjeto(new Mensaje(0, null));
							} catch (IOException e1) {
							}
							us.setGrupo(null);
						}
					} catch (NullPointerException e1) {
					}
				}
			}
		});
		btnBorrarGrupo.setBounds(250, 147, 130, 23);
		add(btnBorrarGrupo);

		dataModel.setRowCount(0);
		for (GrupoDeUsuarios gdu : grupos) {
			dataModel.addRow(new Object[] { gdu.getNombreGrupo() });
		}

		// cuando se cierra desde la x, pasa esto
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					for (Usuario usuario : usuariosConectados) {
						usuario.getSocket().getSocket().close();
					}
					socket.getServerSocket().close();
					txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ')
							+ " [Server OFF]: Se ha apagado el servidor abruptamente");
					txt.escribirArchivo(
							"------------------------------------------------------------------------------------------------");
					txt.cerrarEscribir();
				} catch (IOException e) {
				}
			}
		});
	}
}
