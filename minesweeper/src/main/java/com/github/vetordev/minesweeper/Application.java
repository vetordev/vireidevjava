package com.github.vetordev.minesweeper;

import com.github.vetordev.minesweeper.model.Board;
import com.github.vetordev.minesweeper.view.Console;

public class Application {
    public static void main(String[] args) {

        Board board = new Board(6,6,3);

        new Console(board);
    }
}
