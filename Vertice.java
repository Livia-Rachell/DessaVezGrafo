import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private int id;
    private String conteudo;
    private List<Aresta> arestas;
    private boolean visitado;
    private int distanciaDaOrigem;
    private Vertice antecessor;

    public Vertice(String conteudo) {
        this.conteudo = conteudo;
        this.arestas = new ArrayList<Aresta>();
        this.visitado = false;
        this.distanciaDaOrigem = Integer.MAX_VALUE;
        this.antecessor = null;
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

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public int getDistanciaDaOrigem() {
        return distanciaDaOrigem;
    }

    public void setDistanciaDaOrigem(int distanciaDaOrigem) {
        this.distanciaDaOrigem = distanciaDaOrigem;
    }

    public Vertice getAntecessor() {
        return antecessor;
    }

    public void setAntecessor(Vertice caminhoAnterior) {
        this.antecessor = caminhoAnterior;
    }

    public Aresta getArestaDoOposto(Vertice v) {
        for (Aresta aresta : arestas) {
            if (aresta.getOposto(this) == v)
                return aresta;
        }
        return null;
    }
}
