import java.util.*;

public class UndirectedGraph {
    public static List<List<Character>> findAllPaths(int[][] graph, char start, char end) {
        List<List<Character>> paths = new ArrayList<>();
        Set<Character> visited = new HashSet<>();
        dfs(graph, start, end, new ArrayList<>(), paths, visited);
        return paths;
    }

    private static void dfs(int[][] graph, char current, char end, List<Character> path, List<List<Character>> paths,
            Set<Character> visited) {
        path.add(current);
        visited.add(current);

        if (current == end) {
            paths.add(new ArrayList<>(path));
        } else {
            for (char neighbor : getNeighbors(graph, current)) {
                if (!visited.contains(neighbor)) {
                    dfs(graph, neighbor, end, path, paths, visited);
                }
            }
        }

        path.remove(path.size() - 1);
        visited.remove(current);
    }

    private static List<Character> getNeighbors(int[][] graph, char node) {
        List<Character> neighbors = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            if (graph[node - 'A'][i] == 1) {
                neighbors.add((char) (i + 'A'));
            }
        }
        return neighbors;
    }

    public static List<Character> findShortestPath(int[][] graph, char start, char end) {
        Map<Character, Integer> distances = new HashMap<>();
        Map<Character, Character> parents = new HashMap<>();
        Queue<Character> queue = new LinkedList<>();

        distances.put(start, 0);
        parents.put(start, null);
        queue.offer(start);

        while (!queue.isEmpty()) {
            char current = queue.poll();

            if (current == end) {
                return reconstructPath(parents, start, end);
            }

            for (char neighbor : getNeighbors(graph, current)) {
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, distances.get(current) + 1);
                    parents.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        return null; // No path found
    }

    private static List<Character> reconstructPath(Map<Character, Character> parents, char start, char end) {
        List<Character> path = new ArrayList<>();
        char current = end;

        while (current != start) {
            path.add(0, current);
            current = parents.get(current);
        }

        path.add(0, start);
        return path;
    }

    public static void main(String[] args) {
        // Example 8-node, 12-edge undirected graph
        int[][] graph = {
                { 0, 1, 0, 1, 0, 0, 0, 1 },
                { 1, 0, 1, 1, 0, 0, 0, 0 },
                { 0, 1, 0, 1, 0, 1, 0, 0 },
                { 1, 1, 1, 0, 1, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 1, 0, 1 },
                { 0, 0, 1, 0, 1, 0, 1, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 1 },
                { 1, 0, 0, 0, 1, 0, 1, 0 }
        };

        List<List<Character>> allPaths = findAllPaths(graph, 'A', 'H');
        System.out.println("All possible paths:\n " + allPaths);

        List<Character> shortestPath = findShortestPath(graph, 'A', 'H');
        System.out.println("Shortest Path:\n " + shortestPath);
    }
}