package Proportional;

import com.cyberbotics.webots.controller.*;

public class CProportionalController extends BaseProportionalController {

    public CProportionalController() {
        super();
    }

    @Override
    protected void init() {
        CONSTANT_LEFT_MOTOR = 500;
        CONSTANT_RIGHT_MOTOR = 501;

        this.sensors = new DistanceSensor[] {
                getDistanceSensor("ps0"),
                getDistanceSensor("ps7")
        };

        this.actorArray = new double[][]{ { 10, -10 }, { -10, 10 } };
        this.sensorArray = new double[sensors.length][1];
    }

    @Override
    protected void move(double leftMotorSpeed, double rightMotorSpeed) {
        leftMotorSpeed = Math.max(0,leftMotorSpeed);
        rightMotorSpeed = Math.max(0,rightMotorSpeed);

        leftMotorSpeed = Math.min(MAX_MOTOR_SPEED, leftMotorSpeed);
        rightMotorSpeed = Math.min(MAX_MOTOR_SPEED, rightMotorSpeed);

        System.out.println("left: " +leftMotorSpeed + "\tright: " + rightMotorSpeed);
        setSpeed(leftMotorSpeed, rightMotorSpeed);
    }

    public static void main(String[] args) {
        CProportionalController controller = new CProportionalController();
        controller.run();
    }
}