
import java.util.Scanner;

public class User
{
    private static int numUsers; // numero total de users
    String nome;
    int idade;
    int idUser;
    boolean isPremium;
    boolean isProgrammer;
    int[] idConvidados;
    
    public User(String nome, int idade, boolean isPremium, boolean isProgrammer) {
        this.nome = nome;
        this.idade = idade;
        idUser = numUsers;
        this.isPremium = isPremium;
        this.isProgrammer = isProgrammer;
        numUsers++;
    }
    
    public String getNome()
    {

        return nome;
    }

    public int getIdade()
    {

        return idade;
    }

    public int getIdUser()
    {

        return idUser;
    }

    public boolean getIdPremium()
    {

        return isPremium;
    }

    public boolean getProgrammer()
    {

        return isProgrammer;
    }

    public int[] getIdConvidados()
    {

        return idConvidados;
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

    public void setConvidados(int convidado)
    {
        return;
    }
    
    // MÉTODO toString
    public String toString() {
        String texto;
        texto = "ID: " + idUser + ";   " + nome + "; VIP: " + isPremium + ";  PROG: " + isProgrammer;
        return texto;
    }
}
