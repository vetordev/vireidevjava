package com.github.vetordev.minesweeper.model;

import com.github.vetordev.minesweeper.exception.ExplosionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell cell;

    @BeforeEach
    void initCell() {
        cell = new Cell(3, 3);
    }
    @Test
    void testAddLeftNeighbor() {
        Cell leftNeighbor = new Cell(3, 2);
        boolean result = cell.addNeighbor(leftNeighbor);

       assertTrue(result);
    }

    @Test
    void testAddDiagonalNeighbor() {
        Cell diagonalNeighbor = new Cell(4, 2);
        boolean result = cell.addNeighbor(diagonalNeighbor);

        assertTrue(result);
    }

    @Test
    void testAddInvalidNeighbor() {
        Cell diagonalNeighbor = new Cell(1, 2);
        boolean result = cell.addNeighbor(diagonalNeighbor);

        assertFalse(result);
    }

    @Test
    void testRevealWithBomb() {
        cell.setHasBomb(true);
        assertThrows(ExplosionException.class, () -> {
            cell.reveal();
        });
    }
}