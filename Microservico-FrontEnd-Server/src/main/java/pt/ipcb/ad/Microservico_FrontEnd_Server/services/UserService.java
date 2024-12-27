package pt.ipcb.ad.Microservico_FrontEnd_Server.services;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getAuthenticatedUsername() {
        // Obtém o objeto Authentication atual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o principal (usuário) não é nulo e é uma instância de User
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            // Obtém o nome de utilizador do principal
            String username = ((User) authentication.getPrincipal()).getUsername();

            // Imprime o nome de utilizador
            return username;
        } else {
            System.out.println("Nenhum usuário autenticado");
        }
        return null;
    }
}
