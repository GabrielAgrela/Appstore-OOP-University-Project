public class Subscricao extends Compra
{
    private int diasSubscricao;
    private boolean podeUsarApp;
    
    public Subscricao(String data, int idUser, int idApp, double total, int diasSubscricao, boolean podeUsarApp,float classi,String comment){
        super(data, idUser, idApp, total,classi,comment);
        this.diasSubscricao += diasSubscricao;
        this.podeUsarApp = podeUsarApp;
    }

    public int getDiasSubscricao() {
        return diasSubscricao;
    }

    public boolean isPodeUsarApp() {
        return podeUsarApp;
    }

    public void setDiasSubscricao(int diasSubscricao) {
        this.diasSubscricao += diasSubscricao;
    }

    public void setPodeUsarApp(boolean podeUsarApp) {
        this.podeUsarApp = podeUsarApp;
    }
}
