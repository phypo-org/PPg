#!/bin/bash
java -debug  --module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAME --add-reads javafx.graphics=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.charts=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio.common=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED -cp Jixmu.jar:PPgLib.jar org.phypo.Jixmu.Main
