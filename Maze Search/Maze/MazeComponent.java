package Maze;

/**
 ######################################################
 #################### by Shu Liu ######################
 ############ shutel at hotmail dot com ###############
 ################### 04/02/2016 #######################
 ######## MazeSearch project @ USC CSCI455 ############
 ######################################################
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JComponent;



/**
MazeComponent class

A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
private Maze maze;

private static final int START_X = 10; // where to start drawing maze in frame
private static final int START_Y = 10;
private static final int BOX_WIDTH = 20;  // width and height of one maze unit
private static final int BOX_HEIGHT = 20;
private static final int INSET = 2;  
                 // how much smaller on each side to make entry/exit inner box


/**
   Constructs the component.
   @param maze   the maze to display
*/
public MazeComponent(Maze maze) 
{   
   this.maze = maze;
}


/**
  Draws the current state of maze including the path through it if one has
  been found.
  @param g the graphics context
*/
public void paintComponent(Graphics g)
{
	Graphics2D g2 = (Graphics2D) g;
	paintBorder(g2);
	paintWalls(g2);
	paintEntryExit(g2);
	paintPath(g2);

}

//Draw the border of the maze
private void paintBorder(Graphics2D g2){
	
	Line2D.Double segmentUp = new Line2D.Double(START_X, START_Y, START_X+BOX_HEIGHT*maze.numCols(), START_Y);
	Line2D.Double segmentLeft = new Line2D.Double(START_X, START_Y, START_X, START_Y+BOX_WIDTH*maze.numRows());
	Line2D.Double segmentDown = new Line2D.Double(START_X, START_Y+BOX_WIDTH*maze.numRows(), START_X+BOX_HEIGHT*maze.numCols(), START_Y+BOX_WIDTH*maze.numRows());
	Line2D.Double segmentRight = new Line2D.Double(START_X+BOX_HEIGHT*maze.numCols(), START_Y, START_X+BOX_HEIGHT*maze.numCols(), START_Y+BOX_WIDTH*maze.numRows());
	g2.draw(segmentUp);
	g2.draw(segmentLeft);
	g2.draw(segmentDown);
	g2.draw(segmentRight);
}

//Draw the walls and free ways of the maze
private void paintWalls(Graphics2D g2){
	for(int i=0; i<maze.numRows(); i++){
		for(int j=0; j<maze.numCols(); j++){
			if(maze.hasWallAt(new MazeCoord(i, j))){
			Rectangle unit = new Rectangle(START_X+j*BOX_WIDTH, START_Y+i*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
			g2.draw(unit);
			g2.fill(unit);
			}
		}
	}
}

//Draw the entry and exit
private void paintEntryExit(Graphics2D g2){
	Color ENTRY_COLOR = Color.green;
	MazeCoord entryCoord = maze.getEntryLoc();
	MazeCoord exitCoord = maze.getExitLoc();
	Rectangle entry = new Rectangle(START_X+entryCoord.getCol()*BOX_WIDTH+INSET/2, START_Y+entryCoord.getRow()*BOX_HEIGHT+INSET/2, BOX_WIDTH-INSET, BOX_HEIGHT-INSET);
	g2.setColor(ENTRY_COLOR);
	g2.draw(entry);
	g2.fill(entry);
	Rectangle exit = new Rectangle(START_X+exitCoord.getCol()*BOX_WIDTH+INSET/2, START_Y+exitCoord.getRow()*BOX_HEIGHT+INSET/2, BOX_WIDTH-INSET, BOX_HEIGHT-INSET);
	g2.setColor(ENTRY_COLOR);
	g2.draw(exit);
	g2.fill(exit);
}

//Draw the path
private void paintPath(Graphics2D g2){
	Color EXIT_COLOR = Color.blue;
	g2.setColor(EXIT_COLOR);
	
	LinkedList<MazeCoord> pathList = maze.getPath();
	ListIterator<MazeCoord> iter = pathList.listIterator();
	
  	if(iter.hasNext()){
	MazeCoord startCoord = iter.next();
	while(iter.hasNext()){
		MazeCoord endCoord = iter.next();
		Line2D.Double pathStep = new Line2D.Double(START_X+BOX_WIDTH*startCoord.getCol()+BOX_WIDTH/2, START_Y+BOX_HEIGHT*startCoord.getRow()+BOX_HEIGHT/2, START_X+BOX_WIDTH*endCoord.getCol()+BOX_WIDTH/2, START_Y+BOX_HEIGHT*endCoord.getRow()+BOX_HEIGHT/2);
    		g2.draw(pathStep);
    		startCoord = endCoord;
		
	}
	}
	}
}




