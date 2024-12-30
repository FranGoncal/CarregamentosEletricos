package pt.ipcb.ad.Microservico_FrontEnd_Server.controladores;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pt.ipcb.ad.Microservico_FrontEnd_Server.excecoes.PostoIndisponivelException;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.*;
import pt.ipcb.ad.Microservico_FrontEnd_Server.proxies.*;
import org.springframework.security.core.Authentication;
import pt.ipcb.ad.Microservico_FrontEnd_Server.services.UserService;

import java.util.ArrayList;
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

    @GetMapping("/acesso-negado")
    String acessoNegado(){
        return "acesso-negado.html";
    }
    @GetMapping("/inicio")
    String inicio(){

        return "pagina-principal.html";
    }

    @GetMapping("/registo")
    String getRegisto(@ModelAttribute Utilizador utilizador){
        return "registar.html";
    }
    @PostMapping("/registar")
    ModelAndView registar(@Valid @ModelAttribute Utilizador utilizador,
                    BindingResult apanhaErrosValidacao) {

        ModelAndView modeloEVista = new ModelAndView();
        if (apanhaErrosValidacao.hasErrors()) {
            modeloEVista.setViewName("registar.html");
            return modeloEVista;
        }


        try {

            //utilizador.setPassword(bCryptPasswordEncoder.encode(password));
            proxyMicroservicoUtilizadorVeiculo.registrar(utilizador.getEmail(), utilizador.getName(), utilizador.getPassword(), "STANDARD");

            modeloEVista.setViewName("registar.html");
            return modeloEVista;
        }  catch (Exception e) {

            modeloEVista.addObject(utilizador);
            modeloEVista.setViewName("redirect:/registo?email_invalid");
            return modeloEVista;
        }
    }
    /*@PostMapping("/registar")
    String registar(@RequestParam String email,
                    @RequestParam String nome,
                    @RequestParam String password){
        //TODO Ver se pode criar utilizador


        //criar utilizador
        proxyMicroservicoUtilizadorVeiculo.registrar(email,nome,password,"STANDARD");

        return "login.html";
    }*/


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
        String startSimulacao(@PathVariable Long id, @RequestParam Long carroId, @RequestParam Long cemeId, @RequestParam Long cargaMaxima,Model model){

            //ver se o posto esta disponivel
            if(!proxyMicroservicoOPC.consultarEstado(id).equals("disponivel")){
                throw new PostoIndisponivelException("O ponto de carregamento selecionado não está disponível! Pesquise um ponto disponivel");
            }

            //TODO ver se o carro é do user

            //criar simulacao
            Long idSessao = proxySimulacaoSessaoCarregamento.registrar(id,carroId,userService.getAuthenticatedUsername(), cemeId, cargaMaxima);


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

            String nomeCeme = proxyCeme.getNome(sessaoCarregamento.get().getIdCeme());
            model.addAttribute("nomeCeme", nomeCeme);

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

            String nomeCeme = proxyCeme.getNome(fatura.get().getIdCeme());
            model.addAttribute("nomeCeme",nomeCeme);

            return "fatura.html";
        }



        //------------------------------------------------------------------------------

        //----------------------------------OPC-----------------------------------------
        @GetMapping("/gestao/OPC/eliminar/{id}")
        String eliminaPonto(Model model,@PathVariable Long id){

            proxyMicroservicoOPC.eliminarPonto(id);

            List<PontoCarregamento> listaPontos = proxyMicroservicoOPC.getPontosProrios(userService.getAuthenticatedUsername());
            model.addAttribute("listaPontos", listaPontos);

            return "lista-pontos-proprios.html";
        }

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

            return "editar-ponto.html";
        }
        @PostMapping("/gestao/OPC/editar")
        String editarPonto(Model model, @RequestParam Long id, @RequestParam String local, @RequestParam String estado ,@RequestParam double maxCapacity, @RequestParam double taxaOPC){

            //editar ponto
            proxyMicroservicoOPC.editaPonto(id, local, estado ,maxCapacity, taxaOPC);

            //obter o ponto
            PontoCarregamento ponto1 = proxyMicroservicoOPC.getPontoProrio(id).get();
            model.addAttribute("ponto",ponto1);

            return "editar-ponto.html";
        }
        @PostMapping("/gestao/OPC/adicionar-ponto")
        String adicionarPonto(@RequestParam String local, @RequestParam String estado ,@RequestParam double maxCapacity, @RequestParam double taxaOPC){

            String email = userService.getAuthenticatedUsername();
            proxyMicroservicoOPC.criarPonto(email,local,estado,maxCapacity,taxaOPC);

            return "redirect:/gestao/OPC";
        }

        //------------------------------------------------------------------------------

        //----------------------------------CEME-----------------------------------------


        @GetMapping("/gestao/CEME/eliminar/{id}")
        String eliminaCeme(Model model,@PathVariable Long id){

            proxyCeme.eliminarCeme(id);

            List<CEME> listaCeme = proxyCeme.getFornecedoresProrios(userService.getAuthenticatedUsername());
            model.addAttribute("listaCeme", listaCeme);
            return "lista-ceme.html";
        }

        @GetMapping("/gestao/CEME/adicionar")
        String getCriarCeme(){
            return "criar-CEME.html";
        }
        @PostMapping("/gestao/CEME/criar")
        String criarCeme(@RequestParam String fornecedor, @RequestParam double precoPorKWH, @RequestParam double taxaCEME){

            String email = userService.getAuthenticatedUsername();
            proxyCeme.criarCeme(email,fornecedor,precoPorKWH, taxaCEME);

            return "redirect:/gestao/CEME";
        }
        @GetMapping("/gestao/CEME")
        String getServicosCEME(Model model){

            List<CEME> listaCeme = proxyCeme.getFornecedoresProrios(userService.getAuthenticatedUsername());
            model.addAttribute("listaCeme", listaCeme);
            return "lista-ceme.html";
        }
        @GetMapping("/gestao/CEME/editar/{id}")
        String getServicosCEME(Model model, @PathVariable Long id){

            CEME ceme = proxyCeme.getCeme(id);
            model.addAttribute("ceme", ceme);
            return "editar-ceme.html";
        }
        @PostMapping("/gestao/CEME/editar")
        String editarCeme(Model model, @RequestParam Long id, @RequestParam String name, @RequestParam double precoPorKWh, @RequestParam double taxaCEME){

            //editar ponto
            proxyCeme.editaCeme(id, name ,precoPorKWh, taxaCEME);

            //obter o ponto
            CEME ceme1 = proxyCeme.getCeme(id);
            model.addAttribute("ceme",ceme1);

            return "editar-ceme.html";
        }


        //------------------------------------------------------------------------------

        //----------------------------------ADMIN---------------------------------------
        @GetMapping("/gestao/ADMIN")
        String getServicosADMIN(Model model){

            List<UtilizadorDTO> listaUsers = proxyMicroservicoUtilizadorVeiculo.listarSeguro();
            model.addAttribute("utilizadores", listaUsers);
            return "lista-users.html";
        }
        @GetMapping("/gestao/ADMIN/utilizadores/criar")
        String getCriarUser(){
            return "criar-utilizador.html";
        }
        @PostMapping("/gestao/ADMIN/utilizadores/criar-user")
        String criarUser(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String role){
            proxyMicroservicoUtilizadorVeiculo.registrar(email,name,password,role);
            return "redirect:/gestao/ADMIN";
        }
        @GetMapping("/gestao/ADMIN/utilizadores/{id}")
        String getUser(Model model, @PathVariable Long id){

            UtilizadorDTO user = proxyMicroservicoUtilizadorVeiculo.getUserSeguro(id);
            model.addAttribute("utilizador",user);

            return "editar-utilizador.html";
        }
        @GetMapping("/gestao/ADMIN/utilizadores/eliminar/{id}")
        String eliminarUser(@PathVariable Long id){
            proxyMicroservicoUtilizadorVeiculo.eliminarUser(id);
            return "redirect:/gestao/ADMIN";
        }
        @PostMapping("/gestao/ADMIN/utilizadores/editar/{id}")
        String editarUser(Model model,@PathVariable Long id,@RequestParam String name, @RequestParam String role){

            proxyMicroservicoUtilizadorVeiculo.editaUser(id,name,role);

            UtilizadorDTO user = proxyMicroservicoUtilizadorVeiculo.getUserSeguro(id);
            model.addAttribute("utilizador",user);

            return "editar-utilizador.html";
        }
        @GetMapping("/gestao/ADMIN/utilizadores/{id}/veiculos")
        String getUserVeiculos(Model model, @PathVariable Long id){
            UtilizadorDTO utilizadorDTO = proxyMicroservicoUtilizadorVeiculo.getUserSeguro(id);
            String email = utilizadorDTO.getEmail();

            Set<Veiculo> veiculos = proxyMicroservicoUtilizadorVeiculo.consultarVeiculos(email);
            System.out.println(veiculos.size());

            model.addAttribute("veiculos",veiculos);
            model.addAttribute("id",id);

            return "lista-veiculos.html";
        }
        @GetMapping("/gestao/ADMIN/utilizadores/{id}/veiculos/{idVeiculo}")
        String getUserVeiculos(Model model, @PathVariable Long id, @PathVariable Long idVeiculo){

            Veiculo veiculo = proxyMicroservicoUtilizadorVeiculo.consultar(idVeiculo).get();
            model.addAttribute("veiculo",veiculo);
            model.addAttribute("id",id);

            return "editar-veiculo.html";
        }
        @PostMapping("/gestao/ADMIN/utilizadores/{id}/veiculos/{idVeiculo}/edicao")
        String editarVeiculo(Model model, @PathVariable Long id,
                @PathVariable Long idVeiculo,
                @RequestParam String marca,
                @RequestParam String modelo,
        @RequestParam double bateria,
        @RequestParam double bateriaAtual,
        @RequestParam double capacidadeCarregamento){
            //editar objeto
            proxyMicroservicoUtilizadorVeiculo.editaVeiculo(idVeiculo,marca, modelo,bateria,bateriaAtual,capacidadeCarregamento);

            return "redirect:/gestao/ADMIN/utilizadores/"+id+"/veiculos/"+idVeiculo;
        }

        @GetMapping("/gestao/ADMIN/utilizadores/{id}/veiculos/{idVeiculo}/remover")
        String getEditarVeiculo(Model model, @PathVariable Long id, @PathVariable Long idVeiculo){
            //remover veiculo da lista do user
            proxyMicroservicoUtilizadorVeiculo.removerVeiculo(id,idVeiculo);

            //remover veiculo da BD
            proxyMicroservicoUtilizadorVeiculo.apagarVeiculo(idVeiculo);
            return "redirect:/gestao/ADMIN/utilizadores/"+id+"/veiculos";
        }
        @GetMapping("/gestao/ADMIN/utilizadores/{id}/veiculos/adicionar")
        String getAdicionarVeiculo(Model model, @PathVariable Long id){
            model.addAttribute("id",id);

            List<String> emails = proxyMicroservicoUtilizadorVeiculo.consultarEmails();
            model.addAttribute("emails",emails);
            return "adicionar-veiculo.html";
        }
        @PostMapping("/gestao/ADMIN/utilizadores/{id}/veiculos/criar")
        String criaVeiculo(Model model, @PathVariable Long id,
                @RequestParam String marca,
                @RequestParam String modelo,
                @RequestParam String email,
        @RequestParam double bateria,
        @RequestParam double bateriaAtual,
        @RequestParam double capacidadeCarregamento){

            Long idUser = proxyMicroservicoUtilizadorVeiculo.getUserId(email);
            //criar objeto e guardar
            Veiculo veiculo = proxyMicroservicoUtilizadorVeiculo.registrar(marca,modelo,bateria,bateriaAtual,capacidadeCarregamento,idUser);
            //System.out.println(proxyMicroservicoUtilizadorVeiculo.adicionaVeiculo(email,veiculo));

            model.addAttribute("id",id);
            return "redirect:/gestao/ADMIN/utilizadores/"+id+"/veiculos";
        }

        //------------------------------------------------------------------------------
    }


