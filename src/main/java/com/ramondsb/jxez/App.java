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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final int BOARD_SIZE = 600;
        Board board = new Board(BOARD_SIZE);
        Scene scene = new Scene(board,
                BOARD_SIZE,
                BOARD_SIZE,
                Color.BLACK);

        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(1.0));
        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(1.0));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getGreeting() {
        return "Hello world.";
    }
}
