package Proportional;

import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.LightSensor;

public abstract class CProportionalController extends DifferentialWheels {

    protected static int TIME_STEP = 15;
    protected LightSensor[] sensors;

    public CProportionalController() {
        super();
        sensors = new LightSensor[] { getLightSensor("ls1"), getLightSensor("ls4"), getLightSensor("ls6") };
        for (int i=0; i<3; i++) {
            sensors[i].enable(10);
        }
    }

    protected double getNewSensorValue(double value) {
        double newValue = (value * 100) / 4200;
        newValue = 100 - newValue;
        return  newValue;
    }

    protected double getSpeedForWheel(double[][] result, int i) {
        double value = 0.0;
        for(int j = 0; j < result[i].length; j++) {
            value += result[i][j];
        }
        return value;
    }

    protected double [][] multiplyMatrix(double firstarray[][], double secondarray[][]) {
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
