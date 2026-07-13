package org.firstinspires.ftc.teamcode.opmodes;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.vision.Vcons;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

@TeleOp

public class pollenTest extends LinearOpMode {

    @Override
    public void runOpMode(){
        hackingHoundsHardware robot = new hackingHoundsHardware();
        robot.init(hardwareMap);
        follower = Constants.createFollower(hardwareMap);

       Vcons vcons = new Vcons(robot, follower);


        Scheduler.reset();



        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                schedule(vcons.scanThenMove());
            }




        }

    }
}
