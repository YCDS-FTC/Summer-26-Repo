package org.firstinspires.ftc.teamcode.Mechanisms;



import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.ivy.commands.Commands;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Drivetrain {
    private Follower follower;
    private hackingHoundsHardware robot;
    private Gamepad gamepad1;

    private Pose redPose = new Pose(9, 7.65625, Math.toRadians(0));
    private Pose bluePose = new Pose(135, 7.65625, Math.toRadians(180));

    public Drivetrain(hackingHoundsHardware robot, Follower follower, Gamepad gamepad1){
        this.robot = robot;
        this.follower = follower;
        this.gamepad1 = gamepad1;
    }


    private void redRelocalize(){
        follower.setPose(redPose);;
    }
    private void blueRelocalize(){
        follower.setPose(bluePose);
    }
    public CommandBuilder relocalizeRed(){
        return Commands.instant(this::redRelocalize);
    }

    public CommandBuilder relocalizeBlue(){
        return Commands.instant(this::blueRelocalize);
    }

    public void periodic(){
        follower.startTeleopDrive(true);
        follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x, false);
    }
}
