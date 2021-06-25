package interfazCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import appServidor.GmailImpl;
import ws.*;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;

public class Outbox extends JPanel {	
	
	private DefaultTableModel dataModelOutbox = new DefaultTableModel(new Object[][] {},
			new String[] { "Receptor", "Fecha", "Asunto" }) {
		boolean[] columnEditables = new boolean[] { false };

		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JTable table;

	/**
	 * Create the panel.
	 */
	public Outbox(JFrame frame, String correo, GmailInterface gmail, DefaultTableModel dataModelInbox) {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 30, 400, 280);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(dataModelOutbox);
		scrollPane.setViewportView(table);
		
		ArrayList<ws.Mail> outbox = (ArrayList<ws.Mail>) gmail.solicitarMailsOutbox(correo);
		for (ws.Mail mail : outbox) {
			String receptor = mail.getFromUser();
			String asunto = mail.getAsunto();
			String fecha = mail.getFecha();
			dataModelOutbox.addRow(new Object [] {receptor, fecha, asunto});
		}
		
		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				gmail.desconexion(correo);
				frame.setContentPane(new LoginCliente(frame));
				frame.revalidate();
			}
		});
		btnDesconectar.setBounds(220, 325, 110, 23);
		add(btnDesconectar);
		
		JButton btnRedactar = new JButton("<html>Redactar <br>Mail</html>");
		btnRedactar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new RedaccionMail(frame, correo, gmail, dataModelInbox));
				frame.revalidate();
			}
		});
		btnRedactar.setBounds(455, 325, 78, 35);
		add(btnRedactar);
		
		JButton btnEnviados = new JButton("<html>Inbox <br>Recibidos</html>");
		btnEnviados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new InicioCliente(frame, correo, gmail, dataModelInbox));
				frame.revalidate();
			}
		});
		btnEnviados.setBounds(10, 325, 89, 35);
		add(btnEnviados);
		
		JLabel lblOutboxEnviados = new JLabel("Outbox ~ Enviados");
		lblOutboxEnviados.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblOutboxEnviados.setBounds(215, 11, 120, 20);
		add(lblOutboxEnviados);
		
		table.addMouseListener(new MouseAdapter() {					//MOUSE LISTENER, DETALLE MAIL
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() > 1) {
					JTable tableDos = (JTable) evt.getSource();
					int row = tableDos.rowAtPoint(evt.getPoint());
					TableModel modelo = table.getModel();
					Mail mailSeleccionado = outbox.get(table.convertRowIndexToModel(row));
					frame.setContentPane(new DetalleMail(frame, mailSeleccionado, correo, gmail,dataModelInbox));
					frame.revalidate();
				}
			}
		});
		
	}
}
