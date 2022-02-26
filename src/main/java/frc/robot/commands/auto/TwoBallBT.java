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


public class TwoBallBT extends SequentialCommandGroup {

    public TwoBallBT(Drivetrain drivetrain){
        new Rotation2d();
        new Rotation2d();
        CustomRamseteCommand goTofirstBall =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(21.744), Units.feetToMeters(16.139), Rotation2d.fromDegrees(0)),
                new Pose2d(Units.feetToMeters(14.236), Units.feetToMeters(20.121), Rotation2d.fromDegrees(0))
            ),
            Units.feetToMeters(10), Units.feetToMeters(7), false
        );
        CustomRamseteCommand goTosecondBall =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(14.235), Units.feetToMeters(20.119), Rotation2d.fromDegrees(0)),
                new Pose2d(Units.feetToMeters(14.787), Units.feetToMeters(6.128), Rotation2d.fromDegrees(0))
            ),
            Units.feetToMeters(11), Units.feetToMeters(8), false
        );

        CustomRamseteCommand parktoLine =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(14.787), Units.feetToMeters(6.128), Rotation2d.fromDegrees(0)),
                new Pose2d(Units.feetToMeters(3.842), Units.feetToMeters(6.403), Rotation2d.fromDegrees(0.00))
            ),
            Units.feetToMeters(9), Units.feetToMeters(7), true
        );
                addCommands(
                    sequence(
                       // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                       new InstantCommand(() -> drivetrain.resetOdometry(goTofirstBall.getInitialPose())),
                        deadline(
                            goTofirstBall
                            //new IntakeCommand(intake, IntakeCommand.IntakeType.INTAKE),
                            // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                            //new AlignToTarget(drivetrain, limelight, true)
                        ),
                        // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                        deadline(
                            goTosecondBall
                            //new IntakeCommand(intake, IntakeCommand.IntakeType.INTAKE),
                            // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                            //new AlignToTarget(drivetrain, limelight, false)
                        ),
                        parktoLine.andThen(() -> drivetrain.tankDriveVolts(0,0))
                    )
                );

    }
}
