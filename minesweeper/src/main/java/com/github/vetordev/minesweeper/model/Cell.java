package com.github.vetordev.minesweeper.model;

import com.github.vetordev.minesweeper.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

class Cell {
    private boolean isRevealed;
    private boolean hasBomb;
    private boolean isFlagged;

    private final int line;
    private final int column;

    private List<Cell> neighbors = new ArrayList<>();

    Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }

    boolean hasBomb() {
        return hasBomb;
    }

    void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    boolean isFlagged() {
        return isFlagged;
    }

    void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    int getLine() {
        return line;
    }

    int getColumn() {
        return column;
    }

    boolean isRevealed() {
        return isRevealed;
    }

    void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    List<Cell> getNeighbors() {
        return neighbors;
    }

    void setNeighbors(List<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    boolean addNeighbor(Cell neighbor) {
        int delta = Math.abs(line - neighbor.line) + Math.abs(column - neighbor.column);
        boolean diagonal = line != neighbor.line && column != neighbor.column;

        if (delta == 1 && !diagonal) {
            neighbors.add(neighbor);
            return true;
        }
        if (delta == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        }

        return false;
    }

    void flag() {
        if (!isRevealed) {
            isFlagged = !isFlagged;
        }
    }

    boolean reveal() {
        if (!isRevealed && !isFlagged) {
            isRevealed = true;

            if (hasBomb) {
                throw new ExplosionException();
            }

            if (neighborsAreSafe()) {
                neighbors.forEach(Cell::reveal);
            }

            return true;
        }

        return false;
    }

    boolean neighborsAreSafe() {
        return neighbors.stream().noneMatch(Cell::hasBomb);
    }

    long neighborsWithBombCount() {
        return neighbors.stream().filter(n -> n.hasBomb).count();
    }

    void restart() {
        isRevealed = false;
        hasBomb = false;
        isFlagged = false;
    }

    public String toString() {
        if (isFlagged) return "x";
        if (isRevealed && hasBomb) return "*";
        if (isRevealed && neighborsWithBombCount() > 0) return Long.toString(neighborsWithBombCount());
        if (isRevealed) return " ";
        return "?";
    }
}
