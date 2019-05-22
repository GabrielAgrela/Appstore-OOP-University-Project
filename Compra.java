import java.util.ArrayList;

public class Compra
{
    private static int numCompras;
    private String data;
    private int idUser;
    private int idApp;
    private int idCompra;
    private double total;
    private Classificacao classificacao;
    
    public Compra(String data, int idUser, int idApp, double total,float classi, String comment){
        this.idUser = idUser;
        this.data = data;
        idCompra = numCompras;
        this.idApp = idApp;
        this.total = total;
        classificacao = new Classificacao(idCompra, idApp,classi,comment,idUser);
        numCompras++;
    }

    public String getData() {
        return data;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public double getTotal() {
        return total;
    }
    
    public int getIdApp() {
        return idApp;
    }
    
    public Classificacao getClassificacao(){
        return classificacao;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }
    
    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }
    
    public String toString() {
        String texto;
        texto = "idCompra: " + idCompra + "; idUser:  " + idUser + "; Classificacao : " + classificacao + "\n";
        return texto;
    }
}
