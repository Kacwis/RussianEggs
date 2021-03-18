import Objects.Egg;
import Objects.Wolf;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class EggFalling {

    Thread eggFalling;
    int durationOfRolling = GlobalStates.timeOfEggRolling;

    //ROLLING

    final Line upRight = new Line(1180,80,890,230);
    final Line downRight = new Line(1150,275,890,420);
    final Line upLeft = new Line(50,75, 310, 220);
    final Line downLeft = new Line(50,270,300,420);

    //FALLING

    final Line upRightFallInBasket = new Line(890, 230,890, 300);
    final Line upRightFall = new Line(890,230,890, 580);
    final Line downRightFallInBasket = new Line(890,420, 890,460);
    final Line downRightFall = new Line(890,420,890,550);
    final Line upLeftFallInBasket = new Line(310,220,310,300);
    final Line upLeftFall = new Line(310,220,310,550);
    final Line downLeftFallInBasket = new Line(310,420, 310, 470);
    final Line downLeftFall = new Line(310,420,310,550);


    RotateTransition rotation;
    PathTransition path;
    PathTransition pathOfFalling;
    ParallelTransition allEffects;
    Egg egg;
    boolean isRightPosition = false;
    int durationOFFalling = 500;


    public EggFalling(int verticalState, int horizontalState){
        this.egg = new Egg();
        egg.setVisible(true);
        eggFalling = new Thread(new Runnable() {
            @Override
            public void run() {
                settingThread(verticalState, horizontalState);
            }
        });
        eggFalling.start();
    }

    private void settingThread(int verticalState, int horizontalState){
        rotation = new RotateTransition();
        if(horizontalState == Wolf.RIGHT){
            rotation.setFromAngle(360);
            rotation.setToAngle(0);
        }
        else if(horizontalState == Wolf.LEFT){
            rotation.setFromAngle(0);
            rotation.setToAngle(360);
        }
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.setDuration(Duration.millis(GlobalStates.timeOfEggRolling));
        rotation.setNode(egg);
        path = new PathTransition();
        path.setCycleCount(1);
        path.setPath(pickRollingPath(verticalState,horizontalState));
        path.setDuration(Duration.millis(GlobalStates.timeOfEggRolling));
        allEffects = new ParallelTransition(egg,path,rotation);
        allEffects.play();
        try {
            Thread.sleep(GlobalStates.timeOfEggRolling);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        allEffects.pause();
        if(verticalState == Wolf.INSTANCE.currentStateVertical && horizontalState ==  Wolf.INSTANCE.currentStateHorizontal) {
            isRightPosition = true;
            GlobalStates.INSTANCE.pointsCounter = GlobalStates.INSTANCE.pointsCounter + 1;
        }
        pathOfFalling = new PathTransition();
        pathOfFalling.setPath(pickFallingPath(verticalState,horizontalState, isRightPosition));
        pathOfFalling.setDuration(Duration.millis(durationOFFalling));
        pathOfFalling.setCycleCount(1);
        pathOfFalling.setNode(egg);
        pathOfFalling.play();
        try{
            Thread.sleep(durationOFFalling);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(!isRightPosition) {
            GlobalStates.INSTANCE.counterOfCrackedEggs = GlobalStates.INSTANCE.counterOfCrackedEggs + 1;
            MainFrame.crackingEggs(horizontalState);
            try{
                Thread.sleep(500);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        MainFrame.disappearEgg();
        }
        egg.setVisible(false);

    }

    private Line pickRollingPath(int verticalStart, int horizontalStart){
        if(verticalStart == Wolf.UP && horizontalStart == Wolf.RIGHT)
            return upRight;
        if(verticalStart == Wolf.DOWN && horizontalStart == Wolf.RIGHT)
            return downRight;
        if(verticalStart == Wolf.UP && horizontalStart == Wolf.LEFT)
            return upLeft;
        return downLeft;
    }

    private Line pickFallingPath(int verticalStart,int horizontalStart, boolean isRightPosition){
        if(verticalStart == Wolf.UP && horizontalStart == Wolf.RIGHT) {
            if (isRightPosition)
                return upRightFallInBasket;
            durationOFFalling = 1000;
            return upRightFall;
        }
        if(verticalStart == Wolf.DOWN && horizontalStart == Wolf.RIGHT) {
            if (isRightPosition)
                return downRightFallInBasket;
            durationOFFalling = 1000;
            return downRightFall;
        }
        if(verticalStart == Wolf.UP && horizontalStart == Wolf.LEFT){
            if(isRightPosition)
                return upLeftFallInBasket;
            durationOFFalling = 1000;
            return upLeftFall;
        }
        if(isRightPosition)
            return downLeftFallInBasket;
        durationOFFalling = 1000;
        return downLeftFall;
    }

    public Egg getEgg() {
        return egg;
    }
}
