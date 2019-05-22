import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.util.Comparator.*;

public class AppStore
{
    private static int dias;
    private static boolean sair;
    
    // ArrayList de utilizadores
    private static ArrayList<User> utilizadores;
    // ArrayList de classificações
    private static ArrayList<Classificacao> classificacoes;
    // ArrayList de aplicações
    private static ArrayList<Apps> apps;

    public AppStore(){
        dias = 1;
        sair = false;
        utilizadores = new ArrayList<>();
        classificacoes = new ArrayList<>();
        apps = new ArrayList<>();
    }
    
    public static void main(String[] args) throws IOException
    {
        AppStore appStore = new AppStore();
        System.out.println(obterDiaAtual());
        inicio();
        while(!sair) {
           modoVisualizacao();
        }
    }
    
    private static String obterDiaAtual() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();
        return dateFormat.format(date);
    }
    
    private void avancarDia()
    {
        if(dias % 30 == 0){
            resetDescontoMensal();
            descontoMensal();
        }
        if(dias % 7 == 0){
            resetDescontoMenosCompras();
            resetDescontoTotal();
            descontoMenosCompras();
            descontoTotal();
        }
    }

    private void avancarSemana()
    {
        resetDescontoMenosCompras();
        resetDescontoTotal();
        descontoMenosCompras();
        descontoTotal();
    }

    private void avancarMes()
    {
        resetDescontoMensal();
        resetDescontoTotal();
        descontoMensal();
        descontoTotal();
    }
    
    //Feito
    // Criar utilizador tipo 0 - normal 1 - programador
    private static void novoUtilizador(int tipo){
        String nome;
        int idade;
        boolean premium; 
        Scanner teclado = new Scanner(System.in);
        
        if (tipo == 0 )
            System.out.println("Novo Utilizador");
        else
            System.out.println("Novo Programador");
        
        try{   
            System.out.print("Nome: ");
            nome = teclado.nextLine();
            
            System.out.print("Idade: ");
            idade = teclado.nextInt();
            
            System.out.print("Premium? (S / N) ");
            char pre =  teclado.next().charAt(0); 
            if (pre == 's' || pre == 'S')
                premium = true;
            else if (pre == 'n' || pre == 'N')
                premium = false;
            else
                throw new InputMismatchException();
            
            if (tipo == 0 )
                utilizadores.add(new User(nome, idade, premium, false,0));
            else
                utilizadores.add(new Programador(nome, idade, premium, true,0));
            System.out.println("");
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Algo correu mal, tente novamente!");
            System.out.println("");
            novoUtilizador(tipo);
        }
    }

    //Feito
    private static void carregarUtilizadores() throws IOException {
        FileReader frd = new FileReader("Users.txt");
        BufferedReader fR = new BufferedReader(frd);
        String linha = fR.readLine();
        String separar[];
        String nome;
        int idade;
        double total;
        boolean premium, isProgrammer;
        int i=0;
        
        while (linha != null) {
            if (!"".equals(linha)) {
                separar = linha.split(","); // array com nome, idade, isPremium, isProgrammer
                nome = separar[0];
                idade = Integer.parseInt(separar[1]);
                premium = Boolean.parseBoolean(separar[2]);
                isProgrammer = Boolean.parseBoolean(separar[3]);
                total = Double.parseDouble(separar[4]);
                if(isProgrammer)
                    utilizadores.add( new Programador(nome, idade, premium, isProgrammer,total));
                else
                    utilizadores.add( new User(nome, idade, premium, isProgrammer,total));
                utilizadores.get(i).setTotalGasto(total);
                i++;
            }
            linha = fR.readLine();
        }
        fR.close();
    }
    
    //Feito
    private static void carregarAplicacoes() throws IOException {
        FileReader frd = new FileReader("Apps.txt");
        BufferedReader fR = new BufferedReader(frd);
        String linha = fR.readLine();
        String separar[];
        String nome; 
        double preco; 
        String categoria;
        boolean temSubscricao;
        int idProgrammer, numAvs;
        double avaliacao;
     
        while (linha != null) {
            if (!"".equals(linha)) {
                separar = linha.split(","); // array com nome, preco, categoria, temSubscricao, idProgrammer, avaliacao
                nome = separar[0];
                preco = Float.valueOf(separar[1]);
                categoria = separar[2];
                temSubscricao = Boolean.parseBoolean(separar[3]);
                idProgrammer = Integer.parseInt(separar[4]);
                avaliacao = Double.parseDouble(separar[5]);
                numAvs = Integer.parseInt(separar[6]);
                apps.add(new Apps(nome, preco, categoria, temSubscricao, idProgrammer, avaliacao, numAvs));
            }
            linha = fR.readLine();
        }
        fR.close();    
     }

    //Feito
    private static void carregarCompras() throws IOException {
        FileReader frd = new FileReader("Compras.txt");
        BufferedReader fR = new BufferedReader(frd);
        String linha = fR.readLine();
        String separar[];
        String data;
        int idUser;
        int idApp;
        double total;
        float classi;
        String comment;

        while (linha != null) {
            if (!"".equals(linha)) {
                separar = linha.split(","); // array com data, idUser, idApp, total
                data = separar[0];
                idUser = Integer.parseInt(separar[1]);
                idApp = Integer.parseInt(separar[2]);
                total = Double.parseDouble(separar[3]);
                classi = Float.parseFloat(separar[4]);
                comment = separar[5];
                for(int i = 0; i < utilizadores.size(); i++)
                {
                    if (utilizadores.get(i).getIdUser() == idUser)
                    {
                        utilizadores.get(i).getCarrinhoCompras().novaCompra(new Compra(data, idUser, idApp, total, classi, comment));
                    }
                }
            }
            linha = fR.readLine();
        }
        fR.close();
    }
    
    //Feito
    private static void carregarSubscricoes() throws IOException {
        FileReader frd = new FileReader("Subscricoes.txt");
        BufferedReader fR = new BufferedReader(frd);
        String linha = fR.readLine();
        String separar[];
        String data;
        int idUser;
        int idApp;
        double total;
        int diasSubscricao;
        float classi;
        String comment;
        boolean podeUsarApp;
        while (linha != null) {
            if (!"".equals(linha)) {
                separar = linha.split(","); // array com data, idUser, idApp, total, diasSubs, podeUsarapp
                data = separar[0];
                idUser = Integer.parseInt(separar[1]);
                idApp = Integer.parseInt(separar[2]);
                total = Double.parseDouble(separar[3]);
                diasSubscricao = Integer.parseInt(separar[4]);
                podeUsarApp = Boolean.parseBoolean(separar[5]);
                classi = Float.parseFloat(separar[6]);
                comment = separar[7];
                
                for(int i = 0; i < utilizadores.size(); i++) {
                    if (utilizadores.get(i).getIdUser() == idUser)
                        utilizadores.get(i).getCarrinhoCompras().novaSubscricao(new Subscricao(data, idUser, idApp, total, diasSubscricao, podeUsarApp, classi, comment));
                }
            }
            linha = fR.readLine();
        }
        fR.close();
    }

    //Feito
    private static void carregarClassificacoes() throws IOException {
        FileReader frd = new FileReader("classificacoes.txt");
        BufferedReader fR = new BufferedReader(frd);
        String linha = fR.readLine();
        String separar[],comentario;
        int idCompra,idApp,idUser;
        float classificacao;


        while (linha != null) {
            if (!"".equals(linha)) {
                separar = linha.split(","); // array com nome, idade, isPremium, isProgrammer
                idApp = Integer.parseInt(separar[0]);
                idCompra = Integer.parseInt(separar[1]);
                classificacao =Float.valueOf(separar[2]);
                comentario =separar[3];
                idUser = Integer.parseInt(separar[4]);


                classificacoes.add( new Classificacao(idCompra,idApp,classificacao,comentario,idUser));
            }
            linha = fR.readLine();
        }
        fR.close();
    }

    //Feito
    private static void listaOrdenada(int option)
    {
        int classificacao = 0;
        Scanner teclado = new Scanner(System.in);

        System.out.println("Aplicações:");

        if (option == 1)//por alfabetica
        {
            Collections.sort(apps, comparing(Apps::getNome));

            for (int i = 0; i < apps.size(); i++)
            {
                if (apps.get(i).getAvaliacaoApp() >= classificacao)
                    System.out.println(apps.get(i).getIdApp() + " - " + apps.get(i).getNome());
            }
        }
        if (option == 2)//por vendas
        {
            Collections.sort(apps, comparingDouble(Apps::getNumCompras));
            Collections.reverse(apps);
            for (int i = 0; i < apps.size(); i++)
            {
                if (apps.get(i).getAvaliacaoApp() >= classificacao)
                    System.out.println(apps.get(i).getIdApp() + " - " + apps.get(i).getNome()+ " - " + apps.get(i).getNumCompras());
            }
        }
        if (option == 3)//por classificacao
        {
            Collections.sort(apps, comparingDouble(Apps::getMediaAvaliacaoApp));
            Collections.reverse(apps);

            for (int i = 0; i < apps.size(); i++)
            {
                System.out.println(apps.get(i).getIdApp() + " - " + apps.get(i).getNome()+ " - " + apps.get(i).getMediaAvaliacaoApp());
            }
        }

        Collections.sort(apps, comparing(Apps::getIdApp));
        int opcao;
        Compra compras;
        int contador = 0;
        
        System.out.print("Escolha uma das aplicações: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            if (opcao < apps.size())
            {
                for (int i = 0; i < utilizadores.size(); i++)
                {
                    for (int j = 0; j < utilizadores.get(i).getCarrinhoCompras().getCompras().size(); j++)
                    {
                        compras = utilizadores.get(i).getCarrinhoCompras().getCompras().get(j);
                        if(compras.getClassificacao().getClassificacao() != 0 && compras.getIdApp()==opcao)
                        {
                            if(contador == 0)
                                System.out.println("Classificações atribuídas à aplicação escolhida\n");
                            System.out.println("User: " + utilizadores.get(i).getNome() + "\n" + compras.getClassificacao().getComentario() +"\n");
                            contador++;
                        }
                    }
                    for (int j = 0; j < utilizadores.get(i).getCarrinhoCompras().getSubscricoes().size(); j++)
                    {
                        compras = utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j);
                        if(compras.getClassificacao().getClassificacao() != 0 && compras.getIdApp()==opcao)
                        {
                            if(contador == 0)
                                System.out.println("Classificações atribuídas à aplicação escolhida\n");
                            System.out.println("User: " + utilizadores.get(i).getNome() + "\n" + compras.getClassificacao().getComentario() +"\n");
                            contador++;
                        }
                    }
                }
                if (contador != 0)
                    System.out.println("Média de classificação da app: " + apps.get(opcao).getMediaAvaliacaoApp());
                else
                    System.out.println("Nenhuma classificação para esta app");
                System.out.println("");
            }else{
                throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            listaPorClassificacao();
        }
    }
    
    //Rever a situação de quando não aparecem todos os ids (têm de ser excluídos os que não aparecem)
    /*
        EXEMPLO:
    
        Introduza a classificação miníma: 4

        Aplicações:
        0 - ccc
        2 - dad

        Escolha uma das aplicações: 1

        Classificações atribuídas à aplicação escolhida

        User: dada
    */
    private static void listaPorClassificacao() 
    {
        int classificacao = 0;
        int tmp = 0;
        boolean existeAplicacao = false;
        Scanner teclado = new Scanner(System.in);
        
        System.out.print("Introduza a classificação miníma: ");
        
        try{
            classificacao = teclado.nextInt();
            System.out.println("");

            for (int i = 0; i < apps.size(); i++)
            {
                if (apps.get(i).getMediaAvaliacaoApp() >= classificacao)
                {
                    tmp++;
                    if(tmp == 1)
                        System.out.println("Aplicações:");
                    existeAplicacao = true;
                    System.out.println(i + " - " + apps.get(i).getNome());
                }
            }
            
            auxListaPorClassificacao(existeAplicacao, tmp, classificacao);
            
            System.out.println("");
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            listaPorClassificacao();
        }
    }
    
    private static void auxListaPorClassificacao(boolean existeAplicacao, int tmp, int classificacao){
        int opcao;
        Compra compras;
        int contador = 0;
        Scanner teclado = new Scanner(System.in);
        
        if(!existeAplicacao){
            for (int i = 0; i < apps.size(); i++)
            {
                if (apps.get(i).getMediaAvaliacaoApp() >= classificacao)
                {
                    tmp++;
                    if(tmp == 1)
                        System.out.println("Aplicações:");
                    existeAplicacao = true;
                    System.out.println(i + " - " + apps.get(i).getNome());
                }
            }
        }
 
        try{
            if (existeAplicacao)
            {
                System.out.println("");
                System.out.print("Escolha uma das aplicações: ");
                
                opcao = teclado.nextInt();
                System.out.println("");
                
                if (opcao < apps.size())
                {
                    for (int i = 0; i < utilizadores.size(); i++)
                    {
                        for (int j = 0; j < utilizadores.get(i).getCarrinhoCompras().getCompras().size(); j++)
                        {
                            compras = utilizadores.get(i).getCarrinhoCompras().getCompras().get(j);
                            if(compras.getClassificacao().getClassificacao() != 0 && compras.getIdApp()==opcao)
                            {
                                if(contador == 0)
                                    System.out.println("Classificações atribuídas à aplicação escolhida\n");
                                System.out.println("User: " + utilizadores.get(i).getNome() + "\n" + compras.getClassificacao().getComentario() +"\n");
                                contador++;
                            }
                        }
                        for (int j = 0; j < utilizadores.get(i).getCarrinhoCompras().getSubscricoes().size(); j++)
                        {
                            compras = utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j);
                            if(compras.getClassificacao().getClassificacao() != 0 && compras.getIdApp()==opcao)
                            {
                                if(contador == 0)
                                    System.out.println("Classificações atribuídas à aplicação escolhida\n");
                                System.out.println("User: " + utilizadores.get(i).getNome() + "\n" + compras.getClassificacao().getComentario() +"\n");
                                contador++;
                            }
                        }
                    }
                    if (contador != 0)
                        System.out.println("Média de classificação da app: " + apps.get(opcao).getAvaliacaoApp()/contador);
                    else
                        System.out.println("Nenhuma classificação para esta app");
                }else
                    throw new InputMismatchException();
            }else
                System.out.println("Não existe Apps com esse rating minimo");
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            auxListaPorClassificacao(false, 0, classificacao);
        }
    }
    
    
    //Feito
    private static void mostraTodosUtilizadores() {
        System.out.println("Escolha o utilizador: ");
        for(int i = 0; i < utilizadores.size(); i++) {
            System.out.println(utilizadores.get(i).getIdUser() + " : " + utilizadores.get(i).getNome());
        }
    }
    
    //Feito
    private static void novaAplicacao(int idProgrammer)
    {
        String nome;
        double preco;
        String categoria="";
        int categoriaChoice;
        boolean temSubscricao=false;
        Scanner teclado = new Scanner(System.in);

        System.out.println("Nova Aplicação");
        
        try{
            System.out.print("Nome: ");
            nome = teclado.nextLine();

            System.out.print("Preço: ");
            preco = teclado.nextDouble();


            System.out.println("Escolha a categoria: \n1 - Games \n2 - Business \n3 - Education \n4 - Lifestyle \n5 - Entertainment \n6 - Utilities \n7 - Travel \n8 - Health & Fitness");
            System.out.print("Opção: ");
            categoriaChoice = teclado.nextInt();
            System.out.println("");

            switch(categoriaChoice)
            {
                case 1:
                    categoria = "Games";
                    break;
                case 2:
                    categoria = "Business";
                    break;
                case 3:
                    categoria = "Education";
                    break;
                case 4:
                    categoria = "Lifestyle";
                    break;
                case 5:
                    categoria = "Entertainment";
                    break;
                case 6:
                    categoria = "Utilities";
                    break;
                case 7:
                    categoria = "Travel";
                    break;
                case 8:
                    categoria = "Health & Fitness";
                    break;
                default:
                    throw new InputMismatchException();
            }

            System.out.println("Tem subscrição: \n1 - Sim \n2 - Não");
            System.out.print("Opção: ");
            categoriaChoice = teclado.nextInt();
            System.out.println("");

            switch(categoriaChoice)
            {
                case 1:
                    temSubscricao = true;
                    break;
                case 2:
                    temSubscricao = false;
                    break;
                default:
                    throw new InputMismatchException();
            }

            apps.add( new Apps(nome, preco, categoria, temSubscricao, idProgrammer));
            System.out.println("");
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Algo correu mal, tente novamente!");
            System.out.println("");
            novaAplicacao(idProgrammer);
        }
    }

    private static void menuCategoriasUser(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Deseja");
        System.out.println("Categoria: \n1 - Games \n2 - Business \n3 - Education \n4 - Lifestyle \n5 - Entertainment \n6 - Utilities \n7 - Travel \n8 - Health & Fitness");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                //idUser é necessário para que o utilizador não volte a comprar a mesma aplicação
                case 1:
                    listApps("Games");
                    break;
                case 2:
                    listApps("Business");
                    break;
                case 3:
                    listApps("Education");
                    break;
                case 4:
                    listApps("Lifestyle");
                    break;
                case 5:
                    listApps("Entertainment");
                    break;
                case 6:
                    listApps("Utilities");
                    break;
                case 7:
                    listApps("Travel");
                    break;
                case 8:
                    listApps("Health & Fitness");
                    break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuCategoriasUser();
        }
    }
    
    private static void listApps(String cat) {

        System.out.println("Aplicações: \n");

        for (int i = 0; i < apps.size(); i++)
        {
            if (apps.get(i).getCategoria().equals(cat))
            System.out.println(i + " - " + apps.get(i).getNome());
        }
        int opcao;
        Scanner teclado = new Scanner(System.in);
        Compra compras;
        int contador = 0;
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            if (opcao < apps.size())
            {
                for (int i = 0; i < utilizadores.size(); i++)
                {
                    for (int j = 0; j < utilizadores.get(i).getCarrinhoCompras().getCompras().size(); j++)
                    {
                        compras = utilizadores.get(i).getCarrinhoCompras().getCompras().get(j);
                        if(compras.getClassificacao().getClassificacao() != 0 && compras.getIdApp()==opcao)
                        {
                            System.out.println("User: " + utilizadores.get(i).getNome() + "\n" + compras.getClassificacao().getComentario() +"\n");
                            contador++;
                        }
                    }
                    for (int j = 0; j < utilizadores.get(i).getCarrinhoCompras().getSubscricoes().size(); j++)
                    {
                        compras = utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j);
                        if(compras.getClassificacao().getClassificacao() != 0 && compras.getIdApp()==opcao)
                        {
                            System.out.println("User: " + utilizadores.get(i).getNome() + "\n" + compras.getClassificacao().getComentario() +"\n");
                            contador++;
                        }
                    }
                }
                if (contador != 0)
                    System.out.println("Média de classificação da app: " + apps.get(opcao).getAvaliacaoApp()/contador);
                else
                    System.out.println("Nenhuma classificação para esta app");
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            listApps(cat);
        }
    }
    
    private static void listAppsPerCategory(int idUser, String category, boolean subscricao, boolean compra)
    {
        boolean jaFoiComprada = false;
        boolean jaFoiAdicionada = false;
        
        for(int i = 0; i < apps.size(); i++)
        {
            //Se escolheu apenas visualizar as apps de uma dada categoria
            if (apps.get(i).getCategoria().equals(category) && !subscricao && !compra)
            {
                System.out.println(apps.get(i).getIdApp() + " - " + apps.get(i).getNome());
                System.out.println("Preço: "+ apps.get(i).getPreco()+ "€");
                for(User u : utilizadores){
                    if(u.getIdUser() == apps.get(i).getIdProgrammer())
                        System.out.println("Programador: " + u.getNome());
                }
                System.out.print("Subscrição: ");
                if(apps.get(i).isTemSubscricao())
                    System.out.println("Disponível");
                else
                    System.out.println("Indisponível");
                System.out.println("");
            }
            //Se escolheu subscrever a uma aplicação de uma dada categoria
            else if (apps.get(i).getCategoria().equals(category) && subscricao && !compra)
            {
                if(apps.get(i).isTemSubscricao()){
                    System.out.println(apps.get(i).getIdApp() + " - " + apps.get(i).getNome());
                    System.out.println("Preço: "+ apps.get(i).getPreco()+ "€");
                    for(User u : utilizadores){
                        if(u.getIdUser() == apps.get(i).getIdProgrammer())
                            System.out.println("Programador: " + u.getNome());
                    }
                    System.out.println("");
                }
            }
            //Se escolheu comprar uma aplicação de uma dada categoria
            else if (apps.get(i).getCategoria().equals(category) && !subscricao && compra)
            {
                if(!apps.get(i).isTemSubscricao()){
                    for(User u : utilizadores){
                        if(u.getIdUser() == idUser){
                            for(int idApp : u.getCarrinhoCompras().getIdApps()){
                                if(idApp == apps.get(i).getIdApp())
                                    jaFoiAdicionada = true;
                            }
                            for(Compra c : u.getCarrinhoCompras().getCompras()){
                                if(c.getIdApp() == apps.get(i).getIdApp())
                                    jaFoiComprada = true;
                            }
                        }
                    }
                    
                    if(!jaFoiComprada && !jaFoiAdicionada){
                        System.out.println(apps.get(i).getIdApp() + " - " + apps.get(i).getNome());
                        System.out.println("Preço: "+ apps.get(i).getPreco()+ "€");
                        for(User u : utilizadores){
                            if(u.getIdUser() == apps.get(i).getIdProgrammer())
                                System.out.println("Programador: " + u.getNome());
                        }
                        System.out.println("");
                    }
                }
            }
        }
    }

    private static void listAppsPerOrder()
    {
        for(int i = 0; i < apps.size(); i++)
        {
            System.out.println(apps.get(i).getIdApp() + " - " + apps.get(i).getNome());
            System.out.println("Preço: "+ apps.get(i).getPreco()+ "€");
            for(User u : utilizadores){
                if(u.getIdUser() == apps.get(i).getIdProgrammer())
                    System.out.println("Programador: " + u.getNome());
            }
            System.out.print("Tipo: ");
            if(apps.get(i).isTemSubscricao())
                System.out.println("Subscrição");
            else
                System.out.println("Compra");
            System.out.println("");
        }
    }

    private static double totalAppStore()
    {
        double total=0;
        for(int i = 0; i < apps.size(); i++)
        {
            total = total + apps.get(i).getPreco();
        }
        return total;
    }
    
    /**
    * Menus de interação com o utilizador 
    **/
    //Feito
    private static void inicio() throws IOException{
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);

        System.out.println("Deseja carregar os dados?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1:
                    carregarUtilizadores();
                    carregarAplicacoes();
                    carregarCompras();
                    carregarSubscricoes();
                    //carregarClassificacoes();
                    break;
                case 2:
                    break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuAdmin();
        }
    }
    
    //Feito
    private static void modoVisualizacao(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Escolha o Modo de Visualização");
        System.out.println("1 - Modo de Admin");
        System.out.println("2 - Modo de Utilizador/Programador");
        System.out.println("3 - Sair");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: menuAdmin(); break;
                case 2: menuUtilizador(); break;
                case 3: sair = true; break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            modoVisualizacao();
        }
    }
    
    //Rever parte dos valores acumulados
    private static void menuAdmin(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);

        System.out.println("Administrador");
        System.out.println("1 - Novo Utilizador");
        System.out.println("2 - Nova Aplicação");
        System.out.println("3 - Visualizar Utilizadores por ordem de registo");
        System.out.println("4 - Visualizar Utilizadores por ordem de valor pago");
        System.out.println("5 - Visualizar Aplicações");
        System.out.println("6 - Valor acumulado pela AppStore");
        System.out.println("7 - Valor acumulado por Programador");
        System.out.println("8 - Gravar dados");
        System.out.println("9 - Voltar atrás");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: utilizadorProgramador();
                        menuAdmin();
                        break;
                case 2: lerIDProgramador();
                        menuAdmin();
                        break;
                case 3: escreverDadosUtilizador();
                        menuAdmin();
                        break;
                case 4: listUserValue();
                        menuAdmin();
                        break;
                case 5: verApps();
                        break;
                case 6: System.out.println(totalAppStore()+" euros");
                        menuAdmin();
                        break;
                case 7: valorAcumuladoProgramador();
                        menuAdmin();
                        break;
                case 8: gravar();
                        menuAdmin();
                        break;
                case 9: modoVisualizacao();
                        break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuAdmin();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
    
    //Rever acho que está mal quando não existe nenhum programador, por exemplo, se só tivermos criado um utilizador normal
    private static void lerIDProgramador(){
        if (utilizadores.size()>0)
        {
            int opcao = 0;
            Scanner teclado = new Scanner(System.in);
         
            try
            {
                System.out.println("Escolha um programador: ");

                for(int i = 0; i < utilizadores.size(); i++) {
                    if(utilizadores.get(i).getProgrammer() == true)
                        System.out.println(utilizadores.get(i).getIdUser() + " - " + utilizadores.get(i).getNome());
                }
                
                System.out.print("Opção: ");
                opcao=teclado.nextInt();
                System.out.println("");
                
                if (opcao < utilizadores.size() && utilizadores.get(opcao).getProgrammer() == true) {
                    novaAplicacao(opcao);
                } else {
                    System.out.println("Não existe um programador com este id.");
                    System.out.println("");
                    lerIDProgramador();
                }
            }catch(InputMismatchException ime){
                System.out.println("");
                System.out.println("Opção Inválida, tente novamente!");
                System.out.println("");
                lerIDProgramador();
            }
        }
        else{
            System.out.println("Não existem utilizadores");
            System.out.println("");
            menuAdmin();
        }

    }

    private static void verApps(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Deseja");
        System.out.println("1 - Listar por Categoria");
        System.out.println("2 - Listar por Ordem");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1:
                    readCat();
                    break;
                case 2:
                    listAppsPerOrder();
                    break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            utilizadorProgramador();
        }
    }

    private static void readCat(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Deseja");
        System.out.println("Categoria: \n1 - Games \n2 - Business \n3 - Education \n4 - Lifestyle \n5 - Entertainment \n6 - Utilities \n7 - Travel \n8 - Health & Fitness");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                //Manda parametro idUser como -1 porque é irrevelante (porque apenas quer visualizar todas as aplicações de uma categoria)
                case 1: listAppsPerCategory(-1,"Games", false, false); break;
                case 2: listAppsPerCategory(-1,"Business", false, false); break;
                case 3: listAppsPerCategory(-1,"Education", false, false); break;
                case 4: listAppsPerCategory(-1,"Lifestyle", false, false); break;
                case 5: listAppsPerCategory(-1,"Entertainment", false, false); break;
                case 6: listAppsPerCategory(-1,"Utilities", false, false); break;
                case 7: listAppsPerCategory(-1,"Travel", false, false); break;
                case 8: listAppsPerCategory(-1,"Health & Fitness", false, false); break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            readCat();
        }
    }
    
    private static void utilizadorProgramador(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Deseja");
        System.out.println("1 - Adicionar um utilizador normal");
        System.out.println("2 - Adicionar um programador");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1:
                    novoUtilizador(0);
                    break;
                case 2: 
                    novoUtilizador(1);
                    break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            utilizadorProgramador();
        }
    }
    
    private static void menuUtilizador(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        mostraTodosUtilizadores();
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            if (opcao >= 0 && opcao < utilizadores.size()) {
                menuPrincipalUtilizador(opcao);
            } else throw new InputMismatchException();
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuUtilizador();
        }
    }
    
    private static void menuPrincipalUtilizador(int idUser){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        
        if (utilizadores.get(idUser).getProgrammer() == true)
            System.out.println("Utilizador: " + utilizadores.get(idUser).getNome() + " - " + "Programador");
        else
            System.out.println("Utilizador: " + utilizadores.get(idUser).getNome());
        
        System.out.println("1 - Ver aplicações disponíveis para compra/subscrição");
        System.out.println("2 - Ver carrinho de compras");
        System.out.println("3 - Ver aplicações adquiridas");
        System.out.println("4 - Listar apps");
        
        if (utilizadores.get(idUser).getProgrammer() == true)
            System.out.println("5 - Adicionar nova aplicação");
        if(utilizadores.get(idUser).getCarrinhoCompras().getCompras().size() > 0 ) // se tiver compras feitas
            System.out.println("6 - Classificar uma aplicação");
        
        System.out.println("0 - Voltar atrás");
        
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: compraSubscricao(idUser);
                        menuPrincipalUtilizador(idUser);
                        break;
                case 2: verCarrinhoCompras(idUser);
                        menuPrincipalUtilizador(idUser);
                        break;
                case 3: verAplicacoesUtilizador(idUser);
                        menuPrincipalUtilizador(idUser);
                        break;
                case 4: tipoLista();
                        menuPrincipalUtilizador(idUser);
                        break;
                case 5: if (utilizadores.get(idUser).getProgrammer() == true)
                            novaAplicacao(idUser);
                        menuPrincipalUtilizador(idUser);
                        break;
                case 6: if(utilizadores.get(idUser).getCarrinhoCompras().getCompras().size() > 0 )
                            classificacarApps(idUser);
                        menuPrincipalUtilizador(idUser);
                        break;
                case 0: modoVisualizacao();
                        break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuPrincipalUtilizador(idUser);
        }
    }
    
    /*
    *   Saber se utilizador escolheu comprar ou subscrever
    */
    private static void compraSubscricao(int idUser){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Deseja");
        System.out.println("1 - Comprar");
        System.out.println("2 - Subscrever");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: menuCategorias(idUser, false, true); break;             //Escolheu comprar
                case 2: menuCategorias(idUser, true, false); break;             //Escolheu subscrever
                default: throw new InputMismatchException();
            }
            
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            compraSubscricao(idUser);
        }
    }
    
    //Feito
    private static void tipoLista(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Listar por:");
        System.out.println("1 - Ordem");
        System.out.println("2 - Seletiva");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: listaOrdem(); break;                
                case 2: listaSeletiva(); break;             
                default: throw new InputMismatchException();
            }

        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            tipoLista();
        }
    }

    //Feito
    private static void listaSeletiva(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Listar por:");
        System.out.println("1 - Categoria");
        System.out.println("2 - Classificação miníma");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: menuCategoriasUser(); break;                
                case 2: listaPorClassificacao(); break;            
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            listaSeletiva();
        }
    }

    private static void listaOrdem(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Listar por ordem:");
        System.out.println("ALPHAbetica");
        System.out.println("Numero de vendas");
        System.out.println("Classificacao");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: listaOrdenada(1); break;             //Escolheu comprar
                case 2: listaOrdenada(2); break;
                case 3: listaOrdenada(3); break;//Escolheu subscrever
                default: throw new InputMismatchException();
            }

        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            listaSeletiva();
        }
    }
 
    /*
    *   Menu que permite escolher a categoria de uma aplicação e aguardar a seleção da mesma
    */
    private static void menuCategorias(int idUser, boolean subscricao, boolean compra){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Deseja");
        System.out.println("Categoria: \n1 - Games \n2 - Business \n3 - Education \n4 - Lifestyle \n5 - Entertainment \n6 - Utilities \n7 - Travel \n8 - Health & Fitness");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                //idUser é necessário para que o utilizador não volte a comprar a mesma aplicação
                case 1: listAppsPerCategory(idUser,"Games", subscricao, compra);
                        lerOpcaoApp(idUser, "Games", subscricao, compra);
                        break;
                case 2: listAppsPerCategory(idUser,"Business", subscricao, compra); 
                        lerOpcaoApp(idUser, "Business", subscricao, compra);
                        break;
                case 3: listAppsPerCategory(idUser,"Education", subscricao, compra); 
                        lerOpcaoApp(idUser, "Education", subscricao, compra);
                        break;
                case 4: listAppsPerCategory(idUser,"Lifestyle", subscricao, compra); 
                        lerOpcaoApp(idUser, "Lifestyle", subscricao, compra);
                        break;
                case 5: listAppsPerCategory(idUser,"Entertainment", subscricao, compra); 
                        lerOpcaoApp(idUser, "Entertainment", subscricao, compra);
                        break;
                case 6: listAppsPerCategory(idUser,"Utilities", subscricao, compra); 
                        lerOpcaoApp(idUser, "Utilities", subscricao, compra);
                        break;
                case 7: listAppsPerCategory(idUser,"Travel", subscricao, compra); 
                        lerOpcaoApp(idUser, "Travel", subscricao, compra);
                        break;
                case 8: listAppsPerCategory(idUser,"Health & Fitness", subscricao, compra); 
                        lerOpcaoApp(idUser, "Health & Fitness", subscricao, compra);
                        break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuCategorias(idUser, subscricao, compra);
        }
    }
    
    /*
    *   Responsável por verificar a escolha da aplicação a comprar/subscrever
    */
    private static void lerOpcaoApp(int idUser, String categoria, boolean subscricao, boolean compra){
        int opcao = 0;
        int numAppNaCategoria = 0;                          //Conta o número de apps, para saber se é possível ou não haver escolha de compra/subscrição
        int numAppCompradasDaCategoria = 0;                 //Conta o número de apps que foram compradas pelo utilizador na categoria, para saber se é possível ou não haver escolha de compra/subscrição
        int numAppAdicionadas = 0;                          //Conta o número de apps que foram adicionadas pelo utilizador na categoria, para saber se é possível ou não haver escolha de compra/subscrição
        boolean jaComprou = false;                          //Saber se app já foi ou não comprada pelo utilizador
        boolean jaAdicionou = false;                        //Saber se app já foi ou não adicionada pelo utilizador
        Scanner teclado = new Scanner(System.in);
        
        for(Apps a : apps){
             if(a.getCategoria().equals(categoria) && a.isTemSubscricao() && subscricao)
                 numAppNaCategoria++;
             else if(a.getCategoria().equals(categoria) && !a.isTemSubscricao() && compra)
                 numAppNaCategoria++;
        }
        
        for(User u : utilizadores){
            if(u.getIdUser() == idUser){
                for(int idApp : u.getCarrinhoCompras().getIdApps()){
                    for(Apps a : apps){
                        if(idApp == a.getIdApp() && a.getCategoria().equals(categoria))
                            numAppAdicionadas++;
                    }
                }
                for(Compra c : u.getCarrinhoCompras().getCompras()){
                    for(Apps a : apps){
                        if(c.getIdApp() == a.getIdApp() && a.getCategoria().equals(categoria))
                            numAppCompradasDaCategoria++;
                   }
                }
            }
        }
        
        //Se existirem aplicações na categoria desejada aguarda a escolha do utilizador
        if(numAppNaCategoria > 0 && numAppNaCategoria > numAppCompradasDaCategoria && numAppNaCategoria > numAppAdicionadas){
            System.out.print("Escolha a aplicação: ");
            try{
                opcao = teclado.nextInt();
                System.out.println("");
                //Procura pela aplicação com as caracteristicas pretendidas
                for(Apps a : apps){
                    //Limita opções de escolha a apenas as que sejam de subscrição e que sejam da categoria escolhida
                    if(a.isTemSubscricao() && subscricao){
                        if(a.getIdApp() == opcao && a.getCategoria().equals(categoria)){
                            adicionarCarrinhoCompras(idUser, opcao);
                            return ;
                        }
                    }
                    //Se a app não for de subscrição e estiver a efetuar uma compra da categoria escolhida
                    else if(!a.isTemSubscricao() && compra){
                        if(a.getIdApp() == opcao && a.getCategoria().equals(categoria)){
                            //Verifica se aplicação "escolhida" foi comprada (apesar de já não aparecer, mas o utilizador pode inserir o id à mesma)
                            for(User u : utilizadores){
                                if(u.getIdUser() == idUser){
                                    for(Compra c : u.getCarrinhoCompras().getCompras()){
                                        if(c.getIdApp() == a.getIdApp())
                                            jaComprou = true;
                                    }
                                    for(int idApp : u.getCarrinhoCompras().getIdApps()){
                                        if(idApp == a.getIdApp())
                                            jaAdicionou = true;
                                    }
                                }
                            }
                            
                            //Se não comprou e se não adicionou ao carrinho
                            if(!jaComprou && !jaAdicionou){
                                adicionarCarrinhoCompras(idUser, opcao);
                                return ;
                            }   
                        }
                    }              
                }
                throw new InputMismatchException();      
            }catch(InputMismatchException ime){
                System.out.println("Opção Inválida, tente novamente!");
                System.out.println("");
                listAppsPerCategory(idUser, categoria, subscricao, compra);
                lerOpcaoApp(idUser, categoria, subscricao, compra);
            }
        }
        //Se não existirem, volta a perguntar se quer comprar/subscrever e a categoria ...
        else{
            System.out.println("Nenhuma aplicação na categoria escolhida!");
            System.out.println("Tente novamente!");
            System.out.println("");
            compraSubscricao(idUser);
        }    
    }
    
    /*
    *   Adiciona o contéudo escolhido ao carrinho de compras do utilizador
    */
    private static void adicionarCarrinhoCompras(int idUser, int idApp){
        double total = 0;
        double desconto = 0;
        
        for(User u : utilizadores){
            if(u.getIdUser() == idUser){
                u.adicionarCarrinhoCompras(idApp);
                for(Apps a : apps){
                    if(a.getIdApp() == idApp){
                        if(a.isDescontoMenosCompras())
                            desconto = desconto + 0.05;
                        if(a.isDescontoMensal())
                            desconto = desconto + 0.15;
                        if(u.getIdConvidados().size() > 0)
                            desconto = desconto + 0.05;
                        if(u.getIsPremium())
                            desconto = desconto + 0.60;
                        if(a.isDescontoTotal())
                            desconto = 1;
                        
                        total = a.getPreco() - (a.getPreco() * desconto);
                    }
                }

                total = total + u.getCarrinhoCompras().getTotal();
                u.getCarrinhoCompras().setTotal(total);
            }
        }
    }
    
    /*
    *   Escreve o contéudo do carrinho de compras
    */
    private static void verCarrinhoCompras(int idUser) {
        int desconto = 0;
        for(User u : utilizadores){
            if(u.getIdUser() == idUser){
                if(u.getCarrinhoCompras().getIdApps().size() > 0){
                    for(int idApp : u.getCarrinhoCompras().getIdApps()){
                        for(Apps a : apps){
                            if(a.getIdApp() == idApp){
                                System.out.println("Nome: " + a.getNome());
                                System.out.println("Categoria: " + a.getCategoria());
                                System.out.println("Preço: " + a.getPreco());
                                System.out.print("Desconto: ");
                                if(a.isDescontoMenosCompras())
                                    desconto = desconto + 5;
                                if(a.isDescontoMensal())
                                    desconto = desconto + 15;
                                if(u.getIsPremium())
                                    desconto = desconto + 60;
                                if(u.getIdConvidados().size() > 0)
                                    desconto = desconto + 5;
                                if(a.isDescontoTotal())
                                    desconto = 100;
                                System.out.println(desconto+"%");
                                System.out.print("Tipo: ");
                                if(a.isTemSubscricao())
                                    System.out.println("Subscrição");
                                else
                                    System.out.println("Compra");
                                System.out.println("");
                            }   
                            desconto = 0;
                        } 
                    }
                    System.out.println("Total: " + u.getCarrinhoCompras().getTotal() + "€");
                    System.out.println("");
                    opcoesCarrinho(idUser);
                }
                else
                    System.out.println("Carrinho de Compras Vazio!");
                System.out.println("");
            }
        }
    }
    
    /*
    *   Opções disponíveis no carrinho de compras - Pagar ou voltar ao menu de utilizador
    */
    private static void opcoesCarrinho(int idUser){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Deseja");
        System.out.println("1 - Pagar");
        System.out.println("2 - Remover do carrinho de compras");
        System.out.println("3 - Voltar atrás");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: pagar(idUser); break;
                case 2: menuRemoverDoCarrinhoCompras(idUser); break;
                case 3: menuPrincipalUtilizador(idUser); break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            verCarrinhoCompras(idUser);
        }
    }
    
    /*
    *   Responsável pelo "registo" de uma compra/subscrição
    */
    private static void pagar(int idUser){
        double desconto = 0;                    //Guarda a quantidade de desconto associada a cada app para o registo da mesma
        double total = 0;                       //Guarda o total pago após o desconto associado a cada app
        boolean normal = true;                  //Determina se é uma compra (true) ou uma subscrição (false)
        boolean jaTemSubscricao = false;        //Determina se o utilizador já está subscrito ou não
        for(User u : utilizadores)
        {
            if(u.getIdUser() == idUser){
                for(int idApp : u.getCarrinhoCompras().getIdApps()){
                    for(Apps a : apps){
                        if(a.getIdApp() == idApp){
                            //Calcula a quantidade de desconto para a app
                            //Verifica as flags se estão ativas e determina os descontos associados a cada uma
                            if(a.isDescontoMenosCompras())
                                desconto = desconto + 0.05;
                            if(a.isDescontoMensal())
                                desconto = desconto + 0.15;
                            if(u.getIsPremium())
                                desconto = desconto + 0.6;
                            if(u.getIdConvidados().size() > 0)
                                    desconto = desconto + 0.05;
                            if(a.isDescontoTotal())
                                desconto = 1;

                            //Determina total com desconto
                            total = a.getPreco() - (a.getPreco() * desconto);
                            
                            //Verifica se a app é do tipo subscrever e se o utilizador já possui uma subscrição
                            if(a.isTemSubscricao()){
                                 normal = false;
                                 for(Subscricao s : u.getCarrinhoCompras().getSubscricoes()){
                                     if(s.getIdApp() == idApp)
                                         jaTemSubscricao = true;
                                 }
                            }
                            a.incrementaNumCompras();
                            u.setTotalGasto(total);
                            Programador temp = (Programador)utilizadores.get(a.getIdProgrammer());
                            temp.setDinheiroRecebido(total);
                        }
                    }
                    
                    //Efetua uma compra normal
                    if(normal)
                        u.getCarrinhoCompras().novaCompra(new Compra(obterDiaAtual(), idUser, idApp, total,0,""));
                    //Efetua uma nova subscrição
                    else if(!normal && !jaTemSubscricao)
                        u.getCarrinhoCompras().novaSubscricao(new Subscricao(obterDiaAtual(), idUser, idApp, total, 365, true,0,""));
                    //Atualiza uma subscrição existente
                    else if(!normal && jaTemSubscricao){
                        for(Subscricao s : u.getCarrinhoCompras().getSubscricoes()){
                            if(s.getIdApp() == idApp){
                                s.setDiasSubscricao(365);
                                s.setData(Integer.toString(dias));
                            }
                        }
                    }
                        
                    
                    //Se usar o desconto de convidados remove sempre o último
                    if(u.getIdConvidados().size() > 0)
                        u.getIdConvidados().remove(u.getIdConvidados().size() - 1);

                    normal = true;
                    jaTemSubscricao = false;
                    desconto = 0;
                    total = 0;
                }
                //Limpa o carrinho de compras após efetuar o "pagamento"
                u.getCarrinhoCompras().getIdApps().clear();
            }
        }
        System.out.println("Pagamento com sucesso!");
    }
    // classificar apps
    private static void classificacarApps(int idUser) {
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        Classificacao classi;
        int contador = 0;
        
        System.out.println("Deseja classificar que aplicação? ");
        // percorre as compras para encontrar as classificações referentes ao idUser
        for (int j = 0; j < utilizadores.get(idUser).getCarrinhoCompras().getCompras().size(); j++) {
            classi = utilizadores.get(idUser).getCarrinhoCompras().getCompras().get(j).getClassificacao();
            
            // Verifica se o valor da classificação é 0, ou seja, não foi inserido
            if (classi.getClassificacao() == 0) {
                System.out.println(classi.getIdClassificacao() + "- " + apps.get(classi.getIdApp()).getNome());   // imprime idclassific - nomeApp
                contador++;
            } 
        }
        // percorre as subscricoes para encontrar as classificações referentes ao idUser
        for (int j = 0; j < utilizadores.get(idUser).getCarrinhoCompras().getSubscricoes().size(); j++) {
            classi = utilizadores.get(idUser).getCarrinhoCompras().getSubscricoes().get(j).getClassificacao();
            
            // Verifica se o valor da classificação é 0, ou seja, não foi inserido
            if (classi.getClassificacao() == 0) {
                System.out.println(classi.getIdClassificacao() + "- " + apps.get(classi.getIdApp()).getNome());   // imprime idclassific - nomeApp
                contador++;
            } 
        }
        
        if (contador == 0) {
            System.out.println("-> Já classificou todas as suas aplicações\n");
            menuUtilizador();
        }
        
        System.out.print("Opção: ");
        try {
            opcao = teclado.nextInt();
            for(int i = 0; i < utilizadores.get(idUser).getCarrinhoCompras().getCompras().size(); i++) {
                classi = utilizadores.get(idUser).getCarrinhoCompras().getCompras().get(i).getClassificacao();
                if (classi.getIdClassificacao() == opcao) {
                    classificar(idUser, i, classi.getIdApp()); // chama metodo para pedir dados da classificacao
                }
            }
            
            for(int i = 0; i < utilizadores.get(idUser).getCarrinhoCompras().getSubscricoes().size(); i++) {
                classi = utilizadores.get(idUser).getCarrinhoCompras().getSubscricoes().get(i).getClassificacao();
                if (classi.getIdClassificacao() == opcao) {
                    classificar(idUser, i, classi.getIdApp()); // chama metodo para pedir dados da classificacao
                }
            }
        } catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            classificacarApps(idUser);
        }
    }
    
    // responsavel por inserir dados da classificacao
    private static void classificar(int idUser, int index, int idApp) {
        int pontuacao;
        String comentario;
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Que pontuação dá a esta aplicação? 1-5");
        pontuacao = teclado.nextInt();
        String str1 = teclado.nextLine(); // necessário para não fazer com que o comentário faça skip a uma linha
        
        System.out.println("Escreva um comentário sobre esta aplicação: ");
        comentario = teclado.nextLine();
        
        if( pontuacao > 0 && pontuacao <= 5) {
            utilizadores.get(idUser).getCarrinhoCompras().getCompras().get(index).getClassificacao().setClassificacao(pontuacao);
            utilizadores.get(idUser).getCarrinhoCompras().getCompras().get(index).getClassificacao().setComentario(comentario);
            
            for (int i = 0; i < apps.size(); i++) {
                if(apps.get(i).getIdApp() == idApp)
                {
                    apps.get(i).setAvaliacaoApp(pontuacao); // calcular o valor da pontuacao de uma app
                    apps.get(i).setNumAvs();
                }
            }
            
        } 
        else {
            System.out.println("Pontuação inválida.");
            classificar(idUser, index, idApp);
        }
    
    }
    
    private static void menuRemoverDoCarrinhoCompras(int idUser){
        int opcao = 0;
        double total = 0;
        double desconto = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Qual a aplicação a remover");
        
        for(User u : utilizadores){
            if(u.getIdUser() == idUser){
                for(int idApp : u.getCarrinhoCompras().getIdApps()){
                    for(Apps a : apps){
                        if(a.getIdApp() == idApp){
                            System.out.println(a.getIdApp() + " - " + a.getNome());
                        }   
                    }
                }
            }
        }
        
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            for(User u : utilizadores){
                if(u.getIdUser() == idUser){
                    for(int i = 0; i < u.getCarrinhoCompras().getIdApps().size(); i++){
                        if(u.getCarrinhoCompras().getIdApps().get(i) == opcao){
                            //Descobrir preço a remover do carrinho
                            for(Apps a : apps){
                                if(a.getIdApp() == u.getCarrinhoCompras().getIdApps().get(i)){
                                    if(a.isDescontoMenosCompras())
                                        desconto = desconto + 0.05;
                                    if(a.isDescontoMensal())
                                        desconto = desconto + 0.15;
                                    if(u.getIdConvidados().size() > 0)
                                        desconto = desconto + 0.05;
                                    if(a.isDescontoTotal())
                                        desconto = 1;

                                    total = a.getPreco() - (a.getPreco() * desconto);
                                }
                            }
                            u.getCarrinhoCompras().getIdApps().remove(i);
                            total = u.getCarrinhoCompras().getTotal() - total;
                            u.getCarrinhoCompras().setTotal(total);
                            return ;
                        }    
                    }
                }
            }
            throw new InputMismatchException();
        }catch(InputMismatchException ime){
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            verCarrinhoCompras(idUser);
        }
    }
    
    private static void escreverDadosUtilizador()
    {
        if (utilizadores.size()>0)
        {
            for (int i = 0; i < utilizadores.size(); i++)
            {

                System.out.println(utilizadores.get(i).toString());
            }
            System.out.println("");
        }
        else System.out.println("Não existem utilizadores");
    }

    private static void listUserValue() {

        Collections.sort(utilizadores, comparingDouble(User::getTotalGasto));
        Collections.reverse(utilizadores);
        for (int i = 0; i < utilizadores.size(); i++)
        {
                System.out.println(utilizadores.get(i).getNome() + " - " + utilizadores.get(i).getTotalGasto()+ " id: " + utilizadores.get(i).getIdUser());
        }
    }

    
    private static void valorAcumuladoProgramador() {
        int opcao = 0;
        System.out.println("Escolha o programador: ");
        Scanner teclado = new Scanner(System.in);
        
        for(int i = 0; i < utilizadores.size(); i++) {
            if(utilizadores.get(i).getProgrammer() == true)
                System.out.println(utilizadores.get(i).getIdUser() + " - " + utilizadores.get(i).getNome());
        }
        opcao=teclado.nextInt();
        if (opcao >= 0 && opcao < utilizadores.size() && utilizadores.get(opcao).getProgrammer() == true) {
            Programador temp = (Programador)utilizadores.get(opcao);
            System.out.println("Valor acumulado pelo programador: " + temp.getDinheiroRecebido());
        } else {
            System.out.println("Não existe um programador com este id.");
        }
    }

    //Feito
    private static void gravar() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter userFile = new PrintWriter("Users.txt", "UTF-8");
        for(int i = 0; i < utilizadores.size(); i++) {
            userFile.println(utilizadores.get(i).getNome() + "," + utilizadores.get(i).getIdade() + "," + utilizadores.get(i).getIsPremium() + "," + utilizadores.get(i).getProgrammer() + "," + utilizadores.get(i).getTotalGasto()+"");
        }
        userFile.close();

        PrintWriter appsFile = new PrintWriter("Apps.txt", "UTF-8");
        for(int i = 0; i < apps.size(); i++) {
            appsFile.println(apps.get(i).getNome() + "," + apps.get(i).getPreco()+ "," + apps.get(i).getCategoria()+ "," + apps.get(i).isTemSubscricao()+ "," + apps.get(i).getIdProgrammer()+ "," + apps.get(i).getAvaliacaoApp()+"," + apps.get(i).getNumAvs()+"");
        }
        appsFile.close();

        PrintWriter comprasFile = new PrintWriter("Compras.txt", "UTF-8");
        for(int i = 0; i < utilizadores.size(); i++) {
            for(int j = 0; j <  utilizadores.get(i).getCarrinhoCompras().getCompras().size(); j++)
                comprasFile.println( utilizadores.get(i).getCarrinhoCompras().getCompras().get(j).getData()+ "," + utilizadores.get(i).getIdUser()+ "," + (int)utilizadores.get(i).getCarrinhoCompras().getCompras().get(j).getIdApp()+ "," + utilizadores.get(i).getTotalGasto()+ ","  + utilizadores.get(i).getCarrinhoCompras().getCompras().get(j).getClassificacao().getClassificacao()+ ","   + utilizadores.get(i).getCarrinhoCompras().getCompras().get(j).getClassificacao().getComentario()+ "-");
        }
        comprasFile.close();

        PrintWriter subsFile = new PrintWriter("Subscricoes.txt", "UTF-8");
        for(int i = 0; i < utilizadores.size(); i++) {
            for(int j = 0; j <  utilizadores.get(i).getCarrinhoCompras().getSubscricoes().size(); j++)
                    subsFile.println( utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j).getData()+ "," + utilizadores.get(i).getIdUser()+ "," + (int)utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j).getIdApp()+ "," + utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j).getTotal()+ "," + utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j).getDiasSubscricao()+"," + utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j).isPodeUsarApp()+ "," + utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j).getClassificacao().getClassificacao()+ ","   + utilizadores.get(i).getCarrinhoCompras().getSubscricoes().get(j).getClassificacao().getComentario()+ "-");
        }
        subsFile.close();

        System.out.println("Dados gravados com sucesso!");
        System.out.println("");
    }
    
    /*
    *   Permite visualizar as compras/subscrições de um certo utilizador
    *   Cada utilizador fica associado apenas a um carrinho de compras e através desse carrinho é possível chegar as suas compras/subscrições
    */
    private static void verAplicacoesUtilizador(int idUser) {
       for(User u : utilizadores){
            if(u.getIdUser() == idUser){
                if(u.getCarrinhoCompras().getCompras().size() > 0){
                    System.out.println("Compras: ");
                    System.out.println("");
                    for(Compra c : u.getCarrinhoCompras().getCompras()){
                        for(Apps a : apps){
                            if(a.getIdApp() == c.getIdApp()){
                                System.out.println("Data de compra: " + c.getData());
                                System.out.println("Nome: " + a.getNome());
                                System.out.println("Categoria: " + a.getCategoria());
                                System.out.println("Preço: " + c.getTotal());
                                System.out.println("");
                            }   
                        } 
                    }
                }
                else{
                    System.out.println("O utilizador não comprou aplicações!");
                    System.out.println("");
                }
                
                
                if(u.getCarrinhoCompras().getSubscricoes().size() > 0){
                    System.out.println("Subscrições: ");
                    System.out.println("");
                    for(Subscricao s : u.getCarrinhoCompras().getSubscricoes()){
                        for(Apps a : apps){
                            if(a.getIdApp() == s.getIdApp()){
                                System.out.println("Data de compra: " + s.getData());
                                System.out.println("Nome: " + a.getNome());
                                System.out.println("Categoria: " + a.getCategoria());
                                System.out.println("Preço: " + s.getTotal());
                                System.out.println("Número de dias até fim da subscrição: " + s.getDiasSubscricao());
                                System.out.println("");
                            }   
                        } 
                    }
                }
                else{
                    System.out.println("O utilizador não possui subscrições!");
                    System.out.println("");
                }
                
            }
        }
    }
    
    /*
    *   Das 8 categorias, escolhe uma aleatoriamente e ativa o boolean responsável por determinar se se encontra em desconto ou não
    */
    private static void descontoMensal(){
        String [] categorias = {"Games", "Business", "Education", "Lifestyle", "Entertainment", "Utilities", "Travel", "Health & Fitness"};
        Random rand = new Random();
        int i = rand.nextInt(categorias.length);
        try{
            for(Apps a : apps){
                if(a.getCategoria().equals(categorias[i])){
                    a.setDescontoMensal(true);
                }
            }
        }catch(ArrayIndexOutOfBoundsException aioobe){
            System.out.println("Ocorreu um erro, tentou aceder a uma posição fora do array!");
            System.out.println("");
        }
    }
    
    /*
    *   De todas as aplicações escolhe as 5 que menos venderam e ativa o boolean responsável por determinar se se encontra em desconto ou não
    */
    private static void descontoMenosCompras(){
        int min = apps.get(0).getNumCompras();
        int pos = 0;
        int appsMenosCompras = 5;
        
        while(appsMenosCompras != 0){
            for(int i = 0; i < apps.size() ; i++){
                if(apps.get(i).getNumCompras() < min && !apps.get(i).isDescontoMenosCompras()){
                    min = apps.get(i).getNumCompras();
                    pos = i;
                }
            }
            apps.get(pos).setDescontoMenosCompras(true);
            appsMenosCompras--;
        }  
    }
    
    /*
    *   Das aplicações que já tem desconto, escolhe aleatoriamente a quantidade que deve ficar gratuita e ativa o boolean responsável por determinar se se encontra em desconto ou não
    */
    private static void descontoTotal(){
        int numAppsDesconto = 0;
        Random rand = new Random();
        
        for(Apps a : apps){
            if(a.isDescontoMenosCompras() || a.isDescontoMensal())
                numAppsDesconto++;
        }
        
        int numAleatorioAppsGratis = rand.nextInt(numAppsDesconto)+1;
        int gratis;
        
        for(Apps a : apps){
            gratis = rand.nextInt(2);
            if(a.isDescontoMensal() || a.isDescontoMenosCompras()){
                if(gratis == 1){
                    a.setDescontoTotal(true);
                    numAleatorioAppsGratis--;
                }
            }
            if(numAleatorioAppsGratis == 0)
                break;
        }
    }
    
    /*
    *   Faz o reset do desconto Mensal
    */
    private static void resetDescontoMensal(){
        for(Apps a : apps){
            if(a.isDescontoMensal()){
                a.setDescontoMensal(false);
            }
        }
    }
    
    /*
    *   Faz o reset do desconto das apps com menos compras
    */
    private static void resetDescontoMenosCompras(){
        for(Apps a : apps){
            if(a.isDescontoMenosCompras()){
                a.setDescontoMenosCompras(false);
            }
        }
    }
    
    /*
    *   Faz o reset do desconto total (apps gratuitas)
    */
    private static void resetDescontoTotal(){
        for(Apps a : apps){
            if(a.isDescontoTotal()){
                a.setDescontoTotal(false);
            }
        }
    }
}