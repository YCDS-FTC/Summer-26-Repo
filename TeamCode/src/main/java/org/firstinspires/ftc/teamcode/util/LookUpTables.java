package org.firstinspires.ftc.teamcode.util;

import com.seattlesolvers.solverslib.util.InterpLUT;

public class LookUpTables {
    public static final InterpLUT hoodAngleLut = new InterpLUT();
    public static final InterpLUT shooterPowerLut = new InterpLUT();

    static {
        hoodAngleLut.add(-100, 0.23);
        hoodAngleLut.add(0,0.23);
        hoodAngleLut.add(30, 0.22);
        hoodAngleLut.add(40, 0.18);
        hoodAngleLut.add(45, 0.17);
        hoodAngleLut.add(50, 0.16);
        hoodAngleLut.add(55, 0.16);
        hoodAngleLut.add(60, 0.14);
        hoodAngleLut.add(65, 0.14);
        hoodAngleLut.add(70, 0.14);
        hoodAngleLut.add(75, 0.13);
        hoodAngleLut.add(80, 0.11);
        hoodAngleLut.add(85, 0.11);
        hoodAngleLut.add(90, 0.11);
        hoodAngleLut.add(100, 0.08);
        hoodAngleLut.add(110, 0.07);
        hoodAngleLut.add(120, 0.09);
        hoodAngleLut.add(123, 0.086);
        hoodAngleLut.add(125, 0.095);
        hoodAngleLut.add(130, 0.1);
        hoodAngleLut.add(140, .1);
        hoodAngleLut.add(150, .08);
        hoodAngleLut.add(190, .15);
    }
    static {
        shooterPowerLut.add(-200, 1000);
        shooterPowerLut.add(1,1000);
        shooterPowerLut.add(27, 1000);
        shooterPowerLut.add(30, 960);
        shooterPowerLut.add(40, 1000);
        shooterPowerLut.add(45, 1020);
        shooterPowerLut.add(50, 1080);
        shooterPowerLut.add(55, 1100);
        shooterPowerLut.add(60, 1140);
        shooterPowerLut.add(65, 1140);
        shooterPowerLut.add(70, 1160);
        shooterPowerLut.add(75, 1220);
        shooterPowerLut.add(80, 1260);
        shooterPowerLut.add(85, 1280);
        shooterPowerLut.add(90, 1320);
        shooterPowerLut.add(100, 1350);
        shooterPowerLut.add(105, 1460);
        shooterPowerLut.add(110, 1470);
        shooterPowerLut.add(120, 1520);
        shooterPowerLut.add(125, 1590);
        shooterPowerLut.add(130,1600);
        shooterPowerLut.add(135,1640);
        shooterPowerLut.add(140,1680);
        shooterPowerLut.add(150, 1720);
        shooterPowerLut.add(250, 1700);
    }

}
