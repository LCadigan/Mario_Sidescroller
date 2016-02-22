// Applet with Intersections Example
// This Applet has two objects that have rectangles.
// A checkIntersections() method checks for rectangle intersections.
// When the car hits the robot (Mario), it "dies".

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Math.*;


public class sideScroller extends Applet implements Runnable, KeyListener,MouseMotionListener, MouseListener
{  


   //notes: for some reason, myMario seems to revert to a sprite when first reaching globalXpos
   //platform stuff
   public int platHeight, platWidth,brickNum;
   public boolean isBrick,isGoomba,isEraser;
   public Rectangle startRect,goombaRect,brickRect;
   //graphic stuff
   public Graphics bufferGraphics;
   public Image offscreen;
   public Image marioBrickPic,MarioSprite,GoombaSprite, startButton;
   //game stuff
   public int globalXpos,xWidth,yWidth;
   public boolean isGravity,isJump,gameStart, worldConstructed;
   //sprite stuff
   public Mario myMario;
   public Sprite plat1;
   public Goomba testGoomba;
   public ArrayList<Sprite>Platforms = new ArrayList<>();
   public ArrayList<Goomba>GoombaList=new ArrayList<>();
   Thread thread;
   //Set up the world by initializing varibles, constructing objects and setting Applet characteristics.
   public void init()
   {  worldConstructed=false;
      gameStart=false;
      xWidth=700;
      yWidth=600;
      setSize(xWidth,yWidth);
   	//initialize variables
      //graphics
      platHeight=30;
      platWidth=30;
      brickNum=1;
      isBrick=true;
      isGoomba=false;
      isGravity=true;
      isEraser=false;
      plat1 = new Sprite(100,150,platHeight,platWidth);
      Platforms.add(plat1);
      myMario = new Mario(100,100);
      GoombaSprite = getImage(getDocumentBase(),"GoombaSprite.jpg");
      MarioSprite = getImage(getDocumentBase(),"MarioSprite.gif");
      marioBrickPic = getImage(getDocumentBase(),"marioBrick.png");
      startButton = getImage(getDocumentBase(),"StartButton.jpg");
      //INIT METHOD
      //double buffering
      offscreen = createImage(800,600);
      //create a new image that's the size of the applet
      bufferGraphics = offscreen.getGraphics();
      //set bufferGraphics to the graphics of the offscreen image
      //construct thread and start it
      thread = new Thread(this); 		//constructs a new thread
      thread.start();				//starts the thread
      addKeyListener(this);
      addMouseMotionListener(this);
      addMouseListener(this);
      globalXpos=0;
      
   }//init()

   public String whatDirection(Sprite Player, Sprite Object){
      if(Object.topRect.intersects(Player.bottomRect)){
         return "above";   
      }
      
      else if(Object.bottomRect.intersects(Player.rect)){
         return "below";
      }
      //stops from side
      else if(Object.rect.intersects(Player.rect)){
         return"side";
      }
      else{
         return  "null";
      }
   }
	//move the objects on the screen
   public void moveEverything()
   {  
      
      if(myMario.xpos>xWidth-100){
         globalXpos+=myMario.dx;
      }
      if(myMario.xpos<0){
         myMario.xpos=0;
      }
      //System.out.println(myMario.dy);
      myMario.dy = isGravity ? myMario.dy+1 : myMario.dy;
      myMario.move();
      
         myMario.animate(); 
      
      for(int i=0; i<GoombaList.size();i++){
         GoombaList.get(i).animate();
         GoombaList.get(i).move();
         GoombaList.get(i).dy+=1;
         
      }
      
   }
   
   
 
