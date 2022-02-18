package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

    private CANSparkMax intakemotor;

    public Intake() {
        intakemotor = new CANSparkMax(IntakeConstants.kIntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        intakemotor.setSmartCurrentLimit(40);
    }

    public void intakeBalls() {
        setIntakePower(IntakeConstants.kIntakePower);
    }

    public void extakeBalls() {
        setIntakePower(-IntakeConstants.kIntakePower); //putting in this constant from old code cause its funny
    }

    public void disbale() {
        setIntakePower(0);
    }

    public void setIntakePower(double power){
        intakemotor.set(power);
    }
}