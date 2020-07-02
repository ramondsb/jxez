/*
 * Copyright 2020 Ramon Brito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ramondsb.jxez;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import static com.ramondsb.jxez.Game.Color.BLACK;
import static com.ramondsb.jxez.Game.Color.WHITE;
import static com.ramondsb.jxez.Piece.PieceType.*;


public class Board extends Region {
    private float squareSize;
    float boardSize;
    private final int NUMBER_OF_ROWS = 8;
    private final int NUMBER_OF_FILES = 8;
    Group container = null;
    private final boolean isDebugMode = false;
    public Piece selectedPiece = null;

    public Board(float boardSize) {

        this.container = new Group();

        if (isDebugMode) {
            this.setBorder(
                new Border(
                    new BorderStroke(
                        Color.WHITE,
                        BorderStrokeStyle.SOLID,
                        null,
                        BorderWidths.DEFAULT
                    )
                )
            );
        }

        this.boardSize = boardSize;
        this.squareSize = this.boardSize / NUMBER_OF_FILES;

        // Set squares
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_FILES; j++) {
                Square.ColorType type = (i % 2 == j % 2) ? Square.ColorType.LIGHT : Square.ColorType.DARK;
                Square square = new Square(this.squareSize, type);
                square.setLayoutX(this.squareSize * i);
                square.setLayoutY(this.squareSize * j);
                square.setId(makeId(i, j));
                square.setOnMouseClicked(event -> {
                    if (MouseEvent.MOUSE_CLICKED == event.getEventType() ) {
                        System.out.println("Square with id: " + square.getId());
                        int x = Integer.parseInt(square.getId().split("-")[0]);
                        int y = Integer.parseInt(square.getId().split("-")[1]);
                        if (selectedPiece != null) {
                            this.setPieceAtPosition(this.selectedPiece, y, x);
                        }
                    }
                });

               container.getChildren().add(square);
            }
        }

        this.setupPieces();
        this.getChildren().add(container);
    }

    private void addPiece(Piece.PieceType type, Game.Color color, int row, int column) {
        Piece piece = new Piece(type, color, row, column);
        this.container.getChildren().add(piece);
        piece.setOnMouseClicked(event -> {
            if (MouseEvent.MOUSE_CLICKED == event.getEventType() ) {
                this.selectedPiece = piece;
            }
        });
        this.setPieceAtPosition(piece, row, column);
    }

    /**
     * Place all pieces at initial position
     */
    private void setupPieces() {
        // Paws
        for (int i =0; i < 8; i++) {
            addPiece(PAWN, BLACK, 1, i);
            addPiece(PAWN, WHITE, 6, i);
        }

        // Rooks
        addPiece(ROOK, WHITE, 7, 0);
        addPiece(ROOK, WHITE, 7, 7);

        addPiece(ROOK, BLACK, 0, 0);
        addPiece(ROOK, BLACK, 0, 7);

        // Bishops
        addPiece(BISHOP, WHITE, 7, 2);
        addPiece(BISHOP, WHITE, 7, 5);

        addPiece(BISHOP, BLACK, 0, 2);
        addPiece(BISHOP, BLACK, 0, 5);

        // Knights
        addPiece(KNIGHT, WHITE, 7, 1);
        addPiece(KNIGHT, WHITE, 7, 6);

        addPiece(KNIGHT, BLACK, 0, 1);
        addPiece(KNIGHT, BLACK, 0, 6);

        // Queens
        addPiece(QUEEN, WHITE, 7, 3);
        addPiece(QUEEN, BLACK, 0, 3);

        // Kings
        addPiece(KING, WHITE, 7, 4);
        addPiece(KING, BLACK, 0, 4);

    }

    private void setPieceAtPosition(Piece piece, int row, int column) {
        double baseX = column * this.squareSize;
        double baseY = row * this.squareSize;
        double newX = baseX + (this.squareSize / 2) - (piece.size / 2);
        double newY = baseY + (this.squareSize / 2) - (piece.size / 2);
        piece.row = row;
        piece.column = column;
        piece.setLayoutX(newX);
        piece.setLayoutY(newY);
    }

    @Override
    protected double computePrefHeight(double width) {
        return super.computePrefHeight(this.boardSize);
    }

    @Override
    protected double computePrefWidth(double height) {
        return super.computePrefWidth(this.boardSize);
    }

    private String makeId(int x, int y) {
        return x + "-" + y;
    }

    @Override
    protected void layoutChildren() {
        ObservableList<Node> children = this.container.getChildren();
        for (int i=0, max= children.size(); i<max; i++) {
            final Node node = children.get(i);

            if (node.isResizable() && node.isManaged()) {
                if (node instanceof Square) {
                    ((Square) (node)).size = this.squareSize;
                    node.autosize();
                    String id = node.getId();
                    int x = Integer.parseInt(id.split("-")[0]);
                    int y = Integer.parseInt(id.split("-")[1]);
                    node.relocate(x * this.squareSize, y * this.squareSize);
                } else if (node instanceof Piece) {
                    Piece p = (Piece)node;
                    p.size = this.squareSize * 0.75;
                    setPieceAtPosition(p, p.row, p.column);
                }
            }
        }
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        this.boardSize = (float)width;
        this.squareSize = this.boardSize / 8;
    }
}
