public class MazeSpaceClear extends MazeSpace {
    private Boolean propertiesPrintXtra = true; //See TEST_MARKERS_README.txt

    private Boolean start = false; //Is this the starting space
    private Boolean end = false; //Is this the ending space
    private Boolean potential = false; //This is a direction that can be explored
    private Boolean glados = false; //Will be able to check if this object's was a 0 which was the start of a row. Thereby allowing it able to be traversed. Now you're thinking with Portals ;)
    private Boolean traveled = false; //Has this space been used for travel
    private Boolean failure = false; //This is a direction that has been tried but failed

    /***
     * @param number either a 1 or 0
     * @param positionX position on row
     * @param positionY position on column
     * @param mazeHeight what is the height of this Maze, used to make sure if this position is on the edge or not
     * @param mazeWidth what is the width of this Maze, used to make sure if this position is on the edge or not
     * @throws Exception If start and end point are the same
     */
    public MazeSpaceClear(String number, int positionX, int positionY, int mazeHeight, int mazeWidth) throws Exception {
        super(number, positionX, positionY);

        /***
         * Glados check, essentially check if the space is at an edge where the program can traverse, you've referred to this as wrapping.
         */
        if (positionX == 0 || positionX == mazeWidth)
            glados = true;
        if (positionY == 0 || positionY == mazeHeight)
            glados = true;
    }

    Boolean getStart() {
        return start;
    }

    Boolean getEnd() {
        return end;
    }

    Boolean getPotential() {
        return potential;
    }

    Boolean getGlados() {
        return glados;
    }

    Boolean getTraveled() {
        return traveled;
    }

    Boolean getFailure() {
        return failure;
    }


    void setStart(Boolean start) {
        this.start = start;
    }

    void setEnd(Boolean end) {
        this.end = end;
    }

    void setPotential(Boolean potential) {
        this.potential = potential;
    }

    void setGlados(Boolean glados) {
        this.glados = glados;
    }

    void setTraveled(Boolean traveled) {
        this.traveled = traveled;
    }

    void setFailure(Boolean failure) {
        this.failure = failure;
    }

    /***
     * To string will now check if the space is a Start, End or traveled and will mark it correctly.
     * Will also check if this space is marked as both a start and end point and notify the program may not work correctly.
     * @return A string with the correct value of that MazeSpace.
     */
    @Override
    public String toString() {
        if (start && end)
            try {
                throw new Exception("toString() MazeSpaceClear.java You have set the start point and end point as the same co-ordinates this program will not work correctly\n"
                        + toStringProperties());
            } catch (Exception e) {
                e.printStackTrace();
            }

        String patternBuild = "";
        if (positionX == 0)
            patternBuild = "\n";

        if (start)
            patternBuild = patternBuild + "S";
        else if (end)
            patternBuild = patternBuild + "E";
        else if (traveled)
            patternBuild = patternBuild + "X";
        else if (failure) {
            if (propertiesPrintXtra)
                patternBuild = patternBuild + "!";
        } else if (potential) {
            if (propertiesPrintXtra)
                patternBuild = patternBuild + "~";
        } else
            patternBuild = patternBuild + " ";
        return patternBuild;
    }

    /***
     * Prints properties of object
     * @return wall, positionX, positionY, glados, traveled, start, end
     */
    @Override
    public String toStringProperties() {
        return "MazeSpaceClear Object Properties{" +
                "wall=" + wall +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", wrapping=" + glados +
                ", traveled=" + traveled +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}