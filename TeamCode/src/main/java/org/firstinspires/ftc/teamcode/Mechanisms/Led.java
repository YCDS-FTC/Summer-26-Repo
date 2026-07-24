package org.firstinspires.ftc.teamcode.Mechanisms;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Led {

    private final hackingHoundsHardware robot;

    public Led(hackingHoundsHardware robot){this.robot = robot;}


    //These probably should be instant command but im to lazy ;p
    //https://pedropathing.com/docs/ivy/utilities-and-decorators


    /**
     * Read here for the color values for the lights <a href="https://cdn11.bigcommerce.com/s-x56mtydx1w/images/stencil/original/products/2275/15126/3118-0808-0002-Product-Insight-4__88285.1757516465.png?c=1" target="_blank">Example Here</a>
     * @param color  Sets the shooter light to a color
     */
    public Command setShooterColor(double color){
        return Command.build()
                .setExecute(() -> {
                    robot.shooterLight.setPosition(color);
                });
    };

    /**
     * Read here for the color values for the lights <a href="https://cdn11.bigcommerce.com/s-x56mtydx1w/images/stencil/original/products/2275/15126/3118-0808-0002-Product-Insight-4__88285.1757516465.png?c=1" target="_blank">Example Here</a>
     * @param color  Sets the intake light to a color
     */
    public Command setIntakeLight(double color){
        return Command.build()
                .setExecute(() -> {
                    robot.intakeLight.setPosition(color);
                });

    }}
