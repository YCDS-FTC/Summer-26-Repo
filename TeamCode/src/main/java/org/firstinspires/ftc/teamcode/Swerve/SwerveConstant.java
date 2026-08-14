package org.firstinspires.ftc.teamcode.Swerve;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
@Config
public final class SwerveConstant {

    private SwerveConstant() {}

    public static double TurnKp = 0.0;
    public static double TurnKi = 0.0;
    public static double TurnKd = 0.0;

    public static final double LEFT_FRONT_OFFSET_DEG = 0.0;
    public static final double LEFT_BACK_OFFSET_DEG = 0.0;
    public static final double RIGHT_FRONT_OFFSET_DEG = 0.0;
    public static final double RIGHT_BACK_OFFSET_DEG = 0.0;

    public static final boolean LEFT_FRONT_STEER_REVERSED = false;
    public static final boolean LEFT_BACK_STEER_REVERSED = false;
    public static final boolean RIGHT_FRONT_STEER_REVERSED = false;
    public static final boolean RIGHT_BACK_STEER_REVERSED = false;
}