package TankGame.Actions;

import TankGame.Objects.Tank;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controls implements KeyListener {
    private final int upKey;
    private final int downKey;
    private final int rightKey;
    private final int leftKey;
    private final int shootKey;
    private Tank tank;

    public Controls(Tank tank, int up, int down, int left, int right, int shoot) {
        this.tank = tank;
        this.upKey = up;
        this.downKey = down;
        this.rightKey = right;
        this.leftKey = left;
        this.shootKey = shoot;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == upKey) {
            this.tank.setUpKeyPressed(true);
        }
        if (key == downKey) {
            this.tank.setDownKeyPressed(true);
        }
        if (key == leftKey) {
            this.tank.setLeftKeyPressed(true);
        }
        if (key == rightKey) {
            this.tank.setRightKeyPressed(true);
        }
        if (key == shootKey) {
            this.tank.setShootKeyPressed(true);
        }
    }


    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == upKey) {
            this.tank.setUpKeyPressed(false);
        }
        if (key == downKey) {
            this.tank.setDownKeyPressed(false);
        }
        if (key == leftKey) {
            this.tank.setLeftKeyPressed(false);
        }
        if (key == rightKey) {
            this.tank.setRightKeyPressed(false);
        }
        if (key == shootKey) {
            this.tank.setShootKeyPressed(false);
        }
    }
}
