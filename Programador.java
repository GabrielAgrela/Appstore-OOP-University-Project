public class Programador extends User
{
    private double mediaAvaliacoes, dinheiroRecebido;
    
    public Programador(String nome, int idade, boolean isPremium, boolean isProgrammer, double total){
        super(nome, idade, isPremium, isProgrammer,total);
        mediaAvaliacoes = 0;
        dinheiroRecebido = total;
    }
    
    public double getMediaAvaliacoes()
    {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes)
    {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public void setDinheiroRecebido(double total)
    {
        this.dinheiroRecebido = this.dinheiroRecebido + total;
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
    
    public void obterApps(){

    }

    public void inserirApps(){

    }
}
