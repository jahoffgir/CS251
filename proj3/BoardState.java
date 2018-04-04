//******************************************************************************
//
// File:    BoardState.java
// Package: ---
// Unit:    Class BoardState
//
// This Java source file is copyright (C) 2018 by Alan Kaminsky. All rights
// reserved. For further information, contact the author, Alan Kaminsky, at
// ark@cs.rit.edu.
//
// This Java source file is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by the Free
// Software Foundation; either version 3 of the License, or (at your option) any
// later version.
//
// This Java source file is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
// details.
//
// You may obtain a copy of the GNU General Public License on the World Wide Web
// at http://www.gnu.org/licenses/gpl.html.
//
//******************************************************************************

import java.util.Arrays;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

/**
 * Class BoardState encapsulates the state of a Tic-Tac-Toe game board.
 *
 * @author  Alan Kaminsky
 * @version 26-Feb-2018
 */
public class BoardState
	{

// Exported data members.

	/**
	 * Number of squares on the board.
	 */
	public static final int N_SQUARES = 6;

	// Hidden data members.
	
	private Mark[][] mark;    // Mark on each square

// Exported constructors.

	/**
	 * Construct a new board state object.
	 */
	public BoardState() {
		mark = new Mark[N_SQUARES][N_SQUARES];
		clear();
	}

// Exported operations.

	/**
	 * Clear this board state object.
	 */
	public void clear() {
		for (int i = 0; i < N_SQUARES; i++) {
			Arrays.fill (mark[i], Mark.BLANK);
		}
		
	}

	public void setQueen(int i, int j, Mark mark) {
		if (this.mark[i][j] != Mark.I) {
			setMark(i, j, mark);
			setVisible(i, j, Mark.I);
		}
	}
	private void setVisibleHelper(int i, int j, Mark mark) {
		int sI = i;
		int sJ = j;
		int aI = i;
		int aJ = j;
		for (int a = 0; a < i; a++) {
			sI--;
			sJ--;
			aI--;
			aJ++;
			if (sI >= 0 && sJ >= 0)
				if (this.mark[sI][sJ] != Mark.Q)
					this.mark[sI][sJ] = mark;
			if (aI >= 0 && aJ < BoardState.N_SQUARES)
				if (this.mark[aI][aJ] != Mark.Q)
					this.mark[aI][aJ] = mark;
		}
		sI = i;
		sJ = j;
		aI = i;
		aJ = j;
		for (int a = 0; a < (BoardState.N_SQUARES - i) ; a++) {
			sI++;
			sJ++;
			aI++;
			aJ--;
			if (sI < BoardState.N_SQUARES && sJ < BoardState.N_SQUARES)
				if (this.mark[sI][sJ] != Mark.Q)
					this.mark[sI][sJ] = mark;
			if (aJ >= 0 && aI < BoardState.N_SQUARES)
				if (this.mark[aI][aJ] != Mark.Q)
					this.mark[aI][aJ] = mark;
		}
	}
	public void setVisible(int i, int j, Mark mark) {
		
		for (int x = 0; x < N_SQUARES; x++) {
			if (x != i)
				this.mark[x][j] = mark;
		}	
		// }
		for (int x = 0; x < N_SQUARES; x++) {
			if (x != j)
				this.mark[i][x] = mark;
		}
		this.setVisibleHelper(i, j, mark);
	}
	/**
	 * Set the mark on the given square.
	 *
	 * @param  i     Square index.
	 * @param  mark  Mark.
	 */
	public void setMark(int i, int j, Mark mark) {
		this.mark[i][j] = mark;
	}

	/**
	 * Get the mark on the given square.
	 *
	 * @param  i  Square index.
	 *
	 * @return  Mark.
	 */
	public Mark getMark(int i, int j) {
		return this.mark[i][j];
	}

	/**
	 * Determine if the marks on the board are a winning combination for the
	 * given player.
	 *
	 * @param  player  Player (X or O).
	 *
	 * @return  Winning combination number if the player wins, &minus;1 if not.
	 */
	public int checkWin() {
		for (int i = 0; i < N_SQUARES; i++)
			for (int j = 0; j < N_SQUARES; j++) {
				if (mark[i][j] == Mark.BLANK)
					return -1;
			}
		return 1;
	}
	public static void main(String [] args) {
		BoardState bs = new BoardState();

		bs.setQueen(0, 0, Mark.Q);

		for (int i = 0; i < N_SQUARES; i++)
			for (int j = 0; j < N_SQUARES; j++) {
				System.out.println(bs.getMark(i, j));
			}
	}
}

