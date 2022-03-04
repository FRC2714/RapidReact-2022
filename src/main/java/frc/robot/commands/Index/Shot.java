package frc.robot.commands.Index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Shot extends CommandBase {

    private Index index;

    public Shot(Index index){
        this.index = index;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        index.setBothTowerPower(1);
    }

    @Override
    public void end(boolean interrupted) {
        index.disable();
    }

}
