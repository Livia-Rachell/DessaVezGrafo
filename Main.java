public class Main {
    public static void main(String[] args) {

        Labirinto labirinto = new Labirinto();
        labirinto.lerLabirinto("labirinto.txt");

        System.out.println("Dijkstra: ");
        long inicioDijkstra = System.currentTimeMillis();
        labirinto.dijkstra();
        long fimDijkstra = System.currentTimeMillis();
        labirinto.imprimirMenorCaminho();
        System.out.println("Tempo gasto pelo Dijkstra: " + (fimDijkstra - inicioDijkstra) + " ms");

        System.out.println("\nA Estrela: ");
        long inicioAEstrela = System.currentTimeMillis();
        labirinto.aEstrela();
        long fimAEstrela = System.currentTimeMillis();
        labirinto.imprimirMenorCaminho();
        System.out.println("Tempo gasto pelo A*: " + (fimAEstrela - inicioAEstrela) + " ms");
    }
}
