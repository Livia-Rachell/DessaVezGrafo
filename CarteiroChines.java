import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CarteiroChines {
    private Grafo grafoCarteiro;

    public CarteiroChines(Grafo grafo) throws Exception {
        this.grafoCarteiro = grafo;
        resolucao(grafoCarteiro);
    }

    private void resolucao(Grafo grafoCarteiro) throws Exception {
        List<Vertice> verticesImpares = new ArrayList<Vertice>(verticesImpares(grafoCarteiro));
        if (verticesImpares.size() % 2 == 0) {
            eulerizar(grafoCarteiro, verticesImpares);
            fleury(grafoCarteiro);
        } else if (verticesImpares.size() == 0) {
            System.out.println("O Grafo é Euleriano");
            fleury(grafoCarteiro);
        } else
            throw new Exception("O Grafo não é Eulerizável, coleguinha!");
    }

    private List<Vertice> verticesImpares(Grafo grafoCarteiro) {
        List<Vertice> vertices = grafoCarteiro.vertices();
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

    private void eulerizar(Grafo grafoCarteiro, List<Vertice> verticesImpares) {
        while (!verticesImpares.isEmpty()) {
            int distancias[][] = matrizDistancias(grafoCarteiro, verticesImpares);
            verticesImpares = emparelhamentoPerfeiro(distancias, verticesImpares);
        }
    }

    private int[][] matrizDistancias(Grafo grafoCarteiro, List<Vertice> verticesImpares) {
        int distancias[][] = new int[verticesImpares.size()][verticesImpares.size()];
        for (int i = 0; i < verticesImpares.size(); i++) {
            for (int j = 0; j < verticesImpares.size(); j++) {
                grafoCarteiro.dijkstra(verticesImpares.get(i), verticesImpares.get(j));
                int menorCaminho = verticesImpares.get(j).getDistanciaDaOrigem();
                distancias[i][j] = menorCaminho;
            }
        }

        return distancias;
    }

    private List<Vertice> emparelhamentoPerfeiro(int[][] distancias, List<Vertice> verticesImpares) {
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
        grafoCarteiro.inserirAresta(v1, v2, menorCaminho);
        verticesImpares.remove(v1);
        verticesImpares.remove(v2);
        return verticesImpares;
    }

    private void fleury(Grafo grafoCarteiro) {
        Grafo grafoAuxiliar = grafoCarteiro;
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
