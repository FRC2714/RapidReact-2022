package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

    private CANSparkMax lIntakeMotor;
    private CANSparkMax rIntakeMotor;
    private CANSparkMax lSerializer;
    private CANSparkMax rSerializer;

    public Intake() {
        lIntakeMotor = new CANSparkMax(IntakeConstants.kLIntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rIntakeMotor = new CANSparkMax(IntakeConstants.kRIntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        lSerializer = new CANSparkMax(IntakeConstants.kLSerializerPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rSerializer = new CANSparkMax(IntakeConstants.kRSerializerPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        lIntakeMotor.setSmartCurrentLimit(40);
        rIntakeMotor.setSmartCurrentLimit(40);
        lSerializer.setSmartCurrentLimit(40);

        rIntakeMotor.setInverted(true);
        rSerializer.setInverted(true);
    }

    public void intakeBalls() {
        setIntakePower(IntakeConstants.kIntakePower);
        setSerializerPower(IntakeConstants.kSerializerPower);
    }
    
    public void serializeBalls() {
        setSerializerPower(IntakeConstants.kSerializerPower);
    }

    private void setSerializerPower(double power) {
        lSerializer.set(power);
        rSerializer.set(power);
    }

    public void extakeBalls() {
        setIntakePower(-IntakeConstants.kIntakePower);
        setSerializerPower(-IntakeConstants.kSerializerPower);
    }

    public void disbale() {
        setIntakePower(0);
        setSerializerPower(0);
    }

    public void setIntakePower(double power){
        lIntakeMotor.set(power);
        rIntakeMotor.set(power);

    }
}