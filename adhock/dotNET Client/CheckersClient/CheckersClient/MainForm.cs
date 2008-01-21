/*
 * MainForm.cs - Checkers Plus Adhoc Design
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

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using CheckersClient.cws;

namespace CheckersClient
{
    public partial class MainForm : Form
    {
        // Constants
        private const int SIZE = 8;
        private const int MAXP = 12;


        // Variables
        private CheckersWS game;

        private int pid = -1;
        private int opid = -1;

        private int[,] board;

        private int py, px;
        private int qy, qx;

        public MainForm()
        {
            game = new cws.CheckersWS();

            board = new int[SIZE,SIZE];

            Random tmp = new Random();
            pid = tmp.Next(100, 200);

            px = -1;
            qx = -1;

            InitializeComponent();
        }

        private void refresher_Tick(object sender, EventArgs e)
        {

            getOpornentID tmp = new getOpornentID();
            tmp.int_1 = pid;
            getOpornentIDResponse opidr = game.getOpornentID(tmp);
            opid = opidr.result;

            gameEnd ge = new gameEnd();
            gameEndResponse ger = game.gameEnd(ge);

            nextMove nm = new nextMove();
            nm.int_1 = pid;
            nextMoveResponse nmr = game.nextMove(nm);

            gameStarted gs = new gameStarted();
            gameStartedResponse gsr = game.gameStarted(gs);

            if (ger.result == pid)
                labelOutput.Text = "You won the game. :-)";
            else if (ger.result == opid)
                labelOutput.Text = "You lost the game. :-(";

            fillBoard();
            setBoard();
            
            if (nmr.result)
                labelOutput.Text = "Your move.";
            else if (gsr.result)
                labelOutput.Text = "Waiting for Opponent's move.";
        }

        private void fillBoard()
        {
            getSatus input = new getSatus();
            for (int i = 0; i < SIZE; i++)
            {
                for (int j = 0; j < SIZE; j++)
                {
                    input.int_1 = i;
                    input.int_2 = j;
                    input.int_3 = pid;
                    getSatusResponse status = game.getSatus(input);
                    board[i, j] = status.result;
                }
            }
        }

        private void setBoard()
        {
            a0.Image = getImage(0, 0);
            a2.Image = getImage(0, 2);
            a4.Image = getImage(0, 4);
            a6.Image = getImage(0, 6);

            b1.Image = getImage(1, 1);
            b3.Image = getImage(1, 3);
            b5.Image = getImage(1, 5);
            b7.Image = getImage(1, 7);

            c0.Image = getImage(2, 0);
            c2.Image = getImage(2, 2);
            c4.Image = getImage(2, 4);
            c6.Image = getImage(2, 6);

            d1.Image = getImage(3, 1);
            d3.Image = getImage(3, 3);
            d5.Image = getImage(3, 5);
            d7.Image = getImage(3, 7);

            e0.Image = getImage(4, 0);
            e2.Image = getImage(4, 2);
            e4.Image = getImage(4, 4);
            e6.Image = getImage(4, 6);

            f1.Image = getImage(5, 1);
            f3.Image = getImage(5, 3);
            f5.Image = getImage(5, 5);
            f7.Image = getImage(5, 7);

            g0.Image = getImage(6, 0);
            g2.Image = getImage(6, 2);
            g4.Image = getImage(6, 4);
            g6.Image = getImage(6, 6);

            h1.Image = getImage(7, 1);
            h3.Image = getImage(7, 3);
            h5.Image = getImage(7, 5);
            h7.Image = getImage(7, 7);
        }

        private Image getImage(int y, int x) {
            int status = board[y, x];

            firstPlayer fp = new firstPlayer();
            fp.int_1 = pid;
            firstPlayerResponse fpr = game.firstPlayer(fp);

            if (fpr.result)
            {
                if (status == pid)
                    return Image.FromFile("images\\black_piece.png");
                else if (status == pid + 9)
                    return Image.FromFile("images\\black_king_piece.png");
                else if (status == opid)
                    return Image.FromFile("images\\white_piece.png");
                else if (status == opid + 9)
                    return Image.FromFile("images\\white_king_piece.png");
                else
                    return null;
            }
            else
            {
                if (status == pid)
                    return Image.FromFile("images\\white_piece.png");
                else if (status == pid + 9)
                    return Image.FromFile("images\\white_king_piece.png");
                else if (status == opid)
                    return Image.FromFile("images\\black_piece.png");
                else if (status == opid + 9)
                    return Image.FromFile("images\\black_king_piece.png");
                else
                    return null;
            }
        }

        private void click(int y, int x) {
            if (px == -1) {
                px = x;
                py = y;
            }
            else if (qx == -1) {
                qx = x;
                qy = y;
                movePiece mv = new movePiece();
                mv.int_1 = py;
                mv.int_2 = px;
                mv.int_3 = qy;
                mv.int_4 = qx;
                mv.int_5 = pid;
                movePieceResponse mpr = game.movePiece(mv);
                if (mpr.result)
                    labelOutput.Text = "Piece Moved.";
                else
                    labelOutput.Text = "Invalid Move.";
                px = -1;
                qx = -1;
            }
        }

        private void a0_Click(object sender, EventArgs e)
        {
            click(0, 0);
        }

        private void a2_Click(object sender, EventArgs e)
        {
            click(0, 2);
        }

        private void a4_Click(object sender, EventArgs e)
        {
            click(0, 4);
        }

        private void a6_Click(object sender, EventArgs e)
        {
            click(0, 6);
        }

        private void b1_Click(object sender, EventArgs e)
        {
            click(1, 1);
        }

        private void b3_Click(object sender, EventArgs e)
        {
            click(1, 3);
        }

        private void b5_Click(object sender, EventArgs e)
        {
            click(1, 5);
        }

        private void b7_Click(object sender, EventArgs e)
        {
            click(1, 7);
        }

        private void c0_Click(object sender, EventArgs e)
        {
            click(2, 0);
        }

        private void c2_Click(object sender, EventArgs e)
        {
            click(2, 2);
        }


        private void c4_Click(object sender, EventArgs e)
        {
            click(2, 4);
        }

        private void c6_Click(object sender, EventArgs e)
        {
            click(2, 6);
        }

        private void d1_Click(object sender, EventArgs e)
        {
            click(3, 1);
        }

        private void d3_Click(object sender, EventArgs e)
        {
            click(3, 3);
        }

        private void d5_Click(object sender, EventArgs e)
        {
            click(3, 5);
        }

        private void d7_Click(object sender, EventArgs e)
        {
            click(3, 7);
        }

        private void e0_Click(object sender, EventArgs e)
        {
            click(4, 0);
        }

        private void e2_Click(object sender, EventArgs e)
        {

            click(4, 2);
        }

        private void e4_Click(object sender, EventArgs e)
        {

            click(4, 4);
        }

        private void e6_Click(object sender, EventArgs e)
        {
            click(4, 6);
        }

        private void f1_Click(object sender, EventArgs e)
        {
            click(5, 1);
        }

        private void f3_Click(object sender, EventArgs e)
        {
            click(5, 3);
        }

        private void f5_Click(object sender, EventArgs e)
        {
            click(5, 5);
        }

        private void f7_Click(object sender, EventArgs e)
        {
            click(5, 7);
        }

        private void g0_Click(object sender, EventArgs e)
        {
            click(6, 0);
        }

        private void g2_Click(object sender, EventArgs e)
        {
            click(6, 2);
        }

        private void g4_Click(object sender, EventArgs e)
        {
            click(6, 4);
        }

        private void g6_Click(object sender, EventArgs e)
        {
            click(6, 6);
        }

        private void h1_Click(object sender, EventArgs e)
        {
            click(7, 1);
        }

        private void h3_Click(object sender, EventArgs e)
        {
            click(7, 3);
        }

        private void h5_Click(object sender, EventArgs e)
        {
            click(7, 5);
        }

        private void h7_Click(object sender, EventArgs e)
        {
            click(7, 7);
        }

        private void buttonRegister_Click(object sender, EventArgs e)
        {
            registerPlayer rp = new registerPlayer();
            rp.int_1 = pid;
            registerPlayerResponse rpr = game.registerPlayer(rp);

            if (rpr.result)
                labelOutput.Text = "Registered.";
            else
                labelOutput.Text = "Registration Failed";
        }

        private void buttonStart_Click(object sender, EventArgs e)
        {
            startGame s = new startGame();
            s.int_1 = pid;
            startGameResponse r = game.startGame(s);
            if (r.result)
                labelOutput.Text = "Game Started.";
            else
                labelOutput.Text = "Waiting for Second Player.";
        }

        private void buttonEndGame_Click(object sender, EventArgs e)
        {
            restartGame rg = new restartGame();
            restartGameResponse rgr = game.restartGame(rg);
            labelOutput.Text = "Checkers";
        }
    }
}
