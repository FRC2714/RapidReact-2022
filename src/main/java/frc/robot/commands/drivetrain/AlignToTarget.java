package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class AlignToTarget extends ProfiledPIDCommand{
 
    private static Drivetrain drive;
    private Limelight limelight;
    private Drivetrain drivetrain;
    private boolean isAutoEnabled = false;

    


    public AlignToTarget(Drivetrain drivetrain, Limelight limelight){
        this(limelight, drivetrain, null);
    }
    
    public AlignToTarget(Drivetrain drivetrain, Limelight limelight, boolean isAutoEnabled){
        this(drivetrain, limelight);
        this.isAutoEnabled = isAutoEnabled;
    }

    public AlignToTarget(Limelight limelight, Drivetrain drivetrain, DoubleSupplier rawY){
       super(
            new ProfiledPIDController(DriveConstants.kAlignP, 0, DriveConstants.kAlignD,
                        new TrapezoidProfile.Constraints(100, 300)),
            limelight::getXAngleOffset,
            0,
            (output, setpoint) -> drive.arcadeDrive(-rawY.getAsDouble(), Math.signum(output) * 0.16),
            drive
        );
        addRequirements(drive);

        this.limelight = limelight;
        this.drivetrain = drive; 
        getController().enableContinuousInput(-180, 180);

        getController().setTolerance(.75,4);    
    }

    @Override
    public void initialize() {
        limelight.setLED(true);
    }

    @Override
    public boolean isFinished() {
        if(isAutoEnabled)
            return
                getController().atGoal() || !limelight.targetVisible();
        else
            return false;

    }
    @Override
    public void end(boolean interrupted){
        drivetrain.tankDriveVolts(0, 0);

        if(!isAutoEnabled)
            limelight.setLED(false);
    }
}
