package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Intake.ConveyorPeriodic;

import frc.robot.Constants.*;



public class Index {
    public CANSparkMax lCON;
    public CANSparkMax rCON;
    public CANSparkMax sMI0;



    public enum IndexState {
        SHOOTING,
        UNRESTRICTED_SHOOTING,
        INTAKING,
        EXTAKING,
        DEFAULT
    }

    private IndexState indexState = IndexState.DEFAULT;

    private boolean enabled = false;

    public Index(Boolean shooterAtVelocity) {
        lCON = new CANSparkMax(IndexConstants.kleftConveyorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rCON = new CANSparkMax(IndexConstants.krightConveyorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        sMI0 = new CANSparkMax(IndexConstants.ksmIndexPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        lCON.setSmartCurrentLimit(30);
        rCON.setSmartCurrentLimit(30);
        sMI0.setSmartCurrentLimit(30);

        lCON.setInverted(true);
        rCON.setInverted(false);
        sMI0.setInverted(true);

        lCON.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rCON.setIdleMode(CANSparkMax.IdleMode.kBrake);
        sMI0.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void moveAll(double power) {
        lCON.set(power);
        rCON.set(power);
        sMI0.set(power);
    }

    public void setIndexState(IndexState indexState) {
        this.indexState = indexState;
    }



    public void updateIndexMotion(boolean horiz, boolean vert, boolean reversed){
        double horizontalPower = indexState == IndexState.SHOOTING  ? 0.5 : 0.3;

        double verticalPower = indexState == IndexState.SHOOTING ? 0.6 : 0.45;

        if(indexState.equals(IndexState.UNRESTRICTED_SHOOTING)) {
            verticalPower = 1;
            horizontalPower = 1;
        }

        if (reversed) {
            horizontalPower *= -1;
            verticalPower *= -1;
        }

        if(horiz)
            lCON.set(horizontalPower);
        else
            if (indexState != indexState.EXTAKING)
                lCON.set(0);

        if(vert)
            sMI0.set(verticalPower);
        else
            if(indexState != indexState.EXTAKING)
                sMI0.set(0);
    }

    @Override
    public void periodic() {

        SmartDashboard.putString("Index State", indexState.toString());
    }

    public IndexState getIndexState() {
        return indexState;
    }

    public void disable() {
        indexState = IndexState.DEFAULT;
        moveAll(0);
    }

    public boolean enabled() {
        return enabled;
    }

    public void initDefaultCommand(Boolean shooterAtVelocity) {
        setDefaultCommand(new IndexPeriodic(this, shooterAtVelocity));
    }
}
