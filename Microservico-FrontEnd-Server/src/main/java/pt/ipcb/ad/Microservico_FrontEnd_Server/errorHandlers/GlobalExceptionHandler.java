package pt.ipcb.ad.Microservico_FrontEnd_Server.errorHandlers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ipcb.ad.Microservico_FrontEnd_Server.excecoes.CredenciaisIncorretasException;
import pt.ipcb.ad.Microservico_FrontEnd_Server.excecoes.PostoIndisponivelException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostoIndisponivelException.class)
    public String handlePostoIndisponivelException(PostoIndisponivelException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "pesquisa-postos.html";
    }
    @ExceptionHandler(CredenciaisIncorretasException.class)
    public String handleCredenciaisIncorretasException(CredenciaisIncorretasException ex, Model model) {
        System.out.println("Handlei");
        model.addAttribute("errorMessage", ex.getMessage());
        return "login.html";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "erro-geral";  // Retorna a página de erro genérica
    }
}

