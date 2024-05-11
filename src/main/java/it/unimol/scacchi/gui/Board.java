package it.unimol.scacchi.gui;

import it.unimol.scacchi.app.Input;
import it.unimol.scacchi.pieces.King;
import it.unimol.scacchi.pieces.Knight;
import it.unimol.scacchi.pieces.Piece;
import it.unimol.scacchi.pieces.Rook;
import it.unimol.scacchi.pieces.Bishop;
import it.unimol.scacchi.pieces.Pawn;
import it.unimol.scacchi.pieces.Queen;
import it.unimol.scacchi.app.Move;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public int titleSize = 85;

    int cols = 8;
    int rows = 8;

    ArrayList<Piece> piecesList = new ArrayList<>();

    public Piece selectedPiece;

    Input input = new Input(this);
    public int enPeasantTile = -1;
    public Board(){
        this.setPreferredSize(new Dimension(cols * titleSize, rows * titleSize));

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPiece();
    }

    public Piece getPiece(int col, int row){
        for (Piece piece : piecesList){
            if (piece.col == col && piece.row == row){
                return piece;
            }
        }
        return null;
    }

    public void makeMove(Move move){
        if(move.piece.name.equals("Pawn")){
            movePawn(move);
        }else {
            move.piece.col = move.newcol;
            move.piece.row = move.newrow;
            move.piece.xPos = move.newcol * titleSize;
            move.piece.yPos = move.newrow * titleSize;

            move.piece.isFirstMove = false;

            capture(move.capture);
        }
    }

    private void movePawn(Move move){
        //en passant
        int colorIndex = move.piece.isWhite ? 1 : -1;

        if(getTileNum(move.newcol, move.newrow) == enPeasantTile){
            move.capture = getPiece(move.newcol, move.piece.row + colorIndex);
        }
        if(Math.abs(move.piece.row - move.newrow) == 2) {
            enPeasantTile = getTileNum(move.newcol, move.piece.row + colorIndex);
        }else{
            enPeasantTile = -1;
        }

        //promozione
        colorIndex = move.piece.isWhite ? 0 : 7;
        if(move.newrow == colorIndex){
            promotePawn(move);
        }

        move.piece.col = move.newcol;
        move.piece.row = move.newrow;
        move.piece.xPos = move.newcol * titleSize;
        move.piece.yPos = move.newrow * titleSize;

        move.piece.isFirstMove = false;

        capture(move.capture);
    }

    private void promotePawn(Move move){
        piecesList.add(new Queen(this, move.newcol, move.newrow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece){
        piecesList.remove(piece);
    }

    public boolean isValidMove(Move move){
        if(sameTeam(move.piece, move.capture)){
            return false;
        }

        if(!move.piece.isValidMovement(move.newcol, move.newrow)){
            return false;
        }
        if(move.piece.moveColidesWithPiece(move.newcol, move.newrow)){
            return false;
        }

        return true;
    }

    public boolean sameTeam(Piece p1, Piece p2){
        if(p1 == null || p2 == null){
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    public int getTileNum(int col, int row){
        return row * rows + col;
    }

    public void addPiece() {
        piecesList.add(new Knight(this, 1, 0, false));
        piecesList.add(new Knight(this, 6, 0, false));
        piecesList.add(new Knight(this, 1, 7, true));
        piecesList.add(new Knight(this, 6, 7, true));

        piecesList.add(new Rook(this, 0, 7, true));
        piecesList.add(new Rook(this, 7, 7, true));
        piecesList.add(new Rook(this, 0, 0, false));
        piecesList.add(new Rook(this, 7, 0, false));

        piecesList.add(new Bishop(this, 2, 0, false));
        piecesList.add(new Bishop(this, 5, 0, false));
        piecesList.add(new Bishop(this, 2, 7, true));
        piecesList.add(new Bishop(this, 5, 7, true));

        piecesList.add(new Pawn(this, 0, 1, false));
        piecesList.add(new Pawn(this, 1, 1, false));
        piecesList.add(new Pawn(this, 2, 1, false));
        piecesList.add(new Pawn(this, 3, 1, false));
        piecesList.add(new Pawn(this, 4, 1, false));
        piecesList.add(new Pawn(this, 5, 1, false));
        piecesList.add(new Pawn(this, 6, 1, false));
        piecesList.add(new Pawn(this, 7, 1, false));

        piecesList.add(new Pawn(this, 0, 6, true));
        piecesList.add(new Pawn(this, 1, 6, true));
        piecesList.add(new Pawn(this, 2, 6, true));
        piecesList.add(new Pawn(this, 3, 6, true));
        piecesList.add(new Pawn(this, 4, 6, true));
        piecesList.add(new Pawn(this, 5, 6, true));
        piecesList.add(new Pawn(this, 6, 6, true));
        piecesList.add(new Pawn(this, 7, 6, true));

        piecesList.add(new Queen(this, 3, 0, false));
        piecesList.add(new Queen(this, 3, 7, true));

        piecesList.add(new King(this, 4, 0, false));
        piecesList.add(new King(this, 4, 7, true));
    }


    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++){
                g2d.setColor((c + r) % 2 == 0 ? new Color(227,198,181) : new Color(157,105,53));
                g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
            }

        if(selectedPiece != null)
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++){
                    if(isValidMove(new Move(this, selectedPiece, c, r))){
                        g2d.setColor(new Color(65, 194, 65, 100));
                        g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
                    }
                }

        for (Piece piece : piecesList){
            piece.paint(g2d);
        }

    }
}
