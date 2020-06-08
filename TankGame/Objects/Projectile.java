package TankGame.Objects;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Projectile extends GameObjects {

    private final int R = 9;
    private BufferedImage explosion;
    private Tank shotBy;
    private int explosionTime;
    private boolean collided;


    Projectile(int x, int y, int angle, Tank tank) {
        this.x = x;
        this.y = y;
        this.vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        this.angle = angle;
        this.active = true;
        this.explosionTime = 0;
        this.shotBy = tank;
        this.collided = false;

        try {

            if (tank.returnType() == "bag") {
                this.pic = ImageIO.read(getClass().getResource("/Resources/straw.png"));
            } else {
                this.pic = ImageIO.read(getClass().getResource("/Resources/octopus.png"));

            }

            this.explosion = ImageIO.read(getClass().getResource("/Resources/ex.gif"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        r = new Rectangle(x, y, pic.getWidth(), pic.getHeight());

    }




    @Override
    public void update() {
        x += vx;
        y += vy;

        r.setLocation(x, y);

        if (explosionTime >= 4) {
            active = false;
        }

        if (collided) {
            explosionTime++;
        }
    }

    @Override
    public void collision(GameObjects obj) {


        if (!(obj instanceof StarPowerUp) || (obj instanceof Shield)) {
            this.collided = true;
            this.vx = 0;
            this.vy = 0;


            this.pic = explosion;
        }
    }

    public boolean hasCollided() {
        return collided;
    }

    public Tank getShotBy() {
        return shotBy;
    }


}
