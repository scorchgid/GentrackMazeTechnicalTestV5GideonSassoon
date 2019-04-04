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
    private List<String> mazeString = null;
    private List<MazeSpace> mazeList = new ArrayList<>();
    private String[] parts;
    private String mazeFileName;

    public Maze(Path file) {
        try {
            mazeFileName = file.getFileName().toString();
            mazeString = Files.readAllLines(file);
            //printRawMaze("\nMaze file:");
            getMazeProperties();
            maze2DimensionalArray();
            markStartAndEndPosition();

        } catch (Exception e) {
            System.err.println("\nException thrown on: " + file.toString());
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

        System.out.println("\nCurrent Values (" + mazeFileName + "):" +
                "\nmazeWidth: " + mazeWidth +
                "\nmazeHeight: " + mazeHeight +
                "\nmazeStartX: " + mazeStartX +
                "\nmazeStartY: " + mazeStartY +
                "\nmazeEndX: " + mazeEndX +
                "\nmazeEndY: " + mazeEndY);

        System.out.println("\nMaze line removal");
        for (int i = 0; i < 3; i++) {
            System.out.println(mazeString.get(0));
            mazeString.remove(0);
        }
        //printRawMaze("\nMaze file after removal:");
    }

    private void maze2DimensionalArray() {
        mazeString.replaceAll(x -> x.replaceAll(" ", ""));

        int currentIndexRow = 0;
        for (String mazeItemRow : mazeString) {
            //System.out.println(mazeItemRow + " Current Row index " + currentIndexRow);
            parts = mazeItemRow.split("");

            int currentIndexColumn = 0;
            for (String mazeItem : parts) {
                //System.out.println(mazeItem + " Current Column index " + currentIndexColumn);
                try {
                    if (mazeItem.equals("0"))
                        mazeList.add(new MazeSpaceClear(mazeItem, currentIndexRow, currentIndexColumn, mazeHeight, mazeWidth));
                    else
                        mazeList.add(new MazeSpace(mazeItem, currentIndexRow, currentIndexColumn));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentIndexColumn++;
            }
            currentIndexRow++;
        }
        //printMaze("End of maze2DimensionalArray()");
    }

    private void printRawMaze(String message){
        System.out.println("PrintMaze Raw Version : " + message + "\nFile: " + mazeFileName);
        mazeString.forEach(System.out::println);
    }

    private void printMaze(String message) {
        System.out.println("PrintMaze: " + message + "\nFile: " + mazeFileName);
        for (MazeSpace mazeItemRow : mazeList) {
            System.out.print(mazeItemRow.toString());
            //System.out.println(mazeItemRow.toStringProperties());
        }
    }

    private void markStartAndEndPosition() throws Exception {
        for (MazeSpace mazeSearch : mazeList) {
            if (mazeSearch.positionY == mazeStartY && mazeSearch.positionX == mazeStartX) {
                if (mazeSearch instanceof MazeSpaceClear) {
                    ((MazeSpaceClear) mazeSearch).setStart(true);
                    //System.out.println("\nSettingStart: " + mazeSearch.toStringProperties());
                } else
                    throw new Exception("markStartAndEndPosition Maze.java, Start position is marked here but MazeSpace " + mazeSearch.hashCode() + " is does not inherit MazeSpaceClear." +
                            "\nStart Co-ordinates:" +
                            "\nX: " + mazeStartX +
                            "\nY: " + mazeStartY +
                            "\nFileName: " + mazeFileName +
                            "\nProperties for this object is as follows:\n" +
                            mazeSearch.toStringProperties());
            }

            if (mazeSearch.positionY == mazeEndY && mazeSearch.positionX == mazeEndX) {
                if (mazeSearch instanceof MazeSpaceClear) {
                    ((MazeSpaceClear) mazeSearch).setEnd(true);
                    //System.out.println("\nSettingEnd: " + mazeSearch.toStringProperties());
                } else
                    throw new Exception("markStartAndEndPosition Maze.java, End position is marked here but MazeSpace " + mazeSearch.hashCode() + " is does not inherit MazeSpaceClear." + "\nEnd Co-ordinates:" +
                            "\nX: " + mazeEndX +
                            "\nY: " + mazeEndY +
                            "\nFileName: " + mazeFileName +
                            "\nProperties for this object is as follows:\n" +
                            mazeSearch.toStringProperties());
            }
        }
        //printMaze("End of markStartAndEndPosition()");
        findStartAndEndFromMazeObject();
    }

    private void findStartAndEndFromMazeObject() throws Exception {
        boolean end = false;
        boolean start = false;
        for (MazeSpace mazeSearch : mazeList) {
            if (mazeSearch instanceof MazeSpaceClear) {
                if (((MazeSpaceClear) mazeSearch).getStart()) {
                    System.out.println("Found a Start True Match:\n" + mazeSearch.toStringProperties());
                    start = true;
                }
            }
            if (mazeSearch instanceof MazeSpaceClear) {
                if (((MazeSpaceClear) mazeSearch).getEnd()) {
                    System.out.println("Found a End True Match:\n" + mazeSearch.toStringProperties());
                    end = true;
                }
            }
        }
        if (!start && !end)
            throw new Exception("Missing Start & end in: " + mazeFileName +
                    "\nStart Should be at " + mazeStartX + "x" + mazeStartY +
                    "\nEnd Should be at " + mazeEndX + "x" + mazeEndY);
        if (!start)
            throw new Exception("Missing Start in: " + mazeFileName +
                    "\nStart Should be at " + mazeStartX + "x" + mazeStartY);
        if (!end)
            throw new Exception("Missing End in: " + mazeFileName +
                    "\nEnd Should be at " + mazeEndX + "x" + mazeEndY);
    }

    //TODO Do the calculation logic to find exit of maze, Switch case? Or: Is this a space I can move to?
    //TODO Find out how to do deal with "Have I been this way before?"
    private void navigateMaze() {

    }
}