   public void checkIntersections(){
      if(gameStart==false){
         for(int i = 0; i<Platforms.size();i++){
            for(int k=0; k<GoombaList.size();k++){
               if(Platforms.get(i).rect.intersects(GoombaList.get(k).rect)){
                  GoombaList.get(k).isAlive=false;
               }
            }
         }
      }
      //for all of "Platform"
      for(int i = 0; i<Platforms.size();i++){
      //stops from falling
         if(whatDirection(myMario,Platforms.get(i))!="null"){
            //System.out.println(whatDirection(myMario,Platforms.get(i)));
         }
         switch(whatDirection(myMario,Platforms.get(i))){
            case "above":
               {  isJump=false;
                  myMario.dy=0;
                  isGravity=false;
                  myMario.ypos=Platforms.get(i).ypos-50;
                  myMario.jumpNum=0;
                  break;}
            case "below":
               {  isJump=false;
                  myMario.dy=-myMario.dy;
                  break;}
            case "side":
               {  isJump=false;
                  myMario.xpos-=myMario.dx;
                  myMario.dx=-myMario.dx;
                  break;}
            default:
               {  
                  isGravity=true;
                  break;}
         }
        
      }
      //for goombas and platforms
      for (int k=0;k<GoombaList.size();k++){
         for(int i = 0; i<Platforms.size();i++){
         //stops from falling
            switch(whatDirection(GoombaList.get(k),Platforms.get(i))){
               case "above":
                  {  GoombaList.get(k).onSurface=true;
                     GoombaList.get(k).dy=0;
                     GoombaList.get(k).isGravity=false;
                     GoombaList.get(k).ypos=Platforms.get(i).ypos-50;
                     break;}
               case "below":
                  {
                     GoombaList.get(k).dy=-GoombaList.get(k).dy;
                     break;}
               case "side":
                  {
                     GoombaList.get(k).xpos-=GoombaList.get(k).dx;
                     GoombaList.get(k).dx=-GoombaList.get(k).dx;
                     break;}
               default:
                  {  if(GoombaList.get(k).onSurface){
                        GoombaList.get(k).dx-=GoombaList.get(k).dx;
                     }
                     isGravity=true;
                     break;}
            }
         }
      }
      
      //for Mario and Goombas
      for (int k=0;k<GoombaList.size();k++){
         switch(whatDirection(myMario,GoombaList.get(k))){
               case "above":
                  {
                     GoombaList.get(k).isShell=true;
                     myMario.dy=-20;
                     break;
                  }
               case "below":
                  {
                     myMario.isAlive=false;
                     break;}
               case "side":
                  {
                     myMario.isAlive=false;
                     break;}
               default:
                  {break;}
        }

      }
      
   }
   public void systemOut(){
      for(int i =0; i < Platforms.size(); i++){
         System.out.println("plat1= new Sprite("+Platforms.get(i).xpos+","+Platforms.get(i).ypos+","+platWidth+","+platHeight+");");
         System.out.println("Platforms.add(plat1);");
      }         
      
      for(int i = 0; i<GoombaList.size();i++){
         System.out.println("testGoomba = new Goomba("+GoombaList.get(i).xpos+","+GoombaList.get(i).ypos+");");
         System.out.println("GoombaList.add(testGoomba);");
      }
      
   }
   public void update(Graphics g)
   {
      
      paint(g);
   
   }
   public void constructWorld(){
   
         //NEVER CHANGE****************************************************
      System.out.println("ConstructWorld");
      worldConstructed=true;
   //NEVER CHANGE****************************************************
   }
   
