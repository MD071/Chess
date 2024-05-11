package it.unimol.scacchi.app;

import it.unimol.scacchi.gui.Board;
import it.unimol.scacchi.pieces.Piece;

public class Move {

    public int oldcol;
    public int oldrow;
    public int newcol;
    public int newrow;

    public Piece piece;
    public Piece capture;

    public Move(Board board, Piece piece, int newcol, int newrow){
        this.oldcol = piece.col;
        this.oldrow = piece.row;
        this.newcol = newcol;
        this.newrow = newrow;

        this.piece = piece;
        this.capture = board.getPiece(newcol, newrow);
    }
}
