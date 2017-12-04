import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;

public class AControllerProp extends DifferentialWheels {
    private static int TIME_STEP = 15;
    private static int MAX_SENSOR_VALUE = 200;

    private static int S_LEFT = 0; // Sensor left
    private static int S_FRONT_LEFT = 1; // Sensor front left
    private static int S_FRONT_RIGHT = 2; // Sensor front right
    private static int S_RIGHT = 3; // Sensor left

    private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed

    private DistanceSensor[] sensors; // Array with all distance sensors

    /**
     * Constructor
     */
    public AControllerProp() {
        super();
        // get distance sensors and save them in array
        sensors = new DistanceSensor[] { getDistanceSensor("ps5"),
                getDistanceSensor("ps7"), getDistanceSensor("ps0"),
                getDistanceSensor("ps2") };
        for (int i=0; i<4; i++)
            sensors[i].enable(10);
    }

    /**
     * Main method - in this method an instance of the controller is created and
     * the method to launch the robot is called.
     *
     * @param args
     */
    public static void main(String[] args) {
        AControllerProp controller = new AControllerProp();
        controller.run();
    }

    /**
     * User defined function for initializing and running the
     * BangBangFollowTheWall class
     */
    public void run() {
        while (step(TIME_STEP) != -1) {
            driveRight();
        }
        /*
        while (step(TIME_STEP) != -1) {
            if (sensors[S_FRONT_LEFT].getValue() > MAX_SENSOR_VALUE
                    || sensors[S_LEFT].getValue() > MAX_SENSOR_VALUE) {
                // drive right - reached a wall
                driveRight();
            } else if (sensors[S_FRONT_RIGHT].getValue() > MAX_SENSOR_VALUE
                    || sensors[S_RIGHT].getValue() > MAX_SENSOR_VALUE) {
                // drive left - reached a wall
                driveLeft();
            } else {
                // drive forward if nothing is in front of the robot
                driveForward();
            }
            //System.out.println("ps7: " + sensors[S_FRONT_RIGHT].getValue());
        }
        */
    }

    /*
     * Robot drives to the light bulb
     */
    private void driveToLightBulb() {

    }

    /**
     * Robot drives to the right
     */
    private void driveRight() {
        setSpeed(MAX_SPEED, MIN_SPEED);
    }

    /**
     * Robot drives to the left
     */
    private void driveLeft() {
        setSpeed(MIN_SPEED, MAX_SPEED);
    }

    /**
     * Robot drives forward
     */
    private void driveForward() {
        setSpeed(MAX_SPEED, MAX_SPEED);
    }
}