	//Paint handles all the graphics on the screen
   public void paint(Graphics g)
   {  
   
      bufferGraphics.clearRect(0,0,800,600);
      
      
      if(gameStart==false){
         //draw boxes and rectangles
         bufferGraphics.drawImage(startButton,150,250,50,50,this);
         startRect=new Rectangle(150,250,50,50);
         
         bufferGraphics.drawImage(GoombaSprite,150,300,200,350,7,38,32,78,this);
         goombaRect=new Rectangle(150,300,50,50);
         
         bufferGraphics.drawImage(marioBrickPic,150,350,50,50,this);
         brickRect=new Rectangle(150,350,50,50);
         
        
      }
      for(int i=0; i<GoombaList.size();i++){
         if(GoombaList.get(i).isAlive==true){
            if(GoombaList.get(i).dx<0){
               bufferGraphics.drawImage(GoombaSprite,GoombaList.get(i).xpos-globalXpos,GoombaList.get(i).ypos,GoombaList.get(i).xpos+GoombaList.get(i).width-globalXpos,GoombaList.get(i).ypos+GoombaList.get(i).height,GoombaList.get(i).sx1,GoombaList.get(i).sy1,GoombaList.get(i).sx2,GoombaList.get(i).sy2,this);
            }
            //rotate image if Goomba moving left
            else{
               bufferGraphics.drawImage(GoombaSprite,GoombaList.get(i).xpos+GoombaList.get(i).width-globalXpos,GoombaList.get(i).ypos,GoombaList.get(i).xpos-globalXpos,GoombaList.get(i).ypos+GoombaList.get(i).height,GoombaList.get(i).sx1,GoombaList.get(i).sy1,GoombaList.get(i).sx2,GoombaList.get(i).sy2,this);
            }
         }
      }
      
      //IMPORTANT CHECKS
      System.out.println("myMario.dx = "+myMario.dx+", myMario.dy ="+myMario.dy);
      System.out.println("globalXpos = "+globalXpos);
     // g.fillRect(0,0,1000,1000);
      if(myMario.isAlive)//myMario.isAlive==true)		//check if the robot is alive.  Draw it if it is.
      {  
         // healthBox: g.drawRect(myMario.xpos-myMario.health*5,myMario.ypos-20,10*myMario.health,10); 
         //bufferGraphics.drawImage(MarioPic, myMario.xpos, myMario.ypos, 50, 50, this);
         
         if(myMario.dx>0){
            bufferGraphics.drawImage(MarioSprite,myMario.xpos-globalXpos,myMario.ypos,myMario.xpos+50-globalXpos,myMario.ypos+50,myMario.sx1,myMario.sy1,myMario.sx2,myMario.sy2,this);
         }
         else{
            bufferGraphics.drawImage(MarioSprite,myMario.xpos+50-globalXpos,myMario.ypos,myMario.xpos-globalXpos,myMario.ypos+50,myMario.sx1,myMario.sy1,myMario.sx2,myMario.sy2,this);
         }
      }
      for(int i =0; i<Platforms.size();i++){
         //g.drawRect(Platforms.get(i).xpos-globalXpos,Platforms.get(i).ypos,platHeight,platWidth);
         bufferGraphics.drawImage(marioBrickPic,Platforms.get(i).xpos-globalXpos,Platforms.get(i).ypos,platHeight,platWidth,this);
      }       
      
      g.drawImage(offscreen,0,0,this);
   }// paint()

   //This is the thread. It's what will run after the init method is done running.
   public void run() { 
   	// this thread loop forever and runs the paint method and then sleeps.
      while(true)
      {  
         
         if(gameStart){
            moveEverything();		//move the objects in the game
         }
         if(worldConstructed==false){
            constructWorld();
         }
         checkIntersections();
         
         repaint();				//call paint and redraw
         			//tell the program to sleep for a while.
         try {
            thread.sleep(30);
         }
         catch (Exception e){ }
      }//while
   }// run()
   
//****************************************************************************************
// This section contains 3 required methods to get keyboard input
//**************************a**************************************************************

   //This runs whenever a key is pressed
   public void keyPressed( KeyEvent event )
   {
      String keyin;       
      keyin = ""+event.getKeyText( event.getKeyCode()); //getKeyCode returns the key code number
      //System.out.println("Key pressed "+keyin);
      if(myMario.jumpNum<2){
         if(keyin.equals("W")){
            isJump=true;
            myMario.dy=-20;
            ++myMario.jumpNum;
         }
      }      
      //if you press the D make the hero go right by making its right boolean variable true.
      if(myMario.dy==0){
         if(keyin.equals("D"))
         {
            myMario.dx=5;          
         }
         else if(keyin.equals("A")){
            myMario.dx=-5;
         }
         else{
            //myMario.dx=0;
         }
      }
      
      
      
      
      
   }//keyPressed()


