/*
 * Piece.java
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
public class Piece {
    private Player owner;
    private int x;
    private int y;
    private boolean crowned;
    
    private final int MAX = 16;
    
    /** Creates a new instance of Piece */
    public Piece(Player p, int x, int y) {
        this.owner = p;
        this.x = x;
        this.y = y;
        this.crowned = false;
    }
    
    protected boolean isCrowned() {
        return crowned;
    }
    
    public boolean move(int p, int q) {
        if (q == y + 1 && (p == x + 1 || p == x - 1) && p < MAX && y < MAX && p >= 0 && q >= 0) {
            x = p;
            y = q;
            return true;
        }
        else if (q == y - 1 && (p == x + 1 || p == x - 1) && p < MAX && y < MAX && p >= 0 && q >= 0 && isCrowned()) {
            x = p;
            y = q;
            return true;
        }
        return false;
    }
}
