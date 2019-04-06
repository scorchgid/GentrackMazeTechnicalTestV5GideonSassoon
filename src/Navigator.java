public class Navigator {

    private int currentXLocation;
    private int currentYLocation;

    public Navigator(int currentXLocation, int currentYLocation) {
        this.currentXLocation = currentXLocation;
        this.currentYLocation = currentYLocation;
    }

    Boolean confirmPositionMatches(int x, int y) {
        return x == currentXLocation && y == currentYLocation;
    }

    void setCurrentLocation(int currentXLocation, int currentYLocation) {
        this.currentXLocation = currentXLocation;
        this.currentYLocation = currentYLocation;
    }

    int getCXL() {
        return currentXLocation;
    }

    int getCYL() {
        return currentYLocation;
    }

    int getCYLPlus1() {
        return currentYLocation + 1;
    }

    int getCXLPlus1() {
        return currentXLocation + 1;
    }

    int getCXLMinus1() {
        return currentXLocation - 1;
    }

    int getCYLMinus1() {
        return currentYLocation - 1;
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
