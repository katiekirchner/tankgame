package TankGame.Actions;

import TankGame.Objects.*;
import TankGame.TankGame;

import java.awt.*;

public class Collision {
    private GameObjects obj1, obj2;

    public Collision(GameObjects obj1, GameObjects obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public void checkForCollision() {


        //All these cases, there are no possible collisions between moving objects
        if (obj1.equals(obj2)) {
            return;
        }

        if (obj1 instanceof Background || obj2 instanceof Background) {
            return;
        }

        // Prevents tank from shooting a projectile at itself.
        if (((obj1 instanceof Projectile) && (obj2 instanceof Tank) && obj2.equals(((Projectile) obj1).getShotBy())) || (obj2 instanceof Projectile && obj1 instanceof Tank && obj1.equals(((Projectile) obj2).getShotBy()))) {
            return;
        }

        //Projectile Collision was already handled, but is still drawn to the screen to show the explosion animation
        if (obj1 instanceof Projectile && ((Projectile) obj1).hasCollided() || obj2 instanceof Projectile && ((Projectile) obj2).hasCollided()) {
            return;
        }


        // Checks for collisions by using the rectangle intersect method
        //if (obj1.getR().intersects(obj2.getR())) {
        Rectangle obj1Rect = obj1.getR();
        Rectangle obj2Rect = obj2.getR();

        if (obj1 instanceof Projectile || obj2 instanceof Projectile) {
            //Larger overlap region of the rectangles for showing the explosion of a projectile over the tank
            if ((!obj1Rect.intersection(obj2Rect).isEmpty()) && ((obj1Rect.intersection(obj2Rect).getWidth() > 20) || (obj1Rect.intersection(obj2Rect).getHeight() > 20))) {
                obj1.collision(obj2);
                obj2.collision(obj1);
            }
        } else {
            //smallest overlap region possible97
            if ((!obj1Rect.intersection(obj2Rect).isEmpty()) && ((obj1Rect.intersection(obj2Rect).getWidth() > 0) || (obj1Rect.intersection(obj2Rect).getHeight() > 0))) {
                obj1.collision(obj2);
                obj2.collision(obj1);
            }
        }


        // Look Ahead: For Tanks detecting Walls.
        if (obj1 instanceof Tank && obj2 instanceof Wall) {
            checkAhead((Tank) obj1, (Wall) obj2);
        }

        if (obj1 instanceof Wall && obj2 instanceof Tank) {
            checkAhead((Tank) obj2, (Wall) obj1);
        }


    }


    public void checkAhead(Tank tank, Wall wall) {

        // Look Ahead: For Tanks detecting Walls.
        // Assumes Tanks can move in any direction until a possible collision with a wall is detected.

        int checkAmount = 15;
        Rectangle tankRect = tank.getR();
        // look ahead towards left of screen
        tankRect.setBounds(tankRect.x - checkAmount, tankRect.y, checkAmount, tankRect.height);
        if (tankRect.intersects(wall.getR())) {
            tank.setCanMoveLeft(false);
        }

        tankRect = tank.getR();
        // look ahead towards right of screen
        tankRect.setBounds(tankRect.x + tankRect.width, tankRect.y, checkAmount, tankRect.height);
        if (tankRect.intersects(wall.getR())) {
            tank.setCanMoveRight(false);
        }


        tankRect = tank.getR();
        // look ahead towards top of screen
        tankRect.setBounds(tankRect.x, tankRect.y - checkAmount, tankRect.width, checkAmount);
        if (tankRect.intersects(wall.getR())) {
            tank.setCanMoveUp(false);
        }

        tankRect = tank.getR();
        // look ahead towards bottom of screen
        tankRect.setBounds(tankRect.x, tankRect.y + tankRect.height, tankRect.width, checkAmount);
        if (tankRect.intersects(wall.getR())) {
            tank.setCanMoveDown(false);

        }
    }
}

