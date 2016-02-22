// Sprite class with recttangle

//you need this import to use recttangles!
import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Mario extends Sprite
{

	//VARIABLE DECLARATION SECTION
	//Here's where you state which variables you are going to use.
	public int filler, sx1,sy1,sx2,sy2,jumpNum,spriteNum;
   public boolean hasShell,directChange;
	// METHOD DEFINITION SECTION

	// Constructor Definition
	// A constructor builds the object when called and sets variable values.
	public Mario(int pXpos,int pYpos)
	{
   //each 20 by 28
   super(pXpos, pYpos);
   directChange=true;
   spriteNum=0;
   jumpNum=0;
   //top left corner of first running sprite
   sx1=113;
   sy1=25;
   //botom right
   sx2=sx1+20;
   sy2=sy1+28;
	} // constructor
   public void animate()
	{  
      if(spriteNum==3){
         spriteNum=0;
      }
      else{
         spriteNum++;
      }
      //adjust sx1 and sx2
      if(hasShell){
      sx1=250;
      sy1=0;
      }
      else{
         sy1=25;
         switch(spriteNum){
            case 0:
               sx1= 113;
               break;
            case 1:
               sx1=139;
               break;
            case 2:
               sx1=160;
               break;
            case 3:
               sx1=185;
               break;
         }
      }
      sx2=sx1+20;
      sy2=sy1+30;
      // if he is moving left, switch the sx1 and sx2
      
   }

	//This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
	// if you put in a String, an int and an int the program will use this constructor instead of the one above.
} //end of the generic object class  definition

