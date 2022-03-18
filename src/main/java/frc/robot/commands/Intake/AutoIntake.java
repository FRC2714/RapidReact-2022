package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Tower;

public class AutoIntake extends CommandBase {
    private Intake intake;
    private Tower tower;
    private Serializer serializer;

    public AutoIntake(Intake intake, Tower tower, Serializer serializer){
        this.intake = intake;
        this.tower = tower;
        this.serializer = serializer; 
    }

    public void execute(){
        intake.intakeBalls();
        serializer.serializeBalls();
        tower.breakBeamTower();
    }
    
    @Override
    public void end(boolean interrupted){
        intake.disable();
        serializer.disable();
        tower.disable();
    }

}
