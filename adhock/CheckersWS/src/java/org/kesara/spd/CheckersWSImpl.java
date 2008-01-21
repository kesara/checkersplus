/*
 * CheckersWSImpl.java - Checkers Plus Adhoc Design
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

package org.kesara.spd;

import java.rmi.RemoteException;


/**
 * This is the implementation bean class for the CheckersWS web service.
 * Created Sep 14, 2007 11:42:17 PM
 * @author Kesara
 */
public class CheckersWSImpl implements CheckersWSSEI {
    private final int SIZE = 8;
    private final int MAXP = 12;
    
    private int playerA = -10;
    private int playerB = -10;
    
    private boolean readyA = false;
    private boolean readyB = false;
    
    private int nA = MAXP;
    private int nB = MAXP;
    
    private int next = -1;
    private boolean gameOn = false;
    
    private int nPlayers = 0;
    
    private int[][] board = new int[SIZE][SIZE];
    
    public boolean registerPlayer(int pid) throws RemoteException {
        if (playerA == -10) {
            playerA = pid;
            return true;
        } else if (playerB == -10) {
            playerB = pid;
            return true;
        } else
            return false;
    }
    
    private void setBoard() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = 0;
        
        board[0][0] = playerA;
        board[0][2] = playerA;
        board[0][4] = playerA;
        board[0][6] = playerA;
        
        board[1][1] = playerA;
        board[1][3] = playerA;
        board[1][5] = playerA;
        board[1][7] = playerA;
        
        board[2][0] = playerA;
        board[2][2] = playerA;
        board[2][4] = playerA;
        board[2][6] = playerA;
        
        board[7][1] = playerB;
        board[7][3] = playerB;
        board[7][5] = playerB;
        board[7][7] = playerB;
        
        board[6][0] = playerB;
        board[6][2] = playerB;
        board[6][4] = playerB;
        board[6][6] = playerB;
        
