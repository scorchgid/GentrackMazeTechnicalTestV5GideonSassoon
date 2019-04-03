import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private int mazeWidth;
    private int mazeHeight;
    private int mazeStartX;
    private int mazeStartY;
    private int mazeEndX;
    private int mazeEndY;
    List<String> mazeString = null;
    List<MazeSpace> mazeList = new ArrayList<>();


    private String[] parts;

    public Maze(Path file) {
        try {
            mazeString = Files.readAllLines(file);
            System.out.println("\nMaze file:");
            mazeString.forEach(System.out::println);
            getMazeProperties();
            maze2DimensionalArray();
            //mazeReplace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the Maze's properties and removes it from the loaded file so it no longer interferes with the reading.
     * Properties are stored as variables in this class
     * Will also print current properties assigned
     * Code exists in this method which will print out the maze to confirm properties has been removed. But due to the size of some of the mazes this was commented.
     */
    private void getMazeProperties() {
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

        //System.out.println("\nmaze line removal");
        for (int i = 0; i < 3; i++) {
            System.out.println(mazeString.get(0));
            mazeString.remove(0);
        }
        //System.out.println("\nMaze file after removal:");
        mazeString.forEach(System.out::println);
    }

    private void mazeReplace() {
        mazeString.replaceAll(x -> x.replaceAll("1", "#"));
        mazeString.replaceAll(x -> x.replaceAll(" ", ""));
        mazeString.replaceAll(x -> x.replaceAll("0", " "));
        System.out.println("\nReplacement");
        mazeString.forEach(System.out::println);
    }

    private void maze2DimensionalArray() {
        mazeString.replaceAll(x -> x.replaceAll(" ", ""));

        int currentIndexRow = 0;
        for (String mazeItemRow : mazeString) {
            System.out.println(mazeItemRow + " Current index " + currentIndexRow);
            parts = mazeItemRow.split("");

            int currentIndexColumn = 0;
            for (String mazeItem : parts) {
                System.out.println(mazeItem + " Current Column index " + currentIndexColumn);
                try {
                    if (mazeItem.equals("0")) {
                        mazeList.add(new MazeSpaceClear(mazeItem, currentIndexRow, currentIndexColumn, mazeHeight, mazeWidth));
                    } else {
                        mazeList.add(new MazeSpace(mazeItem, currentIndexRow, currentIndexColumn));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentIndexColumn++;
            }


            System.out.println("Maze line " + currentIndexRow + " ");
            currentIndexRow++;
        }
        for (MazeSpace mazeItemRow : mazeList) {
            System.out.print(mazeItemRow.toString());
            //System.out.println(mazeItemRow.toStringProperties());
        }
    }

    //TODO Mark the star position with an S.
    //TODO Do the calculation logic to find exit of maze, Switch case? Or: Is this a space I can move to?
    //TODO Find out how to do deal with "Have I been this way before?"
    private void markStartPosition() {
    }

    public enum mazeItem {
        WALL, SPACE
    }
}