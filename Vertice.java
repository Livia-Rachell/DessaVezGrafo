import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private int id;
    private String conteudo;
    private List<Aresta> arestas;

    public Vertice(String conteudo) {
        this.conteudo = conteudo;
        this.arestas = new ArrayList<Aresta>();
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

    public void addAresta(Aresta aresta) {
        this.arestas.add(aresta);
    }

    public void removeAresta(Aresta aresta) {
        this.arestas.remove(aresta);
    }

    public List<Vertice> getAdjacentes() {
        List<Vertice> adjacentes = new ArrayList<Vertice>();
        for (int i = 0; i < this.arestas.size(); i++) {
            adjacentes.add(this.arestas.get(i).getOposto(this));
        }
        return adjacentes;
    }
}