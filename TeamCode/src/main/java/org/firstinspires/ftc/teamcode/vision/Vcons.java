package org.firstinspires.ftc.teamcode.vision;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

import java.util.function.Supplier;

public class Vcons {
    private hackingHoundsHardware robot = new hackingHoundsHardware();

    public double robX = robot.pinpoint.getPosX(DistanceUnit.INCH);
    public double robY = robot.pinpoint.getPosY(DistanceUnit.INCH);

    double fieldX;
    double fieldY;
    double pollenX;
    double pollenY;
    /** the follwing are just example values to demonstrate how the math would work **/
    double llheight = 8;
    double polheight = 1.45;

    double llangle = 25;

    LLResult result = robot.limelight.getLatestResult();
    double tx = result.getTx();
    double ty = result.getTy();

    double heading = robot.pinpoint.getHeading(AngleUnit.RADIANS);
    double targetang = llangle + ty;
    double targetHeight = llheight - polheight;

    public double getPollenDis(){
        return Math.tan(Math.toRadians(targetang)) * targetHeight;
    }
    public double getPollenOffset(){
        return Math.tan(Math.toRadians(tx)) * getPollenDis();
    }
    public Pose createPollenPose(){
        fieldX = (getPollenDis() * Math.cos(heading)) - (getPollenOffset() * Math.sin(heading));
        fieldY = (getPollenDis() * Math.sin(heading)) + (getPollenOffset() * Math.cos(heading));

        pollenX = robX + fieldX;
        pollenY = robY + fieldY;
        return new Pose(pollenX, pollenY);
    }

    Supplier<Pose> pollenPoseSupplier = () -> new Pose(
            robX + fieldX,
            robY + fieldY,
            0
    );

    follower.followPath(new Path(new BezierLine(
            new Point(follower.getPose()),
            new Point(pollenPoseSupplier.get())
            )));

    /** Todo - take distance and add to robPose for new coords and make path from one to other
     *  - after above is finished restructure into command that can be used in teleop withg variables in proper areas.**/
}
