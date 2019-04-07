class MazeSpace {
    Boolean wall;
    private int positionX;
    private int positionY;

    MazeSpace(String number, int positionX, int positionY) throws Exception {
        setWall(number);
        this.positionX = positionX;
        this.positionY = positionY;
    }

    private void setWall(String numberWall) throws Exception {
        if (numberWall.equals("1"))
            wall = true;
        else if (numberWall.equals("0"))
            wall = false;
        else
            throw new Exception("MazeSpace (Constructor) MazeSpace.java Given MazeSpace Object did not equal a 1 or 0.\nItem given was: " + numberWall
                    + "\nProperties:\n" + toStringProperties());
    }

    String getWallAsNumber() {
        return wall ? "1" : "0";
    }

    int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    @Override
    public String toString() {
        if (positionX == 0)
            return "\n#";
        else
            return "#";
    }

    /***
     * @return a printed String of properties of object
     */
    public String toStringProperties() {
        return "MazeSpace Object Properties{" +
                "wall=" + wall +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }
}