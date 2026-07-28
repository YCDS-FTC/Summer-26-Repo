package org.firstinspires.ftc.teamcode.Mechanisms;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Turret {
    private final hackingHoundsHardware robot;
    private Follower follower;

    private boolean use_LL = false;
    private boolean no_LL = true;
    private boolean manual = false;
    public Turret(hackingHoundsHardware robot, Follower follower) {
        this.robot = robot;
        this.follower = follower;
    }


    private double ticksperDegree = 6.45;
    private double normA(double angle) {angle %= 360; if (angle < -180) angle += 360; else if (angle > 180) angle -= 360;return angle;}



    public Command

    /**take all methods from previous repo and put into here. ALl act turretmath goes into single method with everything split into methods. Only thing which
     goes into periodic is method for setting turret power. Change between yesLL and noLL using llresult, seperateCommand thats runs infinitely for switching between states**/



    public Command exampleCommand(double value) {
        return Command.build()
                .setExecute(() -> {
                });
    }
}