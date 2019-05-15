public class Apps
{
    String nome;
    double preco;
    int idProgrammer;
    int idApp;
    String categoria;
    boolean temSubscricao;
    double avaliacaoApp;

    public Apps(String nome, double preco, String categoria, boolean temSubscricao, int idProgrammer)
    {
        this.nome = nome;
        this.preco = preco;
        idApp++;
        this.categoria = categoria;
        this.temSubscricao = temSubscricao;
        this.idProgrammer = idProgrammer;
        avaliacaoApp=0;
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

    public String getCategoria()
    {
        return categoria;
    }

    public boolean isTemSubscricao()
    {
        return temSubscricao;
    }

    public double getAvaliacaoApp()
    {
        return avaliacaoApp;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
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
        this.avaliacaoApp = avaliacaoApp;
    }
}
