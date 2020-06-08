package TankGame.Objects;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Shield extends GameObjects {

    public Shield(int x, int y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
        this.active = true;
        try {
            this.pic = ImageIO.read(getClass().getResource("/Resources/shield.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        r = new Rectangle(x, y, pic.getWidth(), pic.getHeight());
    }


    @Override
    public void update() {

    }

    @Override
    public void collision(GameObjects obj) {
        if (obj instanceof Tank) {
            this.active = false;
        }
    }
}
