package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Index.AutoShotMid;
import frc.robot.commands.Intake.AutoIntake;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Intake.IntakeCommand.*;
import frc.robot.commands.drivetrain.CustomAlignToTarget;
import frc.robot.commands.shooter.TeleOpShooter;
import frc.robot.commands.shooter.TeleOpShooter.*;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class FourBallAuto extends SequentialCommandGroup {

	public FourBallAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Serializer serializer, Tower tower, Limelight limelight) {
		CustomRamseteCommand dropIntake =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(28), Units.feetToMeters(5.7), Rotation2d.fromDegrees(180)),
					new Pose2d(Units.feetToMeters(24.5), Units.feetToMeters(5.7), Rotation2d.fromDegrees(180))
				),
				Units.feetToMeters(10), Units.feetToMeters(8), false
			);
		CustomRamseteCommand splineToBallOne =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(24.5), Units.feetToMeters(5.7), Rotation2d.fromDegrees(180)),
					new Pose2d(Units.feetToMeters(16.5), Units.feetToMeters(6.3), Rotation2d.fromDegrees(-150))
				),
				Units.feetToMeters(8), Units.feetToMeters(5), false
			);
		CustomRamseteCommand splineToHuman =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(16.5), Units.feetToMeters(6.3), Rotation2d.fromDegrees(-150)),
					new Pose2d(Units.feetToMeters(4.9), Units.feetToMeters(4.75), Rotation2d.fromDegrees(-135))
				),
				Units.feetToMeters(14), Units.feetToMeters(10), false
			);
			
		CustomRamseteCommand splineToShot =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(4.9), Units.feetToMeters(4.75), Rotation2d.fromDegrees(-135)),
					new Pose2d(Units.feetToMeters(16.5), Units.feetToMeters(6.3), Rotation2d.fromDegrees(-150))
				),
				Units.feetToMeters(14), Units.feetToMeters(10), true
			);

		addCommands(
			sequence(
				//reset odometry
				new InstantCommand(() -> drivetrain.resetOdometry(dropIntake.getInitialPose())),
				dropIntake.andThen(() -> drivetrain.tankDriveVolts(0, 0)),
				//Intake first ball
				deadline(
					splineToBallOne.andThen(() -> drivetrain.tankDriveVolts(0,0)),
					new AutoIntake(intake, tower, serializer)

				),
				//Align and Shoot
				new AutoShotMid(shooter, tower, serializer).withTimeout(1.4),
				//Run intake and move to Human Player
				deadline(
					splineToHuman.andThen(() -> drivetrain.tankDriveVolts(0,0)),
					new AutoIntake(intake, tower, serializer)
				),
				//Pause and Intake
				deadline(
					new AutoIntake(intake, tower, serializer).withTimeout(1.5)
				),
				//Spline to Shooting Position
				deadline(
					splineToShot.andThen(() -> drivetrain.tankDriveVolts(0,0))
				),
				//Align and Shoot
				new AutoShotMid(shooter, tower, serializer).withTimeout(2)
				
			)

		);
	}

}