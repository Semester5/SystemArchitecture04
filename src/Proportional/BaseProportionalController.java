package Proportional;

import com.cyberbotics.webots.controller.*;

public abstract class BaseProportionalController extends DifferentialWheels {

    private static final int TIME_STEP = 15;
    private static final int MAX_DISTANCE_SENSOR_VALUE = 200;
    protected static final int MAX_LIGHT_SENSOR_VALUE = 4250;
    protected static final int MAX_MOTOR_SPEED = 1000;
    protected static int CONSTANT_LEFT_MOTOR = -1;
    protected static int CONSTANT_RIGHT_MOTOR = -2;

    protected Device[] sensors;
    protected double actorArray[][];
    protected double sensorArray[][];

    public BaseProportionalController() {
        super();
        init();

        for (int i = 0; i < sensors.length; i++) {
            if(sensors[i] instanceof LightSensor) {
                ((LightSensor) sensors[i]).enable(10);
            }
            if(sensors[i] instanceof DistanceSensor) {
                ((DistanceSensor) sensors[i]).enable(10);
            }
        }
    }

    public void run() {
        while (step(TIME_STEP) != -1) {
            step();
        }
    }

    protected void step() {
        sensorArray = new double[sensors.length][1];
        for (int i = 0; i < sensors.length; i++) {
            if(sensors[i] instanceof LightSensor) {
                sensorArray[i][0] = getPercentLightSensorValue(((LightSensor) sensors[i]).getValue());
            }
            if(sensors[i] instanceof DistanceSensor) {
                sensorArray[i][0] = getPercentDistanceSensorValue(((DistanceSensor) sensors[i]).getValue());
            }
        }

        double[][] result = multiplyMatrix(actorArray, sensorArray);
        double[][] maxSpeedArray = { {MAX_MOTOR_SPEED} };
        double[][] motorArray = multiplyMatrix(result, maxSpeedArray);

        double leftMotorSpeed = getSpeedForWheel(motorArray, 0);
        double rightMotorSpeed = getSpeedForWheel(motorArray, 1);

        move(leftMotorSpeed + CONSTANT_LEFT_MOTOR, rightMotorSpeed + CONSTANT_RIGHT_MOTOR);
    }

    protected double[][] multiplyMatrix(double firstArray[][], double secondArray[][]) {
        double[][] result = new double[firstArray.length][secondArray[0].length];

        for (int i = 0; i < firstArray.length; i++) {
            for (int j = 0; j < secondArray[0].length; j++) {
                for (int k = 0; k < firstArray[0].length; k++) {
                    result[i][j] += firstArray[i][k] * secondArray[k][j];
                }
            }
        }
        return result;
    }

    protected double getSpeedForWheel(double[][] result, int i) {
        double value = 0.0;
        for (int j = 0; j < result[i].length; j++) {
            value += result[i][j];
        }
        return value / 4; // 4 = Summe der Gesamtgewichtung der Matrixwerte, ansonsten würde die MAXSPEED immer überschritten werden...
    }

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

    protected double getPercentLightSensorValue(double value) {
        return getNegativePercentSensorValue(value, MAX_LIGHT_SENSOR_VALUE);
    }

    protected double getPercentDistanceSensorValue(double value) {
        return getPercentSensorValue(value, MAX_DISTANCE_SENSOR_VALUE);
    }

    private double getPercentSensorValue(double value, int maxValue) {
        return value / maxValue;
    }

    private double getNegativePercentSensorValue(double value, int maxValue) {
        double newSensorValue = value / maxValue;
        return 1 - newSensorValue;
    }

    protected abstract void init();
}
