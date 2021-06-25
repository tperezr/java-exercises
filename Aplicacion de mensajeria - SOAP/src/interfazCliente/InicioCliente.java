package interfazCliente;

import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.sun.javafx.scene.paint.GradientUtils.Point;

import ws.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class InicioCliente extends JPanel {
	private JTable table;
	private JFrame frame2;

	/**
	 * Create the panel.
	 */
	public InicioCliente(JFrame frame, String correo, GmailInterface gmail, DefaultTableModel dataModel) {
		ArrayList<ws.Mail> inbox = (ArrayList<ws.Mail>) gmail.solicitarMailsInbox(correo);
		dataModel.setRowCount(0);
		for (ws.Mail mail : inbox) {
			String emisor = mail.getFromUser();
			String asunto = mail.getAsunto();
			String fecha = mail.getFecha();
			dataModel.addRow(new Object[] { emisor, fecha, asunto });
		}

		setFont(new Font("Tahoma", Font.PLAIN, 14));
		setLayout(null);

		// buscar la forma de conseguir el mail seleccionado de la tabla.

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 30, 400, 280);
		add(scrollPane);

		table = new JTable();
		table.setModel(dataModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setResizable(false);

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
				frame.setContentPane(new RedaccionMail(frame, correo, gmail, dataModel));
				frame.revalidate();
			}
		});
		btnRedactar.setBounds(455, 325, 78, 35);
		add(btnRedactar);

		JButton btnEnviados = new JButton("<html>Outbox <br>Enviados</html>");
		btnEnviados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(new Outbox(frame, correo, gmail, dataModel));
				frame.revalidate();
			}
		});
		btnEnviados.setBounds(10, 325, 89, 35);
		add(btnEnviados);

		JLabel lblNewLabel = new JLabel("Inbox ~ Recibidos");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel.setBounds(215, 11, 120, 20);
		add(lblNewLabel);

		table.addMouseListener(new MouseAdapter() {					//MOUSE LISTENER, DETALLE MAIL
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() > 1) {
					JTable tableDos = (JTable) evt.getSource();
					int row = tableDos.rowAtPoint(evt.getPoint());
					Mail mailSeleccionado = inbox.get(table.convertRowIndexToModel(row));
					frame.setContentPane(new DetalleMail(frame, mailSeleccionado, correo, gmail, dataModel));
					frame.revalidate();
				}

			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				gmail.desconexion(correo);
			}
		});
	}
}
