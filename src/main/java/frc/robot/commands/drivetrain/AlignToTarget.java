package frc.robot.commands.drivetrain;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class AlignToTarget extends CommandBase{
 
    private Limelight limelight;
    private Drivetrain drivetrain;


    private double tolerance = 0.5;

    private double kP = 1;
    private double maxOffset = 29;

    public AlignToTarget(Limelight limelight, Drivetrain drivetrain){
       this.limelight = limelight;
       this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        limelight.setLED(true);
    }

    @Override
    public void execute() {
        double angleOffset = limelight.getXAngleOffset();
        
        double power = angleOffset * (kP/maxOffset);

        if (Math.abs(angleOffset) > tolerance) {

            if(power > 0.7) power = 0.7;
            if(power < -0.7) power = -0.7;
            
            drivetrain.arcadeDrive(0, power);
            
        }
    }


    @Override
    public void end(boolean interrupted){
        drivetrain.tankDriveVolts(0, 0);
    }
}
