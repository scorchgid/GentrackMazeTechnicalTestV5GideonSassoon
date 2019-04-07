class MazeSpaceClearPotential extends MazeSpaceClear {

    private int lastPositionX;
    private int lastPositionY;

    /***
     * @param number either a 1 or 0
     * @param positionX position on row
     * @param positionY position on column
     * @param mazeHeight what is the height of this Maze, used to make sure if this position is on the edge or not
     * @param mazeWidth what is the width of this Maze, used to make sure if this position is on the edge or not
     * @throws Exception If start and end point are the same
     */
    MazeSpaceClearPotential(String number, int positionX, int positionY, int mazeHeight, int mazeWidth, int lastPositionX, int lastPositionY) throws Exception {
        super(number, positionX, positionY, mazeHeight, mazeWidth);
        this.lastPositionX = lastPositionX;
        this.lastPositionY = lastPositionY;
    }

    int getLastPositionX() {
        return lastPositionX;
    }

    int getLastPositionY() {
        return lastPositionY;
    }

    /***
     * @return a printed String of properties of object
     */
    @Override
    public String toStringProperties() {
        return "MazeSpaceClear Object Properties{" +
                "wall=" + wall +
                ", positionX=" + this.getPositionX() +
                ", positionY=" + this.getPositionY() +
                ", lastPositionX=" + lastPositionX +
                ", lastPositionY=" + lastPositionY +
                ", start=" + this.getStart() +
                ", end=" + this.getEnd() +
                ", potential=" + this.getPotential() +
                ", wrappingXUp=" + this.getWrappingXUp() +
                ", wrappingXDown=" + this.getWrappingXDown() +
                ", wrappingYUp=" + this.getWrappingYUp() +
                ", wrappingYDown=" + this.getWrappingYDown() +
                ", traveled=" + this.getTraveled() +
                ", failure=" + this.getFailure() +
                '}';
    }
}
