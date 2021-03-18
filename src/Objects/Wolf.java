package Objects;

import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

import java.io.FileInputStream;

public class Wolf extends Shape {

    public static final Wolf INSTANCE = new Wolf();

    //DIMENSION OF WOLF

    public static final int WIDTH_OF_WOLF = 200;
    public static final int HEIGHT_OF_WOLF = 300;

    //DIMENSION OF HANDS

    public static final int WIDTH_OF_HANDS = 150;
    public static final int HEIGHT_OF_HANDS = 100;

    //POSITION OF WOLF

    public static final int wolfY = 300;
    public static final int leftWolfX = 400;
    public static final int rightWolfX = 580;

    //POSITION OF HAND

    public static final int downHandsY = 450;
    public static final int upHandsY = 300;
    public static final int leftHandsX = 280;
    public static final int rightHandsX = 780;

    //STATES

    public static final int RIGHT = 1;
    public static final int LEFT= 2;

    public static final int UP = 1;
    public static final int DOWN = 2;

    //IMAGES

    public Image leftWolfImage;
    public Image rightWolfImage;
    public Image leftUpHandImage;
    public Image leftDownHandImage;
    public Image rightDownHandImage;
    public Image rightUpHandImage;


    public int currentStateVertical;
    public int currentStateHorizontal;
    int currentWolfX;
    int currentHandsX;
    int currentHandsY;


    Image currentImageWolf;
    Image currentImageHands;

    private Wolf(){
        currentStateHorizontal = RIGHT;
        currentStateVertical = UP;
        currentHandsX = leftHandsX;
        currentHandsY = downHandsY;
        createSetOfImages();
        setState(currentStateVertical, currentStateHorizontal);
    }

    @Override
    public com.sun.javafx.geom.Shape impl_configShape() {
        return null;
    }

    public void setState(int numberOfStateVertical, int numberOfStateHorizontal){
        if(numberOfStateVertical == UP && numberOfStateHorizontal == RIGHT){
            currentStateVertical = UP;
            currentStateHorizontal = RIGHT;
            currentImageHands = rightUpHandImage;
            currentImageWolf = rightWolfImage;
            currentWolfX = rightWolfX;
            currentHandsY = upHandsY;
            currentHandsX = rightHandsX;
        }
        else if(numberOfStateVertical == DOWN && numberOfStateHorizontal == RIGHT){
            currentStateVertical = DOWN;
            currentStateHorizontal = RIGHT;
            currentImageWolf = rightWolfImage;
            currentImageHands = rightDownHandImage;
            currentWolfX = rightWolfX;
            currentHandsY = downHandsY;
            currentHandsX = rightHandsX;
        }
        else if(numberOfStateVertical == UP && numberOfStateHorizontal == LEFT){
            currentStateVertical = UP;
            currentStateHorizontal = LEFT;
            currentImageWolf = leftWolfImage;
            currentImageHands = leftUpHandImage;
            currentWolfX = leftWolfX;
            currentHandsY = upHandsY;
            currentHandsX = leftHandsX;
        }
        else if(numberOfStateHorizontal == DOWN && numberOfStateHorizontal == LEFT){
            currentStateVertical = DOWN;
            currentStateHorizontal = LEFT;
            currentImageWolf = leftWolfImage;
            currentImageHands = leftDownHandImage;
            currentWolfX = leftWolfX;
            currentHandsY = downHandsY;
            currentHandsX = leftHandsX;
        }
    }

    private void createSetOfImages(){
        try {
            leftWolfImage = new Image(new FileInputStream("Graphics/ScaledLeftWolf.png"));
            rightWolfImage = new Image(new FileInputStream("Graphics/ScaledRightWolf.png"));
            leftUpHandImage = new Image(new FileInputStream("Graphics/LeftUpBasket.png"));
            leftDownHandImage = new Image(new FileInputStream("Graphics/LeftDownBasket.png"));
            rightDownHandImage = new Image(new FileInputStream("Graphics/RightDownBasket.png"));
            rightUpHandImage = new Image(new FileInputStream("Graphics/RightUpBasket.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
