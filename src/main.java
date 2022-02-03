import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Board board = new Board();
        board.print_board();
        final char[] players = {'O', 'X'};
        final int rows = 6;
        final int columns = 7;
        boolean winner = false;


        while (!board.isFull()){
            for (char player:players) {
                boolean placed;
                do{
                    int cplace;
                    do{
                        System.out.println("Enter a valid column # from 0-"+ (columns-1) +" to place your piece");
                        cplace = input.nextInt();
                    }while(cplace < 0 || cplace >= columns);
                    placed = board.place_piece(player, cplace);
                }while(!placed);
                board.print_board();
                if(player == board.connected_four(player)){
                    System.out.println(player + "'s won the game");
                    winner = true;
                    break;
                }
            }
            if (winner){
                break;
            }
        }
    }
}
