/*
 * CheckersWSSEI.java - Checkers Plus Adhoc Design
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

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This is the service endpoint interface for the CheckersWSweb service.
 * Created Sep 14, 2007 11:42:17 PM
 * @author Kesara
 */

public interface CheckersWSSEI extends Remote {
    
    public boolean registerPlayer(int pid) throws RemoteException;
    
    public int getSatus(int x, int y, int pid) throws RemoteException;
    
    public boolean movePiece(int py, int px, int qy, int qx, int pid) throws RemoteException;
    
    public boolean nextMove(int pid) throws RemoteException;
    
    public boolean gameStarted() throws RemoteException;
    
    public boolean firstPlayer(int pid) throws RemoteException;
    
    public int getOpornentID(int pid) throws RemoteException;
    
    public boolean startGame(int pid) throws RemoteException;
    
    public int restartGame() throws RemoteException;
    
    public int gameEnd() throws RemoteException;
    
}
