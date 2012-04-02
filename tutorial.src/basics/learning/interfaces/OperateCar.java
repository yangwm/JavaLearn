package basics.learning.interfaces;

public interface OperateCar {

    // constant declarations, if any

    // method signatures
    int turn(Direction direction,   // An enum with values RIGHT, LEFT
               double radius, double startSpeed, double endSpeed);
    int changeLanes(Direction direction, double startSpeed, double endSpeed);
    int signalTurn(Direction direction, boolean signalOn);
    int getRadarFront(double distanceToCar, double speedOfCar);
    int getRadarRear(double distanceToCar, double speedOfCar);
    
    // more method signatures
}

class Direction {
    public int dir;
    public Direction(int dir) {
        this.dir = dir;
    }
    
    public static int LEFT = 0;
    public static int RIGHT = 1;
}
