package modeloServidor;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import Texto.ArchivoTexto;
import dao.GrupoDAO;
import dao.UsuarioDAO;
import modeloMensajes.Mensaje;
import modeloMensajes.MensajeChat;
import modeloMensajes.MensajeGrupo;
import modelo.GrupoDeUsuarios;
import modelo.Usuario;

// TODO: Auto-generated 
public class hiloRecibir implements Runnable {

	private ArrayList<Usuario> usuariosConectados;
	private Mensaje mensaje;
	private Usuario user;
	private ArchivoTexto txt;
	private UsuarioDAO uDao = new UsuarioDAO();
	private GrupoDAO gDao = new GrupoDAO();
	private int idUsuario;
	private int idGrupo;
	private ArrayList<GrupoDeUsuarios> grupos;
	private DefaultTableModel dataModel;

	public hiloRecibir(Usuario _user, ArrayList<Usuario> _usuariosConectados, ArchivoTexto _txt,
			ArrayList<GrupoDeUsuarios> _grupos, DefaultTableModel _dataModel) {
		this.user = _user;
		this.usuariosConectados = _usuariosConectados;
		this.txt = _txt;
		this.grupos = _grupos;
		this.dataModel = _dataModel;
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		try {			
			while (true) {
				mensaje = (Mensaje) user.getSocket().recibirObjeto();
				switch (mensaje.getCodigo()) {
				case 3: // unirse
					MensajeGrupo msjUnirme = (MensajeGrupo) mensaje.getObjecto();
					idUsuario = uDao.usuarioPorNombre(msjUnirme.getUsername()).getId();
					idGrupo = gDao.seleccionarGrupoPorNombre(msjUnirme.getNombreGrupo()).getId();
					
					Usuario us = uDao.usuarioPorId(idUsuario);
					for (GrupoDeUsuarios gdu : grupos) {
						if(gdu.getId() == idGrupo) {
							gdu.getUsuarios().add(us);
							new Thread(new hiloTablaGrupos(gdu.getUsuarios(), dataModel)).start();
							txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ') +" [Server info]: El usuario " + msjUnirme.getUsername()
								+ " se ha vinculado al grupo " + msjUnirme.getNombreGrupo());
							break;
						}
					}

					uDao.asignarUsuarioAlGrupo(idUsuario, idGrupo);
					break;
					
				case 4: // salir
					MensajeGrupo msjSalirme = (MensajeGrupo) mensaje.getObjecto();
					idUsuario = uDao.usuarioPorNombre(msjSalirme.getUsername()).getId();
					idGrupo = gDao.seleccionarGrupoPorNombre(msjSalirme.getNombreGrupo()).getId();
					
					for (GrupoDeUsuarios grupoDeUsuarios : grupos) {
						if (grupoDeUsuarios.getId() == idGrupo) {
							for (Usuario user : grupoDeUsuarios.getUsuarios()) {
								if(user.getId() == idUsuario) {
									grupoDeUsuarios.getUsuarios().remove(user);
									new Thread(new hiloTablaGrupos(grupoDeUsuarios.getUsuarios(), dataModel)).start();
									txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ') +" [Server info]: El usuario " + msjSalirme.getUsername()
									+ " se ha desvinculado del grupo " + msjSalirme.getNombreGrupo());
									break;
								}		
							}
						}
					}
					
					uDao.eliminarUsuarioDelGrupo(idUsuario, idGrupo);
					break;
					
				case 5: // msg chat
					MensajeChat msjChat = (MensajeChat) mensaje.getObjecto();
					txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ') +" [Server info]: Mensaje recibido del usuario " + user.getNombreUsuario()
					+ " para el grupo " + msjChat.getNombreGrupo());
					for (Usuario s : usuariosConectados) {
						try {
						if (s.getSocket() != user.getSocket() && s.getGrupo().equals(msjChat.getNombreGrupo())) {
							s.getSocket().enviarObjeto(msjChat);
						}
						}catch(NullPointerException e) {}
					}
					break;
					
				case 6: // conectarme para chatear a un grupo
					MensajeGrupo msjConectar = (MensajeGrupo) mensaje.getObjecto();					
					txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ') +" [Server info]: El usuario " + user.getNombreUsuario()
					+ " se ha unido para chatear en el grupo " + msjConectar.getNombreGrupo());
//					for (GrupoDeUsuarios grupoDeUsuarios : grupos) {
//						if(grupoDeUsuarios.getNombreGrupo().equals(msjConectar.getNombreGrupo())) {
//							for (Usuario uss : grupoDeUsuarios.getUsuarios()) {
//								try {
//								if(uss.getSocket().socketIsConnected()) {
//									MensajeChat mensajeChat = new MensajeChat(
//											"~  Se ha conectado el usuario " + user.getAlias() + "  ~", LocalDateTime.now(), "",
//											msjConectar.getNombreGrupo());
//									uss.getSocket().enviarObjeto(mensajeChat);
//								}
//								} catch(NullPointerException e) {}
//							}
//						}
//					}
					
					for (Usuario usuario : usuariosConectados) {
						try {
						if (usuario.getGrupo().equals(msjConectar.getNombreGrupo())) {
							MensajeChat mensajeChat = new MensajeChat(
									"~  Se ha conectado el usuario " + user.getAlias() + "  ~", LocalDateTime.now(), "",
									msjConectar.getNombreGrupo());
							usuario.getSocket().enviarObjeto(mensajeChat);
						}
						} catch(NullPointerException e) {}
					}
					
