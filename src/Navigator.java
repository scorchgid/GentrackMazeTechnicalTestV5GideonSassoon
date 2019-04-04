public class Navigator {

    private int currentXLocation;
    private int currentYLocation;

    Boolean confirmPositionMatches(int x, int y) {
        return x == currentXLocation && y == currentYLocation;
    }

    public void setCurrentLocation(int currentXLocation, int currentYLocation) {
        this.currentXLocation = currentXLocation;
        this.currentYLocation = currentYLocation;
    }

    public int getCXL() {
        return currentXLocation;
    }

    public int getCYL() {
        return currentYLocation;
    }

    Boolean confirmPositionMatches(String xy) {
        if (xy.equals(currentXLocation + currentYLocation))
            return true;
        else {
            System.out.println("Position does not match\nCurrentX: " + currentXLocation + currentYLocation + " Submitted xy: " + xy);
            return false;
        }
    }

    @Override
    public String toString() {
        return "Navigator{" +
                "currentXLocation=" + currentXLocation +
                ", currentYLocation=" + currentYLocation +
                '}';
    }
}
