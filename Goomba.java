// Goomba class with recttangle

//you need this import to use recttangles!
import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Goomba extends Sprite
{

	//VARIABLE DECLARATION SECTION
	//Here's where you state which variables you are going to use.
				//declare a recttangle variable
 
   public int filler,sx1,sy1,sx2,sy2,spriteNum,surfaceTime;
   public boolean isShell,isGravity,onSurface;

	// Constructor Definition
	// A constructor builds the object when called and sets variable values.
	public Goomba()
	{  
      
	} // constructor


	//This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
	// if you put in a String, an int and an int the program will use this constructor instead of the one above.
	public Goomba( int pXPos, int pYPos)
	{  
      super(pXPos, pYPos);
      //xpos: 5,30, 58,83,110,137,164,190
      //approximate by adding 26
      //222=final x
      isShell=false;
      width = 26;
      isGravity=true;
      height=36;
      dx=-3;
      sx1=5;
      sy1=38;
      //botom right
      sx2=sx1+25;
      sy2=sy1+40;   
      spriteNum=0;
      onSurface=false;
      
   } // constructor
   public void animate(){
      if(isShell){
         spriteNum++;
         if(spriteNum<3){
            spriteNum=0;
         }
         sx1=61+20*spriteNum;
         sy1=89;
         width=23;
         height=23;
         sx2=sx1+23;
         sy2=sy1+23;
         rect = new Rectangle (xpos,ypos,width,height);	//construct a recttangle.  This one uses width and height varibles
         topRect= new Rectangle(xpos,ypos-(height/2),width,height/2);
	      bottomRect=new Rectangle(xpos,ypos+(height/2),width,height/2);
	
         
      }
      else{
         spriteNum = spriteNum==7 ? 0:spriteNum+1;
         sx1=5+26*spriteNum;
         sy1=4;
         sx2=sx1+25;
         sy2=sy1+40;
      }
      
   }
   
} //end of the generic object class  definition

