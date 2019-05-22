public class Classificacao
{

    private static int numClassificacoes;
    private int idCompra;
    private int idClassificacao;
    private int idApp;
    private int idUser;
    private float classificacao;
    private String comentario;

    public Classificacao(int idCompra, int idApp,float classificacao,String comentario,int idUser) {
        idClassificacao = numClassificacoes;
        this.idCompra = idCompra;
        this.idApp = idApp;
        this.classificacao = classificacao;
        this.comentario = comentario;
        this.idUser = idUser;
        numClassificacoes++;
    }
    
    private void alteraClassificacaoApp(int idClassificacao) {
        return ;
    }

    private void alteraClassificacaoProgramador(int classificacao) {
        return ;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public int getIdClassificacao() {
        return idClassificacao;
    }

    public String getComentario() {
        return comentario;
    }

    public int getIdApp() {
        return idApp;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public void setIdClassificacao(int idClassificacao) {
        this.idClassificacao = idClassificacao;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public float getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public static int getNumClassificacoes() {
        return numClassificacoes;
    }

    public static void setNumClassificacoes(int aNumClassificacoes) {
        numClassificacoes = aNumClassificacoes;
    }
}
