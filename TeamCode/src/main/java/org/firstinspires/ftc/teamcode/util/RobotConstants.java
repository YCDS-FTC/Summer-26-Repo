package org.firstinspires.ftc.teamcode.util;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class RobotConstants {
    public static double gateOpen = .405;
    public static double gateClose = 0.44;
    public static double gateDurationMs = 1500;

    public static PIDFCoefficients closeCoefficients = new PIDFCoefficients(0.03, 0, 0.001, 0);
    public static PIDFCoefficients farCoefficients = new PIDFCoefficients(0.011, 0, 0.00003,0);
    public static double TurretFarkp = 0.011;
    public static double TurretFarki = 0;
    public static double TurretFarkd = 0.00003;
    public static double TurretFarkf = 0;

    public static double Shooterkp = 0.006;
    public static double Shooterki = 0;
    public static double Shooterkd = 0;
    public static double Shooterkf = 0.0003;

    public static double targetX;
    public static double targetY;

}
