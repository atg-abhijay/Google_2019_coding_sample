// you can also use imports, for example:
import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int[] solution(int D, int[] A) {
        // write your code in Java SE 8

        /**
         * better to use a large constant instead
         * of max value of int so that we can avoid
         * overflow.
         */
        final int largeValue = (int) Math.pow(10, 8);
        int numVertices = A.length;
        Vertex[] vertices = new Vertex[numVertices];
        /**
         * the predecessors obtained along the shortest
         * path need not be the same as the parents
         * mentioned in the given array A
         */
        Vertex[] predecessors = new Vertex[numVertices];
        /**
         * some initializations
         */
        init(largeValue, vertices, predecessors, numVertices, 0);

        /**
         * priority queue that will hold the vertices. they will
         * be ordered in increasing order of shortest distances
         * from the source vertex
         */
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
                return Integer.compare(v1.shortestDistance, v2.shortestDistance);
            }
        });

        for(int i = 0; i < numVertices; i++) {
            pq.add(vertices[i]);
        }

        /**
         * adding edges to the vertices
         * according to the parents of
         * the given vertices
         */
        for(int i = 0; i < numVertices; i++) {
            int parent = A[i];
            if(parent != -1) {
                Vertex startVertex = vertices[parent];
                /**
                 * all the edges have weight 1
                 */
                startVertex.addEdge(new Edge(vertices[i], 1));
            }
        }

        while(!pq.isEmpty()) {
            /**
             * get the vertex closest
             * to the source vertex.
             */
            /**
             * ****************
             * IMPORTANT
             * ****************
             * should not have pq.poll() over here.
             * if we do that, we remove the known
             * closest vertex and then the pq DOES
             * reorder its elements as it does upon
             * removal of an element, but then, when
             * we go into the relaxEdge method and we
             * update a vertex's shortest distance, that
             * does NOT end up reordering the pq.
             *
             * this happens because a change made to the
             * vertex object does not automatically trigger
             * the pq to reorder itself. so, we should only
             * peek at the top of the pq for now. update the
             * relaxed edge's end vertex's shortest distance.
             */
            Vertex u = pq.peek();
            for(Edge e: u.edges) {
                relaxEdge(u, e, predecessors);
            }
            /**
             * after we are done relaxing the edges originating
             * from the current closest vertex, the vertices which
             * were connected to it have had their distances updated
             * (or not). we remove the current closest vertex HERE
             * (instead of before the loop) and NOW the pq's reordering
             * kicks in since we have used a method of the pq (to remove
             * the element at the top) and so we get the next closest vertex
             * to the source, taking into account the updates that were
             * made upon relaxation above.
             */
            pq.poll();
        }

        int[] ancestorAtDistanceD = new int[numVertices];
        for(int i = 0; i < numVertices; i++) {
            /**
             * for each of the vertices:
             * count keeps track of performing
             * D number of iterations.
             */
            int count = 0;
            int currentNode = i;
            while(count < D) {
                /**
                 * get the ith node's ancestor along
                 * the shortest path from the source vertex.
                 * if the ancestor is -1, break and set the
                 * ith node's ancestor at distance D to -1
                 * else continue until we find the ancestor
                 * at distance D.
                 */
                Vertex ancestor = predecessors[currentNode];
                if(ancestor == null) {
                    currentNode = -1;
                    break;
                }
                currentNode = ancestor.number;
                count++;
            }
            ancestorAtDistanceD[i] = currentNode;
        }

        return ancestorAtDistanceD;

    }


    /**
     * if there is a shorter way to get to the endvertex
     * of the current edge, then we update its shortest
     * distance from the source vertex and we update its
     * predecessor to the start vertex of the given edge
     */
    public void relaxEdge(Vertex startVertex, Edge e, Vertex[] predecessors) {
        Vertex v = e.endVertex;
        if(v.shortestDistance > startVertex.shortestDistance + e.weight) {
            /**
             * updating the vertex's distance does not
             * update the priority queue. it just changes
             * the value for the Vertex object.
             */
            v.shortestDistance = startVertex.shortestDistance + e.weight;
            System.out.println(startVertex.number + ", " + v.number + ", " + v.shortestDistance);
            predecessors[v.number] = startVertex;
        }
    }


    /**
     * initialize the vertices as having shortest
     * distance from the source equal to the large
     * constant and set the predecessor of each
     * of the vertices to null. the shortest distance
     * of the source from the source is zero
     */
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

}