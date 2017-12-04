import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class AControllerProp extends DifferentialWheels {
    private static int TIME_STEP = 15;
    private static int MAX_SENSOR_VALUE = 200;

    private static int S_LEFT = 0; // Sensor left
    private static int S_FRONT_LEFT = 1; // Sensor front left
    private static int S_FRONT_RIGHT = 2; // Sensor front right
    private static int S_RIGHT = 3; // Sensor left

    private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed

    private LightSensor[] sensors;

    //Sensorenwerte: 4 (getDistanceSensor)
    //Aktorenwerte 2 (links, rechts)

    /**
     * Constructor
     */
    public AControllerProp() {
        super();
        // get distance sensors and save them in array
        sensors = new LightSensor[] { getLightSensor("ls1"), getLightSensor("ls4"), getLightSensor("ls6") };
        for (int i=0; i<4; i++) {
            sensors[i].enable(10);
        }
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

            double sensorarray[][] = {};
            for(int i = 0; i < sensors.length; i++) {
                sensorarray[i][0] = sensors[i].getValue();
            }

            double aktorenarray[][] = {{10, 5, 20}, {20, 5, 10}};
            double [][] result =  multiplyMatrix(aktorenarray, sensorarray);

            //driveRight();
        }
    }

    private double [][] multiplyMatrix(double firstarray[][], double secondarray[][]) {
        /* Create another array to store the result using the original arrays' lengths on row and column respectively. */
        double [][] result = new double[firstarray.length][secondarray[0].length];

        for (int i = 0; i < firstarray.length; i++) {
            for (int j = 0; j < secondarray[0].length; j++) {
                for (int k = 0; k < firstarray[0].length; k++) {
                    result[i][j] += firstarray[i][k] * secondarray[k][j];
                }
            }
        }
        return result;
    }
}