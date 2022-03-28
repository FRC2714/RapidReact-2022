package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class MoveClimber extends CommandBase {
    public Climber climber;
    public ClimberMotionType ClimberMotionType;

    public MoveClimber(Climber climber, ClimberMotionType climberMotionType){
        this.climber = climber;
        this.ClimberMotionType = climberMotionType;
    }

    @Override
    public void execute(){
        switch(ClimberMotionType){
            case EXTEND:
            climber.climberUp();
            System.out.println("Climbing Up");
            break; 
            case RETRACT:
            climber.climberDown();
            System.out.println("Climbing Down");
            break;
            case REACH:
            climber.highUp();
            System.out.println("Reaching Out");
            break;
            case RECOIL:
            climber.highDown();
            System.out.println("Pulling Away");
            break;
        }

    }

    @Override
    public void end(boolean interrupted){
        climber.disable();
        System.out.println("climber End:" + climber.getPosition());
    }

    public enum ClimberMotionType{
        EXTEND,
        RETRACT,
        REACH,
        RECOIL
    }
}
