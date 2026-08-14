package org.firstinspires.ftc.teamcode.Mechanisms;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Led {

    private final hackingHoundsHardware robot;

    public enum Color {
        OFF(0.00),
        RED(0.279),
        ORANGE(0.333),
        YELLOW(0.388),
        SAGE(0.444),
        GREEN(0.5),
        AZURE(0.555),
        BLUE(0.611),
        INDIGO(0.66),
        VIOLET(0.721),
        WHITE(1.0);

        private final double value;

        Color(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }


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
                })
                .setDone(() -> true);
    };

    /**
     * Read here for the color values for the lights <a href="https://cdn11.bigcommerce.com/s-x56mtydx1w/images/stencil/original/products/2275/15126/3118-0808-0002-Product-Insight-4__88285.1757516465.png?c=1" target="_blank">Example Here</a>
     * @param color  Sets the shooter light to a color
     */
    public Command setShooterColor(Color color) {
        return setShooterColor(color.getValue());
    }

    /**
     * Read here for the color values for the lights <a href="https://cdn11.bigcommerce.com/s-x56mtydx1w/images/stencil/original/products/2275/15126/3118-0808-0002-Product-Insight-4__88285.1757516465.png?c=1" target="_blank">Example Here</a>
     * @param color  Sets the intake light to a color
     */
    public Command setIntakeColor(double color){
        return Command.build()
                .setExecute(() -> {
                    robot.intakeLight.setPosition(color);
                })
                .setDone(() -> true);

    }

    /**
     * Read here for the color values for the lights <a href="https://cdn11.bigcommerce.com/s-x56mtydx1w/images/stencil/original/products/2275/15126/3118-0808-0002-Product-Insight-4__88285.1757516465.png?c=1" target="_blank">Example Here</a>
     * @param color  Sets the intake light to a color
     */
    public Command setIntakeColor(Color color) {
        return setIntakeColor(color.getValue());
    }

}