        board[5][1] = playerB;
        board[5][3] = playerB;
        board[5][5] = playerB;
        board[5][7] = playerB;
    }
    
    public int getSatus(int y, int x, int pid) throws RemoteException {
        if (pid == playerA)
            return board[y][x];
        else if (pid == playerB)
            return board[SIZE -1 -y][SIZE -1 -x];
        return 0;
    }
    
    private boolean isCrowned(int y, int x, int pid) {
        if (board[y][x] == pid + 9)
            return true;
        else
            return false;
    }
    
    private boolean isOwn(int y, int x, int pid) {
        if (y > -1 && x > -1 && y < SIZE && x < SIZE)
            if (board[y][x] == pid || board[y][x] == pid + 9)
                return true;
            else
                return false;
        else
            return false;
    }
    
    private boolean isEmpty(int y, int x) {
        if (y > -1 && x > -1 && y < SIZE && x < SIZE)
            if (board[y][x] == 0)
                return true;
            else
                return false;
        else
            return false;
    }
    
    private boolean isOther(int y, int x, int pid) {
        int opid = -1;
        
        if (pid == playerA)
            opid = playerB;
        else
            opid = playerA;
        
        if (y > -1 && x > -1 && y < SIZE && x < SIZE)
            if (board[y][x] == opid || board[y][x] == opid + 9)
                return true;
            else
                return false;
        else
            return false;
    }
    
    private void isKing(int y, int x, int pid) {
        if (pid == playerA) {
            if (y == 7 && !isCrowned(y,x,pid))
                board[y][x] = pid + 9;
        } else if (pid == playerB) {
            if (y == 7 && !isCrowned(SIZE-1-y,SIZE-1-x,pid))
                board[SIZE-1-y][SIZE-1-x] = pid + 9;
        }
    }
    
    public boolean movePiece(int py, int px, int qy, int qx, int pid) throws RemoteException {
        int ipy = -1;
        int ipx = -1;
        int iqy = -1;
        int iqx = -1;
        
        
        int imy = -1;
        int imx = -1;
        int iny = -1;
        int inx = -1;
        
        if (pid == playerA) {
            ipy = py;
            ipx = px;
            iqy = qy;
            iqx = qx;
            
            imy = py-1;
            imx = px-1;
            iny = py+1;
            inx = px+1;
        } else if (pid == playerB) {
            
            ipy = SIZE -1 -py;
            ipx = SIZE -1 -px;
            iqy = SIZE -1 -qy;
            iqx = SIZE -1 -qx;
            
            imy = SIZE -1 -(py-1);
            imx = SIZE -1 -(px-1);
            iny = SIZE -1 -(py+1);
            inx = SIZE -1 -(px+1);
        }
        
        if (next == pid) {
            if (isOwn(ipy, ipx, pid) && isEmpty(iqy, iqx)) {
                if ((qy == py + 1) && ((qx == px + 1) || (qx == px - 1))) {
                    move(py, px, qy, qx, pid);
                    isKing(qy, qx, pid);
                    return true;
                } else if (qy == py + 2 && qx == px + 2 && isOther(iny, inx, pid)) {
                    cut(py+1,px+1,pid);
                    move(py, px, qy, qx, pid);
                    isKing(qy, qx, pid);
                    return true;
                } else if (qy == py + 2 && qx == px - 2 && isOther(iny, imx, pid)) {
                    cut(py+1,px-1,pid);
                    move(py, px, qy, qx, pid);
                    isKing(qy, qx, pid);
                    return true;
                } else if (isCrowned(ipy, ipx, pid)) {
                    if ((qy == py - 1) && ((qx == px + 1) || (qx == px - 1))) {
                        move(py, px, qy, qx, pid);
                        return true;
                    } else if ((qy == py + 2) && qx == px + 2 && isOther(iny, inx, pid)) {
                        cut(py+1,px+1,pid);
                        move(py, px, qy, qx, pid);
                        return true;
                    } else if ((qy == py + 2) && qx == px - 2 && isOther(iny, imx, pid)) {
                        cut(py+1,px-1,pid);
                        move(py, px, qy, qx, pid);
                        return true;
                    } else if ((qy == py - 2) && qx == px + 2 && isOther(imy, inx, pid)) {
                        cut(py-1,px+1,pid);
                        move(py, px, qy, qx, pid);
                        return true;
                    } else if ((qy == py - 2) && qx == px - 2 && isOther(imy, imx, pid)) {
                        cut(py-1,px-1,pid);
                        move(py, px, qy, qx, pid);
                        return true;
                    } else
                        return false;
                } else
                    return false;
            } else
                return false;
        } else
            return false;
    }
    
    private void move(int py, int px, int qy, int qx, int pid) {
        if (pid == playerA) {
            board[qy][qx] = board[py][px];
            board[py][px] = 0;
        } else if (pid == playerB) {
            board[SIZE -1 -qy][SIZE -1 -qx] = board[SIZE -1 -py][SIZE -1 -px];
            board[SIZE -1 -py][SIZE -1 -px] = 0;
        }
        nextMoveUpdate(pid);
    }
    
    
    private void cut(int y, int x, int pid) {
        if (pid == playerA) {
            board[y][x] = 0;
            nB--;
        } else if (pid == playerB) {
            board[SIZE -1 -y][SIZE -1 -x] = 0;
            nA--;
        }
    }
    
    private void nextMoveUpdate(int pid) {
        if (pid == playerA)
            next = playerB;
        else if (pid == playerB)
            next = playerA;
    }
    
    public boolean nextMove(int pid) throws RemoteException {
        if (next == pid) {
            return true;
        } else
            return false;
    }
    
    public boolean gameStarted() throws RemoteException {
        return gameOn;
    }
    
    public int restartGame() throws RemoteException {
        playerA = -10;
        playerB = -10;
        readyA = false;
        readyB = false;
        nA = MAXP;
        nB = MAXP;
        next = -1;
        gameOn = false;
        nPlayers = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = 0;
        return 1;
    }
    
    public boolean firstPlayer(int pid) throws RemoteException {
        if (pid == playerA) {
            return true;
        } else {
            return false;
        }
        
    }
    
    public int getOpornentID(int pid) throws RemoteException {
        if (pid == playerA) {
            return playerB;
        } else if (pid == playerB) {
            return playerA;
        } else {
            return -2;
        }
    }
    
    public boolean startGame(int pid) throws RemoteException {
        if (pid == playerA) {
            readyA = true;
        } else if (pid == playerB) {
            readyB = true;
        }
        if (readyA && readyB) {
            setBoard();
            next = playerA;
            gameOn = true;
            return true;
        } else
            return false;
    }
    
    public int gameEnd() throws RemoteException {
        if (nA == 0) {
            gameOn = false;
            next = -1;
            return playerB;
        } else if (nB == 0) {
            gameOn = false;
            next = -1;
            return playerA;
        } else
            return -1;
    }
    
}
