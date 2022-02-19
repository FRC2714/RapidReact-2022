package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class AlignToTarget extends ProfiledPIDCommand{
    private Limelight limelight;
    private Drivetrain drivetrain;
    private boolean isAutoEnabled = false;

    public AlignToTarget(Drivetrain drivetrain, Limelight limelight){
        this(limelight, drivetrain, () -> 0);

    }
    
    public AlignToTarget(Drivetrain drivetrain, Limelight limelight, boolean isAutoEnabled){
        this(limelight, drivetrain, () -> 0);
        this.isAutoEnabled = isAutoEnabled;
    }

    public AlignToTarget(Limelight limelight, Drivetrain drivetrain, DoubleSupplier rawY){
        super(
            new ProfiledPIDController(DriveConstants.kAlignP, 0, DriveConstants.kAlignD,
                        new TrapezoidProfile.Constraints(100, 300)),
            limelight::getXAngleOffset(),

            0,
            (output, setpoint) -> drive.arcadeDrive(-rawY.getAsDouble(), Math.Signum(output) * 0.16)


            

        );
    }
}
