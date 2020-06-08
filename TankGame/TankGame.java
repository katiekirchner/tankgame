
package TankGame;

import TankGame.Actions.*;
import TankGame.Objects.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;





public class TankGame extends JPanel {


    public static final int WORLD_WIDTH = 1400;
    public static final int WORLD_HEIGHT = 1400;

    private static final int SCREEN_WIDTH = 1250;
    private static final int SCREEN_HEIGHT = 825;

    private Tank t1;
    private Tank t2;
    private String lifeIcon1_path  = "/Resources/pbagsmall.png";
    private String lifeIcon2_path = "/Resources/turtlesmall.png";

    private BufferedImage image;
    private Graphics2D buffer;
    private JFrame jf;
    private BufferedImage pic = null;


    private final int NUM_ROWS = 25, NUM_COLS = 25;
    private int[][] mapLayout;

    private BufferedImage lifeIcon1, lifeIcon2;


    private static ArrayList<GameObjects> WorldObjects;


    public static void main(String[] args) {

        TankGame world = new TankGame();
        world.init();

        try {
            while (world.t1.getLivesRemaining() > 0 && world.t2.getLivesRemaining() > 0) {

                int i = 0;
                // removes projectiles if collided with object or edge.
                while (i < world.WorldObjects.size()) {
                    world.WorldObjects.get(i).update();
                    if (!world.WorldObjects.get(i).isActive()) {
                        world.WorldObjects.remove(i);
                    } else {
                        i++;
                    }
                }


                for (i = 0; i < world.WorldObjects.size(); i++) {
                    for (int j = i; j < world.WorldObjects.size(); j++) {
                        GameObjects obj1 = world.WorldObjects.get(i);
                        GameObjects obj2 = world.WorldObjects.get(j);

                        Collision collision = new Collision(obj1, obj2);
                        collision.checkForCollision(); // Also looks ahead if its a Tank and  Wall or  Wall and Tank
                    }
                }

                world.repaint();

                Thread.sleep(6);
            }
        } catch (InterruptedException ignored) {

        }

        long temp = System.currentTimeMillis();
        while (System.currentTimeMillis() - temp < 10000) {
            world.repaint();
        }
        //Code to exit game window and stop execution
        Window win = SwingUtilities.getWindowAncestor(world);
        win.setVisible(false);
        win.dispose();



    }

    public void addProjectile(Projectile projectile) {
        WorldObjects.add(projectile);
    }

    private BufferedImage setImage(String filepath) {
        try {
            this.pic = ImageIO.read(getClass().getResource(filepath));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return pic;
    }

    private void init() {

        boolean newBool = false;


        if (newBool) {
            System.out.println("Test");
            
        }



        this.jf = new JFrame("CSC 413 Tank Game");
        this.image = new BufferedImage(TankGame.WORLD_WIDTH, TankGame.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        WorldObjects = new ArrayList<>();

        setMapLayout();
        initMap();

        t1 = new Tank(100, 100, 90, "bag", this, "/Resources/pbag.png");
        t2 = new Tank(1200, 1200, 270, "turtle", this, "/Resources/turtle.png");
        Controls tankPlayerControls1 = new Controls(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        Controls tankPlayerControls2 = new Controls(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q);

        this.WorldObjects.add(t1);
        this.WorldObjects.add(t2);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tankPlayerControls1);
        this.jf.addKeyListener(tankPlayerControls2);

        this.jf.setSize(TankGame.SCREEN_WIDTH, SCREEN_HEIGHT);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

        setLifeIcons(setImage(this.lifeIcon1_path), setImage(this.lifeIcon2_path));

    }



    public void setLifeIcons(BufferedImage img1, BufferedImage img2) {
        this.lifeIcon1 = img1;
        this.lifeIcon2 = img2;
    }



    private void setMapLayout() {
        this.mapLayout = new int[][]
                { // current size: 25x25
                        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 0
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 1
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 2
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 3
                        {2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 4
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 5
                        {2, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 2}, // 6
                        {2, 0, 0, 0, 0, 0, 0, 3, 5, 0, 0, 0, 0, 0, 0, 0, 5, 3, 0, 0, 0, 0, 0, 0, 2}, // 7
                        {2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 2}, // 8
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 9
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 2}, // 10
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 4, 2}, // 11
                        {2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2}, // 12
                        {2, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 13
                        {2, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 14
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 15
                        {2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 2}, // 16
                        {2, 0, 0, 0, 0, 0, 0, 3, 5, 0, 0, 0, 0, 0, 0, 0, 5, 3, 0, 0, 0, 0, 0, 0, 2}, // 17
                        {2, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 2}, // 18
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 19
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2}, // 20
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 21
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 22
                        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 23
                        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 24

                };
    }

    private void initMap() {

        //background
        for (int i = 0; i < WORLD_WIDTH; i = i + 500) {
            for (int j = 0; j < WORLD_HEIGHT; j = j + 500) {
                Background b = new Background(i, j);
                WorldObjects.add(b);


                System.out.println("This is another prunt statement");
            }
        }

        // borders and breakable walls
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (this.mapLayout[row][col] == 2) {
                    Wall w = new Wall(row*55, col*55, 9, "border");
                    WorldObjects.add(w);

                } else if (this.mapLayout[row][col] == 3) {
                    Wall w = new Wall(row*55, col*55, 9, "breakable");
                    WorldObjects.add(w);

                } else if (this.mapLayout[row][col] == 4) {
                    StarPowerUp starPower = new StarPowerUp(row*55, col*55);
                    WorldObjects.add(starPower);

                } else if (this.mapLayout[row][col] == 5){
                    Shield shield = new Shield(row*55, col*55);
                    WorldObjects.add(shield);
                } else {
                    System.out.println("The if statement is in this else condition in");
                    System.out.println("This is a second line iand the ");
                }

            }
        }

        // generate recycle floats
        for (int i = 225; i < SCREEN_WIDTH/2; i = i + 250){
            for (int j=225; j < SCREEN_HEIGHT+450; j = j + 250){
                Floaters f = new Floaters(i, j, 3, "recycleFloat");
                WorldObjects.add(f);
            }
        }

        // generate ring floats
        for (int i = SCREEN_WIDTH/2; i < SCREEN_WIDTH - 100; i = i + 250){
            for (int j=225; j < SCREEN_HEIGHT+450; j = j + 250){
                Floaters f = new Floaters(i, j, 3, "ringFloat");
                WorldObjects.add(f);
            }
        }


    }


