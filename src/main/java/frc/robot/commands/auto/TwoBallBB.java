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


public class TwoBallBB extends SequentialCommandGroup {

    public TwoBallBB(Drivetrain drivetrain){
        new Rotation2d();
        new Rotation2d();
        CustomRamseteCommand goTofirstBall =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(24.530), Units.feetToMeters(8.131), Rotation2d.fromDegrees(0)),
                new Pose2d(Units.feetToMeters(23.115), Units.feetToMeters(1.188), Rotation2d.fromDegrees(0))
            ),
            Units.feetToMeters(10), Units.feetToMeters(7), false
        );
        CustomRamseteCommand goTosecondBall =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(22.891), Units.feetToMeters(0.944), Rotation2d.fromDegrees(0)),
                new Pose2d(Units.feetToMeters(16.656), Units.feetToMeters(4.263), Rotation2d.fromDegrees(-90))
            ),
            Units.feetToMeters(10), Units.feetToMeters(10), true
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
