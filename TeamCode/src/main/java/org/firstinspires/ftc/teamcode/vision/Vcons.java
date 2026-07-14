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
    double llheight = 8;
    double polheight = 1.45;

    Pose robPose;
    double llangle = 25;
    double targetHeight = llheight - polheight;

    public Pose createPollenPose(){

        LLResult result = robot.limelight.getLatestResult();
        if (result == null || !result.isValid()){return null;}

        double tx = result.getTx();
        double ty = result.getTy();

        double heading = follower.getHeading();
        double targetAng = llangle + ty;
        double robX = follower.getPose().getX();
        double robY = follower.getPose().getY();

        double dis = getPollenDis(targetAng);
        double offset = getPollenOffset(tx, dis);

        fieldX = (dis * Math.cos(heading)) - (offset * Math.sin(heading));
        fieldY = (dis * Math.sin(heading)) + (offset * Math.cos(heading));

        pollenX = robX + fieldX;
        pollenY = robY + fieldY;
        return new Pose(pollenX, pollenY, heading);

    }

    public double getPollenDis(double targetAng){
        return Math.tan(Math.toRadians(targetAng)) * targetHeight;
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


    public Command followPollenPath(){
       return Command.build()
               .setStart(() -> {
            robot.limelight.start();
            autoDrive = true;
        })
                .setExecute(() ->{
                    Pose targetPollenPose = createPollenPose();
                    if(targetPollenPose != null){
                        follower.followPath(pollenPath());
                    }
                })
                .setDone(() -> !follower.isBusy()
                )
                .setEnd(endCondition -> autoDrive = false

                );

    }

    private void turnLimelightOff(){
       robot.limelight.stop();
    }
    public Command limelightOff(){
       return Commands.instant(this::turnLimelightOff);
    }

    public Command scanThenMove(){
       return sequential(waitMs(300), followPollenPath(), limelightOff());
    }



    /** Todo - take distance and add to robPose for new coords and make path from one to other
     *  - after above is finished restructure into command that can be used in teleop withg variables in proper areas.**/
}
