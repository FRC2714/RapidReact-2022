// package frc.robot.commands.auto;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.commands.shooter.TeleOpShooter;
// import frc.robot.commands.shooter.TeleOpShooter.ShooterType;
// import frc.robot.subsystems.*;
// import frc.robot.utils.*;

// public class OneBallAuton extends SequentialCommandGroup {

//   public OneBallAuton(Drivetrain drivetrain, Shooter shooter){
//     addCommands(new TeleOpShooter(shooter, ShooterType.CLOSE));
//   }