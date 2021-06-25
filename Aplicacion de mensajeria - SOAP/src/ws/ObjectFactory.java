
package ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IniciarSesion_QNAME = new QName("http://appServidor/", "iniciarSesion");
    private final static QName _RegistrarseResponse_QNAME = new QName("http://appServidor/", "registrarseResponse");
    private final static QName _DesconexionResponse_QNAME = new QName("http://appServidor/", "desconexionResponse");
    private final static QName _IniciarSesionResponse_QNAME = new QName("http://appServidor/", "iniciarSesionResponse");
    private final static QName _SolicitarMailsOutbox_QNAME = new QName("http://appServidor/", "solicitarMailsOutbox");
    private final static QName _SolicitarMailsOutboxResponse_QNAME = new QName("http://appServidor/", "solicitarMailsOutboxResponse");
    private final static QName _Registrarse_QNAME = new QName("http://appServidor/", "registrarse");
    private final static QName _Desconexion_QNAME = new QName("http://appServidor/", "desconexion");
    private final static QName _EnviarMailResponse_QNAME = new QName("http://appServidor/", "enviarMailResponse");
    private final static QName _EnviarMail_QNAME = new QName("http://appServidor/", "enviarMail");
    private final static QName _SolicitarMailsInboxResponse_QNAME = new QName("http://appServidor/", "solicitarMailsInboxResponse");
    private final static QName _BorrarMailInbox_QNAME = new QName("http://appServidor/", "borrarMailInbox");
    private final static QName _BorrarMailInboxResponse_QNAME = new QName("http://appServidor/", "borrarMailInboxResponse");
    private final static QName _BorrarMailOutbox_QNAME = new QName("http://appServidor/", "borrarMailOutbox");
    private final static QName _SolicitarMailsInbox_QNAME = new QName("http://appServidor/", "solicitarMailsInbox");
    private final static QName _BorrarMailOutboxResponse_QNAME = new QName("http://appServidor/", "borrarMailOutboxResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BorrarMailInbox }
     * 
     */
    public BorrarMailInbox createBorrarMailInbox() {
        return new BorrarMailInbox();
    }

    /**
     * Create an instance of {@link SolicitarMailsInboxResponse }
     * 
     */
    public SolicitarMailsInboxResponse createSolicitarMailsInboxResponse() {
        return new SolicitarMailsInboxResponse();
    }

    /**
     * Create an instance of {@link BorrarMailOutboxResponse }
     * 
     */
    public BorrarMailOutboxResponse createBorrarMailOutboxResponse() {
        return new BorrarMailOutboxResponse();
    }

    /**
     * Create an instance of {@link BorrarMailInboxResponse }
     * 
     */
    public BorrarMailInboxResponse createBorrarMailInboxResponse() {
        return new BorrarMailInboxResponse();
    }

    /**
     * Create an instance of {@link BorrarMailOutbox }
     * 
     */
    public BorrarMailOutbox createBorrarMailOutbox() {
        return new BorrarMailOutbox();
    }

    /**
     * Create an instance of {@link SolicitarMailsInbox }
     * 
     */
    public SolicitarMailsInbox createSolicitarMailsInbox() {
        return new SolicitarMailsInbox();
    }

    /**
     * Create an instance of {@link EnviarMailResponse }
     * 
     */
    public EnviarMailResponse createEnviarMailResponse() {
        return new EnviarMailResponse();
    }

    /**
     * Create an instance of {@link EnviarMail }
     * 
     */
    public EnviarMail createEnviarMail() {
        return new EnviarMail();
    }

    /**
     * Create an instance of {@link SolicitarMailsOutbox }
     * 
     */
    public SolicitarMailsOutbox createSolicitarMailsOutbox() {
        return new SolicitarMailsOutbox();
    }

    /**
     * Create an instance of {@link SolicitarMailsOutboxResponse }
     * 
     */
    public SolicitarMailsOutboxResponse createSolicitarMailsOutboxResponse() {
        return new SolicitarMailsOutboxResponse();
    }

    /**
     * Create an instance of {@link Desconexion }
     * 
     */
    public Desconexion createDesconexion() {
        return new Desconexion();
    }

    /**
     * Create an instance of {@link Registrarse }
     * 
     */
    public Registrarse createRegistrarse() {
        return new Registrarse();
    }

    /**
     * Create an instance of {@link RegistrarseResponse }
     * 
     */
    public RegistrarseResponse createRegistrarseResponse() {
        return new RegistrarseResponse();
    }

    /**
     * Create an instance of {@link IniciarSesion }
     * 
     */
    public IniciarSesion createIniciarSesion() {
        return new IniciarSesion();
    }

    /**
     * Create an instance of {@link IniciarSesionResponse }
     * 
     */
    public IniciarSesionResponse createIniciarSesionResponse() {
        return new IniciarSesionResponse();
    }

    /**
     * Create an instance of {@link DesconexionResponse }
     * 
     */
    public DesconexionResponse createDesconexionResponse() {
        return new DesconexionResponse();
    }

    /**
     * Create an instance of {@link Mail }
     * 
     */
    public Mail createMail() {
        return new Mail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IniciarSesion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "iniciarSesion")
    public JAXBElement<IniciarSesion> createIniciarSesion(IniciarSesion value) {
        return new JAXBElement<IniciarSesion>(_IniciarSesion_QNAME, IniciarSesion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "registrarseResponse")
    public JAXBElement<RegistrarseResponse> createRegistrarseResponse(RegistrarseResponse value) {
        return new JAXBElement<RegistrarseResponse>(_RegistrarseResponse_QNAME, RegistrarseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DesconexionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "desconexionResponse")
    public JAXBElement<DesconexionResponse> createDesconexionResponse(DesconexionResponse value) {
        return new JAXBElement<DesconexionResponse>(_DesconexionResponse_QNAME, DesconexionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IniciarSesionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "iniciarSesionResponse")
    public JAXBElement<IniciarSesionResponse> createIniciarSesionResponse(IniciarSesionResponse value) {
        return new JAXBElement<IniciarSesionResponse>(_IniciarSesionResponse_QNAME, IniciarSesionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarMailsOutbox }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "solicitarMailsOutbox")
    public JAXBElement<SolicitarMailsOutbox> createSolicitarMailsOutbox(SolicitarMailsOutbox value) {
        return new JAXBElement<SolicitarMailsOutbox>(_SolicitarMailsOutbox_QNAME, SolicitarMailsOutbox.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarMailsOutboxResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "solicitarMailsOutboxResponse")
    public JAXBElement<SolicitarMailsOutboxResponse> createSolicitarMailsOutboxResponse(SolicitarMailsOutboxResponse value) {
        return new JAXBElement<SolicitarMailsOutboxResponse>(_SolicitarMailsOutboxResponse_QNAME, SolicitarMailsOutboxResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Registrarse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "registrarse")
    public JAXBElement<Registrarse> createRegistrarse(Registrarse value) {
        return new JAXBElement<Registrarse>(_Registrarse_QNAME, Registrarse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Desconexion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "desconexion")
    public JAXBElement<Desconexion> createDesconexion(Desconexion value) {
        return new JAXBElement<Desconexion>(_Desconexion_QNAME, Desconexion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarMailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "enviarMailResponse")
    public JAXBElement<EnviarMailResponse> createEnviarMailResponse(EnviarMailResponse value) {
        return new JAXBElement<EnviarMailResponse>(_EnviarMailResponse_QNAME, EnviarMailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarMail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "enviarMail")
    public JAXBElement<EnviarMail> createEnviarMail(EnviarMail value) {
        return new JAXBElement<EnviarMail>(_EnviarMail_QNAME, EnviarMail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarMailsInboxResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "solicitarMailsInboxResponse")
    public JAXBElement<SolicitarMailsInboxResponse> createSolicitarMailsInboxResponse(SolicitarMailsInboxResponse value) {
        return new JAXBElement<SolicitarMailsInboxResponse>(_SolicitarMailsInboxResponse_QNAME, SolicitarMailsInboxResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarMailInbox }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "borrarMailInbox")
    public JAXBElement<BorrarMailInbox> createBorrarMailInbox(BorrarMailInbox value) {
        return new JAXBElement<BorrarMailInbox>(_BorrarMailInbox_QNAME, BorrarMailInbox.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarMailInboxResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "borrarMailInboxResponse")
    public JAXBElement<BorrarMailInboxResponse> createBorrarMailInboxResponse(BorrarMailInboxResponse value) {
        return new JAXBElement<BorrarMailInboxResponse>(_BorrarMailInboxResponse_QNAME, BorrarMailInboxResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarMailOutbox }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "borrarMailOutbox")
    public JAXBElement<BorrarMailOutbox> createBorrarMailOutbox(BorrarMailOutbox value) {
        return new JAXBElement<BorrarMailOutbox>(_BorrarMailOutbox_QNAME, BorrarMailOutbox.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarMailsInbox }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "solicitarMailsInbox")
    public JAXBElement<SolicitarMailsInbox> createSolicitarMailsInbox(SolicitarMailsInbox value) {
        return new JAXBElement<SolicitarMailsInbox>(_SolicitarMailsInbox_QNAME, SolicitarMailsInbox.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarMailOutboxResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://appServidor/", name = "borrarMailOutboxResponse")
    public JAXBElement<BorrarMailOutboxResponse> createBorrarMailOutboxResponse(BorrarMailOutboxResponse value) {
        return new JAXBElement<BorrarMailOutboxResponse>(_BorrarMailOutboxResponse_QNAME, BorrarMailOutboxResponse.class, null, value);
    }

}
