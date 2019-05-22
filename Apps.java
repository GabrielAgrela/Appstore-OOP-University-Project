public class Apps
{
    private static int numApps; // numero total de apps
    private String nome;
    private double preco;
    private int idProgrammer;
    private int idApp;
    private int numAvs;
    private String categoria;
    private boolean temSubscricao;
    private double avaliacaoApp;
    private double mediaAvaliacaoApp;
    private boolean descontoMensal;
    private boolean descontoMenosCompras;
    private boolean descontoTotal;
    private int numCompras;

    public Apps(String nome, double preco, String categoria, boolean temSubscricao, int idProgrammer)
    {
        this.nome = nome;
        this.preco = preco;
        idApp = numApps;
        this.categoria = categoria;
        this.temSubscricao = temSubscricao;
        this.idProgrammer = idProgrammer;
        avaliacaoApp=0;
        descontoMensal = false;
        descontoMenosCompras = false;
        descontoTotal = false;
        numCompras = 0;
        numApps++;
    }
    
    public Apps(String nome, double preco, String categoria, boolean temSubscricao, int idProgrammer, double avaliacaoApp, int numAvs)
    {
        this.nome = nome;
        this.preco = preco;
        this.numAvs = numAvs;
        idApp = numApps;
        this.categoria = categoria;
        this.temSubscricao = temSubscricao;
        this.idProgrammer = idProgrammer;
        this.avaliacaoApp=avaliacaoApp;
        descontoMensal = false;
        descontoMenosCompras = false;
        descontoTotal = false;
        numCompras = 0;
        numApps++;
    }
    public String getNome()
    {
        return nome;
    }


    public double getPreco()
    {
        return preco;
    }

    public int getIdProgrammer()
    {
        return idProgrammer;
    }

    public int getIdApp()
    {
        return idApp;
    }

    public int getNumAvs()
    {
        return numAvs;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public int getNumCompras()
    {
        return numCompras;
    }
    
    public boolean isTemSubscricao()
    {
        return temSubscricao;
    }

    public double getAvaliacaoApp()
    {
        return avaliacaoApp;
    }

    public double getMediaAvaliacaoApp()
    {
        doAvg();
        return mediaAvaliacaoApp;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public void setNumAvs()
    {
        numAvs++;
    }
    public void setPreco(double preco)
    {
        this.preco = preco;
    }

    public void setIdProgrammer(int idProgrammer)
    {
        this.idProgrammer = idProgrammer;
    }

    public void setIdApp(int idApp)
    {
        this.idApp = idApp;
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    public void setTemSubscricao(boolean temSubscricao)
    {
        this.temSubscricao = temSubscricao;
    }

    public void setAvaliacaoApp(double avaliacaoApp)
    {
        this.avaliacaoApp = this.avaliacaoApp + avaliacaoApp;
        doAvg();
    }

    private void doAvg() {
        mediaAvaliacaoApp= avaliacaoApp/(numAvs);
    }

    public boolean isDescontoMensal() {
        return descontoMensal;
    }

    public void setDescontoMensal(boolean descontoMensal) {
        this.descontoMensal = descontoMensal;
    }

    public boolean isDescontoMenosCompras() {
        return descontoMenosCompras;
    }

    public void setDescontoMenosCompras(boolean descontoMenosCompras) {
        this.descontoMenosCompras = descontoMenosCompras;
    }

    public boolean isDescontoTotal() {
        return descontoTotal;
    }

    public void setDescontoTotal(boolean descontoTotal) {
        this.descontoTotal = descontoTotal;
    }
    
    public void incrementaNumCompras(){
        numCompras++;
    }
}