import java.util.ArrayList;
import java.util.List;

public class Grafo implements IGrafo {
    private List<Vertice> vertices;
    private List<Aresta> arestas;

    public Grafo() {
        this.vertices = new ArrayList<Vertice>();
        this.arestas = new ArrayList<Aresta>();
    }

    public List<Vertice> finalVertices(Aresta e) {
        List<Vertice> vertices = new ArrayList<Vertice>();
        vertices.add(e.getA());
        vertices.add(e.getB());
        return vertices;
    }

    public Vertice oposto(Vertice v, Aresta e) {
        Vertice oposto = e.getA().equals(v) ? e.getB() : e.getA();
        return oposto;
    }

    public boolean isAdjacente(Vertice v, Vertice w) {
        List<Aresta> arestas = v.getArestas();

        boolean isAdjacente = arestas.stream().anyMatch(a -> {
            return a.conecta(v, w);
        });

        return isAdjacente;
    }

    public void substituir(Vertice v, String w) {
        v.setConteudo(w);
    }

    public void substituir(Aresta e, int w) {
        e.setValor(w);
    }

    public Vertice inserirVertice(String o) {
        Vertice vertice = new Vertice(o);
        this.vertices.add(vertice);
        return vertice;
    }

    public Aresta inserirAresta(Vertice v, Vertice w, int o) {

        if (!vertices.contains(v)) {
            System.out.println("Vértice de valor " + v.getConteudo()
                    + " não está presente no grafo. Adicionando vértice à lista de vértices.");
            this.vertices.add(v);
        }

        if (!vertices.contains(w)) {
            System.out.println("Vértice de valor " + w.getConteudo()
                    + " não está presente no grafo. Adicionando vértice à lista de vértices.");
            this.vertices.add(w);
        }

        Aresta nova = new Aresta(v, w, o);
        this.arestas.add(nova);
        v.addAresta(nova);
        w.addAresta(nova);
        return nova;
    }

    public String removeVertice(Vertice v) {
        List<Aresta> arestas = v.getArestas();
        for (Aresta a : arestas) {
            Vertice oposto = a.getOposto(v);
            oposto.removeAresta(a);
            this.arestas.remove(a);
        }
        return v.getConteudo();
    }

    public int removeAresta(Aresta e) {
        e.getA().removeAresta(e);
        e.getB().removeAresta(e);
        this.arestas.remove(e);
        return e.getValor();
    }

    public List<Aresta> arestasIncidentes(Vertice v) {
        return v.getArestas();
    }

    public List<Vertice> vertices() {
        return this.vertices;
    }

    public List<Aresta> arestas() {
        return this.arestas;
    }

    public boolean isDirecionada(Aresta e) {
        return e.isDirecionada();
    }

    public Aresta inserirArestaDirecionada(Vertice v, Vertice w, int o) {
        Aresta a = new Aresta(v, w, o, true);
        this.arestas.add(a);

        return a;
    }

    public void imprimirConexoes() {
        for (Vertice vertice : vertices) {
            List<Vertice> adjacentes = vertice.getAdjacentes();

            System.out.print("Vertice de índice " + vertices.indexOf(vertice) + " (" + vertice.getConteudo()
                    + ") está conectada com vertices [");

            for (int i = 0; i < adjacentes.size(); i++) {
                Vertice adjacente = adjacentes.get(i);
                System.out.print(vertices.indexOf(adjacente) + " (" + adjacente.getConteudo() + ")");

                if (i < adjacentes.size() - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println("]");
        }
    }

}