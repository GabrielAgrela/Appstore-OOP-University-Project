import java.util.*;

public class AppStore
{
    private int dias;
    // adicionei este comentario para testar se ta funcionando no lado do client
    // ArrayList de utilizadores
    private static ArrayList<User> utilizadores = new ArrayList<User>();

    private static ArrayList<Apps> apps = new ArrayList<Apps>();
    
    public AppStore(){
        dias = 1;
    }
    
    public static void main(String[] args)
    {
        while(true) {
            modoVisualizacao();
        }
    }
    
    public void avancarDia()
    {

    }

    public void avancarSemana()
    {

    }

    public void avancarMes()
    {

    }
    
    public int getNumCompras()
    {

        return 0;
    }

    public int getNumComprasProgrammer(int idProgrammer)
    {

        return 0;
    }

    public int getNumReviews(int idApp)
    {

        return 0;
    }

    public void newUser(String nome,int idade, boolean isPremium, boolean isProgrammer)
    {



    }

    public static void newApp(int idProgrammer)
    {
        String nome;
        double preco;
        String categoria="";
        int categoriaChoice;
        boolean temSubscricao=false;

        Scanner teclado = new Scanner(System.in);

        System.out.println("Nome: ");
        nome = teclado.nextLine();

        System.out.println("preco: ");
        preco = teclado.nextDouble();


        System.out.println("Categoria: \n 1- Games, \n 2- Business, \n 3- Education, \n 4- Lifestyle, \n 5- Entertainment, \n 6- Utilities, \n 7- Travel, \n 8- Health & Fitness");
        categoriaChoice = teclado.nextInt();

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
                System.out.println("aladeen madafakaaaa");
        }

