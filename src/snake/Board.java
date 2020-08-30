package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

public class Board extends JPanel implements ActionListener {
    
    private Image snakeBodyImg; 
    private Image snakeHeadImg;
    private Image appleImg; 
    
    private int snakeLength; 
    private final int DOT_SIZE = 10;  
    
    /*  Maximum no. of dots in JFrame of Dimensions 350 x 350:  MAX_DOTS = (350 * 350) / (10 * 10) = 1225  */ 
    private final int MAX_DOTS = 1225; 
    
    //Storing coordinates of the Snake. 
    private final int x[] = new int[MAX_DOTS]; 
    private final int y[] = new int[MAX_DOTS]; 
    
    public final int RANDOM_FACTOR = 34; 
    public int apple_x; //x coordinate of apple
    public int apple_y; //y coordinate of apple
    
    Timer timer; 
    
    private boolean moveLeft = false; 
    private boolean moveRight = true; //Since snake moves towards the right initially, moveRight is initialised as true. 
    private boolean moveUp = false; 
    private boolean moveDown = false; 
    
    private boolean gameState = true; //Indicates whether player is still in the game, or the game is over. 
    
    public Board() {
          addKeyListener(new LocalKeyAdapter()); 
          setBackground(Color.BLACK);
          setPreferredSize(new Dimension(350, 350)); 
          setFocusable(true); 
          addImages(); 
          createSnake(); 
    }
    
    public void addImages() {
          ImageIcon snakeBody = new ImageIcon(ClassLoader.getSystemResource("snake/icons/dot.png")); 
          snakeBodyImg = snakeBody.getImage(); 
          ImageIcon snakeHead = new ImageIcon(ClassLoader.getSystemResource("snake/icons/head.png"));
          snakeHeadImg = snakeHead.getImage(); 
          ImageIcon apple = new ImageIcon(ClassLoader.getSystemResource("snake/icons/apple.png")); 
          appleImg = apple.getImage();
    }
    
    public void createSnake() {
          snakeLength = 3;
          for(int i = 0; i<snakeLength; ++i) {
                   x[i] = 50 - (i*DOT_SIZE); 
                   y[i] = 50; 
          }
          appleLocationRandomizer(); 
          
          timer = new Timer(140, this); 
          timer.start(); 
    }
    
    public void appleLocationRandomizer() {
          int randomValue = (int)(Math.random() * RANDOM_FACTOR); 
          apple_x = randomValue * DOT_SIZE; 
          
          randomValue = (int)(Math.random() * RANDOM_FACTOR); 
          apple_y = randomValue * DOT_SIZE;
    }
    
    public void isAppleEaten() {
          if((x[0] == apple_x) && (y[0] == apple_y)) {
                   snakeLength++;    //As an apple has been eaten, snake grows in size by 1 dot. 
                   appleLocationRandomizer(); 
          }
    }
    
    public void checkCollision() {
        
          //If snake collides with boundary of the window. 
          if(y[0] > 350 || x[0] > 350)
                   gameState = false; 
          else if(y[0] < 0 || x[0] < 0)
                   gameState = false; 
          
          //If snake collides with itself.
          for(int i = snakeLength; i > 0; --i) {
                   if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) 
                             gameState = false; 
          } 
          
          if(!gameState) 
                   timer.stop();
          
    }
    
    public void moveSnake() {
          
          for(int i = snakeLength; i > 0; --i) {
                   x[i] = x[i-1]; 
                   y[i] = y[i-1]; 
          }
          
          if(moveLeft) 
                   x[0] -= DOT_SIZE; 
          else if(moveRight)
                   x[0] += DOT_SIZE; 
          else if(moveUp)
                   y[0] -= DOT_SIZE; 
          else if(moveDown) 
                   y[0] += DOT_SIZE; 
    }
 
    @Override
    public void paintComponent(Graphics g) {
          super.paintComponent(g); 
          draw(g); 
    }
    
    public void draw(Graphics g) {
          if(gameState) {
                   g.drawImage(appleImg, apple_x, apple_y, this); 
                   for(int i = 0; i < snakeLength; ++i) {
                             if(i == 0) {
                                       g.drawImage(snakeHeadImg, x[0], y[0], this); 
                             } else {
                                       g.drawImage(snakeBodyImg, x[i], y[i], this); 
                             }
                   }
                   Toolkit.getDefaultToolkit().sync(); 
          } else {
                    // If the game is over i.e. a collision has occurred. 
                    gameOver(g); 
          }
    }
    
    public void gameOver(Graphics g) {
          String message = "GAME OVER";
          Font font = new Font("SANS_SERIF", Font.BOLD, 16); 
          FontMetrics fMetrics = getFontMetrics(font); 
          g.setColor(Color.YELLOW);
          g.setFont(font); 
          g.drawString(message, (350 - fMetrics.stringWidth(message))/2, 350/2);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
          if(gameState) {
                   isAppleEaten(); 
                   checkCollision();
                   moveSnake(); 
          }
          repaint(); 
    }
    
    private class LocalKeyAdapter extends KeyAdapter {
          
          @Override
          public void keyPressed(KeyEvent ke) {
                   int pressedKey = ke.getKeyCode(); 
                   if(pressedKey == KeyEvent.VK_LEFT && !moveRight) {
                             moveLeft = true;
                             moveUp = moveDown = false; 
                   } else if(pressedKey == KeyEvent.VK_RIGHT && !moveLeft) {
                             moveRight = true;
                             moveUp = moveDown = false; 
                   } else if(pressedKey == KeyEvent.VK_UP && !moveDown) {
                             moveUp = true;
                             moveLeft = moveRight = false; 
                   } else if(pressedKey == KeyEvent.VK_DOWN && !moveUp) {
                             moveDown = true;
                             moveLeft = moveRight = false; 
                   }
          }
        
    }
    
}
