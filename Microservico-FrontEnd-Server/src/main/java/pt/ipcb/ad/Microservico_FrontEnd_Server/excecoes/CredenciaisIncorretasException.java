package pt.ipcb.ad.Microservico_FrontEnd_Server.excecoes;

public class CredenciaisIncorretasException extends RuntimeException {
    public CredenciaisIncorretasException(String message) {
        super(message);
    }
}
