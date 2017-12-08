import com.cyberbotics.webots.controller.Camera;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class CBangBangController extends BangBangController {
    private static int BALL_FOUND_VALUE  = 50;
    private static int GREEN_TOL = 10;
    private static int BLUE_TOL = 10;
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
       // boolen foundEl
        while (step(TIME_STEP) != -1 ) {
            int[] image = camera.getImage();
            int red = Camera.imageGetRed(image, camera.getWidth(), camera.getHeight() /2 , camera.getWidth()/2);
            int blue = Camera.imageGetBlue(image, camera.getWidth(), camera.getHeight() /2 , camera.getWidth()/2);
            int green = Camera.imageGetGreen(image, camera.getWidth(), camera.getHeight() /2 , camera.getWidth()/2);
            System.out.println("RED: " + red + " GREEN: " + green + " BLUE: " + blue);
            double diff = Math.abs(distanceSensors[D_FRONT_RIGHT].getValue() - distanceSensors[D_FRONT_LEFT].getValue());
            if(distanceSensors[D_FRONT_RIGHT].getValue() > distanceSensors[D_FRONT_LEFT].getValue() && diff > BALL_FOUND_VALUE) {
                driveRight();
            } else if (distanceSensors[D_FRONT_RIGHT].getValue() < distanceSensors[D_FRONT_LEFT].getValue() && diff > BALL_FOUND_VALUE) {
                driveLeft();
            }else  if (distanceSensors[D_LEFT_LEFT].getValue() < distanceSensors[D_RIGHT_RIGHT].getValue() && diff > BALL_FOUND_VALUE){
                driveLeft();
            } else  if (distanceSensors[D_LEFT_LEFT].getValue() > distanceSensors[D_RIGHT_RIGHT].getValue() && diff > BALL_FOUND_VALUE) {
                driveRight();
            } else {
                driveForward();
            }
        }
    }

    public static void main(String[] args) {
        CBangBangController controller = new CBangBangController();
        controller.run();
    }


}
