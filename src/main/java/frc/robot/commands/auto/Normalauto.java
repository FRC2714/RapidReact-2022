package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class Normalauto extends SequentialCommandGroup {

    public Normalauto(Drivetrain drivetrain){
        new Rotation2d();
        new Rotation2d();
        CustomRamseteCommand splinetofirstball =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(28.75), Units.feetToMeters(19.26), Rotation2d.fromDegrees(0.00)),
                new Pose2d(Units.feetToMeters(38.07), Units.feetToMeters(20.28), Rotation2d.fromDegrees(0.00))
            ),
            Units.feetToMeters(10), Units.feetToMeters(10), false
        );
        CustomRamseteCommand splinetosecondball =
        RamseteGenerator.getRamseteCommand(
        drivetrain,
        List.of(
            new Pose2d(Units.feetToMeters(38.1), Units.feetToMeters(19.3), Rotation2d.fromDegrees(0.00)),
            new Pose2d(Units.feetToMeters(29), Units.feetToMeters(25.63), Rotation2d.fromDegrees(0.00))
        ),
        Units.feetToMeters(10), Units.feetToMeters(10), false
    );
            addCommands(
                sequence(
                // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                 new InstantCommand(() -> drivetrain.resetOdometry(splinetofirstball.getInitialPose())),
                     deadline(
                         splinetofirstball
                         //new IntakeCommand(intake, IntakeCommand.IntakeType.INTAKE)
                             ),
                             deadline(
                        // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //
                        splinetosecondball.andThen(() -> drivetrain.tankDriveVolts(0,0))
                             )
        )
    );
    }
}