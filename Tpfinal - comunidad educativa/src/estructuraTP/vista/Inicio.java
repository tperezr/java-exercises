package estructuraTP.vista;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import estructuraTP.dao.ProblematicaDAO;
import estructuraTP.dao.PropuestaDAO;
import estructuraTP.dao.ReferenteDAO;
import estructuraTP.dao.TematicaDAO;
import estructuraTP.modelo.Post;
import estructuraTP.modelo.Problematica;
import estructuraTP.modelo.Propuestas;
import javax.swing.ListSelectionModel;

public class Inicio extends JPanel {
	private JTable table;
	private DefaultTableModel dataModel;
	private JCheckBox chckbxNewCheckBox;
	private JCheckBox chckbxMostrarPropuestas;
	private JTextField textField;
	private ArrayList<Problematica> problematicas;
	private ArrayList<Propuestas> propuestas;
	private static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	public void mostrarTodesProblematicas() {



		for (Problematica c : problematicas) {

			dataModel.addRow(new Object[] { c.getId(), c.getTitulo(), c.getTematica().getNombre(),
					c.getPersonaQuePosteoNombre() + " " + c.getPersonaQuePosteoApellido(),
					c.getReferente().getNombre() + " " + c.getReferente().getApellido(), c.getCerrada() == true ? "Abierta" : "Cerrada", 
					c.getFechaPublicacion(), "", c.getFuePlanteada() == true ? "si" : "no" });
	
		}
	}

	public void mostrarTodesPropuestas(ArrayList<Propuestas> propuestas) {

		for (Propuestas c : propuestas) {
			dataModel.addRow(new Object[] { c.getId(), c.getTitulo(), c.getTematica().getNombre(),
					c.getPersonaQuePosteoNombre() + " " + c.getPersonaQuePosteoApellido(), "", "",
					c.getFechaPublicacion(), c.getFechaCaducidad(), "" });
		}
		
		
	}

