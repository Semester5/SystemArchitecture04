package BangBang;

import com.cyberbotics.webots.controller.DistanceSensor;

public class EBangBangController extends BangBangController {

    private static int DIST_TOL = 100;
    private static int WALL_FAR = 120;
    private static int WALL_NEAR = 500;

    private static int D_SIDE_FRONT_L = 4;

    public EBangBangController() {
        super();
        this.distanceSensors = new DistanceSensor[]{
                getDistanceSensor("ps0"),
                getDistanceSensor("ps7"),
                getDistanceSensor("ps2"),
                getDistanceSensor("ps5"),
                getDistanceSensor("ps6")
        };
        for (int i = 0; i < distanceSensors.length; i++) {
            distanceSensors[i].enable(10);
        }
    }

    public static void main(String[] args) {
        EBangBangController controller = new EBangBangController();
        controller.run();
    }

    public void run() {
        while (step(TIME_STEP) != -1) {
            if ((distanceSensors[D_FRONT_LEFT].getValue() > DIST_TOL) && (distanceSensors[D_FRONT_RIGHT].getValue() > DIST_TOL)) {
                driveRight();
            } else if (distanceSensors[D_LEFT_LEFT].getValue() > WALL_NEAR) {
                driveRight();
            } else if (distanceSensors[D_SIDE_FRONT_L].getValue() > DIST_TOL) {
                driveRight();
            } else if (distanceSensors[D_LEFT_LEFT].getValue() < WALL_FAR) {
                driveLeft();
            } else {
                driveForward();
            }
        }
    }
}

