package it.unimol.scacchi.pieces;

import it.unimol.scacchi.gui.Board;

import java.awt.image.BufferedImage;

public class Queen extends Piece{
    public Queen(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "King";

        this.sprite = sheet.getSubimage(1 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        return Math.abs(this.col - col) == Math.abs(this.row - row) || this.col == col || this.row == row;
    }

    public boolean moveColidesWithPiece(int col, int row) {
        if (this.col == col || this.row == row) {
            // Spostamento verticale o orizzontale
            if (this.col == col) {
                // Spostamento orizzontale
                int start = Math.min(this.row, row) + 1;
                int end = Math.max(this.row, row);
                for (int r = start; r < end; r++) {
                    if (board.getPiece(col, r) != null) {
                        return true;
                    }
                }
            } else if (this.row == row) {
                // Spostamento verticale
                int start = Math.min(this.col, col) + 1;
                int end = Math.max(this.col, col);
                for (int c = start; c < end; c++) {
                    if (board.getPiece(c, row) != null) {
                        return true;
                    }
                }
            }
        } else {
            // Spostamento diagonale
            int dx = Math.abs(this.col - col);
            int dy = Math.abs(this.row - row);
            int startX = Math.min(this.col, col) + 1;
            int startY = Math.min(this.row, row) + 1;
            for (int i = 0; i < dx - 1; i++) {
                if (board.getPiece(startX + i, startY + i) != null) {
                    return true;
                }
            }
        }

        return false;
    }
}
