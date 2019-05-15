public class Compra
{
    String data;
    int idUser;
    int[] idApp;
    int[] idClassificacao;
    int idCompra;
    double total;

    public String getData() {
        return data;
    }

    public int getIdUser() {
        return idUser;
    }

    public int[] getIdApp() {
        return idApp;
    }

    public int[] getIdClassificacao() {
        return idClassificacao;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdApp(int[] idApp) {
        this.idApp = idApp;
    }

    public void setIdClassificacao(int[] idClassificacao) {
        this.idClassificacao = idClassificacao;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
