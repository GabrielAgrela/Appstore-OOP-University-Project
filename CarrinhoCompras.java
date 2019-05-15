public class CarrinhoCompras
{
    int idUser;
    int[] idApp;
    double total;
    double desconto;

    public int getIdUser() {
        return idUser;
    }

    public int[] getIdApp() {
        return idApp;
    }

    public double getTotal() {
        return total;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdApp(int[] idApp) {
        this.idApp = idApp;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}
