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

public class Board extends Region {
    private float squareSize;
    float boardSize;
    private final int NUMBER_OF_ROWS = 8;
    private final int NUMBER_OF_FILES = 8;
    Group container = null;
    private final boolean isDebugMode = false;

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
                        System.out.println("Square with id: " +  square.getId());
                    }
                });

                container.getChildren().add(square);
            }
        }

        this.getChildren().add(container);
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
                ((Square)(node)).size = this.squareSize;
                node.autosize();
                String id = node.getId();
                int x = Integer.parseInt(id.split("-")[0]);
                int y = Integer.parseInt(id.split("-")[1]);
                node.relocate(x * this.squareSize, y * this.squareSize);
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
