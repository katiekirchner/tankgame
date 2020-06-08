package TankGame.Objects;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class Background extends GameObjects {

    public Background(int x, int y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
        try {
            this.pic = ImageIO.read(getClass().getResource("/Resources/waterlarge.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.active = true;
        r = new Rectangle(x, y, pic.getWidth(), pic.getHeight());
    }


    @Override
    public void update() {

    }

    @Override
    public void collision(GameObjects obj) {

    }


}
