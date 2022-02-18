package frc.robot.commands.Index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
//plz 
public class SingleShot extends CommandBase {

    private Shooter shooter;
    private Index index;
    private Intake intake;

    public SingleShot(Shooter shooter, Intake intake, Index index){
        this.shooter = shooter;
        this.index = index;
        this.intake = intake;
    }

    @Override
    public void initialize() {
        index.setIndexState(Index.IndexState.SHOOTING);
        intake.setIntakePower(0.5);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        intake.disbale();
        shooter.disable();
        index.disable();
        index.setIndexState(Index.IndexState.DEFAULT);
    }
}
