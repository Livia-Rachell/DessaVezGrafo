import java.util.List;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        /*
         * Grafo de exemplo:
         * A -- B
         * | ./ |
         * | /. |
         * C -> D
         */

        grafo.inserirVertice("A");
        grafo.inserirVertice("B");
        grafo.inserirVertice("C");
        grafo.inserirVertice("D");

        List<Vertice> vertices = grafo.vertices();

        Vertice a, b, c, d;
        a = vertices.get(0);
        b = vertices.get(1);
        c = vertices.get(2);
        d = vertices.get(3);

        grafo.inserirAresta(a, b, 10);
        grafo.inserirAresta(b, d, 10);
        grafo.inserirAresta(a, c, 10);
        grafo.inserirAresta(c, b, 10);
        grafo.inserirArestaDirecionada(c, d, 10);

        List<Aresta> arestas = grafo.arestas();

        for (Aresta e : arestas) {
            String valorVerticeA = e.getA().getConteudo();
            String valorVerticeB = e.getB().getConteudo();

            System.out.println("A aresta de valor " + e.getValor() + " que conecta [" + valorVerticeA + "] com ["
                    + valorVerticeB + "] " + (grafo.isDirecionada(e) ? " é " : " não é ") + " direcionada.");

        }

    }
}
