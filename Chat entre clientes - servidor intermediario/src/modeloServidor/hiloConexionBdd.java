package modeloServidor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import Sockets.progredes.SocketServidorTCP;
import Texto.ArchivoTexto;
import dao.Conexion;
import interfazServidor.InicioServidor;
import interfazServidor.MainServidor;

public class hiloConexionBdd implements Runnable {
	private Conexion dao = new Conexion();
	private Connection con;
	private MainServidor frame;
	private SocketServidorTCP socket;
	private ArchivoTexto txt;

	public hiloConexionBdd(MainServidor _frame, SocketServidorTCP _socket, ArchivoTexto _txt) {
		this.frame = _frame;
		this.socket = _socket;
		this.txt = _txt;
	}

	@Override
	public void run() {
		try {
			con = dao.isConnected();
			while (true) {
				Thread.sleep(5000);
				if (!con.isValid(5)) {
					throw new SQLException();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ') +" [Server OFF]: Se ha apagado el servidor inesperadamente	[MOTIVO]: Perdida de conexion con la base de datos");
				socket.getServerSocket().close();
			} catch (IOException e1) {
			}
			
			frame.setContentPane(new InicioServidor(frame));
			frame.validate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
