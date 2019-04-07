public class MazeSpaceClear extends MazeSpace {
    private Boolean propertiesPrintXtra = true; //See README.txt

    private Boolean start = false; //Is this the starting space
    private Boolean end = false; //Is this the ending space
    private Boolean potential = false; //This is a direction that can be explored
    private Boolean wrappingXUp = false; //Will be able to check if this object's was a 0 which was the start of a row. Thereby allowing it able to be traversed. Now you're thinking with Portals ;)
    private Boolean wrappingXDown = false; //Will be able to check if this object's was a 0 which was the start of a row. Thereby allowing it able to be traversed. Now you're thinking with Portals ;)
    private Boolean wrappingYUp = false; //Will be able to check if this object's was a 0 which was the start of a row. Thereby allowing it able to be traversed. Now you're thinking with Portals ;)
    private Boolean wrappingYDown = false; //Will be able to check if this object's was a 0 which was the start of a row. Thereby allowing it able to be traversed. Now you're thinking with Portals ;)
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
        determineWrapping(positionX, positionY, mazeWidth, mazeHeight);
    }

    /***
     * Wrapping check, essentially check if the space is at an edge where the program can traverse, you've referred to this as wrapping.
     * @param positionX
     * @param positionY
     * @param mazeWidth
     * @param mazeHeight
     */
    private void determineWrapping(int positionX, int positionY, int mazeWidth, int mazeHeight) {
        if (positionX == 0)
            wrappingXUp = true;
        if (positionX == mazeWidth)
            wrappingXDown = true;
        if (positionY == 0)
            wrappingYUp = true;
        if (positionY == mazeHeight)
            wrappingYDown = true;
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

    Boolean getWrappingXUp() {
        return wrappingXUp;
    }

    Boolean getWrappingXDown() {
        return wrappingXDown;
    }

    Boolean getWrappingYUp() {
        return wrappingYUp;
    }

    Boolean getWrappingYDown() {
        return wrappingYDown;
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

    void setWrappingXUp(Boolean wrappingX) {
        this.wrappingXUp = wrappingX;
    }

    void setWrappingXDown(Boolean wrappingX) {
        this.wrappingXDown = wrappingX;
    }

    void setWrappingYUp(Boolean wrappingY) {
        this.wrappingYUp = wrappingY;
    }

    void setWrappingYDown(Boolean wrappingY) {
        this.wrappingYDown = wrappingY;
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
        if (this.getPositionX() == 0)
            patternBuild = "\n";

        if (start)
            patternBuild = patternBuild + "S";
        else if (end)
            patternBuild = patternBuild + "E";
        else if (failure) {
            if (propertiesPrintXtra)
                patternBuild = patternBuild + "!";
        } else if (traveled)
            patternBuild = patternBuild + "X";
        else if (potential) {
            if (propertiesPrintXtra)
                patternBuild = patternBuild + "~";
        } else
            patternBuild = patternBuild + " ";
        return patternBuild;
    }
//Prints properties of object

    /***
     * @return a printed String of properties of object
     */
    @Override
    public String toStringProperties() {
        return "MazeSpaceClear Object Properties{" +
                "wall=" + wall +
                ", positionX=" + this.getPositionX() +
                ", positionY=" + this.getPositionY() +
                ", start=" + start +
                ", end=" + end +
                ", potential=" + potential +
                ", wrappingXUp=" + wrappingXUp +
                ", wrappingXDown=" + wrappingXDown +
                ", wrappingYUp=" + wrappingYUp +
                ", wrappingYDown=" + wrappingYDown +
                ", traveled=" + traveled +
                ", failure=" + failure +
                '}';
    }
}