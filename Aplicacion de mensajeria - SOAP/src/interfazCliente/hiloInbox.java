package interfazCliente;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.WebServiceException;

import modelo.Mail;
import ws.GmailInterface;

public class hiloInbox implements Runnable {
	private ArrayList<ws.Mail> listaInboxMails;
	private DefaultTableModel model;
	private GmailInterface gmail;
	private String correo;

	public hiloInbox(DefaultTableModel model, GmailInterface _gmail, String correo) {
		super();
		this.model = model;
		this.gmail = _gmail;
		this.correo = correo;
	}

	@Override
	public void run() {
		try {
			while (true) {
				listaInboxMails = (ArrayList<ws.Mail>) gmail.solicitarMailsInbox(correo);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						model.setRowCount(0);
						for (ws.Mail mail : listaInboxMails) {
							model.addRow(new Object[] { mail.getFromUser(), mail.getFecha(), mail.getAsunto() });
						}

					}
				});
				Thread.sleep(10000);
			}
		} catch (WebServiceException e) {

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
