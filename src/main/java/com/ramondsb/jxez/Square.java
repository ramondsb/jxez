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

public class Square extends Region {

    private final ColorType colorType;
    private final float size;

    public enum ColorType {
        DARK,
        LIGHT
    };

    public Square(float size, ColorType type) {
        super();
        this.colorType = type;
        this.size = size;

        Image image = null;
        if (this.colorType == ColorType.DARK) {
            image = new Image("images/dark_square_128.png");
        } else {
            image = new Image("images/light_square_128.png");
        }

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );

        Background background = new Background(backgroundImage);

        this.setBackground(background);
        this.setMinWidth(this.size);
        this.setMinHeight(this.size);
    }

    @Override
    protected double computePrefHeight(double width) {
        return super.computePrefHeight(this.size);
    }

    @Override
    protected double computePrefWidth(double height) {
        return super.computePrefWidth(this.size);
    }
}
