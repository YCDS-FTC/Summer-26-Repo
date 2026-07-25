package org.firstinspires.ftc.teamcode.Mechanisms;

import static com.pedropathing.ivy.groups.Groups.parallel;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

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
    public Command runIntake(double power) {
        return parallel(Command.build()
                .setExecute(() -> {
                    robot.intake.setPower(power);
                    led.setShooterColor(Led.Color.RED);
                }));
    }


    /**
     * Stops the intake
     */
    public Command stopIntake(){
        return Command.build()
                .setExecute(() -> {
                    robot.intake.setPower(0);
                });
    }

}
