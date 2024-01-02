import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class CarteiroChines {
    private Grafo grafoCarteiro;

    public CarteiroChines(Grafo grafo) {
        this.grafoCarteiro = grafo;
    }

    public void resolucao() throws Exception {
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
            caminhoArtificial(distancias, grafoCarteiro, verticesImpares);
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

    private void fleury(Grafo grafoCarteiro) {
        Grafo grafoAuxiliar = grafoCarteiro;
        Vertice v = grafoAuxiliar.vertices().get(0);
        List<Vertice> circuito = new ArrayList<Vertice>();
        List<Aresta> arestasCircuito = new ArrayList<Aresta>();
        circuito.add(v);

        while (grafoAuxiliar.arestas().size() > 0) {

            Iterator<Vertice> it = v.getAdjacentes().iterator();

            while (it.hasNext()) {
                Vertice oposto = it.next();
                Aresta e = v.getArestaDoOposto(oposto);

                if (grafoAuxiliar.isPonte(e)) 
                    continue;

                arestasCircuito.add(e);
                grafoAuxiliar.removeAresta(e);

                v = oposto;
                circuito.add(v);
                break;
            }

        }

        Collections.reverse(circuito);
        System.out.print("Circuito Euleriano: \n*");
        for (int i = 0; i < circuito.size(); i++) {
            System.out.print(" -> " + circuito.get(i).getConteudo());
        }
        System.out.println();
        System.out.print("Circuito Euleriano: \n*");
        for (int i = 0; i < arestasCircuito.size(); i++) {
            System.out.print(" -> " + arestasCircuito.get(i).getValor());
        }
        System.out.println();
    }

}
