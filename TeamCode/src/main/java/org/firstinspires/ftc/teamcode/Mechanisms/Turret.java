package org.firstinspires.ftc.teamcode.Mechanisms;

import static com.pedropathing.ivy.commands.Commands.infinite;

import static org.firstinspires.ftc.teamcode.util.RobotConstants.closeCoefficients;
import static org.firstinspires.ftc.teamcode.util.RobotConstants.farCoefficients;
import static org.firstinspires.ftc.teamcode.util.RobotConstants.targetX;
import static org.firstinspires.ftc.teamcode.util.RobotConstants.targetY;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;
import org.firstinspires.ftc.teamcode.util.RobotConstants;

public class Turret {
    private final hackingHoundsHardware robot;
    private final Follower follower;

    private boolean on = true;
    private double dx;
    private double dy;
    private double goalHeading;
    private double robHeading;


    private final PIDFController closeTurretController = new PIDFController(closeCoefficients);
    private final PIDFController farTurretController = new PIDFController(farCoefficients);

    public Turret(hackingHoundsHardware robot, Follower follower) {
        this.robot = robot;
        this.follower = follower;
    }


    private double normA(double angle) {
        angle %= 360; if (angle < -180) angle += 360; else if (angle > 180) angle -= 360;
        if(angle > 155){
            angle = 155;
        } else if(angle < -179){
            angle = -179;
        }
        return angle;
    }
    public double getTurretPos(){
        double ticksperDegree = 6.45;
        return robot.turret.getCurrentPosition()/ ticksperDegree;
    }

    public double turretLeft(){
       follower.setHeading(follower.getHeading() + 0.0261799);
       return follower.getHeading();
    }
    public double turretRight(){
        follower.setHeading(follower.getHeading() - 0.0261799);
        return follower.getHeading();
    }
    public double makeTargetAngle(){
        dx = follower.getPose().getX() - targetX;
        dy = follower.getPose().getY() - targetY;
        goalHeading = Math.toDegrees(Math.atan2(-dy, -dx));
        robHeading = Math.toDegrees(follower.getHeading());
        return normA(goalHeading - robHeading);
    }


    public Command periodic(){
        return infinite(()->{
            if(on){
                closeTurretController.calculate(getTurretPos(), makeTargetAngle());
            } else{
                closeTurretController.calculate(getTurretPos(), getTurretPos());
            }
                }
        );
    }

    /**take all methods from previous repo and put into here. ALl act turretmath goes into single method with everything split into methods. Only thing which
     goes into periodic is method for setting turret power. Change between yesLL and noLL using llresult, seperateCommand thats runs infinitely for switching between states**/

}