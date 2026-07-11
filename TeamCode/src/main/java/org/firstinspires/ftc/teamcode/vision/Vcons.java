package org.firstinspires.ftc.teamcode.vision;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class Vcons {
    private hackingHoundsHardware robot = new hackingHoundsHardware();

    public double robX = robot.pinpoint.getPosX(DistanceUnit.INCH);
    public double robY = robot.pinpoint.getPosY(DistanceUnit.INCH);

    /** the follwing are just example values to demonstrate how the math would work **/
    public double llheight = 8;
    public double llangle = 25;
    public double polheight = 1.45;

    /** Todo - add in getting tx,ty from ll pipeline and distances + horizontal offsets
    take distance and add to robPose for new coords and make path from one to other **/
}
