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

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Piece extends Region {
    private final PieceType pieceType;
    int row;
    int column;
    double size = 50;

    public enum PieceType {
        KING
    }

    Piece(PieceType type, int row, int column) {
        this.row = row;
        this.column = column;
        pieceType = type;
        this.setPrefHeight(50);
        this.setPrefWidth(50);
        Image image = null;
        switch (this.pieceType) {
            case KING:
            image = new Image("images/w_king_png_128px.png", 50, 50, false, true);
            default:
            image = new Image("images/w_king_png_128px.png", 50, 50, true, false);
        }

        this.setBorder(
                new Border(
                        new BorderStroke(
                                Color.BLUE,
                                BorderStrokeStyle.SOLID,
                                null,
                                BorderWidths.DEFAULT)
                )
        );

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.SPACE,
                BackgroundRepeat.SPACE,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        this.size,
                        this.size,
                        false,
                        false,
                        true,
                        true)
        );

        Background background = new Background(backgroundImage);
        this.setBackground(background);
    }

    @Override
    protected double computePrefWidth(double height) {
        return super.computePrefWidth(this.size);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(this.size, this.size);
    }
}
