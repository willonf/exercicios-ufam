package graphs;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GrafoListaAdjacencias {
    private Vertice[] vertices;
    private int numVertices;
    private int numArestas;


    public GrafoListaAdjacencias(int numVertices) {
        this.numVertices = numVertices;
        this.numArestas = 0;
        this.vertices = new Vertice[numVertices];

        for (int i = 0; i < numVertices; i++) {
            vertices[i] = new Vertice(i);
        }
    }

    public Vertice getVertice(int v) {
        if (v < 0 || v >= numVertices) {
            throw new IndexOutOfBoundsException();
        }
        return vertices[v];
    }

    public void insereAresta(int v1, int v2, int peso) {
        if (v1 < 0 || v1 >= numVertices || v2 < 0 || v2 >= numVertices) {
            throw new IndexOutOfBoundsException();
        }

        vertices[v1].insereVizinho(vertices[v2], peso);
        numArestas++;
    }

    public void insereArestaSimetrica(int v1, int v2, int peso) {
        this.insereAresta(v1, v2, peso);
        this.insereAresta(v2, v1, peso);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < numVertices; i++) {
            result = result.concat(vertices[i] + "\n");
        }
        return result;
    }

    public int existeAresta(int v1, int v2) {
        if (v1 < 0 || v1 >= numVertices || v2 < 0 || v2 >= numVertices) {
            throw new IndexOutOfBoundsException();
        }

        VerticeAdjacente v = vertices[v1].buscaVizinho(vertices[v2]);

        return v != null ? v.getPeso() : -1;
    }

    public void removeAresta(int v1, int v2) {
        if (v1 < 0 || v1 >= numVertices || v2 < 0 || v2 >= numVertices) {
            throw new IndexOutOfBoundsException();
        }

        vertices[v1].removeVizinho(vertices[v2]);
        numVertices--;
    }

    public void removeArestaSimetrica(int v1, int v2) {
        removeAresta(v1, v2);
        removeAresta(v2, v1);
    }

    public int[] caminhoGuloso(int origem, int destino) {
        int tamCaminho = 0;
        int[] caminho = new int[this.getNumVertices()];
        boolean[] visitado = new boolean[this.getNumVertices()];
        Vertice vDestino = this.getVertice(destino);
        Vertice vizinho;

        for (int i = 0; i < this.getNumVertices(); i++) {
            caminho[i] = -1;
            visitado[i] = false;
        }

        caminho[tamCaminho++] = origem;
        Vertice noAtual = this.getVertice(origem);

        while (noAtual.getId() != vDestino.getId()) {
            vizinho = this.buscarMelhorVizinho(noAtual, visitado, destino);
            if (vizinho == null) {
                return null;
            }
            caminho[tamCaminho++] = vizinho.getId();
            visitado[vizinho.getId()] = true;
            noAtual = vizinho;
        }
        return caminho;
    }

    public Vertice buscarMelhorVizinho(Vertice noAtual, boolean[] visitado, int destino) {
        VerticeAdjacente vizinhos;
        Vertice vizinho;

        double xDestino = this.getVertice(destino).getX();
        double yDestino = this.getVertice(destino).getY();

        Vertice melhorVizinho = null;
        double dist;
        double menorDistancia = Double.MAX_VALUE;

        vizinhos = noAtual.getVizinhos();
        do {
            if (!visitado[vizinhos.getVertice().getId()]) {
                vizinho = vizinhos.getVertice();
                double xAtual = vizinho.getX();
                double yAtual = vizinho.getY();
                dist = Math.sqrt(Math.pow(xAtual - xDestino, 2) + Math.pow(yAtual - yDestino, 2));
                if (dist < menorDistancia) {
                    melhorVizinho = vizinho;
                    menorDistancia = dist;
                }
                System.out.println(menorDistancia);
            }
            vizinhos = vizinhos.getProx();
        } while (vizinhos != null);
        return melhorVizinho;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getNumArestas() {
        return numArestas;
    }

    // Busca em largura
    void bfs(int vInicial) { // BFS  >  Breadth-First Search
        int[] cor = new int[numVertices]; // branco=0, cinza=1, preto=2
        int[] dist = new int[numVertices]; // distância até o nó
        int[] pred = new int[numVertices]; // predecessor do nó

        Queue<Integer> fila = new LinkedList<Integer>();

        cor[vInicial] = 1;
        dist[vInicial] = 0;
        pred[vInicial] = -1;

        fila.add(vInicial);

        while (!fila.isEmpty()) {
            int u = fila.remove();

            for (VerticeAdjacente va = vertices[u].getVizinhos(); va != null; va = va.getProx()) {
                int v = va.getVertice().getId();

                if (cor[v] == 0) {
                    System.out.println("Nó " + v + " ficou cinza...");
                    cor[v] = 1;
                    dist[v] = dist[u] + 1;
                    pred[v] = u;
                    fila.add(v);
                }
            }
            System.out.println("Nó " + u + " ficou preto...");
            cor[u] = 2;
        }
    }


    // Busca em profundidade
    int tempo = 0;

    public void dfs(int vInicial) { // DFS > Depth-First Search
        int[] cor = new int[numVertices]; // branco=0, cinza=1, preto=2
        int[] pred = new int[numVertices]; // predecessor do nó
        int[] tDesc = new int[numVertices]; // tempo de descoberta
        int[] tFim = new int[numVertices]; // tempo de término
        tempo = 0;
        dfsVisita(vInicial, cor, pred, tDesc, tFim);
    }

    public void dfsVisita(int u, int[] cor, int[] pred, int[] tDesc, int[] tFim) {
        cor[u] = 1;
        tDesc[u] = ++tempo;
        System.out.println("No " + u + " ficou cinza no tempo " + tempo + " ...");

        for (VerticeAdjacente va = vertices[u].getVizinhos(); va != null; va = va.getProx()) {
            int v = va.getVertice().getId();
            if (cor[v] == 0) {
                pred[v] = u;
                dfsVisita(v, cor, pred, tDesc, tFim);
            }
        }

        cor[u] = 2;
        tFim[u] = ++tempo;
        System.out.println("No " + u + " ficou preto no tempo  " + tempo + " ...");
    }

    //Algoritmmo de Djikstra
    public int[] caminhoMinimoDijkstra(int source) { // source = id da origem
        int[] prev = new int[numVertices]; // predecessor do nó
        int[] dist = new int[numVertices]; // distância até o nó
        int alt;
        PriorityQueue<Integer> Q = new PriorityQueue<Integer>();

        for (Vertice v : vertices) {
            if (v.getId() != source) {
                dist[v.getId()] = Integer.MAX_VALUE;
                prev[v.getId()] = -1;
                Q.add(v.getId());
            }
        }

        while (!Q.isEmpty()) {
            int u = Q.poll();
            for (VerticeAdjacente v = vertices[u].getVizinhos(); v != null; v = v.getProx()) {
                alt = dist[u] + v.getPeso();
                if (alt < dist[v.getVertice().getId()]) {
                    dist[v.getVertice().getId()] = alt;
                    prev[v.getVertice().getId()] = u;
                    Q.add(v.getVertice().getId());
                }
            }
        }
        // Points = 2.7
        prev[source] = -1;
        return prev;
    }
}
