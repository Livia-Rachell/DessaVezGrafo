import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Labirinto {
    private Grafo grafo;
    private Vertice entrada;
    private List<Vertice> saidas;
    private List<Vertice> vertices;
    private int larguraLabirinto;

    public Labirinto() {
        this.grafo = new Grafo();
        this.saidas = new ArrayList<Vertice>();
        this.vertices = new ArrayList<Vertice>();
        this.larguraLabirinto = 0;
    }

    private void conectar(Vertice v, int x, int y, int len, int w) {
        if (x < 0 || x >= len || y < 0)
            return;
        int index = x + y * len;
        Vertice e = vertices.get(index);
        if (!e.getConteudo().equals("1") && !v.getConteudo().equals("1")) {
            grafo.inserirAresta(v, e, w);
        }

    }

    public void lerLabirinto(String labirinto) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(labirinto))) {
            String linha;
            int linhaAtual = 0;

            while ((linha = leitor.readLine()) != null) {
                String[] elementos = linha.split("");
                // Usado mais tarde para medir a heurística para o A Estrela
                this.larguraLabirinto = elementos.length;

                for (int coluna = 0; coluna < elementos.length; coluna++) {
                    String valor = elementos[coluna];
                    Vertice vertice = grafo.inserirVertice(valor);
                    vertices.add(vertice);

                    if (coluna > 0 && coluna < elementos.length - 1) {
                        // Conecta com o da esquerda
                        conectar(vertice, coluna - 1, linhaAtual, elementos.length, 10);
                        // Conecta com o diagonal esquerda
                        conectar(vertice, coluna - 1, linhaAtual - 1, elementos.length, 14);
                        // Conecta com o de cima
                        conectar(vertice, coluna, linhaAtual - 1, elementos.length, 10);
                        // Conecta com o da direita
                        conectar(vertice, coluna + 1, linhaAtual - 1, elementos.length, 14);
                    }
                    if (valor.equals("2")) {
                        this.entrada = vertice;
                    } else if (valor.equals("3")) {
                        this.saidas.add(vertice);
                    }
                }

                linhaAtual++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public Vertice getEntrada() {
        return entrada;
    }

    public List<Vertice> getSaidas() {
        return saidas;
    }

    public void dijkstra() {
        for (Vertice vertice : vertices) {
            vertice.setVisitado(false);
            vertice.setDistanciaDaOrigem(Integer.MAX_VALUE);
        }

        entrada.setDistanciaDaOrigem(0);

        PriorityQueue<Vertice> filaDePrioridade = new PriorityQueue<>(
                (v1, v2) -> Integer.compare(v1.getDistanciaDaOrigem(), v2.getDistanciaDaOrigem()));
        filaDePrioridade.add(entrada);

        while (!filaDePrioridade.isEmpty()) {
            Vertice atual = filaDePrioridade.poll();

            if (!atual.isVisitado()) {
                if (atual.equals(saidas.get(0))) {
                    // Chegou ao destino
                    break;
                }
                atual.setVisitado(true);

                for (Aresta aresta : grafo.arestasIncidentes(atual)) {
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

    /*
     * O aEstrela só consegue trabalhar com uma saída. Pois ele "mira" por onde quer
     * sair. O Dijkstra consegue encontrar o menor caminho até qualquer saída
     * existente.
     * 
     * Para esse exemplo foi sempre usada a primeira saída na busca linear
     * do arquivo labirinto.txt para manter a comparação de ambos algorítmos justa
     */
    public void aEstrela() {
        for (Vertice vertice : vertices) {
            vertice.setVisitado(false);
            vertice.setDistanciaDaOrigem(Integer.MAX_VALUE);
        }

        entrada.setDistanciaDaOrigem(0);
        entrada.setAntecessor(null);

        PriorityQueue<Vertice> filaDePrioridade = new PriorityQueue<>((v1, v2) -> {
            int f1 = v1.getDistanciaDaOrigem() + heuristica(v1);
            int f2 = v2.getDistanciaDaOrigem() + heuristica(v2);
            return Integer.compare(f1, f2);
        });

        filaDePrioridade.add(entrada);

        while (!filaDePrioridade.isEmpty()) {
            Vertice atual = filaDePrioridade.poll();

            if (atual.equals(saidas.get(0))) {
                // Chegou ao destino
                break;
            }

            if (!atual.isVisitado()) {
                atual.setVisitado(true);

                for (Aresta aresta : grafo.arestasIncidentes(atual)) {
                    Vertice vizinho = aresta.getOposto(atual);
                    if (!vizinho.isVisitado()) {
                        int novaDistancia = atual.getDistanciaDaOrigem() + aresta.getValor();
                        if (novaDistancia < vizinho.getDistanciaDaOrigem()) {
                            vizinho.setDistanciaDaOrigem(novaDistancia);
                            vizinho.setAntecessor(atual);
                            filaDePrioridade.add(vizinho);
                        }
                    }
                }
            }
        }
    }

    private int heuristica(Vertice v) {
        // Distância euclidiana até a primeira saída
        double distanciaEuclidiana = Math
                .sqrt(Math.pow(v.getId() % larguraLabirinto - saidas.get(0).getId() % larguraLabirinto, 2)
                        + Math.pow(v.getId() / larguraLabirinto - saidas.get(0).getId() / larguraLabirinto, 2));
        return (int) distanciaEuclidiana;
    }

    public void imprimirMenorCaminho() {
        for (Vertice saida : saidas) {
            if (Integer.valueOf(saida.getDistanciaDaOrigem()).equals(Integer.MAX_VALUE))
                continue;
            System.out.println("Menor caminho até a saída em " + saida.getConteudo() + ":");
            imprimirCaminho(saida);
            System.out.println("Distância: " + saida.getDistanciaDaOrigem());
            System.out.println();
        }
    }

    private void imprimirCaminho(Vertice destino) {
        if (destino.getDistanciaDaOrigem() == Integer.MAX_VALUE) {
            System.out.println("Não há caminho até este destino.");
            return;
        }

        Vertice atual = destino;
        List<Vertice> caminho = new ArrayList<>();

        while (atual != null) {
            caminho.add(atual);
            atual = atual.getAntecessor();
        }

        Collections.reverse(caminho);

        for (int i = 0; i < caminho.size(); i++) {
            System.out.print(caminho.get(i).getConteudo());
            if (i < caminho.size() - 1) {
                System.out.print(" -> ");
            }
        }

        System.out.println();
    }
}
