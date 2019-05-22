
import java.util.ArrayList;

public class CarrinhoCompras
{
    private int idUser;
    private ArrayList<Integer> idApps;
    private double total;
    private ArrayList<Compra> compras;
    private ArrayList<Subscricao> subscricoes;
    
    public CarrinhoCompras(int idUser) {
        this.idUser = idUser;
        idApps = new ArrayList<>();
        compras = new ArrayList<>(idUser);
        subscricoes = new ArrayList<>(idUser);
        total = 0;
    }
    
    public int getIdUser() {
        return idUser;
    }

    public double getTotal() {
        return total;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<Integer> getIdApps() {
        return idApps;
    }

    public void addIdApp(int idApp) {
        getIdApps().add(idApp);
    }

    public ArrayList<Compra> getCompras() {
        return compras;
    }
    
    public void novaCompra(Compra compra) {
        this.compras.add(compra);
    }
    
    public ArrayList<Subscricao> getSubscricoes() {
        return subscricoes;
    }
    
    public void novaSubscricao(Subscricao subscricao) {
        this.subscricoes.add(subscricao);
    }
}