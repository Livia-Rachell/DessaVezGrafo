import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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

    public void dijkstra(Vertice inicio, Vertice fim) {
        for (Vertice vertice : vertices) {
            vertice.setVisitado(false);
            vertice.setDistanciaDaOrigem(Integer.MAX_VALUE);
        }

        inicio.setDistanciaDaOrigem(0);
        inicio.setAntecessor(null);

        PriorityQueue<Vertice> filaDePrioridade = new PriorityQueue<>(
                (v1, v2) -> Integer.compare(v1.getDistanciaDaOrigem(), v2.getDistanciaDaOrigem()));
        filaDePrioridade.add(inicio);

        while (!filaDePrioridade.isEmpty()) {
            Vertice atual = filaDePrioridade.poll();

            if (!atual.isVisitado()) {
                if (atual.equals(fim)) {
                    // Chegou ao destino
                    break;
                }
                atual.setVisitado(true);

                for (Aresta aresta : this.arestasIncidentes(atual)) {
                    Vertice vizinho = aresta.getOposto(atual);
                    if (!vizinho.isVisitado()) {
                        int novaDistancia = atual.getDistanciaDaOrigem() + aresta.getValor();
                        if (novaDistancia < vizinho.getDistanciaDaOrigem()) {
                            vizinho.setDistanciaDaOrigem(novaDistancia);
                            filaDePrioridade.add(vizinho);
                            vizinho.setAntecessor(atual);
                        }
                    }
                }
            }
        }
    }

    public List<Vertice> getCaminho(Vertice destino) {
        if (destino.getDistanciaDaOrigem() == Integer.MAX_VALUE) {
            System.out.println("Não há caminho até este destino.");
            return null;
        }

        Vertice atual = destino;
        List<Vertice> caminho = new ArrayList<>();

        do {
            caminho.add(atual);
            atual = atual.getAntecessor();
        } while (atual != null);

        Collections.reverse(caminho);
        return caminho;
    }

}