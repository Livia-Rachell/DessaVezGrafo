import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Labirinto {
    private Grafo grafo;
    private Vertice entrada;
    private List<Vertice> saidas;
    private List<Vertice> vertices;

    public Labirinto() {
        this.grafo = new Grafo();
        this.saidas = new ArrayList<Vertice>();
        this.vertices = new ArrayList<Vertice>();
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
                    if (valor == "2") {
                        this.entrada = vertice;
                    } else if (valor == "3") {
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

}
