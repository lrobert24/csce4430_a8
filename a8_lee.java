//LEE ROBERTSON | CSCE 4430 | a8

import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.util.Arrays;
import java.util.TreeMap; 

/*********** CLASSES ***********/
/* 
    game_board CLASS
    ----------

    GOAL: 
        (1) Keep track on position and pieces
        (2) Reset and Display board
*/ 
class game_board {
    public int spots_avail = 0, peg = 0, new_board = 0; //represent the number of pieces left
    public char spot[] = new char[15]; //array is used to display current board status
    public ArrayList<action> move_list = new ArrayList<action>();
    
    public void print_board(){
        System.out.printf("\t%5c\n", spot[0]);
        System.out.printf("\t%4c %c\n", spot[1], spot[2]);
        System.out.printf("\t%3c %c %c\n", spot[3], spot[4], spot[5]);
        System.out.printf("\t%2c %c %c %c\n", spot[6], spot[7], spot[8], spot[9]);
        System.out.printf("\t%1c %c %c %c %c\n\n", spot[10], spot[11], spot[12], spot[13], spot[14]);
    }
    
    public game_board(int pos){ //Constructor
        Arrays.fill(spot, 'x'); //here we use java Arrays.fill() to fill our array "board"
        spot[pos] = '.'; //no peg in the spot
        /* PIECES LEFT IS SET TO 14 DUE TO THE ONE HOLE NEEDED TO START THE GAME */ 
        spots_avail = 14; 
        peg = pos;
    }
    
    public game_board(game_board board) { //Constructor
        new_board = 0;
        spot = board.spot.clone(); //create a new copy of current spot on
        spots_avail = board.spots_avail;
        peg = board.peg;
        if(board.move_list.size() > 0){ //concat the current move_list
            move_list.addAll(board.move_list);
        }
    }
}

/* 
    action CLASS
    ----------

    GOAL: 
        (1) use for start position (peg used to jump)
        (2) position of peg that is jump 
        (3) landing position
*/ 
class action{
    public int start = 0;
    public int rm_piece = 0;
    public int new_spot = 0;

    public action(int current, int jump, int new_spot){ //constructor
        this.start = current;
        this.rm_piece = jump;
        this.new_spot = new_spot;
    }
}
/******************************/

public class a8_lee{

    /* GLOBAL VARIABLE THAT CONTAINS THE GAME */
    public static TreeMap<Integer, action> user_moves = new TreeMap<Integer, action>();

    public static void main(String[] args) {
        int i = 0, position = 0;
        action set0 = new action(0,1,3);
        action set1 = new action(0,2,5);
        action set2 = new action(1,3,6);
        action set3 = new action(1,4,8);
        action set4 = new action(2,4,7);
        action set5 = new action(2,5,9);
        action set6 = new action(3,6,10);
        action set7 = new action(3,7,12);
        action set8 = new action(4,7,11);
        action set9 = new action(4,8,13);
        action set10 = new action(5,8,12);
        action set11 = new action(5,9,14);
        action set12 = new action(3,4,5);
        action set13 = new action(6,7,8);
        action set14 = new action(7,8,9);
        action set15 = new action(10,11,12);
        action set16 = new action(11,12,13);
        action set17 = new action(12,13,14);

        /* HERE WE INITIALIZE OUR user_moves FOR THE BOARD */
        //action(int start, int rm_piece, int new_spot)
        user_moves.put(0, set0);
        user_moves.put(1, set1);
        user_moves.put(2, set2);
        user_moves.put(3, set3);
        user_moves.put(4, set4);
        user_moves.put(5, set5);
        user_moves.put(6, set6);
        user_moves.put(7, set7);
        user_moves.put(8, set8);
        user_moves.put(9, set9);
        user_moves.put(10, set10);
        user_moves.put(11, set11);
        user_moves.put(12, set12);
        user_moves.put(13, set13);
        user_moves.put(14, set14);
        user_moves.put(15, set15);
        user_moves.put(16, set16);
        user_moves.put(17, set17);

        /* HERE WE INITIALIZE OUR user_moves FOR THE BOARD 18-36 */
        while(i < 18){
            int spot = user_moves.get(i).new_spot;
            int remove = user_moves.get(i).rm_piece;
            int current_piece = user_moves.get(i).start;

            user_moves.put(i+18, new action(spot, remove, current_piece));
            i++; //increment our loop 
        }

        /* HERE WE CALL set_empty_piece()) WHICH REMOVE SETS OUR INITIAL EMPTY peg */
        while(position < 6){ //we remove from top to bottom pos 0 - 5
            set_empty_piece(position); 
            position++; //remove next position
        }
        System.exit(1); 
    }

    public static void set_empty_piece(int pos){
        game_board board = new game_board(pos);
        if(pos > 5) System.exit(1); //reach our five positions
        System.out.printf("    ======= %d =======\n", pos); //display current starting position
        /* BEGIN A NEW GAME */
        begingame(board);
    }

    public static game_board new_move(game_board board, action peg){
        /* VALIDATE WE HAVE A VAILD BOARD and MOVE OBJECT*/
        if(board == null) return board;

        game_board b = board;
        action p = peg;
        /* HERE IS WHERE WE CREATE A NEW MOVE AND MARK THE CURRENT BOARD */ 
        if(b.spot[p.start] == 'x'){ //start peg is there to rm_piece
            if(b.spot[p.rm_piece] == 'x'){  //we have a place to rm_piece
                if(b.spot[p.new_spot] == '.'){ //the new spot is not marked empty
                    b.spot[p.start] = '.';
                    b.spot[p.rm_piece] = '.';
                    b.spot[p.new_spot] = 'x';
                    b.spots_avail--;
                    b.new_board = 1; //set flag peg has been moved
                    return board;
                }
            }
        }
        /* NOTHING HAS OCCURED */
        b.new_board = 0;
        return b;
    }

    public static void begingame(game_board board){
        int i = 0;
        /* Validate game_board */
        if(board == null) return;

        /* HERE WE ITERATE THOUGH ALL OF OUR MOVES*/
        while(i < user_moves.size()){
            game_board b = new game_board(board);

            /* get move from our array of moves */
            action current_move = user_moves.get(i); //get value at key i
            b = new_move(b, current_move);
            if(b.new_board == 1){ //new move has occured
                b.move_list.add(current_move); //adds new new_move
                if(b.spots_avail < 2){ //when you when, 1 peg left
                    result(b);
                }
                begingame(b);
            }
            i++;
        }
    }

    /*
        This function displays the result of each game

        ON ERROR: RETURN @ POINT OF ERROR
    */
    public static void result(game_board board){
        int i = 0;
        game_board b;

        /* VALIDATE BOARD */
        if(board == null) return;
        b = board;

        /*  RESET BOARD and UPDATE HOLE */
        Arrays.fill(b.spot, 'x');
        b.spot[b.peg] = '.';
        /****************/
        board.print_board();
        while(i < b.move_list.size()){
           b = new_move(board, b.move_list.get(i));
           b.print_board(); //print the board for all of our moves
           i++;
        }
        set_empty_piece(b.peg + 1);
        return;
    }
}