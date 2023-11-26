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
        Aresta j, k, l, m, n;
        j = arestas.get(0);
        k = arestas.get(1);
        l = arestas.get(2);
        m = arestas.get(3);
        n = arestas.get(4);

        for (Aresta aresta : grafo.arestas()) {
            String valorVerticeA = aresta.getA().getConteudo();
            String valorVerticeB = aresta.getB().getConteudo();

            System.out.println("A aresta de valor " + aresta.getValor() + " que conecta [" + valorVerticeA + "] com ["
                    + valorVerticeB + "] " + (grafo.isDirecionada(aresta) ? " é " : " não é ") + " direcionada.");

        }

        grafo.removeVertice(a);
        System.out.println("----------------- Removendo vértice A ----------------------------");
        for (Aresta e : grafo.arestas()) {
            String valorVerticeA = e.getA().getConteudo();
            String valorVerticeB = e.getB().getConteudo();

            System.out.println("A aresta de valor " + e.getValor() + " que conecta [" + valorVerticeA + "] com ["
                    + valorVerticeB + "] " + (grafo.isDirecionada(e) ? " é " : " não é ") + " direcionada.");
        }

        grafo.removeAresta(n);

        System.out.println("----------------- Removendo aresta C -> D ------------------------");
        for (Aresta e : grafo.arestas()) {
            String valorVerticeA = e.getA().getConteudo();
            String valorVerticeB = e.getB().getConteudo();

            System.out.println("A aresta de valor " + e.getValor() + " que conecta [" + valorVerticeA + "] com ["
                    + valorVerticeB + "] " + (grafo.isDirecionada(e) ? " é " : " não é ") + " direcionada.");
        }
    }
}
