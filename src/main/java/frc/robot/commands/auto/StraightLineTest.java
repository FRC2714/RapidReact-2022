// package frc.robot.commands.auto;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.subsystems.*;
// import frc.robot.utils.*;

// import java.util.List;

// public class StraightLineTest extends SequentialCommandGroup {

//     public StraightLineTest(Drivetrain drivetrain){
//         new Rotation2d();
//         new Rotation2d();
//         CustomRamseteCommand StraightLine =
//             RamseteGenerator.getRamseteCommand(
//             drivetrain,
//             List.of(
//                 new Pose2d(Units.feetToMeters(39.893), Units.feetToMeters(24.31), Rotation2d.fromDegrees(0.00)),
//                 new Pose2d(Units.feetToMeters(31.611), Units.feetToMeters(22.345), Rotation2d.fromDegrees(0.00))
//             ),
//             Units.feetToMeters(3), Units.feetToMeters(3), true
//         );
//             addCommands(
//                 sequence(
//                  new InstantCommand(() -> drivetrain.resetOdometry(StraightLine.getInitialPose())),
//                  StraightLine.andThen(() -> drivetrain.tankDriveVolts(0, 0)
//                  )
//         )
//     );
//     }
// }