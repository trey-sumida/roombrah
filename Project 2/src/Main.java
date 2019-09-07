import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		// reading file
		Scanner fileScanner = new Scanner(new FileReader("grid.txt"));
		// reading first number on file
		int width = fileScanner.nextInt();
		// reading second number on file
		int height = fileScanner.nextInt();
		// reading the text
		String inputText = fileScanner.nextLine();
		// setting up variables
		int dirtblock = 10;
		int n = 0;
		int walls = 200;
		int w = 0;
		int movex = 10;
		int movey = 10;
		int pieces = 0;
		Color ballcolor = new Color(200, 200, 200, 20);
		// creating arrays for dirt and walls
		EZImage[] dirt = new EZImage[dirtblock];
		EZImage[] wall = new EZImage[walls];
		// adding sounds
		EZSound bounce = EZ.addSound("bounce.wav");
		EZSound vacuum = EZ.addSound("suck.wav");
		EZSound win = EZ.addSound("winner.wav");
		// setup
		EZ.initialize(width * 31, height * 31);
		EZ.setBackgroundColor(Color.WHITE);
		// adding image of roombrah
		Roombrah roombrah = new Roombrah("roombrah.png", 500, 200);
		// getting the coordinates of the roombrah
		int posx = roombrah.getx();
		int posy = roombrah.gety();

		// reading the lines of text
		for (int row = 0; row < height; row++) {

			inputText = fileScanner.nextLine();
			System.out.println(inputText);
			// reading the lines of text
			for (int column = 0; column < inputText.length(); column++) {

				char ch = inputText.charAt(column);
				// placing the image of dirt for the letter "d" when read in the text
				switch (ch) {
				case 'd':
					dirt[n] = EZ.addImage("dirt.png", column * 31, row * 31);
					n++;
					break;
				// placing the image of a wall for the letter "w" when read in the text
				case 'w':
					wall[w] = EZ.addImage("wall.png", column * 32, row * 32);
					w++;
					break;
				default:
					// Do nothing
					break;
				}
			}
		}
		// game loop
		while (true) {
			// creating the circle to track its path
			EZ.addCircle(posx, posy, 16, 16, ballcolor, true);
			// moving the roombrah
			roombrah.move(posx, posy);
			posx += movex;
			posy += movey;
			// cycling through the wall array
			for (int i = 0; i < w; i++) {
				// checking if roombrah will hit wall from x coordinate
				if (wall[i].isPointInElement(posx + movex, posy)) {
					// changing x direction
					movex = -movex;
					bounce.play();
				}
				// checking if roombrah will hit wall from y coordinate
				if (wall[i].isPointInElement(posx, posy + movey)) {
					// changing y direction
					movey = -movey;
					bounce.play();
				}
			}
			// cycling through dirt array
			for (int o = 0; o < n; o++) {
				// checking if roombrah ran over a dirt piece
				if (dirt[o].isPointInElement(posx, posy)) {
					// moving dirt piece to corner of screen
					dirt[o].translateTo(0, 0);
					vacuum.play();
					// incrementing number of dirt pieces picked up
					pieces++;
				}
			}
			// checking if all pieces were collected
			if (pieces == 8) {
				EZ.addText(width * 16, height * 16, "YOUR ROOM IS CLEAN!", Color.green, 75);
				win.play();
				break;
			}
			EZ.refreshScreen();
		}
	}

}
