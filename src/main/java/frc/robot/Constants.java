// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static double kMaxAngularSpeed = 2 * Math.PI; // one rotation per second
        public static double kTrackWidth = 0.6245; // meters
        public static double kWheelRadius = 0.0762; // meters
        public static double kShaftEncoderResolution = 2048; // PPR
        public static double positionChangePerRotation = 8.73; // Motor rotation per shaft rotation
        public static double kMaxVelocity = 3; // feet per second
        public static double kMaxAcceleration = 1; // Max Accel fet per second squared

        public static double kStatic = 0.12587; // Constant feedforward term for the robot drive.
        public static double kV = 3.2903; // Velocity-proportional feedforward term for the robot drive
        public static double kA = 0.28143; //Acceleration-proportional feedforward term for the robot (.348) (.44 protobot)

        // Tuning parameter (b > 0) for which larger values make convergence more aggressive like a proportional term
        public static double kRamseteB = 2;

        // Tuning parameter (0 &lt; zeta &lt; 1) for which larger values provide more damping in response
        public static double kRamseteZeta = 0.7;

        public static double kHeadingP = 1;
        public static double kHeadingD = 0;
        public static double kHeadingI = 0;

        public static double kAlignP = 0.031;
        public static double kAlignD = 0.0003;

        public static double kDriveP = 3.7568; // 3 stable
        public static double kDriveI = 0;
        public static double kDriveD = 0;

        public static int[] kLeftEncoderPorts = new int[]{2, 3};
        public static int[] kRightEncoderPorts = new int[]{0, 1};

        public static boolean kLeftEncoderReversed = false;
        public static boolean kRightEncoderReversed = false;

        public static boolean kGyroReversed = false;

        public static DifferentialDriveKinematics kDriveKinematics
                = new DifferentialDriveKinematics(DriveConstants.kTrackWidth);
    }

    public static final class IntakeConstants {
        public static int kIntakeMotorPort = 15;
        public static int kLSerializerPort = 11;
        public static int kRSerializerPort = 12;

        public static double kIntakePower = 0.6; // was 1.0
        public static double kSerializerPower = 0.5; //was 0.6
    }

    public static final class ShooterConstants {
        public static double kSparkMaxP = 0.000035;
        public static double KSparkMaxI = 0;
        public static double KSparkMaxD = 0;
        public static double kSparkMaxFeedforward = 0.000185; // .00022

       

        public static int kLeftMotorPort = 9;
        public static int kRightMotorPort = 10;


        public static double kVelocityTolerance = 100;
    }

    public static final class IndexConstants {

        public static int kiTowerPort = 7;
        public static int koTowerPort = 8;
    }
    public static final class CameraConstants {
        public static double kCameraHeight = Units.inchesToMeters(27); 
        public static double kCameraMountingAngle = 29; // degrees
    }

    public static final class FieldConstants {
        public static double kGoalHeight = Units.inchesToMeters(98.25); 
    }

    public static final class ClimberConstants {
        public static double kGearRatio = 1.0;
        public static double kSprocketRadius = 1.0; // in

        public static double kP = 0.1;

        public static double kToleranceInches = 1;

        public static double kMaxHeightInches = 0;
            public static double kMaxHeightTicks = 280;

        public static double kMinHeightInches = 0;
        public static double kMinHeightTicks = 5;

        public static double servoLockPosition = 1;
        public static double servoUnlockPosition = -1;

        public static int kLeftMotorPort = 18;
        public static int kRightMotorPort = 19;

        public static int kLHighMotorPort = 16;
        public static int kRHighMotorPort = 17;
    }

    public static final class AutoConstants {
        
    }
}
