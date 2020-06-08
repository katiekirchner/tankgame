package TankGame.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;



public class Floaters extends GameObjects {
    private int strength;

    private BufferedImage floatOne;
    private BufferedImage floatTwo;
    private BufferedImage explosion;
    public String type;



    public Floaters(int x, int y, int strength, String t) {
        type = t;
        this.x = x;
        this.y = y;

        if (type == "recycleFloat" || type == "ringFloat") {

            Random rand = new Random();
            this.vx = rand.nextInt(3) - 1;
            this.vy = rand.nextInt(3) - 1;

        } else {
            this.vx = 0;
            this.vy = 0;
        }

        this.angle = 0;
        this.strength = strength;

        try {
            this.floatOne = ImageIO.read(getClass().getResource("/Resources/recycle.png"));
            this.floatTwo = ImageIO.read(getClass().getResource("/Resources/plasticrings.png"));
            this.explosion = ImageIO.read(getClass().getResource("/Resources/ex.gif"));

            if (type == "recycleFloat"){
                this.pic = floatOne;
            }else if (type == "ringFloat"){
                this.pic = floatTwo;
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        this.active = true;

        r = new Rectangle(x, y, pic.getWidth(), pic.getHeight());
    }


    @Override
    public void update() {

        this.x += this.vx;
        this.y += this.vy;
        r.setLocation(x, y);

        if (StarPowerUp.returnSwitch()){
            if (StarPowerUp.isRingSwitch()){
                this.pic = floatTwo;
            } else {
                this.pic = floatOne;
            }
        }
    }

    @Override
    public void collision(GameObjects obj) {

        if (this.strength < 9) {
            if (obj instanceof Projectile) {
                this.strength -= 1;
                if (strength <= 0) {
                    this.active = false;
                }
            }

            if (obj instanceof Wall || obj instanceof Tank) {
                this.vx = -this.vx;
                this.vy = -this.vy;
                this.x += this.vx;
                this.y += this.vy;
            }
        }


    }

    public String floaterType() {return this.type;}



}
