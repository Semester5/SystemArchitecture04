package BangBang;

import com.cyberbotics.webots.controller.DifferentialWheels;
import com.cyberbotics.webots.controller.LightSensor;

public class ABangBangController extends BangBangController {

    public ABangBangController() {
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
            double hysteresis = Math.abs(lightSensors[0].getValue() - lightSensors[7].getValue());
            if (lightSensors[0].getValue() > lightSensors[7].getValue() && hysteresis > MAX_HYSTERESIS && hysteresis > 50) {
                driveLeft();
            } else if (lightSensors[0].getValue() < lightSensors[7].getValue() && hysteresis > MAX_HYSTERESIS && hysteresis > 50) {
                driveRight();
            } else {
                driveForward();
            }
        }
    }
    public static void main(String[] args) {
        ABangBangController controller = new ABangBangController();
        controller.run();
    }
}