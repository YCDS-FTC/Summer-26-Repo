#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

@TeleOp(name = "${NAME}", group = "Example")
#parse("File Header.java")
public class ${NAME} extends OpMode {

private final hackingHoundsHardware robot = new hackingHoundsHardware();

  private ElapsedTime runtime = new ElapsedTime();

  /**
   * This method will be called once, when the INIT button is pressed.
   */
  @Override
  public void init() {
    robot.init(hardwareMap);
    telemetry.addData("Status", "Initialized");
  }

  /**
   * This method will be called repeatedly during the period between when
   * the INIT button is pressed and when the START button is pressed (or the
   * OpMode is stopped).
   */
  @Override
  public void init_loop() {
  }

  /**
   * This method will be called once, when the START button is pressed.
   */
  @Override
  public void start() {
    runtime.reset();
  }

  /**
   * This method will be called repeatedly during the period between when
   * the START button is pressed and when the OpMode is stopped.
   */
  @Override
  public void loop() {
    telemetry.addData("Status", "Run Time: " + runtime.toString());
  }

  /**
   * This method will be called once, when this OpMode is stopped.
   * <p>
   * Your ability to control hardware from this method will be limited.
   */
  @Override
  public void stop() {

  }
}
