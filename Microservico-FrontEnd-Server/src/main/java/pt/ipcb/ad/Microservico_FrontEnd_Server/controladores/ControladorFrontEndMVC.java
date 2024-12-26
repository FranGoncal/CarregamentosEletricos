package pt.ipcb.ad.Microservico_FrontEnd_Server.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.ProxyMicroservicoUtilizadorVeiculo;

@Controller
public class ControladorFrontEndMVC {

    @Autowired
    ProxyMicroservicoUtilizadorVeiculo proxyMicroservicoUtilizadorVeiculo;

    @GetMapping("/")
    String getIndex(){

        //TODO lógica
        //Se nao esta autenticado
        return "login.html";
        //se esta autenticado
        //return "pagina_inicial.html";
    }
    @GetMapping("/registo")
    String getRegisto(){
        return "registar.html";
    }

    @PostMapping("/login")
    String login(){
        //TODO dar login do gajo

        //TODO redirec to gajo again para o ("/")
        //por enquanto fica assim:
        return "redirect:/inicio";
    }
    @GetMapping("/inicio")
    String inicio(){

        return "pagina-principal.html";
    }


    @PostMapping("/registar")
    String registar(@RequestParam String email,
                    @RequestParam String nome,
                    @RequestParam String password){

        //TODO Ver se pode criar utilizador

        //criar utilizador
        proxyMicroservicoUtilizadorVeiculo.registrar(email,nome,password,"STANDARD");

        return "login.html";
    }

}
