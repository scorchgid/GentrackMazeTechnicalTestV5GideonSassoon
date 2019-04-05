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
    private List<MazeSpace> maze = new ArrayList<>();
    private String[] parts;
    private String mazeFileName;
    private Navigator navi;
    /*
     * Allows the turning on and off of PrintLns rather than finding each of them in the file.
     */
    private Boolean propertiesPrintXY = false;
    private Boolean propertiesPrintRawAfterRemoval = false;
    private Boolean propertiesPrintRawInitialMaze = false;
    private Boolean propertiesPrintMazeException = true;
    private Boolean propertiesPrintMazeLineRemoval = false;
    private Boolean propertiesPrintPropertiesStartEnd = false;
    private Boolean propertiesPrintMazeStartEnd = false;
    private Boolean propertiesPrintLookForStartEnd = false;

    public Maze(Path file) {
        try {
            mazeFileName = file.getFileName().toString();
            mazeString = Files.readAllLines(file);
            if (propertiesPrintRawInitialMaze)
                printRawMaze("Maze file:");
            getMazeProperties();
            maze2DimensionalArray();
            markStartAndEndPosition();

            navigateMaze();

        } catch (Exception e) {
            System.err.println("\nException thrown on: " + file.toString());
            e.printStackTrace();
            if (propertiesPrintMazeException)
                printMaze("Exception Maze");
        }
    }

    String getMaze() {
        StringBuilder mazeExport = new StringBuilder();
        for (MazeSpace mazeItemRow : maze) {
            mazeExport.append(mazeItemRow.toString());
        }
        return mazeExport.toString();
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

        System.out.println("\nMaze Properties (" + mazeFileName + "):" +
                "\nmazeWidth: " + mazeWidth +
                "\nmazeHeight: " + mazeHeight +
                "\nmazeStartX: " + mazeStartX +
                "\nmazeStartY: " + mazeStartY +
                "\nmazeEndX: " + mazeEndX +
                "\nmazeEndY: " + mazeEndY);


        if (propertiesPrintMazeLineRemoval)
            System.out.println("\nMaze line removal");
        for (int i = 0; i < 3; i++) {
            if (propertiesPrintMazeLineRemoval)
                System.out.println(mazeString.get(0));
            mazeString.remove(0);
        }

        if (propertiesPrintRawAfterRemoval)
            printRawMaze("Maze file after removal");
    }

    private void maze2DimensionalArray() {
        mazeString.replaceAll(x -> x.replaceAll(" ", ""));

        int currentIndexY = 0;
        for (String mazeItemRow : mazeString) {
            if (propertiesPrintXY)
                System.out.println("\n" + mazeItemRow + " Current Row index " + currentIndexY);
            parts = mazeItemRow.split("");

            int currentIndexX = 0;
            for (String mazeItem : parts) {
                if (propertiesPrintXY)
                    System.out.println(mazeItem + " Current Column index " + currentIndexX);
                try {
                    if (mazeItem.equals("0"))
                        maze.add(new MazeSpaceClear(mazeItem, currentIndexX, currentIndexY, mazeHeight, mazeWidth));
                    else
                        maze.add(new MazeSpace(mazeItem, currentIndexX, currentIndexY));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentIndexX++;
            }
            currentIndexY++;
        }
        if (propertiesPrintXY)
            printMaze("End of maze2DimensionalArray()");
    }

    private void printRawMaze(String message) {
        System.out.println("\nPrintMaze Raw Version: " + message + " file: " + mazeFileName);
        mazeString.forEach(System.out::println);
        System.out.print("\n");

    }

    void printMaze(String message) {
        System.out.println("\nPrintMaze: " + message + " file: " + mazeFileName);
        for (MazeSpace mazeItemRow : maze) {
            System.out.print(mazeItemRow.toString());
            //System.out.println(mazeItemRow.toStringProperties());
        }
        System.out.print("\n");
    }

    private void markStartAndEndPosition() throws Exception {
        for (MazeSpace mazeSearch : maze) {
            if (mazeSearch.positionY == mazeStartY && mazeSearch.positionX == mazeStartX) {
                if (mazeSearch instanceof MazeSpaceClear) {
                    ((MazeSpaceClear) mazeSearch).setStart(true);
                    if (propertiesPrintPropertiesStartEnd)
                        System.out.println("\nSettingStart: " + mazeSearch.toStringProperties());
                } else
                    throw new Exception("markStartAndEndPosition Maze.java, Start position is marked here but MazeSpace is does not inherit MazeSpaceClear." +
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
                    if (propertiesPrintPropertiesStartEnd)
                        System.out.println("\nSettingEnd: " + mazeSearch.toStringProperties());
                } else
                    throw new Exception("markStartAndEndPosition Maze.java, End position is marked here but MazeSpace is does not inherit MazeSpaceClear." + "\nEnd Co-ordinates:" +
                            "\nX: " + mazeEndX +
                            "\nY: " + mazeEndY +
                            "\nFileName: " + mazeFileName +
                            "\nProperties for this object is as follows:\n" +
                            mazeSearch.toStringProperties());
            }
        }
        if (propertiesPrintMazeStartEnd)
            printMaze("End of markStartAndEndPosition()");
        if (propertiesPrintLookForStartEnd)
            findStartAndEndFromMazeObject();
    }

    private void findStartAndEndFromMazeObject() throws Exception {
        boolean end = false;
        boolean start = false;
        for (MazeSpace mazeSearch : maze) {
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


    //TODO Find out how to do deal with "Have I been this way before?"
    private void navigateMaze() {
        printMaze("before navigation");
        navi = new Navigator();
        navi.setCurrentLocation(mazeStartX, mazeStartY);
        System.out.println(navi.toString());
        while (!navi.confirmPositionMatches(mazeEndX, mazeEndY)) {
            printMaze("Navigating");

            if (moveHere(navi.getCXL(), navi.getCYL() + 1))
                moveNavigator(navi.getCXL(), navi.getCYL() + 1);
            else if (moveHere(navi.getCXL() + 1, navi.getCYL()))
                moveNavigator(navi.getCXL() + 1, navi.getCYL());
            else if (moveHere(navi.getCXL() - 1, navi.getCYL()))
                moveNavigator(navi.getCXL() - 1, navi.getCYL());
            else if (moveHere(navi.getCXL(), navi.getCYL() - 1))
                moveNavigator(navi.getCXL(), navi.getCYL() - 1);
        }
    }

    private void moveNavigator(int x, int y) {
        if (setMazeTraveledProperty(x, y, true)) {
            navi.setCurrentLocation(x, y);

        } else
            System.err.print("You have moved the navi to an invalid space" + x + y);
    }

    private Boolean setMazeTraveledProperty(int x, int y, Boolean traveled) {
        if (moveHere(x, y)) {
            for (MazeSpace mazeSearch : maze) {
                if (mazeSearch.positionX == x && mazeSearch.positionY == y) {
                    ((MazeSpaceClear) mazeSearch).setTraveled(traveled);
                    return true;
                }
            }
        }
        return false;
    }

    //TODO change method to scan if this is a possible route rather than move to this route. Possibly scanning if the surrounding places are moveble to
    private Boolean moveHere(int x, int y) {
        for (MazeSpace mazeSearch : maze) {
            if (mazeSearch.positionX == x && mazeSearch.positionY == y)
                if (!mazeSearch.wall && mazeSearch instanceof MazeSpaceClear) {
                    return !((MazeSpaceClear) mazeSearch).getTraveled();
                } else
                    return false;
        }
        return false;
    }
}