package com.github.vetordev.minesweeper.model;

import com.github.vetordev.minesweeper.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int lines;
    private int columns;
    private int bombs;

    private final List<Cell> cells = new ArrayList<>();

    public Board(int lines, int columns, int bombs) {
        this.lines = lines;
        this.columns = columns;
        this.bombs = bombs;

        generateCells();
        assignNeighbors();
        randomizeMines();
    }

    public void reveal(int line, int column) {
        try {
            cells.parallelStream()
                    .filter( c -> c.getLine() == line && c.getColumn() == column)
                    .findFirst()
                    .ifPresent(Cell::reveal);
        } catch (ExplosionException e) {
            cells.forEach(c -> c.setRevealed(true));
            throw e;
        }
    }

    public void flag(int line, int column) {
        cells.parallelStream()
            .filter( c -> c.getLine() == line && c.getColumn() == column)
            .findFirst()
            .ifPresent(Cell::flag);
    }

    private void generateCells() {
        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                cells.add(new Cell(line, column));
            }
        }
    }

    private void assignNeighbors() {
        for (Cell c1: cells) {
            for (Cell c2: cells) {
                c1.addNeighbor(c2);
            }
        }
    }

    private void randomizeMines() {
        long mines = 0;

        while (mines < bombs) {
            int random = (int) (Math.random() * cells.size());
            cells.get(random).setHasBomb(true);

            mines = cells.stream().filter(Cell::hasBomb).count();
        }
    }

    public boolean isSolved() {
        return cells.stream().allMatch(c -> {
            boolean revealed = !c.hasBomb() && c.isRevealed();
            boolean flagged = c.hasBomb() && c.isFlagged();

            return revealed || flagged;
        });
    }

    public void restart() {
        cells.forEach(Cell::restart);
        randomizeMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int c = 0; c < columns; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }

        sb.append("\n");

        int i = 0;
        for (int l = 0; l < lines; l++) {
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < columns; c++) {
                sb.append(" ");
                sb.append(cells.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
