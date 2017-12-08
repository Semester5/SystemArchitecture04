import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.LightSensor;

public class CBangBangController extends BangBangController {
    private static int BALL_FOUND_VALUE  = 1000;

    public CBangBangController() {
        super();
        this.distanceSensors = new DistanceSensor[] {
                getDistanceSensor("ps0"),
                getDistanceSensor("ps7")
        };
        for (int i = 0; i < distanceSensors.length; i++) {
            distanceSensors[i].enable(2);
        }
    }


    public void run() {
        while (step(TIME_STEP) != -1 ) {
            double diff = Math.abs(distanceSensors[D_FRONT_RIGHT].getValue() - distanceSensors[D_FRONT_LEFT].getValue());
            if(diff > BALL_FOUND_VALUE) {
                driveRight();
            } else if (diff < BALL_FOUND_VALUE) {
                driveLeft();
            }else {
                driveForward();
            }

        }
    }

    public static void main(String[] args) {
        CBangBangController controller = new CBangBangController();
        controller.run();
    }


}
