package org.firstinspires.ftc.teamcode.Swerve;


import com.qualcomm.robotcore.hardware.AnalogInput;

public final class EncoderUtil {

    private EncoderUtil() {}

    private static final double REF_VOLTAGE = 3.3;

    public static double getEncoderAngle(AnalogInput encoder) {
        return encoder.getVoltage() / REF_VOLTAGE * 360.0;
    }

}