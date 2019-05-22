import java.util.ArrayList;

public class User
{
    private static int numUsers; // numero total de users
    private String nome;
    private int idade;
    private int idUser;
    private double totalGasto;
    private boolean isPremium;
    private boolean isProgrammer;
    private ArrayList<Integer> idConvidados;
    private CarrinhoCompras carrinho;
    
    public User(String nome, int idade, boolean isPremium, boolean isProgrammer, double total) {
        idConvidados = new ArrayList<>();
        this.nome = nome;
        this.idade = idade;
        this.totalGasto = total;
        idUser = numUsers;
        carrinho = new CarrinhoCompras(idUser);
        this.isPremium = isPremium;
        this.isProgrammer = isProgrammer;
        numUsers++;
    }
    
    public int getNumUsers() {
        return numUsers;
    }
    
    public String getNome()
    {

        return nome;
    }

    public void setTotalGasto(double totalGasto)
    {
        this.totalGasto = this.totalGasto + totalGasto;
    }

    public double getTotalGasto()
    {

        return totalGasto;
    }

    public int getIdade()
    {

        return idade;
    }

    public int getIdUser()
    {

        return idUser;
    }

    public boolean getIsPremium()
    {

        return isPremium;
    }

    public boolean getProgrammer()
    {

        return isProgrammer;
    }
    
        
    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    public void setNome(String nome)
    {
        this.nome=nome;
    }

    public void setIdade(int idade)
    {

        this.idade=idade;
    }

    public void setPremium(boolean isPremium)
    {

        this.isPremium=isPremium;
    }

    public void setProgrammer(boolean isProgrammer)
    {

        this.isProgrammer=isProgrammer;
    }
    
    // MÉTODO toString
    public String toString() {
        String texto;
        texto = "ID: " + idUser + ";   " + nome + "; VIP: " + isPremium + ";  PROG: " + isProgrammer;
        return texto;
    }

    public ArrayList<Integer> getIdConvidados() {
        return idConvidados;
    }

    public void setIdConvidados(int idConvidados) {
        this.idConvidados.add(idConvidados);
    }

    public CarrinhoCompras getCarrinhoCompras() {
        return carrinho;
    }

    public void adicionarCarrinhoCompras(int idApp) {
        this.carrinho.addIdApp(idApp);
    }
}
