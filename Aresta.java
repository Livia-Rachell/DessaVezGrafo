public class Aresta {
    private Vertice a;
    private Vertice b;
    private int valor;
    private boolean direcionada;

    public Aresta(Vertice a, Vertice b, int valor) {
        this.a = a;
        this.b = b;
        this.valor = valor;
        this.direcionada = false;
    }

    public Aresta(Vertice a, Vertice b, int valor, boolean direcionada) {
        this.a = a;
        this.b = b;
        this.valor = valor;
        this.direcionada = direcionada;
    }

    public Vertice getA() {
        return this.a;
    }

    public void setA(Vertice a) {
        this.a = a;
    }

    public Vertice getB() {
        return this.b;
    }

    public void setB(Vertice b) {
        this.b = b;
    }

    public int getValor() {
        return this.valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isDirecionada() {
        return this.direcionada;
    }

    public void setDirecionada(boolean d) {
        this.direcionada = d;
    }

    public boolean conecta(Vertice v, Vertice w) {
        return (this.a.equals(v) && this.b.equals(w)) || (this.a.equals(w) && this.b.equals(v));
    }

    public Vertice getOposto(Vertice v) {
        if (a == v)
            return b;
        else
            return a;
    }

}
