public class Board {
    private final int rows = 6;
    private final int columns = 7;
    private char[][] board = new char[rows][columns];
    private final char[] players = {'O', 'X'};

    public Board(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                board[i][j] = '-';
            }
        }

    }

    public boolean place_piece(char player, int column){
        for(int i = rows-1; i >= 0; i--){
            if(board[i][column] == '-'){
                if(player == players[0]){
                    board[i][column] = players[0];
                } else {
                    board[i][column] = players[1];
                }
                return true;
            }
        }
        return false;
    }
    public void print_board(){
        System.out.print("  ");
        for (int i = 0; i < columns; i++){
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < rows; i++){
            System.out.print(i + " ");
            for (int j = 0; j < columns; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public char connected_four(char player){
        for (int i = 0; i < rows-3; i++){
            for (int j = 0; j < columns; j++){
                if(board[i][j] == player && board[i+1][j] == player && board[i+2][j] == player && board[i+3][j] == player){
                    return player;
                }
            }
        }

        for (int i = 0; i < columns-3; i++){
            for (int j = 0; j < rows; j++){
                if(board[j][i] == player && board[j][i+1] == player && board[j][i+2] == player && board[j][i+3] == player){
                    return player;
                }
            }
        }

        for (int i = 3; i < rows; i++){
            for (int j = 0; j < columns-3; j++){
                if (board[i][j] == player && board[i-1][j+1] == player && board[i-2][j+2] == player && board[i-3][j+3] == player){
                    return player;
                }
            }
        }

        for (int i = 0; i < rows-3; i++){
            for (int j = 0; j < columns-3; j++){
                if (board[i][j] == player && board[i+1][j+1] == player && board[i+2][j+2] == player && board[i+3][j+3] == player){
                    return player;
                }
            }
        }

        return '-';
    }

    public boolean isFull(){
        for (char[] x: board){
            for (char y: x){
                if(y == '-'){
                    return false;
                }
            }
        }
        return true;
    }
}
