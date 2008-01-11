/*
 * Board.java
 *
 * Copyright (C) 2007, 2008 Kesara Nanayakkara Rathnayake
 *
 * This file is part of Checkers Plus.
 * 
 * Checkers Plus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Checkers Plus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Checkers Plus.  If not, see <http://www.gnu.org/licenses/>.
 */

package CheckersPlusWS;

/**
 *
 * @author Kesara Nanayakkara Rathnayake
 */
public class Board {
    private final int SIZE = 8;
    private Piece[][] board;
    
    /** Creates a new instance of GameBoard */
    public Board(Player playerA, Player playerB) {
        setBoard(playerA, playerB);
    }
    
    protected void setBoard(Player playerA, Player playerB) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = null;
            }
        }
        
        board[0][0] = new Piece(playerA);
        board[0][2] = new Piece(playerA);
        board[0][4] = new Piece(playerA);
        board[0][6] = new Piece(playerA);
        
        board[1][1] = new Piece(playerA);
        board[1][3] = new Piece(playerA);
        board[1][5] = new Piece(playerA);
        board[1][7] = new Piece(playerA);
        
        board[2][0] = new Piece(playerA);
        board[2][2] = new Piece(playerA);
        board[2][4] = new Piece(playerA);
        board[2][6] = new Piece(playerA);
        
        board[7][1] = new Piece(playerB);
        board[7][3] = new Piece(playerB);
        board[7][5] = new Piece(playerB);
        board[7][7] = new Piece(playerB);
        
        board[6][0] = new Piece(playerB);
        board[6][2] = new Piece(playerB);
        board[6][4] = new Piece(playerB);
        board[6][6] = new Piece(playerB);
        
        board[5][1] = new Piece(playerB);
        board[5][3] = new Piece(playerB);
        board[5][5] = new Piece(playerB);
        board[5][7] = new Piece(playerB);
    }
    
    protected Piece getSatus(int y, int x) {
        return board[y][x];
    }
    
    protected boolean isCrowned(int y, int x) {
        return board[y][x].isCrowned();
    }
}
