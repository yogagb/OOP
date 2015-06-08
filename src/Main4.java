import javax.swing.JFrame;


public class Main4 {
	public static void main(String[] args)
	{
		Car car = new Car(600);
		Map map = new Map(car);
		car.getMap(map);
		
		Display main = new Display(map);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//map.obsList.get(0).setX(car.carPositionX);
		//biar obstacle ada yg kena
		map.obsList.get(0).setY(car.carPositionY);
		main.obsIcon.get(0).setLocation(map.obsList.get(0).getX(),car.carPositionY);
		
		
		car.startMoving();
		map.startAllMovingObstacle(map.mObsList);
		main.start();
		
		
		
		
		
	}
}
