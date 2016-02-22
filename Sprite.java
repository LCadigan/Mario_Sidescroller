// Sprite class with recttangle

//you need this import to use recttangles!
import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Sprite
{

	//VARIABLE DECLARATION SECTION
	//Here's where you state which variables you are going to use.
	public String name;				//holds the name of the hero
	public int xpos, ypos,dx,dy,width,height, health;				//the y position
	public boolean isAlive;			//a boolean to denote if the hero is alive or dead.
	public Rectangle rect, topRect,bottomRect;			//declare a recttangle variable
 
	// METHOD DEFINITION SECTION

	// Constructor Definition
	// A constructor builds the object when called and sets variable values.
	public Sprite()
	{
	} // constructor


	//This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
	// if you put in a String, an int and an int the program will use this constructor instead of the one above.
	public Sprite( int pXPos, int pYPos)
	{  
		xpos = pXPos;
		ypos = pYPos;
      dx = 3;
      dy = 0;
	   width = 50;
		height = 50;
		isAlive = true;
      health = 20;
		rect = new Rectangle (xpos,ypos,width,height);	//construct a recttangle.  This one uses width and height varibles
      topRect= new Rectangle(xpos,ypos-(height/2),width,height/2);
	   bottomRect=new Rectangle(xpos,ypos+(height/2),width,height/2);
   } // constructor
   public Sprite( int pXPos, int pYPos, int pHeight, int pWidth)
	{ 
		xpos = pXPos;
		ypos = pYPos;
      dx = 5;
      dy = 0;
	   width = pWidth;
		height = pHeight;
		isAlive = true;
      health = 20;
		rect = new Rectangle (xpos,ypos,width,height);	//construct a recttangle.  This one uses width and height varibles
      topRect= new Rectangle(xpos,ypos-(height/2),width,height/2);
	   bottomRect=new Rectangle(xpos,ypos+(height/2),width,height/2);
	} // constructor

	//The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
	public void move()
	{  
     
		xpos = xpos + dx;
      ypos = ypos + dy;
      
      rect = new Rectangle (xpos,ypos,width,height);	//construct a recttangle.  This one uses width and height varibles
      topRect= new Rectangle(xpos,ypos-(height/2),width,height/2);
	   bottomRect=new Rectangle(xpos,ypos+(height/2),width,height/2);      
   }

} //end of the generic object class  definition

