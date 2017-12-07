import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.LightSensor;

public class AProportionalController extends DifferentialWheels {

    private static final int MAX_SENSOR_VALUE = 4250;
    private static final int MAX_SPEED = 1000;
    private static final int CONSTANT_LEFT_MOTOR = -1;
    private static final int CONSTANT_RIGHT_MOTOR = -2;


    private static int TIME_STEP = 15;
    private LightSensor[] lightSensors;

    public AProportionalController() {
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
            double actorArray[][] = { {1, 0.9, 0.8, 0.6, 0.4, 0.2, 0.1, 0 }, { 0, 0.1, 0.2, 0.4, 0.6, 0.8, 0.9, 1 } };

            double sensorArray[][] = new double[lightSensors.length][1];
            for (int i = 0; i < lightSensors.length; i++) {
                sensorArray[i][0] = getPercentSensorValue(lightSensors[i].getValue());
            }

            double[][] result = multiplyMatrix(actorArray, sensorArray);
            double[][] maxSpeedArray = { { MAX_SPEED } };
            double[][] motorArray = multiplyMatrix(result, maxSpeedArray);

            double leftMotorSpeed = getSpeedForWheel(motorArray, 0);
            double rightMotorSpeed = getSpeedForWheel(motorArray, 1);

            if(leftMotorSpeed > rightMotorSpeed) {
                rightMotorSpeed *= MAX_SPEED / leftMotorSpeed;
                leftMotorSpeed = MAX_SPEED;
            } else {
                leftMotorSpeed *= MAX_SPEED / rightMotorSpeed;
                rightMotorSpeed = MAX_SPEED;
            }
            setSpeed(leftMotorSpeed + CONSTANT_LEFT_MOTOR, rightMotorSpeed + CONSTANT_RIGHT_MOTOR);
        }
    }

    private double getPercentSensorValue(double value) {
        double newSensorValue = value / MAX_SENSOR_VALUE;
        newSensorValue = 1 - newSensorValue;
        return newSensorValue;
    }

    private double[][] multiplyMatrix(double firstArray[][], double secondArray[][]) {
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

    private double getSpeedForWheel(double[][] result, int i) {
        double value = 0.0;
        for (int j = 0; j < result[i].length; j++) {
            value += result[i][j];
        }
        return value / 4;
    }

    public static void main(String[] args) {
        AProportionalController controller = new AProportionalController();
        controller.run();
    }
}