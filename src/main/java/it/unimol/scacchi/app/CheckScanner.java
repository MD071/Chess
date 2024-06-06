package it.unimol.scacchi.app;

import it.unimol.scacchi.gui.Board;
import it.unimol.scacchi.pieces.Piece;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board){
        this.board = board;
    }

    public boolean isKingCheck(Move move){
        Piece king = board.findKing(move.piece.isWhite);

        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if(board.selectedPiece != null && board.selectedPiece.name.equals("King")){
            kingCol = move.newcol;
            kingRow = move.newrow;
        }

        return hitByRook(move.newcol, move.newrow, king, kingCol, kingRow, 0, 1)||
                hitByRook(move.newcol, move.newrow, king, kingCol, kingRow, 1, 0)||
                hitByRook(move.newcol, move.newrow, king, kingCol, kingRow, 0, -1)||
                hitByRook(move.newcol, move.newrow, king, kingCol, kingRow, -1, 0)||

                hitByBishop(move.newcol, move.newrow, king, kingCol, kingRow, -1, -1)||
                hitByRook(move.newcol, move.newrow, king, kingCol, kingRow, 1, -1)||
                hitByRook(move.newcol, move.newrow, king, kingCol, kingRow, 1, 1)||
                hitByRook(move.newcol, move.newrow, king, kingCol, kingRow, -1, 1)||

                hitByKnight(move.newcol, move.newrow, king, kingCol, kingRow)||
                hitByKing(king, kingCol, kingRow)||
                hitByPawn(move.newcol, move.newrow, king, kingCol, kingRow);
    }

    private boolean hitByRook(int col, int row, Piece king, int kingCol, int kingRow, int colValue, int rowValue){
        for (int i = 1; i < 8; i++){
            if(kingCol + (i * colValue) == col && kingRow + (i * rowValue) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol + (i * colValue), kingRow + (i * rowValue));
            if(piece != null && piece != board.selectedPiece){
                if(!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int col, int row, Piece king, int kingCol, int kingRow, int colValue, int rowValue){
        for (int i = 1; i < 8; i++){
            if(kingCol - (i * colValue) == col && kingRow - (i * rowValue) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol - (i * colValue), kingRow - (i * rowValue));
            if(piece != null && piece != board.selectedPiece){
                if(!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByKnight(int col, int row, Piece king, int kingCol, int kingRow){
        return checkKnight(board.getPiece(kingCol - 1, kingRow -2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }

    private boolean hitByKing(Piece king, int kingCol, int kingRow){
        return checkKing(board.getPiece(kingCol - 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow), king);
    }

    private boolean checkKing(Piece p, Piece k){
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    private boolean hitByPawn(int col, int row, Piece king, int kingCol, int kingRow){
        int colorValue = king.isWhite ? -1 : 1;

        return checkPawn(board.getPiece(kingCol - 1, kingRow + colorValue), king, col, row) ||
                checkPawn(board.getPiece(kingCol + 1, kingRow + colorValue), king, col, row);
    }

    private boolean checkPawn(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }
}
