package TankGame.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Wall extends GameObjects {
    private int strength;
    private BufferedImage borderWall;
    private BufferedImage breakWall;
    private String type;



    public Wall(int x, int y, int strength, String t) {
        type = t;
        this.x = x;
        this.y = y;

        this.vx = 0;
        this.vy = 0;

        this.angle = 0;
        this.strength = strength;
        try {
            this.breakWall = ImageIO.read(getClass().getResource("/Resources/coral.png"));
            this.borderWall = ImageIO.read(getClass().getResource("/Resources/seaweed.png"));

            if (type == "border") {
                this.pic = this.borderWall;
            } else if (type == "breakable"){
                this.pic = this.breakWall;
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
    }

    @Override
    public void collision(GameObjects obj) {

        if (!this.type.equals("border")) {
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


}
