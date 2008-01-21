/*
 * MainJFrame.java - Checkers Plus Adhoc Design
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

package checkersclient;

import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import me.kesara.checkersws.*;

/**
 *
 * @author  Kesara
 */
public class MainJFrame extends javax.swing.JFrame {
    private CheckersWS game;
    private final int SIZE = 8;
    private final int MAXP = 12;
    private int board[][];
    
    private int pid;
    private int opid;
    
    private int px;
    private int py;
    private int qx;
    private int qy;
    
    /* Icons */
    private ImageIcon whitePiece = new ImageIcon(getClass().getResource("./images/white_piece.png"));
    private ImageIcon whiteKing = new ImageIcon(getClass().getResource("./images/white_king_piece.png"));
    private ImageIcon blackPiece = new ImageIcon(getClass().getResource("./images/black_piece.png"));
    private ImageIcon blackKing = new ImageIcon(getClass().getResource("./images/black_king_piece.png"));
    
    /** Creates new form MainJFrame */
    public MainJFrame() {
        game = new CheckersWS();
        board = new int[SIZE][SIZE];
        px = -1;
        qx = -1;
        Random ran = new Random();
        pid = ran.nextInt(99);
        
        initComponents();
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    opid = game.getCheckersWSSEIPort().getOpornentID(pid);
                    if (game.getCheckersWSSEIPort().gameEnd() == pid) {
                        jLabelOutput.setText("You won! :-)");
                    } else if (game.getCheckersWSSEIPort().gameEnd() == opid) {
                        jLabelOutput.setText("You lost! :-(");
                    }
                    
                    fillBoard();
                    setBoard();
                    
                    if (game.getCheckersWSSEIPort().nextMove(pid)) {
                        jLabelOutput.setText("Your move.");
                    } else if (game.getCheckersWSSEIPort().gameStarted()) {
                        jLabelOutput.setText("Waiting for opponent's move.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        javax.swing.Timer refresher = new javax.swing.Timer(455, taskPerformer);
        refresher.setRepeats(true);
        refresher.start();
    }
    
    private void fillBoard() {
        int t = 0;
        for (int i = 0; i < SIZE; i++) {
            if (t == 1) {
                t = 0;
            } else {
                t = 1;
            }
            for (int j = t; j <SIZE; j += 2) {
                int status = game.getCheckersWSSEIPort().getSatus(i, j, pid);
                board[i][j] = status;
            }
        }
    }
    
    private void setBoard() {
        
        a0.setIcon(getIcon(0, 0));
        a2.setIcon(getIcon(0, 2));
        a4.setIcon(getIcon(0, 4));
        a6.setIcon(getIcon(0, 6));
        
        b1.setIcon(getIcon(1, 1));
        b3.setIcon(getIcon(1, 3));
        b5.setIcon(getIcon(1, 5));
        b7.setIcon(getIcon(1, 7));
        
        c0.setIcon(getIcon(2, 0));
        c2.setIcon(getIcon(2, 2));
        c4.setIcon(getIcon(2, 4));
        c6.setIcon(getIcon(2, 6));
        
        d1.setIcon(getIcon(3, 1));
        d3.setIcon(getIcon(3, 3));
        d5.setIcon(getIcon(3, 5));
        d7.setIcon(getIcon(3, 7));
        
        e0.setIcon(getIcon(4, 0));
        e2.setIcon(getIcon(4, 2));
        e4.setIcon(getIcon(4, 4));
        e6.setIcon(getIcon(4, 6));
        
        f1.setIcon(getIcon(5, 1));
        f3.setIcon(getIcon(5, 3));
        f5.setIcon(getIcon(5, 5));
        f7.setIcon(getIcon(5, 7));
        
        g0.setIcon(getIcon(6, 0));
        g2.setIcon(getIcon(6, 2));
        g4.setIcon(getIcon(6, 4));
        g6.setIcon(getIcon(6, 6));
        
        h1.setIcon(getIcon(7, 1));
        h3.setIcon(getIcon(7, 3));
        h5.setIcon(getIcon(7, 5));
        h7.setIcon(getIcon(7, 7));
    }
    
    private ImageIcon getIcon(int y, int x) {
        int status = game.getCheckersWSSEIPort().getSatus(y, x, pid);
        if (game.getCheckersWSSEIPort().firstPlayer(pid)) {
            if (status == pid)
                return blackPiece;
            else if (status == pid + 9)
                return blackKing;
            else if (status == opid)
                return whitePiece;
            else if (status == opid + 9)
                return whiteKing;
            else if (status == 0)
                return null;
        } else {
            if (status == pid)
                return whitePiece;
            else if (status == pid + 9)
                return whiteKing;
            else if (status == opid)
                return blackPiece;
            else if (status == opid + 9)
                return blackKing;
            else  if (status == 0)
                return null;
        }
        return null;
    }
    
    private void click(int y, int x) {
        if (px == -1) {
            px = x;
            py = y;
        } else if (qx == -1) {
            qx = x;
            qy = y;
            if (game.getCheckersWSSEIPort().movePiece(py, px, qy, qx, pid)) {
                jLabelOutput.setText("Piece Moved!");
                
            } else{
                jLabelOutput.setText("Invalid Move!");
            }
            px = -1;
            qx = -1;
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        a0 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        a2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        a4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        a6 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        g0 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        e0 = new javax.swing.JButton();
        c0 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        b1 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        d1 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        f1 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        h1 = new javax.swing.JButton();
        c2 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        c4 = new javax.swing.JButton();
        g2 = new javax.swing.JButton();
        e2 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        e4 = new javax.swing.JButton();
        d3 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        f3 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        g4 = new javax.swing.JButton();
        h3 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        h5 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        h7 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        g6 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        d5 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        d7 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();
        c6 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        e6 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        f5 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        f7 = new javax.swing.JButton();
        jLabelOutput = new javax.swing.JLabel();
        jButtonRegister = new javax.swing.JButton();
        jButtonEndGame = new javax.swing.JButton();
        jButtonStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Checkers Client");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        a0.setBackground(java.awt.Color.red);
        a0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a0ActionPerformed(evt);
            }
        });

        jButton2.setBackground(java.awt.Color.white);
        jButton2.setEnabled(false);

        a2.setBackground(java.awt.Color.red);
        a2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(java.awt.Color.white);
        jButton4.setEnabled(false);

        a4.setBackground(java.awt.Color.red);
        a4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a4ActionPerformed(evt);
            }
        });

        jButton8.setBackground(java.awt.Color.white);
        jButton8.setEnabled(false);

        a6.setBackground(java.awt.Color.red);
        a6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a6ActionPerformed(evt);
            }
        });

        jButton6.setBackground(java.awt.Color.white);
        jButton6.setEnabled(false);

        jButton33.setBackground(java.awt.Color.white);
        jButton33.setEnabled(false);

        g0.setBackground(java.awt.Color.red);
        g0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g0ActionPerformed(evt);
            }
        });

        jButton44.setBackground(java.awt.Color.white);
        jButton44.setEnabled(false);

        e0.setBackground(java.awt.Color.red);
        e0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e0ActionPerformed(evt);
            }
        });

        c0.setBackground(java.awt.Color.red);
        c0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c0ActionPerformed(evt);
            }
        });

        jButton60.setBackground(java.awt.Color.white);
        jButton60.setEnabled(false);

        jButton52.setBackground(java.awt.Color.white);
        jButton52.setEnabled(false);

        b1.setBackground(java.awt.Color.red);
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        jButton59.setBackground(java.awt.Color.white);
        jButton59.setEnabled(false);

        b3.setBackground(java.awt.Color.red);
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        jButton58.setBackground(java.awt.Color.white);
        jButton58.setEnabled(false);

        jButton56.setBackground(java.awt.Color.white);
        jButton56.setEnabled(false);

        d1.setBackground(java.awt.Color.red);
        d1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                d1ActionPerformed(evt);
            }
        });

        jButton48.setBackground(java.awt.Color.white);
        jButton48.setEnabled(false);

        f1.setBackground(java.awt.Color.red);
        f1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f1ActionPerformed(evt);
            }
        });

        jButton40.setBackground(java.awt.Color.white);
        jButton40.setEnabled(false);

        h1.setBackground(java.awt.Color.red);
        h1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h1ActionPerformed(evt);
            }
        });

        c2.setBackground(java.awt.Color.red);
        c2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c2ActionPerformed(evt);
            }
        });

        jButton55.setBackground(java.awt.Color.white);
        jButton55.setEnabled(false);

        c4.setBackground(java.awt.Color.red);
        c4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c4ActionPerformed(evt);
            }
        });

        g2.setBackground(java.awt.Color.red);
        g2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g2ActionPerformed(evt);
            }
        });

        e2.setBackground(java.awt.Color.red);
        e2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e2ActionPerformed(evt);
            }
        });

        jButton51.setBackground(java.awt.Color.white);
        jButton51.setEnabled(false);

        jButton43.setBackground(java.awt.Color.white);
        jButton43.setEnabled(false);

        jButton34.setBackground(java.awt.Color.white);
        jButton34.setEnabled(false);

        jButton47.setBackground(java.awt.Color.white);
        jButton47.setEnabled(false);

        e4.setBackground(java.awt.Color.red);
        e4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e4ActionPerformed(evt);
            }
        });

        d3.setBackground(java.awt.Color.red);
        d3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                d3ActionPerformed(evt);
            }
        });

        jButton50.setBackground(java.awt.Color.white);
        jButton50.setEnabled(false);

        f3.setBackground(java.awt.Color.red);
        f3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f3ActionPerformed(evt);
            }
        });

        jButton42.setBackground(java.awt.Color.white);
        jButton42.setEnabled(false);

        jButton39.setBackground(java.awt.Color.white);
        jButton39.setEnabled(false);

        g4.setBackground(java.awt.Color.red);
        g4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g4ActionPerformed(evt);
            }
        });

        h3.setBackground(java.awt.Color.red);
        h3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3ActionPerformed(evt);
            }
        });

        jButton35.setBackground(java.awt.Color.white);
        jButton35.setEnabled(false);

        h5.setBackground(java.awt.Color.red);
        h5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h5ActionPerformed(evt);
            }
        });

        jButton36.setBackground(java.awt.Color.white);
        jButton36.setEnabled(false);

        h7.setBackground(java.awt.Color.red);
        h7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h7ActionPerformed(evt);
            }
        });

        jButton38.setBackground(java.awt.Color.white);
        jButton38.setEnabled(false);

        g6.setBackground(java.awt.Color.red);
        g6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g6ActionPerformed(evt);
            }
        });

        jButton37.setBackground(java.awt.Color.white);
        jButton37.setEnabled(false);

        b5.setBackground(java.awt.Color.red);
        b5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b5ActionPerformed(evt);
            }
        });

        jButton54.setBackground(java.awt.Color.white);
        jButton54.setEnabled(false);

        d5.setBackground(java.awt.Color.red);
        d5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                d5ActionPerformed(evt);
            }
        });

        jButton49.setBackground(java.awt.Color.white);
        jButton49.setEnabled(false);

        d7.setBackground(java.awt.Color.red);
        d7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                d7ActionPerformed(evt);
            }
        });

        jButton57.setBackground(java.awt.Color.white);
        jButton57.setEnabled(false);

        b7.setBackground(java.awt.Color.red);
        b7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b7ActionPerformed(evt);
            }
        });

        c6.setBackground(java.awt.Color.red);
        c6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c6ActionPerformed(evt);
            }
        });

        jButton53.setBackground(java.awt.Color.white);
        jButton53.setEnabled(false);

        jButton46.setBackground(java.awt.Color.white);
        jButton46.setEnabled(false);

        e6.setBackground(java.awt.Color.red);
        e6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e6ActionPerformed(evt);
            }
        });

        jButton45.setBackground(java.awt.Color.white);
        jButton45.setEnabled(false);

        f5.setBackground(java.awt.Color.red);
        f5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f5ActionPerformed(evt);
            }
        });

        jButton41.setBackground(java.awt.Color.white);
        jButton41.setEnabled(false);

        f7.setBackground(java.awt.Color.red);
        f7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(a0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(a2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(a4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(a6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(g0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(d1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(f1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(h1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(c4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(g2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(e2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(e4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(d3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(f3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(g4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(h3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(h5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(h7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(g6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(d5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(d7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(b7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(c6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(e6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(f5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(h1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(h3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(h5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(h7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(g2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(g0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(g4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(f3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(f1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(e0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(e2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(d1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(d3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jButton55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(c0, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton59, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton60, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(g6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(f5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(f7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(e4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(e6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(d7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(d5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(c4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(c6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton57, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(b5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(b7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabelOutput.setText("Checker Client");

        jButtonRegister.setText("Register");
        jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegisterActionPerformed(evt);
            }
        });

        jButtonEndGame.setText("EndGame");
        jButtonEndGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEndGameActionPerformed(evt);
            }
        });

        jButtonStart.setText("Start");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelOutput)
                    .addComponent(jButtonRegister)
                    .addComponent(jButtonStart)
                    .addComponent(jButtonEndGame))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelOutput)
                        .addGap(16, 16, 16)
                        .addComponent(jButtonRegister)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEndGame))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
        if (game.getCheckersWSSEIPort().startGame(pid))
            jLabelOutput.setText("Game Started");
        else
            jLabelOutput.setText("Waiting for Second Player.");
    }//GEN-LAST:event_jButtonStartActionPerformed
    
    private void jButtonEndGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEndGameActionPerformed
        game.getCheckersWSSEIPort().restartGame();
        jLabelOutput.setText("Checker Client");
    }//GEN-LAST:event_jButtonEndGameActionPerformed
    
    private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegisterActionPerformed
        if (game.getCheckersWSSEIPort().registerPlayer(pid)) {
            jLabelOutput.setText("Registered.");
        } else {
            jLabelOutput.setText("Registration Failed");
        }
    }//GEN-LAST:event_jButtonRegisterActionPerformed
    
    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        click(1,1);
    }//GEN-LAST:event_b1ActionPerformed
    
    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        click(1,3);
    }//GEN-LAST:event_b3ActionPerformed
    
    private void b5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b5ActionPerformed
        click(1,5);
    }//GEN-LAST:event_b5ActionPerformed
    
    private void b7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b7ActionPerformed
        click(1,7);
    }//GEN-LAST:event_b7ActionPerformed
    
    private void c0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c0ActionPerformed
        click(2,0);
    }//GEN-LAST:event_c0ActionPerformed
    
    private void c2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c2ActionPerformed
        click(2,2);
    }//GEN-LAST:event_c2ActionPerformed
    
    private void c4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c4ActionPerformed
        click(2,4);
    }//GEN-LAST:event_c4ActionPerformed
    
    private void c6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c6ActionPerformed
        click(2,6);
    }//GEN-LAST:event_c6ActionPerformed
    
    private void d1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_d1ActionPerformed
        click(3,1);
    }//GEN-LAST:event_d1ActionPerformed
    
    private void d3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_d3ActionPerformed
        click(3,3);
    }//GEN-LAST:event_d3ActionPerformed
    
    private void d5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_d5ActionPerformed
        click(3,5);
    }//GEN-LAST:event_d5ActionPerformed
    
    private void d7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_d7ActionPerformed
        click(3,7);
    }//GEN-LAST:event_d7ActionPerformed
    
    private void e0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e0ActionPerformed
        click(4,0);
    }//GEN-LAST:event_e0ActionPerformed
    
    private void e2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e2ActionPerformed
        click(4,2);
    }//GEN-LAST:event_e2ActionPerformed
    
    private void e4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e4ActionPerformed
        click(4,4);
    }//GEN-LAST:event_e4ActionPerformed
    
    private void e6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e6ActionPerformed
        click(4,6);
    }//GEN-LAST:event_e6ActionPerformed
    
    private void f1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f1ActionPerformed
        click(5,1);
    }//GEN-LAST:event_f1ActionPerformed
    
    private void f3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f3ActionPerformed
        click(5,3);
    }//GEN-LAST:event_f3ActionPerformed
    
    private void f5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f5ActionPerformed
        click(5,5);
    }//GEN-LAST:event_f5ActionPerformed
    
    private void f7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f7ActionPerformed
        click(5,7);
    }//GEN-LAST:event_f7ActionPerformed
    
    private void g0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g0ActionPerformed
        click(6,0);
    }//GEN-LAST:event_g0ActionPerformed
    
    private void g2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g2ActionPerformed
        click(6,2);
    }//GEN-LAST:event_g2ActionPerformed
    
    private void g4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g4ActionPerformed
        click(6,4);
    }//GEN-LAST:event_g4ActionPerformed
    
    private void g6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g6ActionPerformed
        click(6,6);
    }//GEN-LAST:event_g6ActionPerformed
    
    private void h1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h1ActionPerformed
        click(7,1);
    }//GEN-LAST:event_h1ActionPerformed
    
    private void h3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3ActionPerformed
        click(7,3);
    }//GEN-LAST:event_h3ActionPerformed
    
    private void h5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h5ActionPerformed
        click(7,5);
    }//GEN-LAST:event_h5ActionPerformed
    
    private void h7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h7ActionPerformed
        click(7,7);
    }//GEN-LAST:event_h7ActionPerformed
    
    private void a6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a6ActionPerformed
        click(0,6);
    }//GEN-LAST:event_a6ActionPerformed
    
    private void a4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a4ActionPerformed
        click(0,4);
    }//GEN-LAST:event_a4ActionPerformed
    
    private void a2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a2ActionPerformed
        click(0,2);
    }//GEN-LAST:event_a2ActionPerformed
    
    private void a0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a0ActionPerformed
        click(0,0);
    }//GEN-LAST:event_a0ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton a0;
    private javax.swing.JButton a2;
    private javax.swing.JButton a4;
    private javax.swing.JButton a6;
    private javax.swing.JButton b1;
    private javax.swing.JButton b3;
    private javax.swing.JButton b5;
    private javax.swing.JButton b7;
    private javax.swing.JButton c0;
    private javax.swing.JButton c2;
    private javax.swing.JButton c4;
    private javax.swing.JButton c6;
    private javax.swing.JButton d1;
    private javax.swing.JButton d3;
    private javax.swing.JButton d5;
    private javax.swing.JButton d7;
    private javax.swing.JButton e0;
    private javax.swing.JButton e2;
    private javax.swing.JButton e4;
    private javax.swing.JButton e6;
    private javax.swing.JButton f1;
    private javax.swing.JButton f3;
    private javax.swing.JButton f5;
    private javax.swing.JButton f7;
    private javax.swing.JButton g0;
    private javax.swing.JButton g2;
    private javax.swing.JButton g4;
    private javax.swing.JButton g6;
    private javax.swing.JButton h1;
    private javax.swing.JButton h3;
    private javax.swing.JButton h5;
    private javax.swing.JButton h7;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButtonEndGame;
    private javax.swing.JButton jButtonRegister;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JLabel jLabelOutput;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}
