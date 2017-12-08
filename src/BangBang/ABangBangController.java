package BangBang;

import com.cyberbotics.webots.controller.LightSensor;

public class ABangBangController extends BangBangController {

    public ABangBangController() {
        super();
        this.lightSensors = new LightSensor[] {
                getLightSensor("ls1"),
                getLightSensor("ls6")
        };

        for (int i = 0; i < lightSensors.length; i++) {
            lightSensors[i].enable(10);
        }
    }

    public void run() {
        while (step(TIME_STEP) != -1) {
            double hysteresis = Math.abs(lightSensors[L_FRONT_RIGHT].getValue() - lightSensors[L_FRONT_LEFT].getValue());
            if (lightSensors[L_FRONT_RIGHT].getValue() > lightSensors[L_FRONT_LEFT].getValue() && hysteresis > MAX_HYSTERESIS && hysteresis > 60) {
                driveLeft();
            } else if (lightSensors[L_FRONT_RIGHT].getValue() < lightSensors[L_FRONT_LEFT].getValue() && hysteresis > MAX_HYSTERESIS && hysteresis > 60) {
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