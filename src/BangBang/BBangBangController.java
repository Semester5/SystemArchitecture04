package BangBang;

import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class BBangBangController extends BangBangController {

    public BBangBangController() {
        super();
        this.lightSensors = new LightSensor[] {
                getLightSensor("ls0"),
                getLightSensor("ls7")
        };
        this.distanceSensors = new DistanceSensor[] {
                getDistanceSensor("ps0"),
                getDistanceSensor("ps7")
        };

        for (int i = 0; i < lightSensors.length; i++) {
            lightSensors[i].enable(10);
        }
        for (int i = 0; i < distanceSensors.length; i++) {
            distanceSensors[i].enable(10);
        }
    }

    public void run() {
        boolean lightFound = false;

        while (step(TIME_STEP) != -1 ) {
            if(!lightFound) {
                System.out.println("RIGHT: " + distanceSensors[D_FRONT_RIGHT].getValue() + " LEFT: " + distanceSensors[D_FRONT_LEFT].getValue());
                double hysteresis = Math.abs(lightSensors[L_FRONT_RIGHT].getValue() - lightSensors[L_FRONT_LEFT].getValue());
                if ((distanceSensors[D_FRONT_LEFT].getValue() > 400 || distanceSensors[D_FRONT_RIGHT].getValue() > 400)) {
                    setSpeed(MIN_SPEED, MIN_SPEED);
                    lightFound = true;
                    System.out.println("ROBOT STOPPED");
                } else if (lightSensors[L_FRONT_RIGHT].getValue() > lightSensors[L_FRONT_LEFT].getValue() && hysteresis > MAX_HYSTERESIS && hysteresis > 50) {
                    driveLeft();
                } else if (lightSensors[L_FRONT_RIGHT].getValue() < lightSensors[L_FRONT_LEFT].getValue() && hysteresis > MAX_HYSTERESIS && hysteresis > 50) {
                    driveRight();
                } else {
                    driveForward();
                }
            }
        }
    }

    public static void main(String[] args) {
        BBangBangController controller = new BBangBangController();
        controller.run();
    }
}
