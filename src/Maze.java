import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Maze {
    private int mazeWidth;
    private int mazeHeight;
    private int mazeStartX;
    private int mazeStartY;
    private int mazeEndX;
    private int mazeEndY;
    List<String> mazeString = null;
    List<String> mazeActual= null;

    List<List<String>> maze;
    private String[] parts;

    public Maze(Path file) {
        try {
            mazeString = Files.readAllLines(file);
            System.out.println("\nMaze file:");
            mazeString.forEach(System.out::println);
            getMazeProperties();
            mazePropertiesRemoval();

            for (int i = 0; i <= mazeHeight; i++){
                System.out.println(mazeString.get(i));
                parts = mazeString.get(i).split(" ");

                //maze.add()
            }

            for (String mazeItem : mazeString) {
                System.out.println(mazeItem);
                //parts = mazeString.get().split(" ");
            }

            //mazeString.forEach(maze.);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMazeProperties(){
        parts = mazeString.get(0).split(" ");
        mazeWidth = Integer.parseInt(parts[0]);
        mazeHeight = Integer.parseInt(parts[1]);
        parts = mazeString.get(1).split(" ");
        mazeStartX = Integer.parseInt(parts[0]);
        mazeStartY = Integer.parseInt(parts[1]);
        parts = mazeString.get(2).split(" ");
        mazeEndX = Integer.parseInt(parts[0]);
        mazeEndY = Integer.parseInt(parts[1]);

        System.out.println("\nCurrent Values:" +
                "\nmazeWidth: " + mazeWidth +
                "\nmazeHeight: " + mazeHeight +
                "\nmazeStartX: " + mazeStartX +
                "\nmazeStartY: " + mazeStartY +
                "\nmazeEndX: " + mazeEndX +
                "\nmazeEndY: " + mazeEndY);
    }

    private void mazePropertiesRemoval(){
        System.out.println("\nmaze line removal");
        for (int i = 0; i <3; i++){
            System.out.println(mazeString.get(0));
            mazeString.remove(0);
        }
        System.out.println("\nMaze file after removal:");
        mazeString.forEach(System.out::println);
    }

    public enum mazeItem {
        WALL, SPACE
    }
}