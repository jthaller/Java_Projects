//Jeremy Thaller P4
//Tester class for gameoflife and board.  
//used to test individual methods and see if they are working properly
public class Tester {

	public static void main(String[] args) {
		//Board Tests
		System.out.println("Board Tests \n-----------");
		Board a = new Board(3,4);
		Board b = new Board (0,1);
		Board c = new Board (0,0);
		Board d = new Board (-2,2);
		a.setSpot(0,2,1);
		a.setSpot(0,0,1);
		a.setSpot(1, 1, 1);
		a.setSpot(1, 4, 2); // invalid to test 
		a.setSpot(2, 2, 1);
		a.setSpot(2,1,2);
		System.out.println(a + "\n");
		a.remove(0,0);
		System.out.println(a + "\n");
		a.clear();
		System.out.println(a + "\n");
		System.out.println("B \n" +b);
		System.out.println("C \n" +c);
		System.out.println("D \n" +d);


		//GOL Tests
		System.out.println("GameOfLife Tests \n-----------------");
		
		GameOfLife world = new GameOfLife(4,4);
		
		world.randomize();
		System.out.println("Random 1: \n" + world);
		world.randomize();
		System.out.println("Random 2: \n" + world);
		world.setCell(0,0,false);
		System.out.println("Same as 'Random 2,' but top left set to 0 \n" + world);
		GameOfLife world2 = new GameOfLife(0,0);
		System.out.println("Size of (0,0) \n" + world2);
		GameOfLife world3 = new GameOfLife(-3,2);
		System.out.println("Negaives Test \n" + world3);
		
		for (int i = 0; i < 3; i++){
			System.out.print("Neighbors for top left: " + world.countNeighbors(0,0) + "\n");
			System.out.print("Is top left alive?: " + world.isAlive(0,0) + "\n");
			System.out.println("Generation: " + world.getGenNum() + "\r\n");
			System.out.print(world + "\n");
			world.nextGeneration();
		}

	}
}
