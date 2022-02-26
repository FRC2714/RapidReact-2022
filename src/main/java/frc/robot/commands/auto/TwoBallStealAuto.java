package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.AlignToTarget;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Intake.IntakeCommand.IntakeType;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class TwoBallStealAuto extends SequentialCommandGroup {

    public TwoBallStealAuto(Drivetrain drivetrain, Limelight limelight, Intake intake){
        new Rotation2d();
        new Rotation2d();
        CustomRamseteCommand goTofirstBall =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(29.369), Units.feetToMeters(19.303), Rotation2d.fromDegrees(0)),
                new Pose2d(Units.feetToMeters(31.107), Units.feetToMeters(24.797), Rotation2d.fromDegrees(45))
            ),
            Units.feetToMeters(10), Units.feetToMeters(7), false
        );
        CustomRamseteCommand goTosecondBall =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(31), Units.feetToMeters(24), Rotation2d.fromDegrees(45)),
                new Pose2d(Units.feetToMeters(39.008), Units.feetToMeters(21.929), Rotation2d.fromDegrees(40))
            ),
            Units.feetToMeters(11), Units.feetToMeters(8), true
        );

        CustomRamseteCommand parktoLine =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(39), Units.feetToMeters(21), Rotation2d.fromDegrees(40)),
                new Pose2d(Units.feetToMeters(44.99), Units.feetToMeters(21.929), Rotation2d.fromDegrees(0.00))
            ),
            Units.feetToMeters(9), Units.feetToMeters(7), true
        );
                addCommands(
                    sequence(
                       // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                       new InstantCommand(() -> drivetrain.resetOdometry(goTofirstBall.getInitialPose())),
                        deadline(
                            goTofirstBall,
                            new IntakeCommand(intake, IntakeCommand.IntakeType.INTAKE),
                            // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                            new AlignToTarget(drivetrain, limelight, true)
                        ),
                        // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                        deadline(
                            goTosecondBall,
                            new IntakeCommand(intake, IntakeCommand.IntakeType.INTAKE),
                            // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                            new AlignToTarget(drivetrain, limelight, false)
                        ),
                        parktoLine.andThen(() -> drivetrain.tankDriveVolts(0,0))
                    )
                );

    }
}
