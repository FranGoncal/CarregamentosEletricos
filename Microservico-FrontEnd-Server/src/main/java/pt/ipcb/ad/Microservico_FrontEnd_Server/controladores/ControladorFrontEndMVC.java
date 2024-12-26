package pt.ipcb.ad.Microservico_FrontEnd_Server.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.Fatura;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.ProxyMicorservicoFaturacao;

@Controller
public class ControladorFrontEndMVC {

    @Autowired
    ProxyMicorservicoFaturacao proxyMicroservicoFaturacao;

    @GetMapping("/")
    String getIndex(Model dadosFaturaDTO){
        return "index.html";
    }


    @PostMapping("/dadosfaturas")
    ModelAndView recebeDadosFatura(@ModelAttribute DadosFaturaDTO dadosFaturaDTO, ModelAndView modelAndView){

        System.out.println("Boas");
        System.out.println(dadosFaturaDTO.toString());

        Fatura fatura = proxyMicroservicoFaturacao.getFatura(
                dadosFaturaDTO.getMarca(),
                dadosFaturaDTO.getModelo(),
                dadosFaturaDTO.getKmP(),
                dadosFaturaDTO.getKC());

        System.out.println(fatura.toString());

        modelAndView.addObject("objfatura",fatura);
        modelAndView.setViewName("fatura.html");
        return modelAndView;
    }
}
