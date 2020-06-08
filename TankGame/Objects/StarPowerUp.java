package TankGame.Objects;

import TankGame.TankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class StarPowerUp extends GameObjects {

    static boolean switched = false;
    static boolean ringSwitch = false;

    public void newMethod(){
        if (this.switched = false){
            this.x = x * x;
            this.y = y + y;

            ArrayList<Integer> newList = new ArrayList<Integer>();

            for (int i=0; i >= x+y; i++){
                newList.add(i);
            }

            System.out.println("Loop in the newMethod");
        }
    }


    private int returnInt(int i){
        int ii = 0;

        for (int n = 0; n < 100; n++){
            for (int j = 0; j < 100; j++){

            }
        }

        return 0;

    }

    public StarPowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
        this.active = true;
        try {
            this.pic = ImageIO.read(getClass().getResource("/Resources/star.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        r = new Rectangle(x, y, pic.getWidth(), pic.getHeight());


    }

    @Override
    public void update() { }

    @Override
    public void collision(GameObjects obj) {
        if (obj instanceof Tank) {
            this.active = false;
        }
    }

    public static void switchFloaters(String tankType){
        ArrayList<GameObjects> temp = TankGame.returnArray();

        for (GameObjects obj : temp){
            if (obj instanceof Floaters) {
                Floaters floater = (Floaters) obj;

                if (tankType == "bag"){
                    floater.type = "ringFloat";
                    ringSwitch = true;
                } else if(tankType == "turtle"){
                    floater.type = "recycleFloat";
                }

                switched = true;
            }
        }
    }

    public static boolean returnSwitch(){return switched;}

    public static boolean isRingSwitch(){return ringSwitch;}


}
