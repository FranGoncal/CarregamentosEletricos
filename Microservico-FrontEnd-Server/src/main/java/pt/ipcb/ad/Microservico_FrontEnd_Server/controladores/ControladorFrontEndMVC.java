package pt.ipcb.ad.Microservico_FrontEnd_Server.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.CEME;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.PontoCarregamentoDTO;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.Veiculo;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.ProxyCeme;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.ProxyMicroservicoOPC;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.ProxyMicroservicoUtilizadorVeiculo;
import org.springframework.security.core.Authentication;
import pt.ipcb.ad.Microservico_FrontEnd_Server.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class ControladorFrontEndMVC {

    @Autowired
    ProxyMicroservicoUtilizadorVeiculo proxyMicroservicoUtilizadorVeiculo;

    @Autowired
    ProxyMicroservicoOPC proxyMicroservicoOPC;

    @Autowired
    ProxyCeme proxyCeme;

    @Autowired
    UserService userService;



    @GetMapping("/")
    String getIndex(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            return "pagina-principal.html";  // Se autenticado
        } else {
            return "login.html";  // Se não autenticado
        }
        /*
        //TODO lógica
        //Se nao esta autenticado
        return "login.html";
        //se esta autenticado
        //return "pagina_inicial.html";
         */
    }

    @GetMapping("/registo")
    String getRegisto(){
        return "registar.html";
    }
    @GetMapping("/acesso-negado")
    String acessoNegado(){
        return "acesso-negado.html";
    }

    /*@PostMapping("/login")
    String login(){
        //TODO dar login do gajo
        System.out.println("vim aqui");
        //TODO redirec to gajo again para o ("/")
        //por enquanto fica assim:
        return "redirect:/inicio";
    }*/
    @GetMapping("/inicio")
    String inicio(){

        return "pagina-principal.html";
    }


    @PostMapping("/registar")
    String registar(@RequestParam String email,
                    @RequestParam String nome,
                    @RequestParam String password){
        System.out.println("Registei um utilizador: "+email);
        //TODO Ver se pode criar utilizador

        //criar utilizador
        proxyMicroservicoUtilizadorVeiculo.registrar(email,nome,password,"STANDARD");

        return "login.html";
    }


    //------------------------POSTOS DE CARREGAMENT---------------------------------

    @GetMapping("/postos")
    String getPesquisaPostos(){
        return "pesquisa-postos.html";
    }

    @GetMapping("/postos/{id}")
    String getPosto(@PathVariable Long id, Model model){

        List<CEME> listaCeme = proxyCeme.listar();
        model.addAttribute("listaCeme",listaCeme);

        PontoCarregamentoDTO ponto = proxyMicroservicoOPC.consultar(id);
        model.addAttribute("ponto",ponto);

        //pesquisar carro tendo o email logado
        Set<Veiculo> veiculos = proxyMicroservicoUtilizadorVeiculo.consultarVeiculos(userService.getAuthenticatedUsername());
        model.addAttribute("veiculos",veiculos);

        return "ponto-carregamento.html";
    }

    @GetMapping("/postos/pesquisar")
    String getPostos(@RequestParam("local") String local, Model model){

        List<PontoCarregamentoDTO> pontos = proxyMicroservicoOPC.listar(local);
        model.addAttribute("postos", pontos);




        return "lista-pontos.html";
    }


    //------------------------------------------------------------------------------




}
