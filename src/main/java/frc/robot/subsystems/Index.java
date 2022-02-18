package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.Constants.*;

public class Index {
    public CANSparkMax lIndex;
    public CANSparkMax rIndex;
    public CANSparkMax innerTower;
    public CANSparkMax outerTower;



    public enum IndexState {
        SHOOTING,
        INTAKING,
        EXTAKING,
        DEFAULT
    }

    private IndexState indexState = IndexState.DEFAULT;

    public Index(Boolean shooterAtVelocity) {
        lIndex = new CANSparkMax(IndexConstants.kleftIndexPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rIndex = new CANSparkMax(IndexConstants.krightIndexPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        innerTower = new CANSparkMax(IndexConstants.kiTowerPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        outerTower = new CANSparkMax(IndexConstants.koTowerPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        lIndex.setSmartCurrentLimit(30);
        rIndex.setSmartCurrentLimit(30);
        innerTower.setSmartCurrentLimit(30);
        outerTower.setSmartCurrentLimit(30);

        lIndex.setInverted(true);
        rIndex.setInverted(false);
        innerTower.setInverted(true);
        outerTower.setInverted(false);

        lIndex.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rIndex.setIdleMode(CANSparkMax.IdleMode.kBrake);
        innerTower.setIdleMode(CANSparkMax.IdleMode.kBrake);
        outerTower.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setIndexState(IndexState indexState) {
        this.indexState = indexState;
    }


    public void updateIndexMotion(){
        double lIndexPower = 0;
        double rIndexPower = 0;
        double innerTowerPower = 0;
        double outerTowerPower = 0;

        if(indexState.equals(IndexState.SHOOTING)){
            lIndexPower = 1;
            rIndexPower = 1;
            innerTowerPower = 1;
            outerTowerPower = 1;
        }

        if(indexState.equals(IndexState.EXTAKING)){
            lIndexPower = -1;
            rIndexPower = -1;
            innerTowerPower = -1;
            outerTowerPower = -1;
        }
        if(indexState.equals(IndexState.INTAKING)){
            lIndexPower = 1;
            rIndexPower = 1;
            innerTowerPower = 1;
        }

        if(indexState.equals(IndexState.DEFAULT)){
            lIndexPower = rIndexPower = innerTowerPower = outerTowerPower = 0;
        }
      
        lIndex.set(lIndexPower);
        rIndex.set(rIndexPower);
        innerTower.set(innerTowerPower); 
        outerTower.set(outerTowerPower);
    }

    public IndexState getIndexState() {
        return indexState;
    }

    public void disable() {
        indexState = IndexState.DEFAULT;
    }


}
