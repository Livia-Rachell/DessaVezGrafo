import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CaixeiroViajante {
    private Grafo grafoViajante;

    public CaixeiroViajante(Grafo grafo) {
        this.grafoViajante = grafo;
    }

    public void resolucao(Grafo grafo) {
        agm(grafo);
    }

    public void agm(Grafo grafo) {
        List<Aresta> arestasAGM = new ArrayList<Aresta>();
        Collections.copy(arestasAGM, grafo.arestas());
        arestasAGM.sort();
    }

}
