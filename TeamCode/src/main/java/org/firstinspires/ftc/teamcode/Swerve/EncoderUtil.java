package org.firstinspires.ftc.teamcode.Swerve;


import com.qualcomm.robotcore.hardware.AnalogInput;

public final class EncoderUtil {

    private EncoderUtil() {}

    private static final double REF_VOLTAGE = 3.3;

    public static double getEncoderAngle(AnalogInput encoder) {
        return encoder.getVoltage() / REF_VOLTAGE * 360.0;
    }

    public static double closestAngle(double currentAngle, double targetAngle) {
        // normalize both angles into 0-360, using true modulo, not Java's %
        currentAngle = modulo(currentAngle);
        targetAngle = modulo(targetAngle);

        double dir = targetAngle - currentAngle;

        // convert from -360..360 to -180..180
        if (Math.abs(dir) > 180.0) {
            dir = dir - Math.signum(dir) * 360.0;
        }
        return dir;
    }

    private static double modulo(double angle) {
        double result = angle % 360.0;
        if (result < 0) {
            result += 360.0;
        }
        return result;
    }

}