package Proportional;

import com.cyberbotics.webots.controller.DistanceSensor;

public class EProportionalController extends BaseProportionalController {

    public EProportionalController() {
        super();
    }

    @Override
    protected void init() {
        CONSTANT_LEFT_MOTOR = 1000;
        CONSTANT_RIGHT_MOTOR = 1000;

        this.sensors = new DistanceSensor[] {
            getDistanceSensor("ps0"),
            getDistanceSensor("ps7"),
        };

        this.actorArray = new double[][]{ { 0, 0 }, { 5, 10 } };
        this.sensorArray = new double[sensors.length][1];
    }

    @Override
    protected void move(double leftMotorSpeed, double rightMotorSpeed) {
        leftMotorSpeed = Math.max(0,leftMotorSpeed);
        rightMotorSpeed = Math.max(0,rightMotorSpeed);

        leftMotorSpeed = Math.min(MAX_MOTOR_SPEED, leftMotorSpeed);
        rightMotorSpeed = Math.min(MAX_MOTOR_SPEED, rightMotorSpeed);

        System.out.println("left: " +leftMotorSpeed + "\tright: " + rightMotorSpeed +
                "ps0: " + ((DistanceSensor) sensors[0]).getValue() + "\tps7: " + ((DistanceSensor) sensors[1]).getValue());
        setSpeed(leftMotorSpeed, rightMotorSpeed);
    }

    @Override
    protected double getPercentDistanceSensorValue(double value) {
        return getNegativePercentSensorValue(value, MAX_DISTANCE_SENSOR_VALUE);
    }

    public static void main(String[] args) {
        EProportionalController controller = new EProportionalController();
        controller.run();
    }
}
