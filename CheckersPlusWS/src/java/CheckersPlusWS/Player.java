/*
 * Player.java
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
public class Player {
    private String name;
    
    /** Creates a new instance of Player */
    public Player(String name) {
        this.name = name;
    }
    
    public String getPlayerName() {
        return name;
    }
}
