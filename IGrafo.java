import java.util.List;

interface IGrafo {
    List<Vertice> finalVertices(Aresta e);

    Vertice oposto(Vertice v, Aresta e);

    boolean isAdjacente(Vertice v, Vertice w);

    void substituir(Vertice v, String w);

    void substituir(Aresta e, int w);

    void inserirVertice(String o);

    Aresta inserirAresta(Vertice v, Vertice w, int o);

    int removeVertice(Vertice v);

    int removeAresta(Aresta e);

    List<Aresta> arestasIncidentes(Vertice v);

    List<Vertice> vertices();

    List<Aresta> arestas();

    boolean isDirecionada(Aresta e);

    Aresta inserirArestaDirecionada(Vertice v, Vertice w, int o);

}