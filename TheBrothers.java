//* FinalProject
// * Desc: Game"The Brothers"
// * @author ICS3U
// * @version Dec 2018

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

// the following imports are needed for pictures
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TheBrothers{
    // Game Window properties
    static JFrame gameWindow;
    static GraphicsPanel canvas;
    static final int WIDTH = 1295;
    static final int HEIGHT = 675;
    //key Listener
    static MyKeyListener keyListener = new MyKeyListener();
    
    //Basic properties
    static final int GROUND_LEVEL = 540;
    static final int RUN_SPEED = 30;
    static final int GRAVITY = 2;
    static final int JUMP_SPEED = -30;
    
    // background properties
    static BufferedImage background1;
    static int background1X = 0;
    static int background1Y = 0;
    static int background1W = 800;
    
    //platform basic properties(Total : 7)
    static int platformW = 200;
    static int platformH = 20;
    
    //Total 7 Platforms X and Y using array
    static int[] platformX = {100,550,550,75,300,995,795,1020};
    static int[] platformY = {125,325,100,375,225,125,225,375};
    
    //Basic Blood Properties 
    static int bloodOneW = 200;
    static int bloodTwoW = 200;
    static int bloodH = 20;
    
    //Player One Blood
    static int bloodOneX = 0;
    static int bloodOneY = 0;
    
    //Player Two blood
    static int bloodTwoX = 1080;
    static int bloodTwoY = 0;
    
    //Because the character only run one step when the player press the keyboard once 
    //and that will becaome diffcult for the player to go on the platform from the side
    //So I set up that the character can go up on to the platform under the flatform
    
    //Character One Properties
    static int character1H = 100;
    static int character1W = 60;
    static int character1X = WIDTH/7;
    static int character1Y = 440;
    static int character1Vx = 0;
    static int character1Vy = 0;
    static int character1PicNum = 1;
    static String character1State = "standing right";
    static BufferedImage[] character1Pic = new BufferedImage[8];
    
    // Character Two Properties
    static int character2H = 100;
    static int character2W = 50;
    static int character2X = WIDTH/4*3;
    static int character2Y = 440;
    static int character2Vx = 0;
    static int character2Vy = 0;
    static int character2PicNum = 1;
    static String character2State = "standing left";
    static BufferedImage[] character2Pic = new BufferedImage[8];
    
    //Ball basic properties 
    static BufferedImage ball;
    static int ballW = 50;
    static int ballH = 50;
    static int ballSpeed = -10;
    
    // Character One balls facing left properties
    static int charOneLeftNumBalls = 40;
    static int[] charOneLeftBallY = new int[charOneLeftNumBalls];
    static int[] charOneLeftBallX = new int[charOneLeftNumBalls];
    static boolean[] charOneLeftBallVisible = new boolean[charOneLeftNumBalls];
    static int charOneLeftCurrentBall = 0;
    
    // Character One balls facing right properties
    static int charOneRightNumBalls = 40; 
    static int[] charOneRightBallY = new int[charOneRightNumBalls];
    static int[] charOneRightBallX = new int[charOneRightNumBalls];
    static boolean[] charOneRightBallVisible = new boolean[charOneRightNumBalls];
    static int charOneRightCurrentBall = 0;
    
    // Character Two balls facing left properties
    static int charTwoLeftNumBalls = 40;
    static int[] charTwoLeftBallY = new int[charTwoLeftNumBalls];
    static int[] charTwoLeftBallX = new int[charTwoLeftNumBalls];
    static boolean[] charTwoLeftBallVisible = new boolean[charTwoLeftNumBalls];
    static int charTwoLeftCurrentBall = 0;
    
    // Character Two balls facing right properties
    static int charTwoRightNumBalls = 40; 
    static int[] charTwoRightBallY = new int[charTwoRightNumBalls];
    static int[] charTwoRightBallX = new int[charTwoRightNumBalls];
    static boolean[] charTwoRightBallVisible = new boolean[charTwoRightNumBalls];
    static int charTwoRightCurrentBall = 0;
    static boolean left = true;
    static boolean hit = false;
    
    //boolean for switching the screen
    static boolean startPlay = true;
    static boolean inPlay = false;
    static boolean endPlay = false;
    
    //Font for writing on the screen
    static Font titleFont = new Font("Cambria", Font.BOLD, 60); 
    static Font subTitleFont = new Font("Cambria", Font.BOLD, 35); 
    static Font messageFont = new Font("Cambria", Font.BOLD, 22); 
//------------------------------------------------------------------------------
    public static void main(String[] args){
        gameWindow = new JFrame("Game Window");
        gameWindow.setSize(WIDTH,HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas = new GraphicsPanel();
        canvas.addKeyListener(keyListener);
        gameWindow.add(canvas);
        
//  load the background images from files
        try {    
            background1 = ImageIO.read(new File("game_background_21-.jpg"));
        } catch (IOException ex){}
        
        //character One image
        for (int i=0; i<8; i++){
            try {       
                character1Pic[i] = ImageIO.read(new File("char1p/charOne" + Integer.toString(i)+ ".png"));
            } catch (IOException ex){}
        }
        //character Two image
        for (int i=0; i<8; i++){
            try {       
                character2Pic[i] = ImageIO.read(new File("char2p/charTwo" + Integer.toString(i)+ ".png"));
            } catch (IOException ex){}
        }
        //ball image
        try {    
            ball = ImageIO.read(new File("ball.png"));
        } catch (IOException ex){}
        
        // generate character One bullets Left and Right
        Arrays.fill(charOneLeftBallX,0);
        Arrays.fill(charOneLeftBallY,0);
        Arrays.fill(charOneLeftBallVisible,false);
        Arrays.fill(charOneRightBallX,0);
        Arrays.fill(charOneRightBallY,0);
        Arrays.fill(charOneRightBallVisible,false);
        
        // generate character Two bullets Left and Right
        Arrays.fill(charTwoLeftBallX,0);
        Arrays.fill(charTwoLeftBallY,0);
        Arrays.fill(charTwoLeftBallVisible,false);
        Arrays.fill(charTwoRightBallX,0);
        Arrays.fill(charTwoRightBallY,0);
        Arrays.fill(charTwoRightBallVisible,false);
        
        gameWindow.setVisible(true);
        runGameLoop();
    } // main method end
    
//------------------------------------------------------------------------------
    public static void runGameLoop(){
        while (true) {
            gameWindow.repaint();
            try  {Thread.sleep(20);} catch(Exception e){}
            
            // update Character One's vertical velocity
            character1Vy = character1Vy + GRAVITY;
            // move Character One in vertical direction
            character1Y = character1Y + character1Vy;
            //Not let the character jump out of the screen(higher than 0)
            if(character1Y + character1Vy + 20 <= 0){
                character1Vy = 0 + GRAVITY;
                character1Y = character1Y - character1Vy;
            }
            //if the character are not on the platform, it will land on the ground
            if (character1Y + character1H > GROUND_LEVEL){
                character1Y = GROUND_LEVEL - character1H;
                character1Vy = 0;
                if (character1State == "jumping left"){
                    character1State = "standing left";
                } else if (character1State == "jumping right"){
                    character1State = "standing right";
                }
            }
            //character one on platform
            for (int i = 0; i<=7;i++){
                if (character1X+character1W>platformX[i] &&
                    character1X<platformX[i]+platformW &&
                    (character1Y + character1H - platformY[i]< platformH-15 &&
                     character1Y +character1H - platformY[i] > -platformH)&&
                    character1Vy>=0){
                    character1Y = platformY[i] - character1H;
                    character1Vy =0;
                    if (character1State == "jumping left")
                        character1State = "standing left";
                    else if (character1State == "jumping right")
                        character1State = "standing right";
                }
            }
            // select Character One's picture
            if (character1State == "standing left"){
                character1PicNum = 0;
            } else if (character1State == "standing right"){
                character1PicNum = 1;                     
            } else if (character1State == "walking left"){
                character1PicNum = 2;
                character1State = "standing left";
            } else if (character1State == "walking right"){           
                character1PicNum = 3;
                character1State = "standing right";
            } else if (character1State == "jumping left"){
                character1PicNum = 4;
            } else if (character1State == "jumping right"){
                character1PicNum = 5;
            } else if (character1State == "shooting left"){
                character1PicNum = 6;
                character1State = "standing left";
            } else if (character1State == "shooting right"){
                character1PicNum = 7;
                character1State = "standing right";
            }
            
            // update Character 2's vertical velocity
            character2Vy = character2Vy + GRAVITY;
            // move Character 2 in vertical direction
            character2Y = character2Y + character2Vy;
            //Not let the character jump out of the screen
            if(character2Y + character2Vy + 20 <= 0){
                character2Vy = 0 + GRAVITY;
                character2Y = character2Y - character2Vy;
            }
            //Character Two's movement on platforms
            for (int i = 0; i<=7;i++){
                if (character2X+character2W>platformX[i] &&
                    character2X<platformX[i]+platformW &&
                    (character2Y + character2H - platformY[i]< platformH-15 &&
                     character2Y +character2H - platformY[i] > -platformH)&&
                    character2Vy>=0){
                    character2Y = platformY[i] - character2H;
                    character2Vy =0;
                    if (character2State == "jumping left")
                        character2State = "standing left";
                    else if (character2State == "jumping right")
                        character2State = "standing right";
                }
            }
            //if the character are not on the platform, it will land on the ground
            if (character2Y + character2H > GROUND_LEVEL){
                character2Y = GROUND_LEVEL - character2H;
                character2Vy = 0;
                if (character2State == "jumping left"){
                    character2State = "standing left";
                } else if (character2State == "jumping right"){
                    character2State = "standing right";
                }
            }
            // select Character Two's picture
            if (character2State == "standing left"){
                character2PicNum = 0;
            } else if (character2State == "standing right"){
                character2PicNum = 1;                     
            } else if (character2State == "walking left"){
                character2PicNum = 2;
                character2State = "standing left";
            } else if (character2State == "walking right"){           
                character2PicNum = 3;
                character2State = "standing right";
            } else if (character2State == "jumping left"){
                character2PicNum = 4;
            } else if (character2State == "jumping right"){
                character2PicNum = 5;
            } else if (character2State == "shooting left"){
                character2PicNum = 6;
                character2State = "standing left";
            } else if (character2State == "shooting right"){
                character2PicNum = 7;
                character2State = "standing right";
            }
            //Move character One bullets Left
            for (int i=0; i<charOneLeftNumBalls; i++){
                if (charOneLeftBallVisible[i]){
                    if (left == true){
                        charOneLeftBallX[i] = charOneLeftBallX[i] + ballSpeed;
                        if (charOneLeftBallX[i]<0)
                            charOneLeftBallVisible[i] = false;
                    }
                    else if (left == false){
                        charOneLeftBallX[i] = charOneLeftBallX[i] + ballSpeed;
                        if (charOneLeftBallX[i]>1290)
                            charOneLeftBallVisible[i] = false;
                    }
                    if(charOneLeftBallX[i]>character2X && charOneLeftBallX[i]<character2X + character2W &&
                       charOneLeftBallY[i]> character2Y && charOneLeftBallY[i] < character2Y + character2H && hit == false){
                        hit = true;
                        charOneLeftBallVisible[i] = false;
                        bloodTwoW = bloodTwoW - 10;
                        bloodTwoX = bloodTwoX + 10;
                    }
                }
            }
            //Move character One bullets Right
            for (int i=0; i<charOneRightNumBalls; i++){
                if (charOneRightBallVisible[i]){
                    if (left == false){
                        charOneRightBallX[i] = charOneRightBallX[i] - ballSpeed;
                        if (charOneRightBallX[i]>1290)
                            charOneRightBallVisible[i] = false;
                    }
                    else if (left == true){
                        charOneRightBallX[i] = charOneRightBallX[i] - ballSpeed;
                        if (charOneRightBallX[i]<0)
                            charOneRightBallVisible[i] = false;
                    }
                    
                    if(charOneRightBallX[i]>character2X && charOneRightBallX[i]<character2X + character2W &&
                       charOneRightBallY[i]> character2Y && charOneRightBallY[i] < character2Y + character2H && hit == false){
                        hit = true;
                        charOneRightBallVisible[i] = false;
                        bloodTwoW = bloodTwoW - 10;
                        bloodTwoX = bloodTwoX + 10;
                    }
                }
            }
            // Move character Two bullets Left
            for (int i=0; i<charTwoLeftNumBalls; i++){
                if (charTwoLeftBallVisible[i]){
                    if (left == true){
                        charTwoLeftBallX[i] = charTwoLeftBallX[i] + ballSpeed;
                        if (charTwoLeftBallX[i]<0)
                            charTwoLeftBallVisible[i] = false;
                    }
                    else if (left == false){
                        charTwoLeftBallX[i] = charTwoLeftBallX[i] + ballSpeed;
                        if (charTwoLeftBallX[i]>1290)
                            charTwoLeftBallVisible[i] = false;
                    }
                    if(charTwoLeftBallX[i]>character1X && charTwoLeftBallX[i]<character1X + character1W &&
                       charTwoLeftBallY[i]> character1Y && charTwoLeftBallY[i] < character1Y + character1H && hit == false){
                        hit = true;
                        charTwoLeftBallVisible[i] = false;
                        bloodOneW = bloodOneW - 10;
                    }
                }
            }
            //Move character Two bullets right
            for (int i=0; i<charTwoRightNumBalls; i++){
                if (charTwoRightBallVisible[i]){
                    
                    if (left == false){
                        charTwoRightBallX[i] = charTwoRightBallX[i] - ballSpeed;
                        if (charTwoRightBallX[i]>1290)
                            charTwoRightBallVisible[i] = false;
                    }
                    else if (left == true){
                        charTwoRightBallX[i] = charTwoRightBallX[i] - ballSpeed;
                        if (charTwoRightBallX[i]<0)
                            charTwoRightBallVisible[i] = false;
                    }
                    
                    if(charTwoRightBallX[i]>character1X && charTwoRightBallX[i]<character1X + character1W &&
                       charTwoRightBallY[i]> character1Y && charTwoRightBallY[i] < character1Y + character1H && hit == false){
                        hit = true;
                        charTwoRightBallVisible[i] = false;
                        bloodOneW = bloodOneW - 10;
                    }
                    
                }
            }
            //reset the hit boolean so that every bullet will hit on the character
            if(bloodOneW <=200 || bloodTwoW<=200)
                hit = false;
            //reset the character on the end page
            if(bloodOneW == 0 || bloodTwoW == 0){
                inPlay = false;
                endPlay = true;
                character1State = "standing right";
                character2State = "standing left";
                character1X = WIDTH/7;
                character1Y = 440;
                character2X = WIDTH/4*3;
                character2Y = 440;
            }
        }
    } // runGameLoop method end
//------------------------------------------------------------------------------
    static class GraphicsPanel extends JPanel{
        public GraphicsPanel(){
            setFocusable(true);
            requestFocusInWindow();
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g); //required
            Color brown;
            
//   draw the background images ("this" refers to the graphics panel)
            g.drawImage(background1,background1X,background1Y,this);
            
            // character image
            g.drawImage(character1Pic[character1PicNum],character1X,character1Y,this);
            g.drawImage(character2Pic[character2PicNum],character2X,character2Y,this);
            
            // draw the blood rectangle and the side.
            g.setColor(Color.red);
            g.fillRect(bloodOneX,bloodOneY,bloodOneW,bloodH);
            g.fillRect(bloodTwoX,bloodTwoY,bloodTwoW,bloodH);
            g.drawRect(0,0,200,bloodH);
            g.drawRect(1080,0,200,bloodH);
            
            //start page wrtting 
            if(startPlay ==true){
                g.setColor(Color.orange);
                g.setFont(titleFont); 
                g.drawString("The Brothers", 425, 200);
                g.setFont(subTitleFont); 
                g.setColor(Color.red);
                g.drawString("Press Space To Start !!!",425,515);
                g.setColor(Color.black);
                g.drawString("How To Play", 500, 300);
                g.setFont(messageFont); 
                g.drawString("Player One : (Keyboard) A To Move Left, D To Move Right, W To Jump, J To Shoot", 225, 375);
                g.drawString("Player Two : (Arrow Key) Left To Move Left, Right To Move Right, Up To Jump, Enter To Shoot", 175, 425);
            }
            //set the platform and balls when in playing
            if(inPlay== true){
                // draw all seven platform
                brown = new Color(94,47,0);
                g.setColor(brown);
                for(int i=0; i<=7;i++){
                    g.fillRect(platformX[i],platformY[i],platformW,platformH);
                }
                //draw the character One's ball using pictures
                for (int i=0; i<charOneLeftNumBalls; i++){
                    if (charOneLeftBallVisible[i])
                        g.drawImage(ball,charOneLeftBallX[i],charOneLeftBallY[i],this);
                }
                for (int a=0; a<charOneRightNumBalls; a++){
                    if (charOneRightBallVisible[a])
                        g.drawImage(ball,charOneRightBallX[a],charOneRightBallY[a],this);
                }
                //draw the character Two's ball using pictures
                for (int i=0; i<charTwoLeftNumBalls; i++){
                    if (charTwoLeftBallVisible[i])
                        g.drawImage(ball,charTwoLeftBallX[i],charTwoLeftBallY[i],this);
                }
                for (int a=0; a<charTwoRightNumBalls; a++){
                    if (charTwoRightBallVisible[a])
                        g.drawImage(ball,charTwoRightBallX[a],charTwoRightBallY[a],this);
                }
            }
            // end page writting
            if(endPlay == true){
                if(bloodOneW <= 0){
                    g.setColor(Color.black);
                    g.setFont(titleFont); 
                    g.drawString("Player Two Won !!!", 375, 300);
                }
                if(bloodTwoW <= 0){
                    g.setColor(Color.black);
                    g.setFont(titleFont); 
                    g.drawString("Player One Won !!!", 375, 300);
                }
                g.setFont(titleFont); 
                g.setColor(Color.black);
                g.drawString("Thank You for Playing !!!", 300, 400);
                g.drawString("Congratulation !!!", 400, 200);
                g.setColor(Color.red);
                g.setFont(subTitleFont); 
                g.drawString("Press Space To Play Again!!!", 400, 500);
                g.drawString("Press Ctrl To Play With a New Player !!!", 325, 535);
            }
            
        } // paintComponent method end
    } // GraphicsPanel class end
//------------------------------------------------------------------------------
    static class MyKeyListener implements KeyListener{ 
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            //Character One Movemwnt
            if (key == KeyEvent.VK_A){
                //let character 1 move within the frame(Not move out from left side)
                if(character1X >20){
                    character1X = character1X - RUN_SPEED;
                }
                if (character1State == "standing left"){
                    character1State = "walking left";
                } else if (character1State == "standing right"){
                    character1State = "walking left";
                } else if (character1State == "walking left"){
                    character1State = "walking left";
                    // no action
                } else if (character1State == "walking right"){
                    character1State = "walking left";
                } else if (character1State == "jumping left"){
                } else if (character1State == "jumping right"){
                    character1State = "walking left";
                }
            } else if (key == KeyEvent.VK_D){
                //let character 1 move within the frame(Not move out from right side)
                if(character1X <1200){
                    character1X = character1X + RUN_SPEED;
                }
                if (character1State == "standing left"){
                    character1State = "walking right";
                } else if (character1State == "standing right"){
                    character1State = "walking right";
                } else if (character1State == "walking left"){
                    character1State = "walking right";
                } else if (character1State == "walking right"){
                    character1State = "walking right";
                } else if (character1State == "jumping left"){
                    character1State = "walking right";
                } else if (character1State == "jumping right"){
                    character1State = "walking right";
                }
            }
            //only let the character jump once per press.
            if (key == KeyEvent.VK_W && (character1Vy == 0)){
                character1Vy = JUMP_SPEED;
                if (character1State == "standing left"){
                    character1State = "jumping left";
                } else if (character1State == "standing right"){
                    character1State = "jumping right";
                } else if (character1State == "walking left"){
                    character1State = "jumping left";
                } else if (character1State == "walking right"){
                    character1State = "jumping right";
                } else if (character1State == "jumping left"){
                    // no action
                } else if (character1State == "jumping right"){
                    // no action
                } 
            }
            if (key == KeyEvent.VK_J){
                if(inPlay == true){
                    if (character1State == "standing left"){
                        character1State = "shooting left";
                        left = true;
                        // assign the coordinates of the left middle point of character 1 to the current 
                        charOneLeftBallX[charOneLeftCurrentBall] = character1X;
                        charOneLeftBallY[charOneLeftCurrentBall] = character1Y + character1H/2 - ballH/2;
                        charOneLeftBallVisible[charOneLeftCurrentBall] = true;
                        charOneLeftCurrentBall = (charOneLeftCurrentBall + 1)%charOneLeftNumBalls;
                        
                    } else if (character1State == "standing right"){
                        character1State = "shooting right";
                        left = false;
                        // assign the coordinates of the right middle point of character 1 to the current 
                        charOneRightBallX[charOneRightCurrentBall] = character1X + character1W;
                        charOneRightBallY[charOneRightCurrentBall] = character1Y + character1H/2 - ballH/2;
                        charOneRightBallVisible[charOneRightCurrentBall] = true;
                        charOneRightCurrentBall = (charOneRightCurrentBall + 1)%charOneRightNumBalls;
                    } else if (character1State == "walking left"){
                        character1State = "shooting left";
                    } else if (character1State == "walking right"){
                        character1State = "shooting right";
                    } else if (character1State == "jumping left"){
                        character1State = "shooting left";
                    } else if (character1State == "jumping right"){
                        character1State = "shooting right";
                    }
                }
            }
            // character Two Movement
            if (key == KeyEvent.VK_LEFT){
                //let character 2 move within the frame(Not move out from left side)
                if(character2X >20){
                    character2X = character2X - RUN_SPEED;
                }
                if (character2State == "standing left"){
                    character2State = "walking left";
                } else if (character2State == "standing right"){
                    character2State = "walking left";
                } else if (character2State == "walking left"){
                    character2State = "walking left";
                } else if (character2State == "walking right"){
                    character2State = "walking left";
                } else if (character2State == "jumping left"){
                    //same picture
                } else if (character2State == "jumping right"){
                    character2State = "walking left";
                }
            } else if (key == KeyEvent.VK_RIGHT){
                //let character 2 move within the frame(Not move out from left side)
                if(character2X <1200){
                    character2X = character2X + RUN_SPEED;
                }
                if (character2State == "standing left"){
                    character2State = "walking right";
                } else if (character2State == "standing right"){
                    character2State = "walking right";
                } else if (character2State == "walking left"){
                    character2State = "walking right";
                } else if (character2State == "walking right"){
                    character2State = "walking right";
                } else if (character2State == "jumping left"){
                    character2State = "walking right";
                } else if (character2State == "jumping right"){
                    character2State = "walking right";
                }
            }
            if (key == KeyEvent.VK_UP && (character2Vy == 0)){
                character2Vy = JUMP_SPEED;
                if (character2State == "standing left"){
                    character2State = "jumping left";
                } else if (character2State == "standing right"){
                    character2State = "jumping right";
                } else if (character2State == "walking left"){
                    character2State = "jumping left";
                } else if (character2State == "walking right"){
                    character2State = "jumping right";
                } else if (character2State == "jumping left"){
                    // no action
                } else if (character2State == "jumping right"){
                    // no action
                } 
            }
            if (key == KeyEvent.VK_ENTER){
                if(inPlay == true){
                    if (character2State == "standing left"){
                        left = true;
                        character2State = "shooting left";
                        // assign the coordinates of the left middle point of character 2 to the current 
                        charTwoLeftBallX[charTwoLeftCurrentBall] = character2X;
                        charTwoLeftBallY[charTwoLeftCurrentBall] = character2Y + character2H/2 - ballH/2;
                        charTwoLeftBallVisible[charTwoLeftCurrentBall] = true;
                        charTwoLeftCurrentBall = (charTwoLeftCurrentBall + 1)%charTwoLeftNumBalls;
                        
                    } else if (character2State == "standing right"){
                        left = false;
                        character2State = "shooting right";
                        // assign the coordinates of the right middle point of character 2 to the current bullet
                        charTwoRightBallX[charTwoRightCurrentBall] = character2X + character2W;
                        charTwoRightBallY[charTwoRightCurrentBall] = character2Y + character2H/2 - ballH/2;
                        charTwoRightBallVisible[charTwoRightCurrentBall] = true;
                        charTwoRightCurrentBall = (charTwoRightCurrentBall + 1)%charTwoRightNumBalls;
                    } else if (character2State == "walking left"){
                        character2State = "shooting left";
                    } else if (character2State == "walking right"){
                        character2State = "shooting right";
                    } else if (character2State == "jumping left"){
                        character2State = "shooting left";
                    } else if (character2State == "jumping right"){
                        character2State = "shooting right";
                    }
                }
            }
            if (key == KeyEvent.VK_SPACE){
                if(startPlay == true){
                    startPlay = false;
                    inPlay = true;
                    //reset character properties
                    character1X = WIDTH/7;
                    character1Y = 440;
                    character2X = WIDTH/4*3;
                    character2Y = 440;
                    character1State = "standing right";
                    character2State = "standing left";
                }
                else if (endPlay == true){
                    endPlay = false;
                    inPlay = true;
                    //reset character properties
                    character1X = WIDTH/7;
                    character1Y = 440;
                    character2X = WIDTH/4*3;
                    character2Y = 440;
                    //reset blood 
                    bloodOneW = 200;
                    bloodTwoW = 200;
                    bloodH = 20;
                    //reset Player One Blood
                    bloodOneX = 0;
                    bloodOneY = 0;
                    //reset Player Two blood
                    bloodTwoX = 1080;
                    bloodTwoY = 0;
                }
            }
            if (key == KeyEvent.VK_CONTROL){
                if(endPlay == true){
                    endPlay = false;
                    startPlay = true;
                    //reset character
                    character1X = WIDTH/7;
                    character1Y = 440;
                    character2X = WIDTH/4*3;
                    character2Y = 440;
                    //reset blood 
                    bloodOneW = 200;
                    bloodTwoW = 200;
                    bloodH = 20;
                    //reset Player One Blood
                    bloodOneX = 0;
                    bloodOneY = 0;
                    //reset Player Two blood
                    bloodTwoX = 1080;
                    bloodTwoY = 0;
                }
            }
        }
        
        public void keyReleased(KeyEvent e){
        }
        public void keyTyped(KeyEvent e){
        } 
    }
    
} // The brother class end