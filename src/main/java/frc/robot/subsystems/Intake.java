package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

    private CANSparkMax lIntakeMotor;
    private CANSparkMax rIntakeMotor;

    public Intake() {
        lIntakeMotor = new CANSparkMax(IntakeConstants.kLIntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rIntakeMotor = new CANSparkMax(IntakeConstants.kRIntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        lIntakeMotor.setSmartCurrentLimit(40);
        rIntakeMotor.setSmartCurrentLimit(40);

        rIntakeMotor.setInverted(true);
    }

    public void intakeBalls() {
        setIntakePower(IntakeConstants.kIntakePower);
    }
    
    public void extakeBalls() {
        setIntakePower(-IntakeConstants.kIntakePower);
    }

    public void disbale() {
        setIntakePower(0);
    }

    public void setIntakePower(double power){
        lIntakeMotor.set(power);
        rIntakeMotor.set(power);

    }
}