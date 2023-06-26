// import java.io.File;
// import java.util.Stack;

// public class FileScanner {
//     public static void scanFilesDFS(String path, String prefix, Stack<String> junks) {
//         File directory = new File(path);

//         if (!directory.isDirectory()) {
//             return;
//         }

//         File[] files = directory.listFiles();
//         if (files == null || files.length == 0) {
//             return;
//         }

//         for (int i = 0; i < files.length; i++) {
//             File file = files[i];
//             String fileName = file.getName();

//             long lastModified = file.lastModified();
//             long currentTime = System.currentTimeMillis();
//             long diff = currentTime - lastModified;

//             if (diff > 2592000000L) {
//                 // push the directory name to the stack
//                 // only push the directory but if the same directory is found again in the stack then don't push it
//                 if (file.isDirectory() && !junks.contains(file.getAbsolutePath())) {
//                     junks.push(file.getAbsolutePath());
//                 }
            
//             }

//             System.out.println(prefix + (i == files.length - 1 ? "└── " : "├── ") + fileName);

//             if (file.isDirectory()) {
//                 String childPrefix = prefix + (i == files.length - 1 ? "    " : "│   ");
//                 scanFilesDFS(file.getAbsolutePath(), childPrefix, junks);
//             }
//         }
//     }

//     public static void main(String[] args) {
//         String path = "D:\\Homework\\Portfolio";
//         Stack<String> junks = new Stack<>();

//         System.out.println(path.split("\\\\")[path.split("\\\\").length - 1]);

//         scanFilesDFS(path, "", junks);

//         if (junks.isEmpty()) {
//             System.out.println("No junks");
//         } else {
//             System.out.println("Junks found: ");
//             while (!junks.isEmpty()) {
//                 System.out.println(junks.pop());
//             }
//         }
//     }
// }

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Graph {
    private Map<String, List<String>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(String vertex) {
        adjacencyList.put(vertex, new ArrayList<>());
    }

    public void addEdge(String source, String destination) {
        if (!adjacencyList.containsKey(source))
            addVertex(source);

        if (!adjacencyList.containsKey(destination))
            addVertex(destination);

        adjacencyList.get(source).add(destination);
    }

    public List<String> getChildren(String vertex) {
        return adjacencyList.get(vertex);
    }

    public void scanDirectory(String directoryPath) {
        File rootDirectory = new File(directoryPath);
        if (!rootDirectory.isDirectory()) {
            System.out.println("Invalid directory path!");
            return;
        }

        String rootFolder = rootDirectory.getName();
        addVertex(rootFolder); // Add root folder as a vertex

        DFS(rootFolder, rootDirectory);
    }

    private void DFS(String vertex, File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String childVertex = (file.isDirectory()) ? fileName + "\\" : fileName;
                addEdge(vertex, childVertex);

                if (file.isDirectory()) {
                    DFS(childVertex, file);
                }
            }
        }
    }

    public void printTree(String vertex, String prefix, boolean isLast) {
        // ANSI escape code for text color
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_CYAN = "\u001B[36m";

        List<String> children = getChildren(vertex);
        int size = children.size();

        System.out.print(prefix);
        if (isLast) {
            System.out.print(ANSI_GREEN + "└── " + ANSI_RESET);
            prefix += "    ";
        } else {
            System.out.print(ANSI_GREEN + "├── " + ANSI_RESET);
            prefix += ANSI_GREEN + "│   " + ANSI_RESET;
        }

        // Apply color to directories and files
        if (vertex.endsWith("\\")) {
            System.out.println(ANSI_BLUE + vertex + ANSI_RESET);
        } else {
            System.out.println(ANSI_CYAN + vertex + ANSI_RESET);
        }

        for (int i = 0; i < size - 1; i++) {
            String child = children.get(i);
            printTree(child, prefix, false);
        }

        if (size > 0) {
            String lastChild = children.get(size - 1);
            printTree(lastChild, prefix, true);
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Graph graph = new Graph();

            String directoryPath = "";
            while (true) {
                System.out.print("Enter directory path: ");
                directoryPath = scanner.nextLine();

                File directory = new File(directoryPath);
                if (directory.isDirectory()) {
                    break;
                } else {
                    System.out.println("Invalid directory path!");
                }
            }
            graph.scanDirectory(directoryPath);

            String rootFolder = new File(directoryPath).getName();
            graph.printTree(rootFolder, "", true);
        }
    }
}
