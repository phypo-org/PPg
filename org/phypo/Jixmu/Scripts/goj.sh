#!/bin/bash
cd /media/phipo/Data/Exec/Jixmu
java -Dprism.order=sw --module-path  ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml,javafx.media -cp Jixmu.jar:PPgLib.jar    org.phypo.Jixmu.Main  > toto.txt
