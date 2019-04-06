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
    private List<MazeSpaceClear> potentialRoutes = new ArrayList<>();
    /*
     * Allows the turning on and off of PrintLns rather than finding each of them in the file.
     */
    private Boolean propertiesPrintXY = false;
    private Boolean propertiesPrintRawAfterRemoval = false;
    private Boolean propertiesPrintRawInitialMaze = false;
    private Boolean propertiesPrintMazeNavigation = false;
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
            buildMazeIn2DimensionalArray();
            markMazeStartAndEndPosition();

            navigateMaze();

        } catch (Exception e) {
            System.err.println("\nException thrown on: " + file.toString());
            e.printStackTrace();
            if (propertiesPrintMazeException)
                printMaze("Exception Maze");
        }
    }

    public enum MazeSpaceClearSetProperties {
        START, END, POTENTIAL, GLADOS, TRAVELED, FAILURE
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

    private void printPotentialList() {
        System.out.println("This is a list of Potential Maze");
        for (MazeSpace mazeItemRow : potentialRoutes) {
            System.out.println(mazeItemRow.toStringProperties());
        }
        System.out.print("\n");
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

    private void buildMazeIn2DimensionalArray() {
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
            printMaze("End of buildMazeIn2DimensionalArray()");
    }

    private void markMazeStartAndEndPosition() throws Exception {
        for (MazeSpace mazeSearch : maze) {
            if (mazeSearch.getPositionX() == mazeStartX && mazeSearch.getPositionY() == mazeStartY) {
                if (mazeSearch instanceof MazeSpaceClear) {
                    ((MazeSpaceClear) mazeSearch).setStart(true);
                    if (propertiesPrintPropertiesStartEnd)
                        System.out.println("\nSettingStart: " + mazeSearch.toStringProperties());
                } else
                    throw new Exception("markMazeStartAndEndPosition Maze.java, Start position is marked here but MazeSpace is does not inherit MazeSpaceClear." +
                            "\nStart Co-ordinates:" +
                            "\nX: " + mazeStartX +
                            "\nY: " + mazeStartY +
                            "\nFileName: " + mazeFileName +
                            "\nProperties for this object is as follows:\n" +
                            mazeSearch.toStringProperties());
            }

            if (mazeSearch.getPositionX() == mazeEndX && mazeSearch.getPositionY() == mazeEndY) {
                if (mazeSearch instanceof MazeSpaceClear) {
                    ((MazeSpaceClear) mazeSearch).setEnd(true);
                    if (propertiesPrintPropertiesStartEnd)
                        System.out.println("\nSettingEnd: " + mazeSearch.toStringProperties());
                } else
                    throw new Exception("markMazeStartAndEndPosition Maze.java, End position is marked here but MazeSpace is does not inherit MazeSpaceClear." + "\nEnd Co-ordinates:" +
                            "\nX: " + mazeEndX +
                            "\nY: " + mazeEndY +
                            "\nFileName: " + mazeFileName +
                            "\nProperties for this object is as follows:\n" +
                            mazeSearch.toStringProperties());
            }
        }
        if (propertiesPrintMazeStartEnd)
            printMaze("End of markMazeStartAndEndPosition()");
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

    //TODO Find out how to do deal with "Have I been this way before?" - Potential routes
    //TODO Manage glados issue.
    private void navigateMaze() {
        printMaze("before navigation");
        navi = new Navigator(mazeStartX, mazeStartY);
        System.out.println(navi.toString());
        int chosenX = mazeStartX;
        int chosenY = mazeStartY;
        while (!navi.confirmPositionMatches(mazeEndX, mazeEndY)) {
            if (propertiesPrintMazeNavigation)
                printMaze("Navigating");
            if (checkCanMoveHere(navi.getCXL(), navi.getCYLPlus1(), false)) {
                markPotentialRoutes(navi.getCXL(), navi.getCYLPlus1(), chosenX, chosenY);
                chosenX = navi.getCXL();
                chosenY = navi.getCYLPlus1();
                moveNavigator(navi.getCXL(), navi.getCYLPlus1());
            } else if (checkCanMoveHere((navi.getCXLPlus1()), navi.getCYL(), false)) {
                markPotentialRoutes(navi.getCXLPlus1(), navi.getCYL(), chosenX, chosenY);
                chosenX = navi.getCXLPlus1();
                chosenY = navi.getCYL();
                moveNavigator(navi.getCXLPlus1(), navi.getCYL());
            } else if (checkCanMoveHere(navi.getCXLMinus1(), navi.getCYL(), false)) {
                markPotentialRoutes(navi.getCXLMinus1(), navi.getCYL(), chosenX, chosenY);
                chosenX = navi.getCXLMinus1();
                chosenY = navi.getCYL();
                moveNavigator(navi.getCXLMinus1(), navi.getCYL());
            } else if (checkCanMoveHere(navi.getCXL(), navi.getCYLMinus1(), false)) {
                markPotentialRoutes(navi.getCXL(), navi.getCYLMinus1(), chosenX, chosenY);
                chosenX = navi.getCXL();
                chosenY = navi.getCYLMinus1();
                moveNavigator(navi.getCXL(), navi.getCYLMinus1());
            } else {
                System.err.println("Error I am stuck");
                printPotentialList();
                backTrack();
            }
        }
    }

    //TODO Waring this is likely the cause of the bug because it's check that the potential route was used seeing as that route was no longer in the list it may be this is what's causing the maze to re-take this route. Though it should be stopped by the failure marker. Investigation will occur
    private void navigateMazeRouteAsFailure(int x, int y) {
        Navigator rougeNavi = new Navigator(x, y);
        Boolean rougeNavigate = true;
        //Set the first direction available as Failure
        if (checkCanMoveHere(rougeNavi.getCXL(), rougeNavi.getCYLPlus1(), true)) {
            setMazeSpaceClearBooleanProperty(rougeNavi.getCXL(), rougeNavi.getCYLPlus1(), MazeSpaceClearSetProperties.FAILURE, true);
            moveRougeNavigator(rougeNavi.getCXL(), rougeNavi.getCYLPlus1(), rougeNavi);
        } else if (checkCanMoveHere((rougeNavi.getCXLPlus1()), rougeNavi.getCYL(), true)) {
            setMazeSpaceClearBooleanProperty(rougeNavi.getCXLPlus1(), rougeNavi.getCYL(), MazeSpaceClearSetProperties.FAILURE, true);
            moveRougeNavigator(rougeNavi.getCXLPlus1(), rougeNavi.getCYL(), rougeNavi);
        } else if (checkCanMoveHere(rougeNavi.getCXLMinus1(), rougeNavi.getCYL(), true)) {
            setMazeSpaceClearBooleanProperty(rougeNavi.getCXLMinus1(), rougeNavi.getCYL(), MazeSpaceClearSetProperties.FAILURE, true);
            moveRougeNavigator(rougeNavi.getCXLMinus1(), rougeNavi.getCYL(), rougeNavi);
        } else if (checkCanMoveHere(rougeNavi.getCXL(), rougeNavi.getCYLMinus1(), true)) {
            setMazeSpaceClearBooleanProperty(rougeNavi.getCXL(), rougeNavi.getCYLMinus1(), MazeSpaceClearSetProperties.FAILURE, true);
            moveRougeNavigator(rougeNavi.getCXL(), rougeNavi.getCYLMinus1(), rougeNavi);
        } else System.err.println("navigateMazeRouteAsFailure - Rouge Navigator could not find route.");

        //Go through and Navigate through the maze and instead of marking the places as traveled mark them as null.
        do {
            printMaze("Rouge Navigation");
            if (checkCanMoveHere(rougeNavi.getCXL(), rougeNavi.getCYLPlus1(), true)) {
                moveRougeNavigator(rougeNavi.getCXL(), rougeNavi.getCYLPlus1(), rougeNavi);
            } else if (checkCanMoveHere((rougeNavi.getCXLPlus1()), rougeNavi.getCYL(), true)) {
                moveRougeNavigator(rougeNavi.getCXLPlus1(), rougeNavi.getCYL(), rougeNavi);
            } else if (checkCanMoveHere(rougeNavi.getCXLMinus1(), rougeNavi.getCYL(), true)) {
                moveRougeNavigator(rougeNavi.getCXLMinus1(), rougeNavi.getCYL(), rougeNavi);
            } else if (checkCanMoveHere(rougeNavi.getCXL(), rougeNavi.getCYLMinus1(), true)) {
                moveRougeNavigator(rougeNavi.getCXL(), rougeNavi.getCYLMinus1(), rougeNavi);
            } else {
                System.out.println("Completed Rouge Action Returning to Base");
                potentialRoutes.remove(potentialRoutes.size() - 1);
                printPotentialList();
                rougeNavigate = false;
            }
        } while (rougeNavigate);

    }

    private void moveNavigator(int x, int y) {
        if (checkCanMoveHere(x, y, false)) {
            setMazeSpaceClearBooleanProperty(x, y, MazeSpaceClearSetProperties.TRAVELED, true);
            navi.setCurrentLocation(x, y);
            if (propertiesPrintMazeNavigation)
                System.out.println("Moved to: " + x + "," + y);
        }
    }
    //TODO Waring This may be another likely issue as again traveled is a marker which checks if it gone in that direction. If this is no longer there then a failure may occur.
    private void moveRougeNavigator(int x, int y, Navigator rouge) {
        if (checkCanMoveHere(x, y, true)) {
            setMazeSpaceClearBooleanProperty(x, y, MazeSpaceClearSetProperties.TRAVELED, false);
            rouge.setCurrentLocation(x, y);
            if (propertiesPrintMazeNavigation)
                System.out.println("Rouge Moved to: " + x + "," + y);
        }
    }

    /***
     * Method also known as Are we going this way. This is the first part of this method. The Method it calls is to check if it is a potential route
     * From the point of X and Y will check the surrounding coordinates and mark if these are potential routes.
     * @param whereWeAreGoingX - This the direction we have decided to take so we need to ignore this X
     * @param whereWeAreGoingY - This the direction we have decided to take so we need to ignore this Y
     * @param checkThisX - This is where we currently are before we have moved so we need to check the surrounding area X
     * @param checkThisY - This is where we currently are before we have moved so we need to check the surrounding area Y
     */
    private void markPotentialRoutes(int whereWeAreGoingX, int whereWeAreGoingY, int checkThisX, int checkThisY) {
        int yP1 = checkThisY + 1;
        int xP1 = checkThisX + 1;
        int xS1 = checkThisX - 1;
        int yS1 = checkThisY - 1;

        if (whereWeAreGoingX != checkThisX && whereWeAreGoingY != yP1) {
            if (checkCanMoveHere(checkThisX, yP1, false)) {
                setMazeSpaceClearBooleanProperty(checkThisX, yP1, MazeSpaceClearSetProperties.POTENTIAL, true);
            }
        }
        if (whereWeAreGoingX != xP1 && whereWeAreGoingY != checkThisY) {
            if (checkCanMoveHere(xP1, checkThisY, false)) {
                setMazeSpaceClearBooleanProperty(xP1, checkThisY, MazeSpaceClearSetProperties.POTENTIAL, true);
            }
        }
        if (whereWeAreGoingX != xS1 && whereWeAreGoingY != checkThisY) {
            if (checkCanMoveHere(xS1, checkThisY, false)) {
                setMazeSpaceClearBooleanProperty(xS1, checkThisY, MazeSpaceClearSetProperties.POTENTIAL, true);
            }
        }
        if (whereWeAreGoingX != checkThisX && whereWeAreGoingY != yS1) {
            if (checkCanMoveHere(checkThisX, yS1, false)) {
                setMazeSpaceClearBooleanProperty(checkThisX, yS1, MazeSpaceClearSetProperties.POTENTIAL, true);
            }
        }

        //Checks each of the MazeSpace
        for (MazeSpace mazeSearch : maze) {
            //if this mazeSpace is not a wall and inherits MazeSpaceClear
            if (!mazeSearch.wall && mazeSearch instanceof MazeSpaceClear) {
                //If MazeSpaceClear potential is true
                if (((MazeSpaceClear) mazeSearch).getPotential()) {
                    boolean thisMazeExists = false;
                    int checkXIsNotInArray = mazeSearch.getPositionX();
                    int checkYIsNotInArray = mazeSearch.getPositionY();
                    // Run through the list of potentialRoute MazeSpaceClear objects (This is a list of MazeSpaceClear which have potential set to true
                    for (MazeSpaceClear potentialMazeObject : potentialRoutes) {
                        //Is the one we are adding already in this list?
                        if (potentialMazeObject.getPositionX() == checkXIsNotInArray && potentialMazeObject.getPositionY() == checkYIsNotInArray)
                            //If so mark this as true
                            thisMazeExists = true;
                    }
                    //As long as this maze does not exist in the list. Add it to it
                    if (!thisMazeExists) {
                        potentialRoutes.add((MazeSpaceClear) mazeSearch);
                    }
                }
            }
        }
    }

    //If navigator is unable to move to this position this method will erase moves made and back track the user to the last known space
    //TODO Move Backtrack to NavigateMaze as Failure
    private void backTrack() {
        //Use the Potential list and find the last one.
        int lastX = potentialRoutes.get(potentialRoutes.size() - 1).getPositionX();
        int lastY = potentialRoutes.get(potentialRoutes.size() - 1).getPositionY();
        //Set the Navigator to that position
        navi.setCurrentLocation(lastX, lastY);
        //Run navigateMazeRouteAsFailure
        navigateMazeRouteAsFailure(lastX, lastY);
    }


    private void setMazeSpaceClearBooleanProperty(int x, int y, MazeSpaceClearSetProperties properties, Boolean bool) {
        for (MazeSpace mazeSearch : maze) {
            if (mazeSearch.getPositionX() == x && mazeSearch.getPositionY() == y) {
                switch (properties) {
                    case START:
                        ((MazeSpaceClear) mazeSearch).setStart(bool);
                        break;
                    case END:
                        ((MazeSpaceClear) mazeSearch).setEnd(bool);
                        break;
                    //Marks the x and y as a potential route.
                    case POTENTIAL:
                        ((MazeSpaceClear) mazeSearch).setPotential(bool);
                        break;
                    case GLADOS:
                        ((MazeSpaceClear) mazeSearch).setGlados(bool);
                        break;
                    case TRAVELED:
                        ((MazeSpaceClear) mazeSearch).setTraveled(bool);
                        break;
                    case FAILURE:
                        ((MazeSpaceClear) mazeSearch).setFailure(bool);
                        break;
                }
                return;
            }
        }
    }
    //TODO change method to scan if this is a possible route rather than move to this route. Possibly scanning if the surrounding places are movable to
    //TODO Check above TODO to see if still valid. It may no longer be valid.

    /***
     * Checks if this position exists
     * Checks if this position is not a wall and is the inherited object MazeSpaceClear
     * Checks if this position has been traveled to
     * This method is just a check and does not make any edits
     * @param x coordinate to check
     * @param y coordinate to check
     * @param rouge if this is checking travel routes or not. True if we want to use routes that are traveled. False is not. Designed to all removal of travel routes
     * @return true if the above is true. False if at any point it is not. Will not identify at what point it does not match.
     */
    private Boolean checkCanMoveHere(int x, int y, boolean rouge) {
        for (MazeSpace mazeSearch : maze) {
            if (mazeSearch.getPositionX() == x && mazeSearch.getPositionY() == y)
                if (!mazeSearch.wall && mazeSearch instanceof MazeSpaceClear) {
                    //TODO You should not be both failed and traveled. You can be one or the other. If you are both something has gone wrong
                    if (rouge) {
                        if (((MazeSpaceClear) mazeSearch).getTraveled() && !((MazeSpaceClear) mazeSearch).getFailure())
                            return true;
                    } else {
                        if (!((MazeSpaceClear) mazeSearch).getTraveled() && !((MazeSpaceClear) mazeSearch).getFailure())
                            return true;
                    }

                } else
                    return false;
        }
        return false;
    }
}