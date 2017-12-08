package Proportional;

import com.cyberbotics.webots.controller.Device;
import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class BProportionalController extends BaseProportionalController {

    public BProportionalController() {
        super();
    }

    @Override
    protected void init() {
        this.sensors = new Device[] {
            getLightSensor("ls0"),
            getLightSensor("ls1"),
            getLightSensor("ls2"),
            getLightSensor("ls3"),
            getLightSensor("ls4"),
            getLightSensor("ls5"),
            getLightSensor("ls6"),
            getLightSensor("ls7"),
            getDistanceSensor("ps0"),
            getDistanceSensor("ps7")
        };

        this.actorArray = new double[][]{ { 1, 0.9, 0.8, 0.6, 0.4, 0.2, 0.1, 0, -0.4, -0.4 }, { 0, 0.1, 0.2, 0.4, 0.6, 0.8, 0.9, 1, -0.4, -0.4  } };
        this.sensorArray = new double[sensors.length][1];
    }

    @Override
    protected void move(double leftMotorSpeed, double rightMotorSpeed) {
        leftMotorSpeed = Math.max(0,leftMotorSpeed);
        rightMotorSpeed = Math.max(0,rightMotorSpeed);

        System.out.println("left: " +leftMotorSpeed + "\tright: " + rightMotorSpeed);
        setSpeed(leftMotorSpeed, rightMotorSpeed);
    }

    public static void main(String[] args) {
        BProportionalController controller = new BProportionalController();
        controller.run();
    }
}