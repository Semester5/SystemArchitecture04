import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.LightSensor;

public class ABangBangController extends DifferentialWheels {
    private static int TIME_STEP = 15;

    private static int MAX_SENSOR_VALUE = 4250;
    private static int MIN_SPEED = 0; // min. motor speed
    private static int MAX_SPEED = 1000; // max. motor speed
    private static int MAX_HYSTERESIS = 1;

    private LightSensor[] lightSensors;

    /**
     * Constructor
     */
    public ABangBangController() {
        super();
        this.lightSensors = new LightSensor[] {
                getLightSensor("ls0"),
                getLightSensor("ls1"),
                getLightSensor("ls2"),
                getLightSensor("ls3"),
                getLightSensor("ls4"),
                getLightSensor("ls5"),
                getLightSensor("ls6"),
                getLightSensor("ls7")
        };

        for (int i = 0; i < lightSensors.length; i++) {
            lightSensors[i].enable(10);
        }
    }

    public void run() {
        while (step(TIME_STEP) != -1) {
            double hysteresis = Math.abs(lightSensors[0].getValue() - lightSensors[7].getValue());
            if (lightSensors[0].getValue() > lightSensors[7].getValue() && hysteresis > MAX_HYSTERESIS) {
                driveLeft();
            } else if (lightSensors[0].getValue() < lightSensors[7].getValue() && hysteresis > MAX_HYSTERESIS) {
                driveRight();
            } else {
                driveForward();
            }
        }
    }

    private void driveRight() {
        setSpeed(MAX_SPEED, MIN_SPEED);
    }

    private void driveLeft() {
        setSpeed(MIN_SPEED, MAX_SPEED);
    }

    private void driveForward() {
        setSpeed(MAX_SPEED, MAX_SPEED);
    }

    public static void main(String[] args) {
        ABangBangController controller = new ABangBangController();
        controller.run();
    }
}