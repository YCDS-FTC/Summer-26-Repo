package org.firstinspires.ftc.teamcode.Swerve;


import com.qualcomm.robotcore.hardware.AnalogInput;

public final class EncoderUtil {

    private EncoderUtil() {}

    private static final double REF_VOLTAGE = 3.3;

    /**
     * Will only work with analog encoders and other encoders should be read other ways
     * This will return the raw angle without any offsets applied when you tune the pods
     * This is not recommended for general use
     * This should be used for mostly debugging and testing
     * @param encoder Pass in which ANALOG encoder you want to read against a 3.3 reference voltage
     * @return The encoder angle
     */
    public static double getEncoderAngle(AnalogInput encoder) {
        return encoder.getVoltage() / REF_VOLTAGE * 360.0;
    }

    /**
     * This will calculate the fastest way to get to the target angle
     * See "delta" in setPod in SwerveDrivePod
     * <a href="https://compendium.readthedocs.io/en/latest/tasks/drivetrains/swerve.html#:~:text=Directing%20the%20wheels">Reference Link</a>
     * @param currentAngle Where you are
     * @param targetAngle Where you want to be
     * @return the shortest way to get between two angles
     */
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

    /**
     * Wraps any angle into the range 0 - 360  for display/telemetry purposes.
     * @param angle Angle in degrees
     * @return Equivalent angle in 0 - 360
     */
    public static double normalize360(double angle) {
        return modulo(angle);
    }

    private static double modulo(double angle) {
        double result = angle % 360.0;
        if (result < 0) {
            result += 360.0;
        }
        return result;
    }

}