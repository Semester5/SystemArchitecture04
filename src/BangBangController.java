import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public abstract class BangBangController extends DifferentialWheels {

    protected static int TIME_STEP = 15;
    protected static int MIN_SPEED = 0; // min. motor speed
    protected static int MAX_SPEED = 1000; // max. motor speed
    protected static int MAX_HYSTERESIS = 1;

    protected static int L_FRONT_RIGHT = 0; //ls0
    protected static int L_FRONT_LEFT = 1; //ls7

    protected static int D_FRONT_RIGHT = 0; //ps0
    protected static int D_FRONT_LEFT = 1; //ps7

    protected LightSensor[] lightSensors;
    protected DistanceSensor[] distanceSensors;

    protected void driveRight() {
        setSpeed(MAX_SPEED, MIN_SPEED);
    }

    protected void driveLeft() {
        setSpeed(MIN_SPEED, MAX_SPEED);
    }

    protected void driveForward() {
        setSpeed(MAX_SPEED, MAX_SPEED);
    }
}
