package pt.ipcb.ad.Microservico_FrontEnd_Server.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.ipcb.ad.Microservico_FrontEnd_Server.excecoes.PostoIndisponivelException;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.*;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.*;
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
    ProxySimulacaoSessaoCarregamento proxySimulacaoSessaoCarregamento;

    @Autowired
    UserService userService;

    @GetMapping("/")
    String getIndex(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            return "pagina-principal.html";  // Se autenticado
        } else {
            return "login.html";  // Se não autenticado
        }
    }

    @GetMapping("/registo")
    String getRegisto(){
        return "registar.html";
    }
    @GetMapping("/acesso-negado")
    String acessoNegado(){
        return "acesso-negado.html";
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


    @GetMapping("/postos/{id}/simulacao")
    String startSimulacao(@PathVariable Long id, @RequestParam Long carroId, @RequestParam Long cemeId, Model model){

        //ver se o posto esta disponivel
        if(!proxyMicroservicoOPC.consultarEstado(id).equals("disponivel")){
            throw new PostoIndisponivelException("O posto de carregamento não está disponível.");
        }

        //TODO ver se o carro é do user

        //criar simulacao
        Long idSessao = proxySimulacaoSessaoCarregamento.registrar(id,carroId,userService.getAuthenticatedUsername(), cemeId);


        //apresentar simulacao
        return "redirect:/sessoes/"+idSessao;
    }

    //------------------------------------------------------------------------------

    //------------------------SESSOES DE CARREGAMENTO-------------------------------
    @GetMapping("/sessoes")
    String getSessoes( Model model){
        //pesquisar sessoes pelo email
        List<SessaoCarregamento> sessoes = proxySimulacaoSessaoCarregamento.getSimulacoesByIdUtilizadorOrderByIdDesc(userService.getAuthenticatedUsername());
        model.addAttribute("sessoes", sessoes);

        return "lista-sessoes.html";
    }
    @GetMapping("/sessoes/{idSessao}")
    String getSessoes(@PathVariable Long idSessao ,Model model){

        //Obter dados da sessao
        Optional<SessaoCarregamento> sessaoCarregamento = proxySimulacaoSessaoCarregamento.consultar(idSessao);
        model.addAttribute("sessaoCarregamento", sessaoCarregamento.get());
        long totalSegundos = sessaoCarregamento.get().getDuracao().getSeconds();
        long horas = totalSegundos/3600;
        totalSegundos = totalSegundos%3600;
        long minutos = totalSegundos/60;
        long segundos = totalSegundos%60;
        model.addAttribute("duracao", horas+":"+minutos+":"+segundos);

        int percentagemCarregamento = proxySimulacaoSessaoCarregamento.getPercentagemCarregamento(sessaoCarregamento.get().getId());
        model.addAttribute("percentagemCarregamento",percentagemCarregamento);

        String carro = proxyMicroservicoUtilizadorVeiculo.getNome(sessaoCarregamento.get().getIdVeiculo());
        model.addAttribute("carro",carro);

        return "sessao.html";
    }
    @PostMapping("/sessoes/{idSessao}/terminar")
    String terminaSessao(@PathVariable Long idSessao){

        //terminar sessao;
        proxySimulacaoSessaoCarregamento.atualizar(idSessao);

        return "redirect:/sessoes/"+idSessao;
    }

    //------------------------------------------------------------------------------

    //----------------------------------FATURAS-------------------------------------

    @GetMapping("/faturas")
    String getFaturas( Model model){
        //pesquisar sessoes pelo email
        List<Fatura> faturas = proxyCeme.consultarByEmailOrderByIdDesc(userService.getAuthenticatedUsername());
        model.addAttribute("faturas", faturas);

        return "lista-faturas.html";
    }
    @GetMapping("/faturas/{idFatura}")
    String getFaturas( Model model, @PathVariable Long idFatura){
        //Obter dados da fatura
        Optional<Fatura> fatura = proxyCeme.consultar(idFatura);
        model.addAttribute("fatura", fatura.get());

        return "fatura.html";
    }



    //------------------------------------------------------------------------------

    //----------------------------------OPC-----------------------------------------

    @GetMapping("/gestao/OPC")
    String getPostosProprios(Model model){
        List<PontoCarregamento> listaPontos = proxyMicroservicoOPC.getPontosProrios(userService.getAuthenticatedUsername());
        model.addAttribute("listaPontos", listaPontos);

        return "lista-pontos-proprios.html";
    }

    @GetMapping("/gestao/OPC/adicionar")
    String getCriarPonto(){
        return "criar-ponto.html";
    }
    @GetMapping("/gestao/OPC/editar/{id}")
    String getEditarPonto(Model model, @PathVariable Long id){
        //obter o ponto
        PontoCarregamento ponto = proxyMicroservicoOPC.getPontoProrio(id).get();
        model.addAttribute("ponto",ponto);

        System.out.println(ponto.getOwnerEmail());
        return "editar-ponto.html";
    }
    @PostMapping("/gestao/OPC/editar")
    String editarPonto(Model model, @RequestParam Long id, @RequestParam String local, @RequestParam String estado ,@RequestParam double maxCapacity){

        //editar ponto
        System.out.println(id);
        System.out.println(local);
        System.out.println(maxCapacity);
        System.out.println(estado);

        proxyMicroservicoOPC.editaPonto(id, local, estado ,maxCapacity);

        //obter o ponto
        PontoCarregamento ponto1 = proxyMicroservicoOPC.getPontoProrio(id).get();
        model.addAttribute("ponto",ponto1);

        return "editar-ponto.html";
    }

}