        apps.add( new Apps(nome, preco, categoria, temSubscricao, idProgrammer));

    }

    public void showAllUsers(int numUsers)
    {

    }

    public void buyApp(boolean isSubscricao, int idApp, double preco, int idUser)
    {

    }

    public void addReview(int idApp, int idUser, String reviewText,int stars)
    {

    }

    public static void listAppsPerCategory(String category)
    {
        for(int i = 0; i < apps.size(); i++)
        {
            if (apps.get(i).categoria.equals(category))
            {
                System.out.println(apps.get(i).nome+" Preço: "+ apps.get(i).preco+ "€ ID Programador: "+ apps.get(i).idProgrammer );
            }
        }
    }

    public static void listAppsPerOrder()
    {
        for(int i = 0; i < apps.size(); i++)
        {
            System.out.println(apps.get(i).nome+" Preço: "+ apps.get(i).preco+ "€ ID Programador: "+ apps.get(i).idProgrammer+ " Categoria: "+ apps.get(i).categoria );
        }
    }

    public static boolean checkID(int id)
    {
        boolean exists = false;
        for(int i = 0; i < utilizadores.size(); i++)
        {
            if (utilizadores.get(i).idUser == id && utilizadores.get(i).isProgrammer == true)
                exists = true;
        }
        return exists;
    }

    public void listReviews(int idApp, int numReview)
    {

    }

    public void listUsersSortBySpent(int numUsers)
    {

    }

    public static double totalAppStore()
    {
        double total=0;
        for(int i = 0; i < apps.size(); i++)
        {
            total = total + apps.get(i).preco;
        }
        return total;
    }

    public void totalProgrammer(int numComprasProgrammer)
    {

    }

    public void exitApp()
    {

    }
    
    /**
    * Menus de interação com o utilizador 
    **/
    private static void modoVisualizacao(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Modo de Visualização");
        System.out.println("1 - Admin");
        System.out.println("2 - Utilizador");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: menuAdmin(); break;
                case 2: menuUtilizador(); break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            modoVisualizacao();
        }
    }
    
    private static void menuAdmin(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);

        System.out.println("Menu Principal");
        System.out.println("1 - Novo Utilizador");
        System.out.println("2 - Nova App");
        System.out.println("3 - Visualizar Utilizadores");
        System.out.println("4 - Visualizar Apps");
        System.out.println("5 - Valor acumulado pela AppStore");
        System.out.println("6 - Valor acumulado por programador");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1:
                    utilizadorProgramador();
                    break;
                case 2:
                    readIdProgrammer();
                    break;
                case 3:
                    escreverDadosUtilizador();
                    break;
                case 4: verApps();
                    break;
                case 5: System.out.println(totalAppStore()+" euros");
                    break;
                case 6:
                    valorAcumuladoProgramador();
                    break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuAdmin();
        }
    }

    private static void readIdProgrammer(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        try
        {
            System.out.println("Escolha o programador: ");

            for(int i = 0; i < utilizadores.size(); i++) {
                if(utilizadores.get(i).isProgrammer == true)
                    System.out.println(utilizadores.get(i).idUser + " - " + utilizadores.get(i).nome);
            }
            opcao=teclado.nextInt();
            if (opcao < utilizadores.size() && utilizadores.get(opcao).isProgrammer == true) {
                newApp(opcao);
            } else {
                System.out.println("Não existe um programador com este id.");
                readIdProgrammer();
            }
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            readIdProgrammer();
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
        System.out.println("1- Games, \n 2- Business, \n 3- Education, \n 4- Lifestyle, \n 5- Entertainment, \n 6- Utilities, \n 7- Travel, \n 8- Health & Fitness");
        System.out.print("Opção: ");

        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: listAppsPerCategory("Games"); break;
                case 2: listAppsPerCategory("Business"); break;
                case 3: listAppsPerCategory("Education"); break;
                case 4: listAppsPerCategory("Lifestyle"); break;
                case 5: listAppsPerCategory("Entertainment"); break;
                case 6: listAppsPerCategory("Utilities"); break;
                case 7: listAppsPerCategory("Travel"); break;
                case 8: listAppsPerCategory("Health & Fitness"); break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuCategorias();
        }
    }
    
    //Falta acabar
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
                    criarUtilizador(0);
                    break;
                case 2: 
                    criarUtilizador(1);
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
    
    //Falta acabar
    private static void menuUtilizador(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Escolha o utilizador");
        System.out.println("1 - Nome");
        System.out.println("...");
        System.out.println("n - Nome");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: menuPrincipalUtilizador(); break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuUtilizador();
        }
    }
    
    //Falta acabar
    private static void menuPrincipalUtilizador(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Utilizador: " + "<nome>" + " - " + "<tipoUtilizador");
        System.out.println("1 - Ver aplicações disponíveis");
        System.out.println("2 - Ver aplicações adquiridas");
        System.out.println("3 - Adicionar nova aplicação (só para programador)");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: compraSubscricao(); break;
                case 2: break;
                case 3: newApp(10); break; //id ta estatico
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuPrincipalUtilizador();
        }
    }
    
    private static void compraSubscricao(){
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
                case 1: menuCategorias(); break;
                case 2: menuCategorias(); break;
                default: throw new InputMismatchException();
            }
            
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            compraSubscricao();
        }
    }
    
    private static void menuCategorias(){
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Deseja");
        System.out.println("1- Games, \n 2- Business, \n 3- Education, \n 4- Lifestyle, \n 5- Entertainment, \n 6- Utilities, \n 7- Travel, \n 8- Health & Fitness");
        System.out.print("Opção: ");
        
        try{
            opcao = teclado.nextInt();
            System.out.println("");
            switch(opcao){
                case 1: listAppsPerCategory("Games"); break;
                case 2: listAppsPerCategory("Business"); break;
                case 3: listAppsPerCategory("Education"); break;
                case 4: listAppsPerCategory("Lifestyle"); break;
                case 5: listAppsPerCategory("Entertainment"); break;
                case 6: listAppsPerCategory("Utilities"); break;
                case 7: listAppsPerCategory("Travel"); break;
                case 8: listAppsPerCategory("Health & Fitness"); break;
                default: throw new InputMismatchException();
            }
        }catch(InputMismatchException ime){
            System.out.println("");
            System.out.println("Opção Inválida, tente novamente!");
            System.out.println("");
            menuCategorias();
        }
    }
    
        // Criar utilizador tipo 0 - normal 1 - programador
    private static void criarUtilizador (int tipo)
    {
        String nome;
        int idade;
        boolean premium;
        
        Scanner teclado = new Scanner(System.in);
        System.out.println("Nome: ");
        nome = teclado.nextLine();
        
        System.out.println("Idade: ");
        idade = teclado.nextInt();
        
        System.out.println("Premium? S / N");
        char pre =  teclado.next().charAt(0);
        
        if (pre == 's' || pre == 'S')
            premium = true;
        else premium = false;
            
        if (tipo == 0 )
            utilizadores.add( new User(nome, idade, premium, false));
        else
            utilizadores.add( new Programador(nome, idade, premium, true));
    }
    
    private static void escreverDadosUtilizador() {
        for(int i = 0; i < utilizadores.size(); i++) {
            System.out.println(utilizadores.get(i).toString());
        }
    }
    
    private static void valorAcumuladoProgramador() {
        int opcao = 0;
        System.out.println("Escolha o programador: ");
        Scanner teclado = new Scanner(System.in);
        
        for(int i = 0; i < utilizadores.size(); i++) {
            if(utilizadores.get(i).isProgrammer == true)
                System.out.println(utilizadores.get(i).idUser + " - " + utilizadores.get(i).nome);
        }
        opcao=teclado.nextInt();
        if (opcao < utilizadores.size() && utilizadores.get(opcao).isProgrammer == true) {
            Programador temp = (Programador)utilizadores.get(opcao);
            System.out.println("Valor acumulado pelo programador: " + temp.dinheiroRecebido);
        } else {
            System.out.println("Não existe um programador com este id.");
        }
    }
}
