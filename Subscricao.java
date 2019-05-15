public class Subscricao extends Compra
{
    int diasSubscricao;
    boolean podeUsarApp;

    public int getDiasSubscricao() {
        return diasSubscricao;
    }

    public boolean isPodeUsarApp() {
        return podeUsarApp;
    }

    public void setDiasSubscricao(int diasSubscricao) {
        this.diasSubscricao = diasSubscricao;
    }

    public void setPodeUsarApp(boolean podeUsarApp) {
        this.podeUsarApp = podeUsarApp;
    }
}
