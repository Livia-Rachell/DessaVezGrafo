public class MainCarteiro {
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
            CarteiroChines carteiroChines = new CarteiroChines(grafo);
            grafo.imprimirConexoes();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
