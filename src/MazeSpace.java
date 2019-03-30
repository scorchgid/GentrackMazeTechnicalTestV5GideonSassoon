public class MazeSpace {

    private Boolean wall;
    private Boolean glados = false; //Will be able to check if this object's was a 0 which was the start of a row. Thereby allowing it able to be traversed
    public MazeSpace(String number) throws Exception {
        if (number.equals("0"))
            wall = true;
        else if (number.equals("1"))
            wall = false;
        else
            throw new Exception("Give MazeSpace did not equal a 1 or 0.\nItem given was: " + number);
    }
}
