package org.firstinspires.ftc.teamcode.opmodes;
import static com.pedropathing.ivy.Scheduler.schedule;

import com.pedropathing.ivy.Scheduler;
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
       Drivetrain drivetrain = new Drivetrain(robot, follower);


        Scheduler.reset();



        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.aWasPressed()){
                schedule(vcons.scanThenMove());

            }
            if(gamepad1.b){
                schedule(drivetrain.relocalizeRed());
            }
            if(gamepad1.xWasPressed()){
                schedule(drivetrain.relocalizeBlue());
            }

            if(!vcons.autoDrive){
                drivetrain.periodic();
            }



            Scheduler.execute();
            follower.update();


        }

    }
}
