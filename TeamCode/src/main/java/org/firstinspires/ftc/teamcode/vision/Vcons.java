package org.firstinspires.ftc.teamcode.vision;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.limelightvision.LLResult;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;
import org.firstinspires.ftc.teamcode.pedroPathing.Tuning;

import java.util.function.Supplier;

public class Vcons{
    private hackingHoundsHardware robot;
    private Follower follower;
    public boolean autoDrive = false;

    public Vcons(hackingHoundsHardware robot, Follower follower) {
        this.robot = robot;
        this.follower = follower;
    }
    double fieldX;
    double fieldY;
    double pollenX;
    double pollenY;
    /** the follwing are just example values to demonstrate how the math would work **/
    double llheight = 13;
    double polheight = 1.45;
    double camOffsetForward = 8.5;
    double camOffsetLeft = 0;

    Pose robPose;
    double llangle = 25;
    double targetHeight = llheight - polheight;

    public Pose createPollenPose(){


        LLResult result = robot.limelight.getLatestResult();

        double[] python = result.getPythonOutput();



        double tx = python[0];
        double ty = python[1];
        double area = python[2];

        double heading = follower.getHeading();
        double targetAng = llangle + ty;
        double robX = follower.getPose().getX();
        double robY = follower.getPose().getY();

        double dis = getPollenDis(targetAng);
        double offset = getPollenOffset(-tx, dis);

        fieldX = (dis * Math.cos(heading)) - (offset * Math.sin(heading));
        fieldY = (dis * Math.sin(heading)) + (offset * Math.cos(heading));

        double camFieldX = (camOffsetForward * Math.cos(heading)) - (camOffsetLeft * Math.sin(heading));
        double camFieldY = (camOffsetForward * Math.sin(heading)) + (camOffsetLeft * Math.cos(heading));


        pollenX = robX + fieldX + camFieldX;
        pollenY = robY + fieldY + camFieldY;
        return new Pose(pollenX, pollenY, heading);

    }

    public double getPollenDis(double targetAng){
        return targetHeight/  Math.tan(Math.toRadians(targetAng));
    }
    public double getPollenOffset(double tx, double dis){
        return Math.tan(Math.toRadians(tx)) * dis;
    }
    Supplier<Pose> pollenPoseSupplier = this::createPollenPose;


   public PathChain pollenPath(){
       return follower.pathBuilder()
               .addPath(new BezierLine(follower.getPose(), pollenPoseSupplier.get()))
               .setConstantHeadingInterpolation(pollenPoseSupplier.get().getHeading())
               .build();

   }
   public PathChain pollenPathRevised(){
       Pose target = createPollenPose();
       if(target == null){
           return null;
       }
       return follower.pathBuilder()
               .addPath(new BezierLine(follower.getPose(), target))
               .setConstantHeadingInterpolation(target.getHeading())
               .build();
   }


    public Command followPollenPath(){
       return Command.build()
               .setStart(() -> {
            autoDrive = true;
            PathChain path = pollenPathRevised();
            if (path != null){
                follower.followPath(path);
            }
        })
                .setDone(() -> !follower.isBusy()
                )
                .setEnd(endCondition -> {autoDrive = false;
                follower.startTeleopDrive(true);
                    }

                );

    }

    private void turnLimelightOff(){
       robot.limelight.stop();
    }
    public Command limelightOff(){
       return Commands.instant(this::turnLimelightOff);
    }

    public Command scanThenMove(){
       return sequential(waitMs(300), followPollenPath());
    }

}
