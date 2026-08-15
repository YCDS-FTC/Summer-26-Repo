package org.firstinspires.ftc.teamcode.Swerve;

import com.acmerobotics.dashboard.config.Config;
@Config
public final class SwerveConstants {

    private SwerveConstants() {}

    public static double TurnKp = 0.0;
    public static double TurnKi = 0.0;
    public static double TurnKd = 0.0;

    public static final double TRACK_WIDTH = 16;
    public static final double TRACK_LENGTH = 16;

    public static double STICK_DEADZONE = 0.05;

    //right front 45
    //right back 135
    //left back 225
    //left front 315

    //point the wheel forward and put the angle you find in here it should make that the new forward or 0
    public static final double LEFT_FRONT_OFFSET_DEG = 0.0;
    public static final double LEFT_BACK_OFFSET_DEG = 0.0;
    public static final double RIGHT_FRONT_OFFSET_DEG = 0.0;
    public static final double RIGHT_BACK_OFFSET_DEG = 0.0;


    //Should we just configure them all properly or keep this?
    public static final boolean LEFT_FRONT_STEER_REVERSED = false;
    public static final boolean LEFT_BACK_STEER_REVERSED = false;
    public static final boolean RIGHT_FRONT_STEER_REVERSED = false;
    public static final boolean RIGHT_BACK_STEER_REVERSED = false;
}