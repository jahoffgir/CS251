//******************************************************************************
//
// File:    TicTacToeModel.java
// Package: ---
// Unit:    Class TicTacToeModel
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

/**
 * Class TicTacToeModel provides the application logic for the Tic-Tac-Toe Game.
 *
 * @author  Alan Kaminsky
 * @version 26-Feb-2018
 */
public class SixQueensModel
	implements ViewListener
	{

// Hidden data members.

	private String name1;
	private String name2;
	private ModelListener view1;
	private ModelListener view2;
	private ModelListener turn;
	private boolean isFinished;
	private BoardState board = new BoardState();

// Exported constructors.

	/**
	 * Construct a new Tic-Tac-Toe model object.
	 */
	public SixQueensModel(){}

// Exported operations.

	/**
	 * Report that a player joined the game.
	 *
	 * @param  view  View object making the report.
	 * @param  name  Player's name.
	 */
	public synchronized void join
		(ModelListener view,
		 String name)
		{
		if (name1 == null)
			{
			name1 = name;
			view1 = view;
			view1.waitingForPartner();
			}
		else
			{
			name2 = name;
			view2 = view;
			doNewGame();
			}
		}

	/**
	 * Report that a square was chosen.
	 *
	 * @param  view  View object making the report.
	 * @param  i     Square index.
	 */
	public synchronized void squareChosen
		(ModelListener view,
		 int i, int j)
		{
		if (view != turn)
			return;
		else if (view == view1)
			setQueen(view1, i, j);
		else
			setQueen(view2, i, j);
		}

	/**
	 * Report that a new game was requested.
	 *
	 * @param  view  View object making the report.
	 */
	public synchronized void newGame
		(ModelListener view)
		{
		if (name2 != null)
			doNewGame();
		}

	/**
	 * Report that the player quit.
	 *
	 * @param  view  View object making the report.
	 */
	public synchronized void quit
		(ModelListener view)
		{
		if (view1 != null)
			view1.quit();
		if (view2 != null)
			view2.quit();
		turn = null;
		isFinished = true;
		}

	/**
	 * Determine whether this game session is finished.
	 *
	 * @return  True if finished, false if not.
	 */
	public synchronized boolean isFinished()
		{
		return isFinished;
		}

// Hidden operations.

	/**
	 * Start a new game.
	 */
	private void doNewGame()
		{
		// Clear the board and inform the players.
		board.clear();
		view1.newGame();
		view2.newGame();

		// Player 1 gets the first turn.
		turn = view1;
		view1.yourTurn();
		view2.otherTurn (name1);
		}

	public void setVisible(ModelListener curr, int i, int j) {
		board.setVisible(i, j, Mark.I);
		for (int a = 0; a < BoardState.N_SQUARES; a++) {
			for (int b = 0; b < BoardState.N_SQUARES; b++) {
				if (board.getMark(a, b) == Mark.I) {
					view1.setVisible(a, b, false);
					view2.setVisible(a, b, false);
				}
			}	
		}	
	}

	/**
	 * Set a mark on the board and switch turns.
	 *
	 * @param  curr  View object whose turn it is.
	 * @param  i     Square index.
	 * @param  j     Square index.
	 */
	public void setQueen(ModelListener curr,int i, int j) {
		board.setQueen(i, j, Mark.Q);
		view1.setQueen(i, j, Mark.Q);
		view2.setQueen(i, j, Mark.Q);
		this.setVisible(curr, i, j);
		int win = board.checkWin();
		// Update game state and inform the players.
		if (win != -1) {
			// Current player wins.
			turn = null;
			if (curr == view1){
				view1.youWin();
				view2.otherWin (name1);
			} else {
				view1.otherWin (name2);
				view2.youWin();
			}
		} else {
			// No win or draw yet.
			if (curr == view1) {
				turn = view2;
				view1.otherTurn (name2);
				view2.yourTurn();
			} else {
				turn = view1;
				view1.yourTurn();
				view2.otherTurn (name1);
			}
		}
	}

}

