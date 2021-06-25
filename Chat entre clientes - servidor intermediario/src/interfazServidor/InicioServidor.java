package interfazServidor;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Sockets.progredes.SocketServidorTCP;
import Texto.ArchivoTexto;
import dao.Conexion;
import dao.GrupoDAO;
import modelo.GrupoDeUsuarios;
import modelo.Usuario;
import modeloServidor.hiloAceptarClientes;
import modeloServidor.hiloConexionBdd;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.BindException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class InicioServidor extends JPanel {
	private JTextField textPuerto;
	private SocketServidorTCP socket;
	private ArrayList<Usuario> usuariosConectados = new ArrayList<Usuario>();
	private ArrayList<GrupoDeUsuarios> grupos = new ArrayList<GrupoDeUsuarios>();
	
	private DefaultTableModel dataModel = new DefaultTableModel(new Object[][] {},
			new String[] { "Nombre", "IP", "Puerto" }) 
	{
		boolean[] columnEditables = new boolean[] { false, false, false };

		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}
	};
	
	private ArchivoTexto txt = new ArchivoTexto();
	private GrupoDAO gDao = new GrupoDAO();
	private Conexion dao = new Conexion();
	
	public void abrirServidor(EventObject arg0, MainServidor marco) {
		try {
			Integer puerto = Integer.valueOf(textPuerto.getText());

			if (puerto >= 0 && puerto <= 65535) {
				try {
					Connection con = dao.isConnected();//chequeo la bdd, si esta off SQLException
					grupos=gDao.seleccionarTodosGrupos();
					
					socket = new SocketServidorTCP();
					socket.setPuertoEscucha(puerto);
					socket.escuchar();
					new Thread(new hiloAceptarClientes(socket, usuariosConectados, grupos, txt, dataModel)).start();
					new Thread(new hiloConexionBdd(marco,socket,txt)).start();
					
					
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) arg0.getSource());
					frame.setContentPane(new GruposActivos(socket,usuariosConectados, txt, grupos, dataModel));
					frame.validate();
				} catch (BindException e) {
					JOptionPane.showMessageDialog(null, "El puerto ya esta siendo utilizado", "Invalid port ~ SERVER",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un numero de puerto valido",
							"Invalid port ~ SERVER", JOptionPane.INFORMATION_MESSAGE);

				} catch (ClassNotFoundException e) {
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Base de datos apagada",
							"SERVER", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
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

	public InicioServidor(MainServidor marco) {
		setLayout(null);
		txt.setArchivo("Server_Log.txt");
		
		textPuerto = new JTextField("5000");
		textPuerto.setBounds(148, 100, 86, 20);
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
					abrirServidor(e, marco);
				}

			}
		});
		add(textPuerto);
		textPuerto.setColumns(10);

		JLabel lblIngresePuerto = new JLabel("Ingrese puerto ");
		lblIngresePuerto.setBounds(148, 75, 86, 14);
		add(lblIngresePuerto);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirServidor(arg0, marco);
			}
		});
		btnAceptar.setBounds(148, 130, 86, 23);
		add(btnAceptar);
	}
}
