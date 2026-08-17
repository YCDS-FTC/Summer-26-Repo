package org.firstinspires.ftc.teamcode.Swerve;

import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.STICK_DEAD_ZONE;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TRACK_LENGTH;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TRACK_WIDTH;
//ima be crystal clear some of this is claude :/
/**
 * Axis convention (matches the corner-angle comments in SwerveConstants):
 * x = forward(+)/backward(-), y = right(+)/left(-), angle = atan2(y, x) in degrees,
 * so 0 deg is straight ahead and positive angles rotate clockwise viewed from above.
 * turnPower follows the same handedness: positive turnPower spins the robot clockwise.
 */
public class SwerveDriveCoordinator {
    private final SwerveDrivePod leftFrontWheel;
    private final SwerveDrivePod leftBackWheel;
    private final SwerveDrivePod rightFrontWheel;
    private final SwerveDrivePod rightBackWheel;

    public SwerveDriveCoordinator(SwerveDrivePod leftFrontWheel,
                                  SwerveDrivePod leftBackWheel,
                                  SwerveDrivePod rightFrontWheel,
                                  SwerveDrivePod rightBackWheel) {
        this.leftFrontWheel = leftFrontWheel;
        this.leftBackWheel = leftBackWheel;
        this.rightFrontWheel = rightFrontWheel;
        this.rightBackWheel = rightBackWheel;
    }

    /**
     * @param direction     desired translation heading in degrees (0 = forward, +90 = right), ignored if translatePower is ~0
     * @param translatePower desired translation speed, -1..1 (only magnitude is used; sign folds into direction)
     * @param turnPower     desired rotation rate, -1..1, positive = clockwise
     */
    public void setSwerveDrive(double direction, double translatePower, double turnPower) {
        if (Math.abs(translatePower) < STICK_DEAD_ZONE) translatePower = 0.0;
        if (Math.abs(turnPower) < STICK_DEAD_ZONE) turnPower = 0.0;

        double directionRadians = Math.toRadians(direction);
        double vectorX = translatePower * Math.cos(directionRadians); // forward component
        double vectorY = translatePower * Math.sin(directionRadians); // right component

        // half-extents, normalized by the corner radius so a full-speed twist alone
        // drives every wheel at exactly the same speed translatePower would (both are -1..1)
        double halfLength = TRACK_LENGTH / 2.0;
        double halfWidth = TRACK_WIDTH / 2.0;
        double cornerRadius = Math.hypot(halfLength, halfWidth);
        double normalizedWheelX = halfLength / cornerRadius;
        double normalizedWheelY = halfWidth / cornerRadius;

        double[][] wheelVectors = {
                wheelVector(+normalizedWheelX, -normalizedWheelY, vectorX, vectorY, turnPower), // left front
                wheelVector(-normalizedWheelX, -normalizedWheelY, vectorX, vectorY, turnPower), // left back
                wheelVector(+normalizedWheelX, +normalizedWheelY, vectorX, vectorY, turnPower), // right front
                wheelVector(-normalizedWheelX, +normalizedWheelY, vectorX, vectorY, turnPower), // right back
        };

        // scale all four wheels down together (not independently) so a combined
        // translate+turn command that would exceed 1.0 keeps its commanded ratio/direction
        double maxSpeed = 1.0;
        for (double[] wv : wheelVectors) {
            maxSpeed = Math.max(maxSpeed, Math.hypot(wv[0], wv[1]));
        }
        double scale = 1.0 / maxSpeed;

        SwerveDrivePod[] pods = {leftFrontWheel, leftBackWheel, rightFrontWheel, rightBackWheel};
        for (int i = 0; i < pods.length; i++) {
            driveWheel(pods[i], wheelVectors[i][0] * scale, wheelVectors[i][1] * scale);
        }
    }

    private double[] wheelVector(double wheelX, double wheelY, double vx, double vy, double turnPower) {
        // rotation contribution is the tangent vector at this corner for clockwise-positive spin
        double wx = vx + turnPower * wheelY;
        double wy = vy - turnPower * wheelX;
        return new double[]{wx, wy};
    }

    private void driveWheel(SwerveDrivePod pod, double wx, double wy) {
        double speed = Math.hypot(wx, wy);
        if (speed < STICK_DEAD_ZONE) {
            // no commanded motion for this wheel: hold current steer angle, just stop driving
            pod.setSpeed(0.0);
            return;
        }

        double angle = Math.toDegrees(Math.atan2(wy, wx));
        pod.setPod(angle, speed);
    }
}
