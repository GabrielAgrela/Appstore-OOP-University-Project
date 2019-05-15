public class Classificacao
{
    int idCompra;
    int idClassificacao;
    int idApp;
    int idUser;
    int IdClassificacao;
    String comentario;

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
}
