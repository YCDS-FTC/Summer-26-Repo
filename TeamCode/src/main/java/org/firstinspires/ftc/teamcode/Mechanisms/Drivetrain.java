package org.firstinspires.ftc.teamcode.Mechanisms;


import static com.pedropathing.ivy.commands.Commands.infinite;
import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.ivy.commands.Commands;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Drivetrain {
    private Follower follower;
    private hackingHoundsHardware robot;
    private Gamepad gamepad1;
    public double offsetHeading =0;
    public double shift = 1;


    private Pose redPose = new Pose(9, 7.65625, Math.toRadians(0));
    private Pose bluePose = new Pose(135, 7.65625, Math.toRadians(180));

    public Drivetrain(hackingHoundsHardware robot, Follower follower, Gamepad gamepad1){
        this.robot = robot;
        this.follower = follower;
        this.gamepad1 = gamepad1;
    }


    private void blueRelocalize(){
        follower.setPose(bluePose);
    }
    private void redRelocalize(){
        follower.setPose(redPose);
    }


    public Command relocalizeBlue = instant(() ->{
         offsetHeading =  follower.getHeading();
         blueRelocalize();
    });
    public Command relocalizeRed = instant(() ->{
        offsetHeading = follower.getHeading();
        redRelocalize();
    });

    public void periodic(){
        follower.setTeleOpDrive(-gamepad1.left_stick_y * shift, -gamepad1.left_stick_x * shift, gamepad1.right_stick_x * shift, offsetHeading);
    }
}
