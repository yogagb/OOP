import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Car {
	Timer timer;
	
	int roda;
	Engine engine;
	FuelTank tank;
	DashBoard dashboard;
	Sensor sensor;
	
	//lampu belum diurus
	
	int carPositionX;
	int carPositionY;
	
	public Car(int silinder)
	{
		engine = new Engine(silinder);
		tank = new FuelTank();
		dashboard = new DashBoard();
		roda = 4;
		
		carPositionX = 20;
		carPositionY = randomY();
		
	}
	
	public int randomY(){
		
		Random rand = new Random();
		return 230+rand.nextInt(170);
	}
	
	
	public void getMap(Map a)
	{
		sensor = new Sensor(a);
	}
	
	
	public void gerak()
	{
		//setiap 0.1 detik
		int gerakan;
		gerakan = engine.speed;
		carPositionX = carPositionX + gerakan;
	}
	
	public void startMoving()
	{
		timer = new Timer();
		timer.schedule(new TimerTask(){
			int oneSecond = 1;

			
			public void run()
			{
				if (carPositionX >= 1000)
				{
					carPositionX = 0;
					carPositionY = randomY();
				}
				
				if (sensor.checkObstacle() != null)
				{
					avoidObstacle(sensor.checkObstacle());
				}
				
				
				
				
				if (oneSecond%10 == 0)
				{
					engine.adjustSpeed();
					engine.adjustTemperature();
					tank.fuelUsage(engine);
				}
				
				/*if (halfMinute%30 == 0)
				{
					avoidObstacle();
				}*/
				
				if (sensor.checkTime() == false)
				engine.maxSpeed = (engine.cylinderSize/100) - 2;
				else
				engine.maxSpeed = engine.cylinderSize/100;
				
				
				
				if (sensor.checkMovingObstacle() == null)
				{
					oneSecond++;
					gerak();
				}

				
				
			}
		},0,100);
		
	}
	
	public void pause()
	{
		timer.cancel();
	}
	
	
	
	
	public void avoidObstacle(Obstacle a)
	{
		pause();
		
		timer = new Timer();
		
		if (a.getY() >=300)
		{
			
			timer.schedule(new TimerTask(){
				int tempY = 0; // mobil naik 25 pixel 
				int lastY = carPositionY - 25;
				int tempGerak = engine.speed/2;
				
				public void run(){
						carPositionX = carPositionX + tempGerak;
						if (tempY+tempGerak > 25)
						{
							tempY = tempY + tempGerak;
							carPositionY = lastY;
						}
						else
						{
							tempY = tempY + tempGerak;
							carPositionY = carPositionY - tempGerak;
						}
						tank.fuelUsage(engine);
					if (tempY >= 25)
					{
						this.cancel();
						startMoving();
					}
					
				}
			},0,150);
			
		}
		else
		{
			timer.schedule(new TimerTask(){
				int tempY = 0; // mobil naik 25 pixel 
				int lastY = carPositionY + 25;
				int tempGerak = engine.speed/2;
				
				public void run(){
						carPositionX = carPositionX + tempGerak;
						if (tempY+tempGerak > 25)
						{
							tempY = tempY + tempGerak;
							carPositionY = lastY;
						}
						else
						{
							tempY = tempY + tempGerak;
							carPositionY = carPositionY + tempGerak;
						}
					
					if (tempY >= 25)
					{
						this.cancel();
						startMoving();
					}
					
				}
				},0,150);
		}
		
	}

}
