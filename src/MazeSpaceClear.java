public class MazeSpaceClear extends MazeSpace {

    public Boolean glados = false; //Will be able to check if this object's was a 0 which was the start of a row. Thereby allowing it able to be traversed. Now you're thinking with Portals ;)
    public Boolean traveled = false; //Has this space been used for travel
    public Boolean start = false; //Is this the starting space
    public Boolean end = false; //Is this the ending space

    /***
     * @param number either a 1 or 0
     * @param positionX position on row
     * @param positionY position on column
     * @param mazeHeight what is the height of this Maze, used to make sure if this position is on the edge or not
     * @param mazeWidth what is the width of this Maze, used to make sure if this position is on the edge or not
     * @throws Exception
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

    @Override
    public String toString() {
        if (positionY == 0)
            return "\n ";
        else
            return " ";
    }

    /***
     * Prints properties of object
     * @return wall, positionX, positionY, glados, traveled, start, end
     */
    @Override
    public String toStringProperties() {
        return "MazeSpaceClear{" +
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