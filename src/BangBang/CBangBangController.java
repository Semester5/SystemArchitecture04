package BangBang;

import com.cyberbotics.webots.controller.Camera;
import com.cyberbotics.webots.controller.DistanceSensor;

public class CBangBangController extends BangBangController {
    private static int BALL_FOUND_VALUE  = 1000;
    private static int GREEN_TOL = 90;
    private static int BLUE_TOL = 90;
    private static int RED_TOL = 140;
    private Camera camera;

    public CBangBangController() {
        super();
        this.distanceSensors = new DistanceSensor[] {
                getDistanceSensor("ps0"),
                getDistanceSensor("ps7"),
                getDistanceSensor("ps2"),
                getDistanceSensor("ps5")
        };
        for (int i = 0; i < distanceSensors.length; i++) {
            distanceSensors[i].enable(10);
        }
        camera = getCamera("camera");
        camera.enable(10);
    }

    public void run() {
        boolean foundElement = false;
        while (step(TIME_STEP) != -1 ) {
            if(!foundElement) {
                int[] image = camera.getImage();
                int red = Camera.imageGetRed(image, camera.getWidth(), camera.getWidth() / 2, camera.getHeight() / 2);
                int blue = Camera.imageGetBlue(image, camera.getWidth(), camera.getWidth() / 2, camera.getHeight() / 2);
                int green = Camera.imageGetGreen(image, camera.getWidth(), camera.getWidth() / 2, camera.getHeight() / 2);
                if((red < RED_TOL) && (blue < BLUE_TOL) && (green < GREEN_TOL)) {
                    foundElement = true;
                } else {
                    System.out.println("TURN ------ RED: " + red + " GREEN: " + green + " BLUE: " + blue);
                    driveLeft();
                }
                System.out.println("RED: " + red + " GREEN: " + green + " BLUE: " + blue);
            } else {
                double diff = Math.abs(distanceSensors[D_FRONT_RIGHT].getValue() - distanceSensors[D_FRONT_LEFT].getValue());
                if (distanceSensors[D_FRONT_RIGHT].getValue() > distanceSensors[D_FRONT_LEFT].getValue() && diff > BALL_FOUND_VALUE) {
                    driveRight();
                } else if (distanceSensors[D_FRONT_RIGHT].getValue() < distanceSensors[D_FRONT_LEFT].getValue() && diff > BALL_FOUND_VALUE) {
                    driveLeft();
                } else {
                    driveForward();
                }
            }
        }
    }

    public static void main(String[] args) {
        CBangBangController controller = new CBangBangController();
        controller.run();
    }

}
