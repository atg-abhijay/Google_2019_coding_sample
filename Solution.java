// you can also use imports, for example:
import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int[] solution(int D, int[] A) {
        // write your code in Java SE 8
        final int largeValue = (int) Math.pow(10, 8);
        int numVertices = A.length;
        Vertex[] vertices = new Vertex[numVertices];
        Vertex[] predecessors = new Vertex[numVertices];
        init(largeValue, vertices, predecessors, numVertices, 0);

        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
                return Integer.compare(v1.shortestDistance, v2.shortestDistance);
            }
        });

        for(int i = 0; i < numVertices; i++) {
            pq.add(vertices[i]);
        }

        for(int i = 0; i < numVertices; i++) {
            int parent = A[i];
            if(parent != -1) {
                Vertex startVertex = vertices[parent];
                startVertex.addEdge(new Edge(vertices[i], 1));
            }
        }

        while(!pq.isEmpty()) {
            /**
             * get the vertex closest
             * to the source vertex.
             */
            Vertex u = pq.peek();
            for(Edge e: u.edges) {
                relaxEdge(pq, u, e, predecessors);
            }
            pq.poll();
        }

        int[] ancestorAtDistanceD = new int[numVertices];
        for(int i = 0; i < numVertices; i++) {
            int count = 0;
            int currentNode = i;
            while(count < D) {
                Vertex parent = predecessors[currentNode];
                if(parent == null) {
                    currentNode = -1;
                    break;
                }
                currentNode = parent.number;
                count++;
            }
            ancestorAtDistanceD[i] = currentNode;
        }

        printArray(ancestorAtDistanceD);
        return ancestorAtDistanceD;

    }

    public void relaxEdge(PriorityQueue<Vertex> pq, Vertex startVertex, Edge e, Vertex[] predecessors) {
        Vertex v = e.endVertex;
        if(v.shortestDistance > startVertex.shortestDistance + e.weight) {
            // pq.remove(v);
            v.shortestDistance = startVertex.shortestDistance + e.weight;
            System.out.println(startVertex.number + ", " + v.number + ", " + v.shortestDistance);
            predecessors[v.number] = startVertex;
            // pq.add(v);
        }
    }

    public void init(int largeValue, Vertex[] vertices, Vertex[] predecessors, int n, int sourceVertex) {
        for(int i = 0; i < n; i++) {
            vertices[i] = new Vertex(i, largeValue);
            predecessors[i] = null;
        }
        vertices[sourceVertex].shortestDistance = 0;
    }

    class Vertex {
        public int number;
        public Integer shortestDistance;
        public ArrayList<Edge> edges = new ArrayList<Edge>();

        Vertex(int num, int dist) {
            this.number = num;
            this.shortestDistance = dist;
        }

        public void addEdge(Edge e) {
            edges.add(e);
        }
    }

    class Edge {
        public Vertex endVertex;
        public int weight;

        Edge(Vertex v, int w) {
            this.endVertex = v;
            this.weight = w;
        }
    }

    public void printArray(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}