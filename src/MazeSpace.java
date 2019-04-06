class MazeSpace {
    Boolean wall;
    private int positionX;
    private int positionY;

    MazeSpace(String number, int positionX, int positionY) throws Exception {
        if (number.equals("1"))
            wall = true;
        else if (number.equals("0"))
            wall = false;
        else
            throw new Exception("MazeSpace (Constructor) MazeSpace.java Given MazeSpace Object did not equal a 1 or 0.\nItem given was: " + number
                    + "\nProperties:\n" + toStringProperties());
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
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
     * Prints properties of object
     * @return wall, positionX, positionY
     */
    public String toStringProperties() {
        return "MazeSpace Object Properties{" +
                "wall=" + wall +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }
}