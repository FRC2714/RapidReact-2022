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
		CustomRamseteCommand splineToBallOne =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(24.5), Units.feetToMeters(5.7), Rotation2d.fromDegrees(180)),
					new Pose2d(Units.feetToMeters(16.5), Units.feetToMeters(6.3), Rotation2d.fromDegrees(-170))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		CustomRamseteCommand splineToHuman =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(16.5), Units.feetToMeters(6.3), Rotation2d.fromDegrees(-170)),
					new Pose2d(Units.feetToMeters(4.3), Units.feetToMeters(4.6), Rotation2d.fromDegrees(-135))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
			
		CustomRamseteCommand splineToShot =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(4.3), Units.feetToMeters(4.6), Rotation2d.fromDegrees(-135)),
					new Pose2d(Units.feetToMeters(16.5), Units.feetToMeters(6.3), Rotation2d.fromDegrees(-170))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), true
			);
		addCommands(
			sequence(
				//reset odometry
				new InstantCommand(() -> drivetrain.resetOdometry(splineToBallOne.getInitialPose())),
				//Run intake and vove to first ball
				deadline(
					splineToBallOne,
					new AutoIntake(intake, tower, serializer)

				),
				//Align and Shoot
				new CustomAlignToTarget(drivetrain, limelight, true).withTimeout(1.5),
				new AutoShotMid(shooter, tower, serializer).withTimeout(2),
				//Run intake and move to Human Player
				deadline(
					splineToHuman,
					new AutoIntake(intake, tower, serializer)
				),
				//Pause and Intake
				deadline(
					new AutoIntake(intake, tower, serializer).withTimeout(3)
				),
				//Spline to Shooting Position
				deadline(
					splineToShot
				),
				//Align and Shoot
				new CustomAlignToTarget(drivetrain, limelight, true).withTimeout(1.5),
				new AutoShotMid(shooter, tower, serializer).withTimeout(2)
				
			)

		);
	}

}