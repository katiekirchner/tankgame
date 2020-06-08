package TankGame.Objects;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObjects {


    int x;
    int y;
    int vx;
    int vy;
    int angle;
    Boolean active;
    BufferedImage pic;
    Rectangle r;

    public abstract void update();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void collision(GameObjects obj);

    public Rectangle getR() {
        return (new Rectangle(this.x, this.y, pic.getWidth(), pic.getHeight()));
    }

    public boolean isActive() {
        return active;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.pic.getWidth() / 2.0, this.pic.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.pic, rotation, null);
        //g.setColor(Color.RED);
        //g.drawRect(this.x, this.y, this.pic.getWidth(), this.pic.getHeight());
    }


}


