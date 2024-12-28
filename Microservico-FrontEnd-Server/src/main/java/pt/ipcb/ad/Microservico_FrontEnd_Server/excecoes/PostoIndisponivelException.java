package pt.ipcb.ad.Microservico_FrontEnd_Server.excecoes;

public class PostoIndisponivelException extends RuntimeException {
    public PostoIndisponivelException(String message) {
        super(message);
    }
}