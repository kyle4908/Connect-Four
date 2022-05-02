import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ConnectFour {
    private JFrame frame;

    public ConnectFour() {
        frame = new JFrame("ConnectFour");
        frame.setSize(505, 570);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new DrawGrid(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ConnectFour();
    }

    public static class DrawGrid extends JPanel  implements MouseListener {
        int startX = 10;
        int startY = 10;
        int cellDiameter = 70;
        int turn = 0;
        int rows = 6;
        int cols = 7;
        boolean gameComplete = false;

        Color[][] grid = new Color[rows][cols];

        public DrawGrid(Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            int x = 0;
            for (int row = 0; row < grid.length; row++) { //initializes all elements of grid to white
                for (int col = 0; col < grid[0].length; col++) {
                    grid[row][col] = new Color(255,255,255);
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Dimension d = getSize();
            g2.setColor(new Color(0, 0, 225));
            g2.fillRect(0,0,d.width,d.height);   //draws the blue board/background
            startX = 0;
            startY = 0;

            for (int row = 0; row < grid.length; row++) { //updates colors on grid
                for (int col = 0; col < grid[0].length; col++) {
                    g2.setColor(grid[row][col]);
                    g2.fillOval(startX,startY, cellDiameter, cellDiameter);
                    startX += cellDiameter;
                }
                startX = 0;
                startY += cellDiameter;
            }
            g2.setColor(new Color(255, 255, 255));
            g2.setFont(new Font("Dialog", Font.PLAIN, 18));
            if (gameComplete){
                if (turn % 2 == 0) { //checks whose turn it was as the game completed and displays appropriate message
                    g2.drawString("Red Won", 206, 440);
                } else {
                    g2.drawString("Yellow Won", 199, 440);
                }
                g2.drawString("Press anywhere to restart", 140, 470);
            } else {
                if (turn % 2 == 0) { //tells whose turn it is
                    g2.drawString("Red's Turn", 203, 440);
                } else {
                    g2.drawString("Yellow's Turn", 192, 440);
                }
            }
        }

        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();
            if (gameComplete){
                restart(); //restarts game if mouse is pressed and the game is completed
                repaint();
            } else {
                if(y <= 425) { //ensures that user touched the grid
                    int xtouched = x / cellDiameter; //determines the column that the user wants to place piece in
                    int ytouched = dropLoc(xtouched); //determines where in column the piece will go
                    if (ytouched >= 0) { //ensures the user selected a valid column
                        if (turn % 2 == 0) { //checks whose turn it is, then places piece and checks for game completion
                            grid[ytouched][xtouched] = new Color(255, 0, 0);
                            gameComplete = gameWon(new Color(255, 0, 0));
                        } else {
                            grid[ytouched][xtouched] = new Color(255, 255, 0);
                            gameComplete = gameWon(new Color(255, 255, 0));
                        }

                        if (gameComplete) {//when the game is complete we do not want the turn to increment
                            repaint();
                        } else {
                            turn++;
                            repaint();
                        }
                    }
                }
            }
        }

        public int dropLoc(int x){ //finds the location that a piece needs to drop to given its column number
            for(int i = rows-1; i >= 0; i--){ //searches for lowest empty space in grid
                if(grid[i][x].equals(new Color(255,255,255))){
                    return i;
                }
            }
            return -1; //returns -1 if the input column is full
        }

        public boolean gameWon(Color c){ //checks if input color has won the game
            int count;
            for (int i = 0; i < rows-3; i++){ //checks for vertical connection
                for (int j = 0; j < cols; j++){
                    count = 0;
                    for (int o = 0; o <= 3; o++){
                        if(grid[i+o][j].equals(c)){
                            count++;
                        }
                    }
                    if(count == 4){
                        return true;
                    }
                }
            }

            for (int i = 0; i < cols-3; i++){ //checks for horizontal connection
                for (int j = 0; j < rows; j++){
                    count = 0;
                    for (int o = 0; o <= 3; o++){
                        if(grid[j][i+o].equals(c)){
                            count++;
                        }
                    }
                    if(count == 4){
                        return true;
                    }
                }
            }

            for (int i = 3; i < rows; i++){ //checks first diagonal connection
                for (int j = 0; j < cols-3; j++){
                    count = 0;
                    for (int o = 0; o <= 3; o++){
                        if(grid[i-o][j+o].equals(c)){
                            count++;
                        }
                    }
                    if(count == 4){
                        return true;
                    }
                }
            }

            for (int i = 0; i < rows-3; i++){ //checks second diagonal connection
                for (int j = 0; j < cols-3; j++){
                    count = 0;
                    for (int o = 0; o <= 3; o++){
                        if(grid[i+o][j+o].equals(c)){
                            count++;
                        }
                    }
                    if(count == 4){
                        return true;
                    }
                }
            }
            return false; //returns false when no connection found
        }

        public void restart(){ //restarts the game
            for (int row = 0; row < grid.length; row++) { //initializes all elements of grid back to white
                for (int col = 0; col < grid[0].length; col++) {
                    grid[row][col] = new Color(255,255,255);
                }
            }

            gameComplete = false; //game is being restarted, so it shouldn't be complete anymore
            turn = 0; //resetting turn counter back to 0
        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseClicked(MouseEvent e) {

        }
    }
}
