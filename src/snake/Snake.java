package snake;

import javax.swing.*; 

public class Snake extends JFrame {
    
    public Snake() {
          Board board = new Board(); 
          add(board); 
          pack(); 
          setLocationRelativeTo(null); 
          setTitle("Snake"); 
          setResizable(false); 
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    
    public static void main(String[] args) {
        
          Snake snake = new Snake();
          snake.setVisible(true); 
          
    }
    
}
