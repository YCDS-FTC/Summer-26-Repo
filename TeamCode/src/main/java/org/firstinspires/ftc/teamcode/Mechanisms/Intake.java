package org.firstinspires.ftc.teamcode.Mechanisms;

import static com.pedropathing.ivy.groups.Groups.parallel;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.commands.Commands;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Intake {

    private final hackingHoundsHardware robot;
    private final Led led;


    public Intake(hackingHoundsHardware robot) {
        this.robot = robot;
        this.led = new Led(robot);
    }

    /**
     * @param power Sets the intake power
     */
    public Command runIntake(double power){
        return Commands.instant(() -> robot.intake.setPower(power));
    }

    /**
     * Stops the intake
     */
    public CommandBuilder stopIntake(){
        return Commands.instant(() -> robot.intake.setPower(0));
    }


}