    public static ArrayList<GameObjects> returnArray(){
        return WorldObjects;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.buffer = this.image.createGraphics();
        super.paintComponent(g2);


        // draw everything
        if (this.t1.getLivesRemaining() > 0 && this.t2.getLivesRemaining() > 0) {

            for (int i = 0; i < this.WorldObjects.size(); i++) {
                this.WorldObjects.get(i).drawImage(this.buffer);
            }


            int fourth = WORLD_HEIGHT/4;
            int half = SCREEN_HEIGHT/2;


            // calculating mini map display.
            int sub1x = t1.getX() - fourth;
            if ((sub1x) < 0) {
                sub1x = 0;
            } else if ((t1.getX() + fourth) > (WORLD_WIDTH)) {
                sub1x = WORLD_WIDTH - (fourth*2);
            }

            int sub1y = t1.getY() - half;
            if ((sub1y) < 0) {
                sub1y = 0;
            } else if ((t1.getY() + half) > (WORLD_HEIGHT)) {
                sub1y = WORLD_HEIGHT - SCREEN_HEIGHT;
            }


            int sub2x = t2.getX() - fourth;
            if ((t2.getX() - fourth) < 0) {
                sub2x = 0;
            } else if ((t2.getX() + fourth) > (WORLD_WIDTH)) {
                sub2x = WORLD_WIDTH - (fourth*2);
            }

            int sub2y = t2.getY() - half;
            if ((sub2y) < 0) {
                sub2y = 0;
            } else if ((t2.getY() + half) > (WORLD_HEIGHT)) {
                sub2y = WORLD_HEIGHT - SCREEN_HEIGHT;
            }

            int miniX = 300;
            int miniY = 300;


            BufferedImage sub1 = this.image.getSubimage(sub1x, sub1y, SCREEN_WIDTH/2, SCREEN_HEIGHT);
            BufferedImage sub2 = this.image.getSubimage(sub2x+50, sub2y, SCREEN_WIDTH/2, SCREEN_HEIGHT);
            BufferedImage miniMap = this.image.getSubimage(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            g2.drawImage(sub1, 0, 0, null);
            g2.drawImage(sub2, SCREEN_WIDTH/2 + 5, 0, null);
            g2.drawImage(miniMap.getScaledInstance(miniX, miniY, BufferedImage.TYPE_INT_RGB), (SCREEN_WIDTH/2)-(miniX/2) + 5, 0, null);



            int t1Health = this.t1.getHealth() * 2;
            int t2Health = this.t2.getHealth() * 2;

            int t1Lives = this.t1.getLivesRemaining();
            int t2Lives = this.t2.getLivesRemaining();

            int t1Health_x = SCREEN_WIDTH/6;
            int t1Health_y = SCREEN_HEIGHT/2 + 325;

            int t2Health_x = SCREEN_WIDTH - (SCREEN_HEIGHT/3);
            int t2Health_y = SCREEN_HEIGHT/25 + 2;

            int health_width = 200;
            int health_height = 20;

            int coord_offset = 4;
            int size_offset = 8;
            int lifeOffset = 40;



            // HEALTH FRAME
            g.setColor(Color.DARK_GRAY);
            g.fillRect(t1Health_x, t1Health_y, health_width, health_height); // p1
            g.fillRect(t2Health_x, t2Health_y, health_width, health_height); // p2

            // HEALTH DEPLIETED
            g.setColor(Color.RED);
            g.fillRect(t1Health_x + coord_offset, t1Health_y + coord_offset,
                    health_width - size_offset, health_height - size_offset); // p1
            g.fillRect(t2Health_x + coord_offset, t2Health_y + coord_offset,
                    health_width - size_offset, health_height - size_offset); // p2

            // HEALTH AVAILABLE
            g.setColor(Color.GREEN);
            g.fillRect(t1Health_x + coord_offset, t1Health_y + coord_offset,
                    t1Health - size_offset, health_height - size_offset); // p1
            g.fillRect(t2Health_x + (health_width - t2Health) + coord_offset, t2Health_y + coord_offset,
                    t2Health - size_offset, health_height - size_offset); // p2



            // Tank 1 lives
            for (int i = 0; i < t1Lives; i++) {
                g.drawImage(lifeIcon1, (t1Health_x - 150) + (i * lifeOffset), t1Health_y - 10, this);
            }


            // Tank 2 lives
            for (int i = 0; i < t2Lives; i++) {
                g.drawImage(lifeIcon2, (t2Health_x - 50) - (i * lifeOffset), t2Health_y - 10, this);
            }


        } else {
            if (this.t1.getLivesRemaining() > 0) {
                g2.drawString("Tank 1 Wins", 576, 316);
            } else {
                g2.drawString("Tank 2 Wins", 576, 316);
            }
        }

    }


}

