public class Programador extends User
{
    double mediaAvaliacoes, dinheiroRecebido;
    
    public Programador(String nome, int idade, boolean isPremium, boolean isProgrammer){
        super(nome, idade, isPremium, isProgrammer);
        mediaAvaliacoes = 0;
        dinheiroRecebido = 0;
    }
    
    public double getMediaAvaliacoes()
    {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes)
    {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }
    
    public double getDinheiroRecebido() {
        return dinheiroRecebido;
    }
    
    public void setDinheiroRecebido(int dinheiroRecebido) {
        this.dinheiroRecebido += dinheiroRecebido;
    }
    
    // MÉTODO toString
    public String toString() {
        String texto;
        texto = super.toString() + "  AVAL: " + mediaAvaliacoes;
        return texto;
    }
    
    public void obterApps()
    {

    }

    public void inserirApps()
    {

    }

}