   // This runs whenever a key is released
   public void keyReleased( KeyEvent event )
   {
      String keyin;
      keyin = ""+event.getKeyText( event.getKeyCode());
      //System.out.println ("Key released: "+ keyin);
      if(keyin=="P"&&myMario.isAlive==false){
         myMario = new Mario(100,100);
         globalXpos=0;
      }
      if(gameStart==false){
         switch(keyin){
            case "1":
               { brickNum= 1;
                  break;}
            case "2":
               { brickNum= 2;
                  break;}
            case "3":
               { brickNum=3;
                  break;}
            case "4":
               { brickNum= 4;
                  break;}
            case "5":
               { brickNum=5; 
                  break;}
            case "6":
               { brickNum= 6;
                  break;}
            case "7":
               { brickNum= 7;
                  break;}
            case "8":
               { brickNum= 8;
                  break;}
            case "9":
               { brickNum= 9;
                  break;}
            case "e":
               {
                  isEraser=true;
               }
            default:
               break;
         }
         
     

      
      
   } 
   }//keyReleased() - don't use this one
   public void keyTyped( KeyEvent event )
   {
   //keyTyped() only runs if a printable key is pressed. It does not respond to arrow keys, space, tab, etc.
      char keyin;
      keyin = event.getKeyChar(); //getKeyChar() returns the character of the printable key pressed.
     // System.out.println ("Key Typed: "+ keyin);
   }//keyTyped()

//****************************************************************************************
// ends keyboard input
//****************************************************************************************
   public void mouseMoved(MouseEvent e) {
      int mouseX = e.getX();
      int mouseY=e.getY();
      if(gameStart==false){
         if(mouseX>xWidth-100){
            globalXpos+=10;
         }
         if(mouseX<100){
            globalXpos-=10;
         }
         
      }
   }
   public void mouseDragged(MouseEvent e) {
   }
//*****MouseListener Methods*****************************************************
   public void mousePressed(MouseEvent e) {
   }
   public void mouseReleased(MouseEvent e) {
      
   }
   public void mouseEntered(MouseEvent e) {
   }
   public void mouseExited(MouseEvent e) {
   }

//This method runs when the left mouse button has been pressed and released (Clicked)
   public void mouseClicked(MouseEvent e) {
   
   //to get the x and y position of the mouse cursor use e.getX() and e.getY()
      int mouseX = e.getX();
      int mouseY=e.getY();
      if(gameStart==false){  
         //note: useless operator, but wanted practice
         gameStart  = startRect.contains(mouseX,mouseY) ? true : false;
         if(startRect.contains(mouseX,mouseY)){
            systemOut();
            globalXpos=0;
         }
      }
      if(gameStart==false){
         if(isEraser){
            for(int i = 0; i<GoombaList.size(); i++){
               if(GoombaList.get(i).rect.contains(mouseX+globalXpos,mouseY)){
                  GoombaList.remove(i);
               }
            }
            for(int i = 0; i<Platforms.size(); i++){
               if(Platforms.get(i).rect.contains(mouseX+globalXpos,mouseY)){
                  Platforms.remove(i);
               }
            }
         }
         if(goombaRect.contains(mouseX,mouseY)){
            isGoomba=true;
            isBrick=false;
            isEraser=false;
         }
         else if(brickRect.contains(mouseX,mouseY)){
            isGoomba=false;
            isBrick=true;
            isEraser=false;
         }
         else if(isBrick){
            for(int i =0; i<brickNum;i++){
               plat1= new Sprite(mouseX+i*platWidth+globalXpos,mouseY,platWidth,platHeight);
               Platforms.add(plat1);
            }
         }
         else{
            testGoomba = new Goomba(mouseX+globalXpos,mouseY);
            //testGoomba.isShell=true;
            GoombaList.add(testGoomba);
         }
      }
      
      
   //see if the mouse's x and y position is in the button's rectangle by using the contains() method
   }


}//Applet