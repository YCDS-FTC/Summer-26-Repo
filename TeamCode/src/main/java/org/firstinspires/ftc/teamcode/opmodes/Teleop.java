package org.firstinspires.ftc.teamcode.opmodes;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.function.Supplier;

@Configurable
@TeleOp
public class Teleop extends OpMode {
    public static Pose startingPose; //See ExampleAuto to understand how to use this
    private final double SHIFT = 0;
    private Follower follower;
    private boolean automatedDrive;
    private Supplier<PathChain> pathChain;
    private TelemetryManager telemetryM;
    private double offsetHeading;
    private boolean isRedAlliance;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        pathChain = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(54, 94))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(135), 0.8))
                .build();
        //Lazy curve generation might look a little different to how we usually make paths,
        //This is because we are using a Lambda expression instead of calling follower.pathbuilder()
        //To use this though, we declare this as a Supplier<PathChain>. And we to call the path chain we instead do pathChain.get()
    }

    @Override
    public void start() {
        //The parameter controls whether the Follower should use break mode on the motors (using it is recommended).
        //In order to use float mode, add .useBrakeModeInTeleOp(true); to your Drivetrain Constants in Constant.java (for Mecanum)
        //If you don't pass anything in, it uses the default (false)
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        //Call this once per loop
        follower.update();
        telemetryM.update();


        /*
        Alliance selection, this is what the Gearhounds drive members are used too.
        It makes sense because you press the left button to select the left goal and vice versa
        */
        if (gamepad1.dpad_left || gamepad2.share) {
            isRedAlliance = false;
        } else if (gamepad1.dpad_right || gamepad2.options) {
            isRedAlliance = true;
        }

        /*
        Drive code, this is what actually tells the robot to move, and we multiply the joystick values by the shift value.
        The shift value is just a multiplier that would control the drivetrain speed. 1 being full speed and 0 being no speed
        As far as I can tell the offsetHeading it how you recenter robot's "forward" when driving field-centric. Documentation is crappy
         */
        follower.setTeleOpDrive(
                -gamepad1.left_stick_y * SHIFT,
                -gamepad1.left_stick_x * SHIFT,
                -gamepad1.right_stick_x * SHIFT,
                offsetHeading
        );


        /*
        Offset heading / baby relocalize, this just takes the current robots position and just modifies the heading value base off of what alliance you are on
        the offsetHeading is definitely needed, but the follower.setPose could be removed because it relies on the driver actually standing on the correct side
         */
        if (gamepad1.options && isRedAlliance) {
            offsetHeading = follower.getPose().getHeading();
            follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), Math.toRadians(180)));
        } else if (gamepad1.options && !isRedAlliance) {
            offsetHeading = follower.getPose().getHeading();
            follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), Math.toRadians(0)));
        }


        /*
        I think it could be cool to look into some automated path-following stuff, I also saw that there was a command to hold position,
        that could be useful for something like automatic park and park holding
         */

//        //Automated PathFollowing
//        if (gamepad1.aWasPressed()) {
//            follower.followPath(pathChain.get());
//            automatedDrive = true;
//        }
//
//        //Stop automated following if the follower is done
//        if (automatedDrive && (gamepad1.bWasPressed() || !follower.isBusy())) {
//            follower.startTeleopDrive();
//            automatedDrive = false;
//        }

        /*
        telemetry to read of the robots current position
        also why is there literally no documentation for this telemetryManager doo-hicky
         */

        telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)", follower.getPose().getX(), follower.getPose().getY()));
        telemetry.addLine(String.format("Angle %6.1f (degrees)", Math.toDegrees(follower.getPose().getHeading())));

//        telemetryM.debug("position", follower.getPose());
//        telemetryM.debug("velocity", follower.getVelocity());
//        telemetryM.debug("automatedDrive", automatedDrive);
    }
}