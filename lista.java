import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CaixeiroViajante {
    private Grafo garfoViajante;

    public CaixeiroViajante(Grafo grafo) {
        this.garfoViajante = grafo;
    }

    public void resolucao() throws Exception {
        List<Vertice> verticesImpares = new ArrayList<Vertice>(verticesImpares(garfoViajante));
        if (verticesImpares.size() % 2 == 0) {
            eulerizar(garfoViajante, verticesImpares);
            fleury(garfoViajante);
        } else if (verticesImpares.size() == 0) {
            System.out.println("O Grafo é Euleriano");
            fleury(garfoViajante);
        } else
            throw new Exception("O Grafo não é Eulerizável, coleguinha!");
    }

    private List<Vertice> verticesImpares(Grafo garfoViajante) {
        List<Vertice> vertices = garfoViajante.vertices();
        int qtdVertices = vertices.size(); // numero de linhas da matriz;
        List<Vertice> verticesImpares = new ArrayList<Vertice>();
        int qtdArestas = 0;

        for (int i = 0; i < qtdVertices; i++) {
            qtdArestas = vertices.get(i).getArestas().size();
            if (qtdArestas % 2 == 1) {
                verticesImpares.add(vertices.get(i));
            }
        }
        return verticesImpares;
    }

    private void eulerizar(Grafo garfoViajante, List<Vertice> verticesImpares) {
        while (!verticesImpares.isEmpty()) {
            int distancias[][] = matrizDistancias(garfoViajante, verticesImpares);
            caminhoArtificial(distancias, garfoViajante, verticesImpares);
        }
    }

    private List<Vertice> caminhoArtificial(int[][] distancias, Grafo grafoAuxiliar, List<Vertice> verticesImpares) {
        int menorCaminho = Integer.MAX_VALUE;
        Vertice v1 = null;
        Vertice v2 = null;

        for (int i = 0; i < verticesImpares.size(); i++) {
            for (int j = 0; j < verticesImpares.size(); j++) {
                if (distancias[i][j] != 0 && distancias[i][j] < menorCaminho) {
                    menorCaminho = distancias[i][j];
                    v1 = verticesImpares.get(i);
                    v2 = verticesImpares.get(j);
                }
            }
        }
        grafoAuxiliar.dijkstra(v1, v2);
        List<Vertice> caminho = new ArrayList<Vertice>(grafoAuxiliar.getCaminho(v2));
        Collections.reverse(caminho);
        for (int i = 0; i < caminho.size() - 1; i++) {
            int valor = caminho.get(i).getArestaDoOposto(caminho.get(i + 1)).getValor();
            grafoAuxiliar.inserirAresta(caminho.get(i), caminho.get(i + 1), valor);
        }
        verticesImpares.remove(v1);
        verticesImpares.remove(v2);
        return verticesImpares;
    }

    private void fleury(Grafo garfoViajante) {
        Grafo grafoAuxiliar = garfoViajante;
        Vertice v = grafoAuxiliar.vertices().get(0);
        Stack<Vertice> pilha = new Stack<Vertice>();
        List<Vertice> circuito = new ArrayList<Vertice>();
        List<Aresta> circuites = new ArrayList<Aresta>();

        pilha.push(v);
        while (!pilha.isEmpty()) {
            v = pilha.peek();
            List<Vertice> adjacentes = v.getAdjacentes();

            if (adjacentes.isEmpty()) {
                circuito.add(v);
                pilha.pop();
            } else {
                Vertice adjacente = adjacentes.get(0);
                circuites.add(v.getArestaDoOposto(adjacente));
                grafoAuxiliar.removeAresta(v.getArestaDoOposto(adjacente));
                pilha.push(adjacente);
            }
        }

        Collections.reverse(circuito);
        for (int i = 0; i < circuito.size(); i++) {
            System.out.println("Circuito Euleriano: " + circuito.get(i).getConteudo());
        }
        for (int i = 0; i < circuites.size(); i++) {
            System.out.println("circuites Euleriano: " + circuites.get(i).getValor());
        }
    }
}

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

public class MainCaixeiro {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        Vertice a = grafo.inserirVertice("a");
        Vertice b = grafo.inserirVertice("b");
        Vertice c = grafo.inserirVertice("c");
        Vertice d = grafo.inserirVertice("d");
        Vertice e = grafo.inserirVertice("e");
        Vertice f = grafo.inserirVertice("f");
        Aresta ab = grafo.inserirAresta(a, b, 10);
        Aresta ae = grafo.inserirAresta(a, e, 8);
        Aresta af = grafo.inserirAresta(a, f, 3);
        Aresta bc = grafo.inserirAresta(b, c, 3);
        Aresta bd = grafo.inserirAresta(b, d, 3);
        Aresta be = grafo.inserirAresta(b, e, 2);
        Aresta cd = grafo.inserirAresta(c, d, 7);
        Aresta de = grafo.inserirAresta(d, e, 6);
        Aresta ef = grafo.inserirAresta(e, f, 4);

        try {
            CaixeiroViajante caixeiroViajante = new CaixeiroViajante(grafo);
            caixeiroViajante.resolucao();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
