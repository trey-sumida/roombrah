
public class Roombrah {
	EZImage img;
	int posx;
	int posy;

	Roombrah(String file_name, int x, int y) {
		img = EZ.addImage(file_name, x, y);
	}

	int getx() {
		return img.getXCenter();
	}

	int gety() {
		return img.getYCenter();
	}

	void move(int posx, int posy) {
		img.translateTo(posx, posy);
	}
}
