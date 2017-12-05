import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.LightSensor;

public class BControllerProp extends DifferentialWheels {

    protected static int TIME_STEP = 15;
    protected LightSensor[] sensors;

    public BControllerProp() {
        super();
        sensors = new LightSensor[] { getLightSensor("ls1"), getLightSensor("ls4"), getLightSensor("ls6") };
        for (int i=0; i<3; i++) {
            sensors[i].enable(10);
        }
    }

    public static void main(String[] args) {
        BControllerProp controller = new BControllerProp();
        controller.run();
    }
    public void run() {
        while (step(TIME_STEP) != -1) {
            double sensorarray[][] = new double[3][1];
            boolean inFrontOfLight = false;
            for(int i = 0; i < sensors.length; i++) {
                if(sensors[i].getValue() <= 1.0 ) {
                    inFrontOfLight = true;
                    break;
                }
                sensorarray[i][0] = getNewSensorValue(sensors[i].getValue());
            }
            if(!inFrontOfLight) {
                double aktorenarray[][] = {{1, 0.3, 0}, {0, 0.7, 1}};
                double[][] result = multiplyMatrix(aktorenarray, sensorarray);

                double rightSpeed = getSpeedForWheel(result, 1);
                double leftSpeed = getSpeedForWheel(result, 0);

                setSpeed(leftSpeed + 500, rightSpeed + 500);
            } else {
                break;
            }
        }
    }

    private double getNewSensorValue(double value) {
        double newValue = (value * 100) / 4200;
        newValue = 100 - newValue;
        return  newValue;
    }

    private double getSpeedForWheel(double[][] result, int i) {
        double value = 0.0;
        for(int j = 0; j < result[i].length; j++) {
            value += result[i][j];
        }
        return value;
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
