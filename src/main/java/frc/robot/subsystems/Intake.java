package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

    private CANSparkMax iM0;

    public Intake() {
        iM0 = new CANSparkMax(IntakeConstants.kIntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        iM0.setSmartCurrentLimit(40);

        iM0.setInverted(false);
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
        iM0.set(power);
    }
}