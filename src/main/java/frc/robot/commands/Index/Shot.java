package frc.robot.commands.Index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Shot extends CommandBase {

    private Index index;
    private IndexType indexType;

    public Shot(Index index, IndexType indexType){
        this.index = index;
        this.indexType = indexType;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        switch(indexType){
            case SHOT:
            index.setBothTowerPower(1);
            break;
            case EXTAKE:
            index.setBothTowerPower(-1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        index.disable();
    }

    public enum IndexType {
        SHOT,
        EXTAKE
    }

}
