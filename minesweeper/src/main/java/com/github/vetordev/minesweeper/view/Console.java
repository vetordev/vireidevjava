package com.github.vetordev.minesweeper.view;

import com.github.vetordev.minesweeper.exception.ExitException;
import com.github.vetordev.minesweeper.exception.ExplosionException;
import com.github.vetordev.minesweeper.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Console {

    private Board board;
    private Scanner scan = new Scanner(System.in);

    public Console(Board board) {
        this.board = board;

        runGame();
    }

    private void runGame() {
        try {
            boolean nextMatch = true;

            while (nextMatch) {
                play();

                System.out.print("Outra partida? (S/n): ");
                String response = scan.nextLine();

                if ("n".equalsIgnoreCase(response)) {
                    nextMatch = false;
                }

                board.restart();
            }
        } catch (ExitException e) {
            System.out.println("Até!");
        } finally {
            scan.close();
        }
    }

    private void play() {
        try {
            while (!board.isSolved()) {
                System.out.println(board.toString());

                String input = getUserInput("Digite (x, y): ");

                Iterator<Integer> xy = Arrays.stream(input.split(","))
                        .map(e -> Integer.parseInt(e.trim()))
                        .iterator();

                input = getUserInput("1 - Abrir ou 2 - (Des)Marcar: ");

                switch (input) {
                    case "1":
                        board.reveal(xy.next(), xy.next());
                        break;
                    case "2": board.flag(xy.next(), xy.next());
                }
            }
            System.out.println(board);
            System.out.println("Você ganhou!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("Você perdeu!");
        }
    }

    private String getUserInput(String text) {
        System.out.print(text);
        String input = scan.nextLine();

        if ("sair".equalsIgnoreCase(input)) {
            throw new ExitException();
        }

        return input;
    }
}
