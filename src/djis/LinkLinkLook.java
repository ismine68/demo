package djis;

import java.util.Random;
import java.util.Scanner;

public class LinkLinkLook {
	static int rows ;//行数
	static int cols ;//列数
	static int level = 5;//级别

	static int[][] checkerBoard;//用来存放棋盘
	static Random rd = new Random();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)  {
	System.out.print("请输入行和列：");

	//获取用户的行和列
	rows = sc.nextInt();
	cols = sc.nextInt();
	
	int count = 0;
	do{
		 if (count == 0)  {
		System.out.println("请输入行数和列数：");
		}  else{
		System.out.println("对不起。其中必须有一个为偶数，请重新输入：");
		}
		rows = sc.nextInt();
		cols = sc.nextInt();
		count++;
	  }   while( rows*cols%2!=0);
	init();
	}
	static void init()  {
	checkerBoard  =  new int[rows][cols];
	int randomNum = 0;
	if (rows % 2==0)  {
	   for(int i= 0;i<rows;i+=2)  {
	      for(int j=0;j<cols;j++)  {
		randomNum = rd.nextInt(level)+1;
		checkerBoard[i][j]=randomNum;
		checkerBoard[i+1][j]=randomNum;
		    }	
		}	
	}else{
	for(int i=0;i<rows;i++)  {
	   for(int j=0;j<cols;j+=2)  {
		randomNum = rd.nextInt(level)+1;
        checkerBoard[i][j]=randomNum;
		checkerBoard[i][j+1]=randomNum;	
		     }		
		  }
		}
	shuffle();
	show();
	startGame();
	}
	static void shuffle()  {
	int x1, y1 ,x2,  y2,temp;
	for (int i = 0;i<rows*cols*level;i++)  {
	   x1= rd.nextInt(rows);
	   y1= rd.nextInt(cols);
	   x2= rd.nextInt(rows);
	   y2= rd.nextInt(cols);
	   temp=checkerBoard[x1][y1];
	   checkerBoard[x1][y1]=checkerBoard[x2][y2];
	   checkerBoard[x1][y1]=temp;
	   		}
		}
	
	static void startGame()  {
	int  x1,y1,x2,y2;
	do{
	System.out.println("\n请输入两个点的坐标(0  0  0  0)");
	x1=sc.nextInt()-1;
	y1=sc.nextInt()-1;
	x2=sc.nextInt()-1;
	y2=sc.nextInt()-1;
	        if(!basicCheck(x1,y1,x2,y2))  {
	         continue;	
	         }
	         if(boundMiss(x1,y1,x2,y2)||oneLineMiss(x1,y1,x2,y2)||twoLineMiss(x1,y1,x2,y2)||threeLineMiss(x1,y1,x2,y2))  {
		checkerBoard[x1][y1]=0;
		checkerBoard[x2][y2]=0;
		
		if(gameOver())  {  
		System.out.println("game  over");
		break;
		}  else  {
		show();
		}
		} else  {
		System.out.println("对不起，不能消除");
		}
                 }while( true);	
	}	

	static boolean basicCheck(int x1, int y1, int x2, int y2){
	if(x1==x2&&y1==y2)  {
		System.out.println("\n同一个不能消除");
		return false;
		}
	if  (checkerBoard[x1][y1]!=checkerBoard[x2][y2])  {
		return false;
		}
	return  true;
	}
	static   boolean  boundMiss(int  x1,int y1,int x2, int y2)  {
		if((x1==0&&x2==0)||(x1==rows-1&&x2==rows-1)) {
			return true;
		}
		if((y1==0&&y2==0)||(y1==cols-1&&y2==cols-1)) {
			return true;
		}
		return false;
	}
	static   boolean  oneLineMiss(int  x1,int y1,int x2, int y2)  {
		if(x1==x2){
		int  max  =  y1  >  y2  ?  y1:  y2;
		int  min  =  y1  >  y2  ?  y2:  y1;
		
			for(int  i=min+1;i<max;i++) {
				if(checkerBoard[x1][i]!=0) {
				return  false;
				}
			}
			return  true;
		} 
		if(y1==y2){
		int  max  =  x1  >  x2  ?  x1:  x2;
		int  min  =  x1  >  x2  ?  x2:  x1;
		
			for(int  i=min+1;i<max;i++) {
				if(checkerBoard[i][y1]!=0) {
				return  false;
				}
			}
			return  true;
		} 
		return false;
	}
	
	static   boolean  twoLineMiss(int  x1,int y1,int x2, int y2)  {
	if  (checkerBoard[x1][y2]  == 0 && oneLineMiss(x1  , y1, x1,  y2)  &&  oneLineMiss(x1,  y2, x2,  y2)){
		return  true;
		}
	if  (checkerBoard[x2][y1]  == 0 && oneLineMiss(x1  , y1, x2,  y1)  &&  oneLineMiss(x2,  y1, x2,  y2)){
		return  true;
		}
		return false;
	}
	static   boolean  threeLineMiss(int  x1,int y1,int x2, int y2)  {
		for  (int i=0;i<rows;i++)   {
		if(i != x1  &&  checkerBoard[i][y1]   ==  0  &&  oneLineMiss(i, y1, x1, y1 )&& twoLineMiss(x1,  y1,  x2,  y2) )
		return  true;
		}
		for  (int i=0;i<cols;i++)   {
		if(i != y1  &&  checkerBoard[x1][i]   ==  0  &&  oneLineMiss(x1, i, x1, y1 )&& twoLineMiss(x1,  i,  x2,  y2) )
		return  true;
		}
		return  false;
	}
	static   boolean  gameOver()   {
		for  (int[] arr  :  checkerBoard)   {
			for(int  num  :  arr)   {
			if(num  !=  0)   {
			return  false;			
				}
			}
		}
		return  true;
	}
	static  void  show()  {
		System.out.println("\n");
		System.out.print("  ");
		for(int i=0;i<cols;i++)  {
			System.out.print((i+1)+" ");
		}
		System.out.println("\n");
		for(int  i=0;i<rows;i++){
			System.out.print((i+1)+" ");
			for  (int  j=0;j<cols;j++)   {
				if(checkerBoard[i][j]==0)  {
				System.out.print("  ");
				}  else  {
				System.out.print(checkerBoard[i][j]+" ");
				}
			}
			System.out.print((i+1)+" ");
			System.out.println("\n");
		}
		System.out.print("  ");
		for(int i=0;i<cols;i++)  {
			System.out.print((i+1)+" ");
		}
		
	}
}
