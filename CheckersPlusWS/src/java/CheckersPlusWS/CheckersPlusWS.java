/*
 * CheckersPlusWS.java
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

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Kesara Nanayakkara Rathnayake
 */

@Stateless()
@WebService()
public class CheckersPlusWS {
    private final int MAX_PLAYERS = 100;
    private Player[] playersPool;
    private Player[] readyPool;
    
    /**
     * Web service registerPlayer
     */
    @WebMethod
    public int registerPlayer(@WebParam(name = "playerName") String playerName) {
        if (playersPool.length < MAX_PLAYERS) {
            playersPool[playersPool.length] = new Player(playerName);
            return playersPool[playersPool.length -1].getPlayerID();
        }
        else {
            return -1;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public void playerReady(@WebParam(name = "playerID") int playerID) {
        // TODO implement operation
    }
    
}
