package pt.ipcb.ad.Microservico_FrontEnd_Server.seguranca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.UtilizadorDTO;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.ProxyMicroservicoUtilizadorVeiculo;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    ProxyMicroservicoUtilizadorVeiculo proxyMicroservicoUtilizadorVeiculo;



    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("OLÁ");
        System.out.println(username+"  ---  "+password);
        // Chama o microserviço para autenticar
        UtilizadorDTO user = proxyMicroservicoUtilizadorVeiculo.autenticacao(username, password);


        if (user != null) {
            // Se a autenticação for bem-sucedida, cria um objeto de autenticação
            User principal = new User(username, password, List.of(new SimpleGrantedAuthority(user.getRole())));
            return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
        } else {
            // Se a autenticação falhar, lança uma exceção
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}