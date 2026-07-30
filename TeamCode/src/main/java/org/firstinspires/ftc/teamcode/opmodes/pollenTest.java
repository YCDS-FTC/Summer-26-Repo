package org.firstinspires.ftc.teamcode.opmodes;
import static com.pedropathing.ivy.Scheduler.schedule;

import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.vision.Vcons;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

@TeleOp

public class pollenTest extends LinearOpMode {

    boolean autoDrive = false;
    @Override
    public void runOpMode(){
        hackingHoundsHardware robot = new hackingHoundsHardware();
        robot.init(hardwareMap);
        follower = Constants.createFollower(hardwareMap);

       Vcons vcons = new Vcons(robot, follower);
       Drivetrain drivetrain = new Drivetrain(robot, follower, gamepad1);


        Scheduler.reset();
        waitForStart();
        follower.startTeleopDrive(true);



        while(opModeIsActive()){
            Scheduler.execute();
            follower.update();

            if(gamepad1.aWasPressed()){
                schedule(vcons.scanThenMove());

            }
            if(gamepad1.b){
                schedule(drivetrain.relocalizeRed);
            }
            if(gamepad1.xWasPressed()){
                schedule(drivetrain.relocalizeBlue);
            }

            if(!vcons.autoDrive){
                drivetrain.periodic();
            }




            // --- Telemetry ---
            telemetry.addData("autoDrive", vcons.autoDrive);
            telemetry.addData("follower busy", follower.isBusy());

            LLResult result = robot.limelight.getLatestResult();
            if (result != null && result.isValid()) {
                telemetry.addData("LL valid", true);
                telemetry.addData("tx", result.getTx());
                telemetry.addData("ty", result.getTy());
            } else {
                telemetry.addData("LL valid", false);
            }

            Pose pollenPose = vcons.createPollenPose();
            if (pollenPose != null) {
                telemetry.addData("pollen X", pollenPose.getX());
                telemetry.addData("pollen Y", pollenPose.getY());
                telemetry.addData("pollen heading", Math.toDegrees(pollenPose.getHeading()));
            } else {
                telemetry.addLine("pollen pose: null");
            }

            telemetry.addData("robot X", follower.getPose().getX());
            telemetry.addData("robot Y", follower.getPose().getY());
            telemetry.addData("robot heading", Math.toDegrees(follower.getHeading()));
            telemetry.update();

        }

    }
}
