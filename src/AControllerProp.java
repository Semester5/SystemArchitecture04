import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class AControllerProp extends ControllerProp {

    public AControllerProp() {
        super();
    }

    public static void main(String[] args) {
        AControllerProp controller = new AControllerProp();
        controller.run();
    }

    public void run() {
        while (step(TIME_STEP) != -1) {
            double sensorarray[][] = new double[3][1];
            for(int i = 0; i < sensors.length; i++) {
                sensorarray[i][0] = getNewSensorValue(sensors[i].getValue());
            }
            double aktorenarray[][] = {{1, 0.3, 0}, {0, 0.7, 1}};
            double [][] result =  multiplyMatrix(aktorenarray, sensorarray);

            double rightSpeed = getSpeedForWheel(result, 1);
            double leftSpeed = getSpeedForWheel(result, 0);

            setSpeed(leftSpeed + 500, rightSpeed + 500);
        }
    }
}
