import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Labirinto {
    private Grafo grafo;
    private Vertice entrada;
    private List<Vertice> saidas;

    public Labirinto() {
        this.grafo = new Grafo();
        this.saidas = new ArrayList<Vertice>();
    }

    private int getIndex(int x, int y, int len) {
        return x + y * len;
    }

    public void lerLabirinto(String labirinto) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(labirinto))) {
            String linha;
            String[] linhaAnterior = null;
            int linhaAtual = 0;
            List<Vertice> vertices = new ArrayList<Vertice>();

            while ((linha = leitor.readLine()) != null) {
                String[] elementos = linha.split("");

                for (int coluna = 0; coluna < elementos.length; coluna++) {
                    String valor = elementos[coluna];
                    Vertice vertice = grafo.inserirVertice(valor);
                    vertices.add(vertice);
                    if (valor == "1")
                        continue;

                    if (linhaAnterior != null && coluna > 0 && coluna < elementos.length - 1) {
                        int index = getIndex(coluna - 1, linhaAtual - 1, elementos.length);
                        Vertice cimaEsquerda = vertices.get(index);
                        if (cimaEsquerda.getConteudo() != "1") {
                            grafo.inserirAresta(vertice, cimaEsquerda, 14);
                        }

                        index = getIndex(coluna, linhaAtual - 1, elementos.length);
                        Vertice cima = vertices.get(index);
                        if (cima.getConteudo() != "1") {
                            grafo.inserirAresta(vertice, cima, 10);
                        }

                        index = getIndex(coluna + 1, linhaAtual - 1, elementos.length);
                        Vertice direitaCima = vertices.get(index);
                        if (direitaCima.getConteudo() != "1") {
                            grafo.inserirAresta(vertice, direitaCima, 14);
                        }

                        index = getIndex(coluna - 1, linhaAtual, elementos.length);
                        Vertice esquerda = vertices.get(index);
                        if (esquerda.getConteudo() != "1") {
                            grafo.inserirAresta(vertice, esquerda, 10);
                        }
                    }
                    if (valor == "2") {
                        this.entrada = vertice;
                    } else if (valor == "3") {
                        this.saidas.add(vertice);
                    }
                }

                linhaAtual++;
                linhaAnterior = elementos;
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

}
