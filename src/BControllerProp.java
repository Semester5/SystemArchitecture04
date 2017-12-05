import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.LightSensor;

public class BControllerProp extends ControllerProp {

    public BControllerProp() {
        super();
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
                if(sensors[i].getValue() <= 1 ) {
                    inFrontOfLight = true;
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

}
