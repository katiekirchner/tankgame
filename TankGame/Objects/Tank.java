package TankGame.Objects;

import TankGame.TankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tank extends GameObjects {
    private final int R = 3;
    private final int rotationSpeed = 2;
    private boolean upKeyPressed, downKeyPressed, leftKeyPressed, rightKeyPressed, shootKeyPressed;
    private int timeSinceLastProjectile, health, livesRemaining;
    private int origX, origY, origAngle;
    private boolean canMoveUp, canMoveDown, canMoveLeft, canMoveRight;
    private TankGame tankGame;
    private boolean hasShield;
    private String type;
    private BufferedImage death;


    public Tank(int x, int y, int angle, String type, TankGame tankGame, String path) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.type = type;
        this.angle = angle;
        try {
            this.pic = ImageIO.read(getClass().getResource(path));
            this.death = ImageIO.read(getClass().getResource("/Resources/smalldeath.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tankGame = tankGame;

        this.canMoveDown = true;
        this.canMoveLeft = true;
        this.canMoveRight = true;
        this.canMoveUp = true;

        this.active = true;
        this.hasShield = false;
        this.timeSinceLastProjectile = 1000;
        this.health = 100;
        this.livesRemaining = 3;

        this.downKeyPressed = false;
        this.leftKeyPressed = false;
        this.rightKeyPressed = false;
        this.upKeyPressed = false;
        this.shootKeyPressed = false;

        this.origAngle = angle;
        this.origX = x;
        this.origY = y;

        r = new Rectangle(x, y, pic.getWidth(), pic.getHeight());
    }


    public String returnType(){return this.type;}

    public void update() {


        timeSinceLastProjectile++;


        if (this.upKeyPressed) {
            this.moveForwards();
        }
        if (this.downKeyPressed) {
            this.moveBackwards();
        }

        if (this.leftKeyPressed) {
            this.rotateLeft();
        }
        if (this.rightKeyPressed) {
            this.rotateRight();
        }
        if (this.shootKeyPressed) {
            if (this.timeSinceLastProjectile > 30) {
                Projectile projectile = new Projectile(this.x, this.y, this.angle, this);
                this.tankGame.addProjectile(projectile);
                this.timeSinceLastProjectile = 0;
            }
        }

        r.setLocation(x, y);

        canMoveRight = true;
        canMoveUp = true;
        canMoveLeft = true;
        canMoveDown = true;


    }

    public void floaters(GameObjects obj){

        // cast game object to floater object
        Floaters floater = (Floaters) obj;

        if (floater.floaterType() == "recycleFloat" && this.type == "bag" ){
            if (!hasShield) {
                health = 0;
            } else if (hasShield){
                hasShield = false;
            }

        } else if(floater.floaterType() == "ringFloat"  && this.type == "turtle" ){
            if (!hasShield) {
                health = 0;
            } else if (hasShield){
                hasShield = false;
            }

        }
    }

    public void switchFloat(){

        StarPowerUp.switchFloaters(returnType());
    }


    @Override
    public void collision(GameObjects obj) {

        if (obj instanceof Tank) {
            health = health - 15;
            x = x - 15 * vx;
            y = y - 15 * vy;
            vx = 0;
            vy = 0;

        } else if (obj instanceof Projectile) {
            if (!hasShield) {
                health = health - 15;
            }
            hasShield = false;
        } else if (obj instanceof StarPowerUp) {

            switchFloat();

        } else if (obj instanceof Shield) {
            hasShield = true;
        } else if (obj instanceof Floaters){
            floaters(obj);
        }

        if (health <= 0) {
            livesRemaining--;
            health = 100;
            x = origX;
            y = origY;
            angle = origAngle;
            vx = 0;
            vy = 0;

        }
    }

    private void rotateLeft() {
        this.angle -= this.rotationSpeed;
        if (this.angle < 0) {
            this.angle += 360;
        } else if (this.angle > 360) {
            this.angle -= 360;
        }
    }

    private void rotateRight() {
        this.angle += this.rotationSpeed;
        if (this.angle < 0) {
            this.angle += 360;
        } else if (this.angle > 360) {
            this.angle -= 360;
        }
    }

    private void moveBackwards() {
        if (angle >= 315 || angle < 45) { // facing right
            if (canMoveLeft) {
                vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            } else {
                vx = 0;
                vy = 0;
            }
        }
        if (angle >= 45 && angle < 135) { // facing down
            if (canMoveUp) {
                vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            } else {
                vx = 0;
                vy = 0;
            }
        }
        if (angle >= 135 && angle < 225) { // facing left
            if (canMoveRight) {
                vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            } else {
                vx = 0;
                vy = 0;
            }
        }
        if (angle >= 225 && angle < 315) { // facing up
            if (canMoveDown) {
                vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            } else {
                vx = 0;
                vy = 0;
            }
        }
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {

        if (angle >= 315 || angle < 45) { // facing right
            if (canMoveRight) {
                vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            } else {
                vx = 0;
                vy = 0;
            }
        }
        if (angle >= 45 && angle < 135) { // facing down
            if (canMoveDown) {
                vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            } else {
                vx = 0;
                vy = 0;
            }
        }
        if (angle >= 135 && angle < 225) { // facing left
            if (canMoveLeft) {
                vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            } else {
                vx = 0;
                vy = 0;
            }
        }
        if (angle >= 225 && angle < 315) { // facing up
            if (canMoveUp) {
                vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            } else {
                vx = 0;
                vy = 0;
            }
        }

        x += vx;
        y += vy;
        checkBorder();
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TankGame.WORLD_WIDTH - 88) {
            x = TankGame.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TankGame.WORLD_HEIGHT - 80) {
            y = TankGame.WORLD_HEIGHT - 80;
        }
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public void setCanMoveDown(boolean canMoveDown) {
        this.canMoveDown = canMoveDown;
    }

    public void setCanMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    public void setCanMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }

    public void setLeftKeyPressed(boolean leftKeyPressed) {
        this.leftKeyPressed = leftKeyPressed;
    }

    public void setUpKeyPressed(boolean upKeyPressed) {
        this.upKeyPressed = upKeyPressed;
    }

    public void setDownKeyPressed(boolean downKeyPressed) {
        this.downKeyPressed = downKeyPressed;
    }

    public void setRightKeyPressed(boolean rightKeyPressed) {
        this.rightKeyPressed = rightKeyPressed;
    }

    public void setShootKeyPressed(boolean shootKeyPressed) {
        this.shootKeyPressed = shootKeyPressed;
    }

    public int getHealth() {
        return health;
    }

    public int getLivesRemaining() {
        return livesRemaining;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle + ", health=" + health;
    }

}



