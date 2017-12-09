package Proportional;

import com.cyberbotics.webots.controller.LightSensor;

public class AProportionalController extends BaseProportionalController {

    public AProportionalController() {
        super();
    }

    @Override
    protected void init() {
        this.sensors = new LightSensor[] {
                getLightSensor("ls0"),
                getLightSensor("ls1"),
                getLightSensor("ls2"),
                getLightSensor("ls3"),
                getLightSensor("ls4"),
                getLightSensor("ls5"),
                getLightSensor("ls6"),
                getLightSensor("ls7")
        };
        this.actorArray = new double[][]{{1, 0.9, 0.8, 0.6, 0.4, 0.2, 0.1, 0}, {0, 0.1, 0.2, 0.4, 0.6, 0.8, 0.9, 1}};
        this.sensorArray = new double[sensors.length][1];
    }

    @Override
    protected void move(double leftMotorSpeed, double rightMotorSpeed) {
        //Normalisierung der Motorenewrte, da der Robi sonst sehr langsam ist
        if(leftMotorSpeed > rightMotorSpeed) {
            rightMotorSpeed *= MAX_MOTOR_SPEED / leftMotorSpeed;
            leftMotorSpeed = MAX_MOTOR_SPEED;
        } else {
            leftMotorSpeed *= MAX_MOTOR_SPEED / rightMotorSpeed;
            rightMotorSpeed = MAX_MOTOR_SPEED;
        }
        System.out.println("left: " + leftMotorSpeed + "\tright: " + rightMotorSpeed);
        setSpeed(leftMotorSpeed, rightMotorSpeed);
    }

    public static void main(String[] args) {
        AProportionalController controller = new AProportionalController();
        controller.run();
    }
}