package pt.ipcb.ad.Microservico_FrontEnd_Server.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.ExplFatura;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.ExplProxyMicorservicoFaturacao;

//@Controller
public class ExplControladorFrontEndMVC {

    @Autowired
    ExplProxyMicorservicoFaturacao proxyMicroservicoFaturacao;

    @GetMapping("/")
    String getIndex(Model dadosFaturaDTO){

        //TODO lógica
        //Se nao esta autenticado
        return "login.html";
        //se esta autenticado
        //return "pagina_inicial.html";
    }

    @PostMapping("/dadosfaturas")
    ModelAndView recebeDadosFatura(@ModelAttribute ExplDadosFaturaDTO dadosFaturaDTO, ModelAndView modelAndView){

        System.out.println("Boas");
        System.out.println(dadosFaturaDTO.toString());

        ExplFatura fatura = proxyMicroservicoFaturacao.getFatura(
                dadosFaturaDTO.getMarca(),
                dadosFaturaDTO.getModelo(),
                dadosFaturaDTO.getKmP(),
                dadosFaturaDTO.getKC());

        System.out.println(fatura.toString());

        modelAndView.addObject("objfatura",fatura);
        modelAndView.setViewName("expl_fatura.html");
        return modelAndView;
    }
}
