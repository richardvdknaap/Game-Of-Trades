package io.gameoftrades.ui;

import io.gameoftrades.student44.HandelaarImpl;

/**
 * Toont de visuele gebruikersinterface.
 * 
 * Let op: dit werkt alleen als je de WereldLader hebt geimplementeerd (Anders krijg je een NullPointerException).
 */
public class StudentUI {

	public static void main(String[] args) {
		MainGui.builder()
		       .add(new HandelaarImpl())
		       .toon(TileSet.T32,"/kaarten/testcases/eiland-kaart.txt");

	}
	
}
