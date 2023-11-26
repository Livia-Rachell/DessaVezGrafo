import java.util.List;

public class Vertice {
    private int id;
    private String conteudo;
    private List<Aresta> arestas;

    public Vertice(String conteudo) {
        this.conteudo = conteudo;
    }

    public Vertice(String conteudo, List<Aresta> arestas) {
        this.conteudo = conteudo;
        this.arestas = arestas;
    }

    public int getId() {
        return this.id;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public List<Aresta> getArestas() {
        return this.arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }
}