					for (Usuario u : usuariosConectados) {
						if (u.getNombreUsuario().equals(msjConectar.getUsername())) {
							for (GrupoDeUsuarios g : grupos) {
								if (g.getNombreGrupo().equals(msjConectar.getNombreGrupo())) {
									for (Usuario ug : g.getUsuarios()) {
										if (ug.getNombreUsuario().equals(msjConectar.getUsername())) {
											ug.setSocket(u.getSocket());
											break;
										}
									}
									new Thread(new hiloTablaGrupos(g.getUsuarios(), dataModel)).start();
									break;
								}
							}
							u.setGrupo(msjConectar.getNombreGrupo());
							break;
						}
					}
					break;
				
				case 7: // desconectarme del servidor, es decir salir
					MensajeGrupo msjDesconectar = (MensajeGrupo) mensaje.getObjecto();

					for (Usuario u : usuariosConectados) {
						if (u.getNombreUsuario().equals(msjDesconectar.getUsername())) {
							u.getSocket().getSocket().close();
							usuariosConectados.remove(u);
							txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ')  + " [Server info]: Se ha desconectado el usuario "
									+ u.getNombreUsuario());
							break;
						}

					}
					break;
				case 8: //grupos para poder unirse
					MensajeGrupo msjGrupos = (MensajeGrupo) mensaje.getObjecto();
					idUsuario = uDao.usuarioPorNombre(msjGrupos.getUsername()).getId();
					ArrayList<String> grupos = gDao.gruposDisponiblesDelUsuario(idUsuario);
					for (Usuario s : usuariosConectados) {
						if (s.getNombreUsuario().equals(msjGrupos.getUsername())) {
							s.getSocket().enviarObjeto(grupos);
						}
					}				
					break;
				case 9://se desconecta el cliente del chat
					MensajeGrupo msjDesconectarChat = (MensajeGrupo) mensaje.getObjecto();	
					txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ') +" [Server info]: El usuario " + user.getNombreUsuario()
					+ " se ha desconectado del grupo " + msjDesconectarChat.getNombreGrupo());
					
					for (Usuario u : usuariosConectados) {
						if (u.getNombreUsuario().equals(msjDesconectarChat.getUsername())) {
							for (GrupoDeUsuarios g : this.grupos) {
								if (g.getNombreGrupo().equals(msjDesconectarChat.getNombreGrupo())) {
									for (Usuario ug : g.getUsuarios()) {
										if (ug.getNombreUsuario().equals(msjDesconectarChat.getUsername())) {
											ug.setSocket(null);
											break;
										}
									}
									break;
								}
							}
							u.getSocket().enviarObjeto(null);
							u.setGrupo(null);
							break;
						}
					}
					for (Usuario usuario : usuariosConectados) {
						try {
						if (usuario.getGrupo().equals(msjDesconectarChat.getNombreGrupo())) {
							MensajeChat mensajeChat = new MensajeChat(
									"~  Se ha desconectado el usuario " + user.getAlias() + "  ~", LocalDateTime.now(), "",
									msjDesconectarChat.getNombreGrupo());
							usuario.getSocket().enviarObjeto(mensajeChat);
						}
						} catch(NullPointerException e) {}
					}
					break;
				default:
					break;
				}

			}

		} catch (SocketException e) {

			if (e.getMessage() == "Connection reset") {	
				for (Usuario u : usuariosConectados) {
					if (u.getNombreUsuario().equals(user.getNombreUsuario())) {
						for (GrupoDeUsuarios g : this.grupos) {
							if (g.getNombreGrupo().equals(user.getGrupo())) {
								for (Usuario ug : g.getUsuarios()) {
									if (ug.getNombreUsuario().equals(user.getNombreUsuario())) {
										ug.setSocket(null);
										break;
									}
								}
								break;
							}
						}
						usuariosConectados.remove(u);
						break;
					}
				}
				for (Usuario usuario : usuariosConectados) {
					try {
					if (usuario.getGrupo().equals(user.getGrupo())) {
						MensajeChat mensajeChat = new MensajeChat(
								"~  Se ha desconectado el usuario " + user.getAlias() + "  ~", LocalDateTime.now(), "",
								user.getGrupo());
						usuario.getSocket().enviarObjeto(mensajeChat);
					}
					} catch(NullPointerException e1) {		
					} catch (IOException e1) {
					}
				}
				// log
				try {
					txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ') + " [Server info]: Se ha desconectado el usuario "
							+ user.getNombreUsuario());
				} catch (IOException e1) {
				}
			}

		} catch (EOFException e) {
			for (Usuario u : usuariosConectados) {
				if (u.getNombreUsuario().equals(user.getNombreUsuario())) {
					for (GrupoDeUsuarios g : this.grupos) {
						if (g.getNombreGrupo().equals(user.getGrupo())) {
							for (Usuario ug : g.getUsuarios()) {
								if (ug.getNombreUsuario().equals(user.getNombreUsuario())) {
									ug.setSocket(null);
									break;
								}
							}
							break;
						}
					}
					usuariosConectados.remove(u);
					break;
				}
			}
			for (Usuario usuario : usuariosConectados) {
				try {
				if (usuario.getGrupo().equals(user.getGrupo())) {
					MensajeChat mensajeChat = new MensajeChat(
							"~  Se ha desconectado el usuario " + user.getAlias() + "  ~", LocalDateTime.now(), "",
							user.getGrupo());
					usuario.getSocket().enviarObjeto(mensajeChat);
				}
				} catch(NullPointerException e1) {		
				} catch (IOException e1) {
				}
			}
			// log
			try {
				txt.escribirArchivo(LocalDateTime.now().toString().replace('T', ' ')  + " [Server info]: Se ha desconectado el usuario "
						+ user.getNombreUsuario());
			} catch (IOException e1) {
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
