package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Index.Shot;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class BackShoot extends SequentialCommandGroup {

    public BackShoot(Drivetrain drivetrain, Shooter shooter, Index index, Intake intake){
        new Rotation2d();
        new Rotation2d();
        CustomRamseteCommand MoveBack =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(28.826), Units.feetToMeters(18.646), Rotation2d.fromDegrees(0.00)),
                new Pose2d(Units.feetToMeters(31.611), Units.feetToMeters(19.382), Rotation2d.fromDegrees(0.00))
            ),
            Units.feetToMeters(3), Units.feetToMeters(3), true
        );
            addCommands(
                sequence(
                 new InstantCommand(() -> drivetrain.resetOdometry(MoveBack.getInitialPose())),
                 deadline(
                         MoveBack,
                         //new IntakeCommand(intake, IntakeCommand.IntakeType.INTAKE)
                         new Shot(shooter, intake, index)
                             )
        )
    );
    }
}
