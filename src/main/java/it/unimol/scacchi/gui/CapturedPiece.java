package it.unimol.scacchi.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CapturedPiece extends JPanel {
    private List<Image> capturedPiece;

    public CapturedPiece() {
        capturedPiece = new ArrayList<>();
        setLayout(new FlowLayout());
    }

    public void addPiece(Image pieceImage) {
        capturedPiece.add(pieceImage);
        repaint();
    }
}