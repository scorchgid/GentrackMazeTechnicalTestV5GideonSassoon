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

    public Maze(Path file) {
        try {
            mazeString = Files.readAllLines(file);
            System.out.println("\nMaze file:");
            for (String mazeItem : mazeString) {
                System.out.println(mazeItem);
            }

            String[] parts = mazeString.get(0).split(" ");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum mazeItem {
        WALL, SPACE
    }
}