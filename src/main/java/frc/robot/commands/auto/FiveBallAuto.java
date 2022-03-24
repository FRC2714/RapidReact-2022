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
import frc.robot.commands.shooter.TeleOpShooter;
import frc.robot.commands.shooter.TeleOpShooter.*;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class FiveBallAuto extends SequentialCommandGroup {

	public FiveBallAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Serializer serializer, Tower tower) {
		CustomRamseteCommand splineToBallOne =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(30.7), Units.feetToMeters(11.2), Rotation2d.fromDegrees(-22)),
					new Pose2d(Units.feetToMeters(37.0), Units.feetToMeters(7.2), Rotation2d.fromDegrees(-32))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		CustomRamseteCommand splineToHuman =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(37.0), Units.feetToMeters(7.2), Rotation2d.fromDegrees(-32)),
					new Pose2d(Units.feetToMeters(49.5), Units.feetToMeters(22.2), Rotation2d.fromDegrees(45))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		CustomRamseteCommand splineToShot =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(49.5), Units.feetToMeters(22.2), Rotation2d.fromDegrees(45)),
					new Pose2d(Units.feetToMeters(35.0), Units.feetToMeters(11.4), Rotation2d.fromDegrees(180))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), true
			);
      CustomRamseteCommand shotToFith =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(35.0), Units.feetToMeters(11.4), Rotation2d.fromDegrees(180)),
					new Pose2d(Units.feetToMeters(37.2), Units.feetToMeters(21.4), Rotation2d.fromDegrees(-135))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		addCommands(
			sequence(
				//reset odometry
				new InstantCommand(() -> drivetrain.resetOdometry(splineToBallOne.getInitialPose())),

				deadline(
					//Run intake and vove to first ball
					splineToBallOne,
					new AutoIntake(intake, tower, serializer)

				),
				deadline(
					//Run shooter for 0.5s
					new AutoShotMid(shooter, tower, serializer).withTimeout(.5)
				),

				deadline(
					//Run intake and move to Human Player
					splineToHuman,
					new AutoIntake(intake, tower, serializer).withTimeout(2)
				),

				deadline(
					//Move to shooting position
					splineToShot
				),
				deadline(
					//Run shooter for 0.5s
					new AutoShotMid(shooter, tower, serializer).withTimeout(.5)
				),

        deadline(
					//Move to fifth ball
					shotToFith
				),
        deadline(
					//Run shooter for 0.5s
					new AutoShotMid(shooter, tower, serializer).withTimeout(.5)
				)
			)

		);
	}

}