	public Inicio(JFrame frame) {
		setLayout(null);
		frame.setTitle("Pantalla de inicio");
		frame.setSize(930, 320);

		ProblematicaDAO daoproblematica = new ProblematicaDAO();
		PropuestaDAO daopropuesta = new PropuestaDAO();
		TematicaDAO daotematica = new TematicaDAO();
		ReferenteDAO daoreferente = new ReferenteDAO();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 35, 884, 154);
		add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataModel = (new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Titulo", "Tematica", "Planteada por", "Referente", "Estado",
						"Fecha de publicacion", "Fecha de caducidad", "Fue planteada" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setModel(dataModel);
		table.setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				ArrayList<Post> publicaciones = new ArrayList<>();
				if (chckbxMostrarPropuestas.isSelected()) {
					publicaciones.addAll(propuestas);
				}
				if (chckbxNewCheckBox.isSelected()) {
					publicaciones.addAll(problematicas);
				}

				Post p = publicaciones.get(row);
				
				if (p.caducar()) {
					c.setBackground(Color.RED);
		            c.setForeground(Color.WHITE);
				}
				return c;
			}
		});

		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(86);
		table.getColumnModel().getColumn(5).setPreferredWidth(58);
		table.getColumnModel().getColumn(6).setPreferredWidth(121);
		table.getColumnModel().getColumn(7).setPreferredWidth(110);
		table.getColumnModel().getColumn(8).setPreferredWidth(91);
		scrollPane.setViewportView(table);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					if (table.getValueAt(table.getSelectedRow(), 5) == "") {
						Propuestas p =  daopropuesta.busquedaUnaPropuesta((int) table.getValueAt(table.getSelectedRow(), 0));
						frame.setContentPane(new ModificarPropuesta(frame, p));
						frame.validate();
					} else {
						Problematica p = daoproblematica
								.busquedaUnaProblematica((int) table.getValueAt(table.getSelectedRow(), 0));
						frame.setContentPane(new ModificarProblematica(frame, p));
						frame.validate();
					}
				}
			}
		});
		btnModificar.setBounds(20, 200, 188, 23);
		add(btnModificar);

		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.setBounds(10, 10, 58, 14);
		add(lblBuscar);

		chckbxNewCheckBox = new JCheckBox("Mostrar problematicas");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				if (chckbxNewCheckBox.isSelected()) {
					problematicas = daoproblematica.obtenerTodos();
					mostrarTodesProblematicas();
				} else {
					/*for (int i = 0; i < table.getRowCount(); i++) {
						if (table.getValueAt(i, 5) != "") {
							dataModel.removeRow(i);
						}
					}
					*/
					//chckbxMostrarPropuestas.setSelected(false);
					dataModel.setRowCount(0);
				}
			}
		});
		chckbxNewCheckBox.setBounds(667, 230, 164, 23);
		add(chckbxNewCheckBox);

		chckbxMostrarPropuestas = new JCheckBox("Mostrar propuestas");
		chckbxMostrarPropuestas.setBounds(667, 200, 142, 23);
		chckbxMostrarPropuestas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxMostrarPropuestas.isSelected()) {
					propuestas = daopropuesta.obtenerTodos();
					mostrarTodesPropuestas(propuestas);
				} else {
					/*for (int i = 0; i < table.getRowCount(); i++) {
						if (table.getValueAt(i, 5) == "") {
							dataModel.removeRow(i);
						}
					}*/
					//chckbxNewCheckBox.setSelected(false);
					dataModel.setRowCount(0);
					
				}
			}
		});
		add(chckbxMostrarPropuestas);

		JButton btnCargarProblematica = new JButton("Cargar problematica");
		btnCargarProblematica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new CargarProblematica(frame));
				frame.validate();
			}
		});
		btnCargarProblematica.setBounds(218, 200, 188, 23);
		add(btnCargarProblematica);

		JButton btnCargarPropuesta = new JButton("Cargar propuesta");
		btnCargarPropuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new CargarPropuesta(frame));
				frame.validate();
			}
		});
		btnCargarPropuesta.setBounds(416, 200, 188, 23);
		add(btnCargarPropuesta);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					if (table.getValueAt(table.getSelectedRow(), 5) == "") {
						daopropuesta.eliminar((int) table.getValueAt(table.getSelectedRow(), 0));
						dataModel.removeRow(table.getSelectedRow());
					} else {
						daoproblematica.eliminar((int) table.getValueAt(table.getSelectedRow(), 0));
						dataModel.removeRow(table.getSelectedRow());
					}
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado un post para eliminar", "Unselected row",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnEliminar.setBounds(20, 230, 188, 23);
		add(btnEliminar);

		JButton btnCerrarPost = new JButton("Cerrar post");
		btnCerrarPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					if (table.getValueAt(table.getSelectedRow(), 5) != "") {
						daoproblematica.cerrar((int) table.getValueAt(table.getSelectedRow(), 0));
					}
				}
			}
		});
		btnCerrarPost.setBounds(218, 230, 188, 23);
		add(btnCerrarPost);

		JButton btnReferentesInstitucionales = new JButton("Referentes institucionales");
		btnReferentesInstitucionales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new ABMreferentes(frame));
				frame.validate();
			}
		});
		btnReferentesInstitucionales.setBounds(416, 230, 188, 23);
		add(btnReferentesInstitucionales);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(444, 7, 116, 20);
		add(dateChooser);

		JLabel lblDesde = new JLabel("Desde");
		lblDesde.setBounds(388, 10, 46, 14);
		add(lblDesde);

		JLabel lblHasta = new JLabel("Hasta");
		lblHasta.setBounds(584, 10, 46, 14);
		add(lblHasta);

		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(640, 7, 116, 20);
		add(dateChooser_1);

		JButton btnAceptar = new JButton("Buscar por fecha");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxMostrarPropuestas.isSelected()) {
					dataModel.setRowCount(0);
					java.sql.Date date1 = new java.sql.Date(dateChooser.getDate().getTime());
					java.sql.Date date2 = new java.sql.Date(dateChooser_1.getDate().getTime());
					mostrarTodesPropuestas(daopropuesta.searchPorFecha(date1, date2));
				}
				if (chckbxNewCheckBox.isSelected()) {
					dataModel.setRowCount(0);
					java.sql.Date date1 = new java.sql.Date(dateChooser.getDate().getTime());
					java.sql.Date date2 = new java.sql.Date(dateChooser_1.getDate().getTime());
					problematicas = daoproblematica.searchPorFecha(date1, date2);
					mostrarTodesProblematicas();
				}
			}
		});
		btnAceptar.setBounds(766, 6, 136, 23);
		add(btnAceptar);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataModel.setRowCount(0);
				if (chckbxNewCheckBox.isSelected()) {
					problematicas = daoproblematica.searchPorTematica(textField.getText());
					mostrarTodesProblematicas();
				}
				if (chckbxMostrarPropuestas.isSelected()) {
					mostrarTodesPropuestas(daopropuesta.searchPorTematica(textField.getText()));
				}
			}

		});
		textField.setBounds(57, 7, 308, 20);
		add(textField);
		textField.setColumns(10);

	}
}

/*
 * table.setModel(new DefaultTableModel( new Object[][] { }, new String[] {
 * "Titulo", "Tematica", "Planteada por", "Referente", "Estado",
 * "Fecha de publicacion", "Fecha de caducidad", "Fue planteada" } ) { boolean[]
 * columnEditables = new boolean[] { false, false, false, false, false, false,
 * false, false }; public boolean isCellEditable(int row, int column) { return
 * columnEditables[column]; } });
 */