package Maze;

/**
 ######################################################
 #################### by Shu Liu ######################
 ############ shutel at hotmail dot com ###############
 ################### 04/02/2016 #######################
 ######## MazeSearch project @ USC CSCI455 ############
 ######################################################
 */

import java.util.LinkedList;


/**
Maze class

Stores information about a maze and can find a path through the maze
(if there is one).

Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
(parameters to constructor), and the path:
  -- no outer walls given in mazeData -- search assumes there is a virtual 
     border around the maze (i.e., the maze path can't go outside of the maze
     boundaries)
  -- start location for a path is maze coordinate startLoc
  -- exit location is maze coordinate exitLoc
  -- mazeData input is a 2D array of booleans, where true means there is a wall
     at that location, and false means there isn't (see public FREE / WALL 
     constants below) 
  -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
  -- only travel in 4 compass directions (no diagonal paths)
  -- can't travel through walls
*/

public class Maze {

public static final boolean FREE = false;
public static final boolean WALL = true;
private boolean[][] mazeData;
private boolean[][] visitedCheck;
private MazeCoord startLoc;
private MazeCoord endLoc;
private LinkedList<MazeCoord> pathList = new LinkedList<MazeCoord>();
private LinkedList<MazeCoord> pathList2 = new LinkedList<MazeCoord>(); //helper to clean pathList after using


/**
   Constructs a maze.
   @param mazeData the maze to search.  See general Maze comments for what
   goes in this array.
   @param startLoc the location in maze to start the search (not necessarily on an edge)
   @param endLoc the "exit" location of the maze (not necessarily on an edge)
   PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
      and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

 */
public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
{
	this.mazeData = mazeData;
	this.visitedCheck = new boolean[mazeData.length][mazeData[0].length];
	this.startLoc = startLoc;
	this.endLoc = endLoc;

}


/**
Returns the number of rows in the maze
@return number of rows
*/
public int numRows() {
	int numRows = mazeData.length;
	return numRows; 
}


/**
Returns the number of columns in the maze
@return number of columns
*/   
public int numCols() {
	int numCols = mazeData[0].length;
	return numCols; 
} 


/**
   Returns true iff there is a wall at this location
   @param loc the location in maze coordinates
   @return whether there is a wall here
   PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
*/
public boolean hasWallAt(MazeCoord loc) {
	return mazeData[loc.getRow()][loc.getCol()];
}


/**
   Returns the entry location of this maze.
 */
public MazeCoord getEntryLoc() {
   return startLoc;  
}


/**
Returns the exit location of this maze.
*/
public MazeCoord getExitLoc() {
   return endLoc; 
}


/**
   Returns the path through the maze. First element is starting location, and
   last element is exit location.  If there was not path, or if this is called
   before search, returns empty list.

   @ return the maze path
 */
public LinkedList<MazeCoord> getPath() {
	pathList2 = pathList;
	pathList = new LinkedList<MazeCoord>();
	return pathList2;
}


/**
   Find a path through the maze if there is one.  Client can access the
   path found via getPath method.
   @return whether path was found.
 */
public boolean search() {
	this.visitedCheck = new boolean[mazeData.length][mazeData[0].length];
	boolean searchResult = searchHelper(startLoc.getRow(), startLoc.getCol());
	return searchResult;
}
	
/**
 * Recursively search the path for this maze
 * @param currentLoc the current location of a point in the path
 * @return the location of the end point
 */

private boolean searchHelper(int locRow, int locCol){
	MazeCoord currentLoc = new MazeCoord(locRow, locCol);
	//Base case
	if (hasWallAt(currentLoc)){              //There is a wall
		return false;	
	}

	else if (visited(currentLoc)){          //This point has been visited
		return false;
	}

	else if(currentLoc.equals(endLoc)){     //This point is the exit
		pathList.addLast(endLoc);
		return true;
	}
	// Recursive Case
	else {
		visitedCheck[locRow][locCol] = true;
		if(locCol+1 < numCols() && searchHelper(locRow, locCol+1)){
			pathList.addFirst(currentLoc);
			return true;
		}
		else if(locRow+1 < numRows() && searchHelper(locRow+1, locCol)){
			pathList.addFirst(currentLoc);
			return true;
		}
		else if(locCol-1 >= 0 && searchHelper(locRow, locCol-1)){
			pathList.addFirst(currentLoc);
			return true;
		}
		else if(locRow-1 >= 0 && searchHelper(locRow-1, locCol)){
			pathList.addFirst(currentLoc);
			return true;
		}
		else
			return false;
	}
}

//Check whether the point has been visited
private boolean visited(MazeCoord testLoc){
	return visitedCheck[testLoc.getRow()][testLoc.getCol()];
}

}