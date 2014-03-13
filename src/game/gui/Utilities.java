package game.gui;

import java.awt.event.KeyEvent;

public class Utilities {	
	public static char getKeyChar(int key) {
		switch (key) {
		case KeyEvent.VK_A: return 'A';
		case KeyEvent.VK_B: return 'B';
		case KeyEvent.VK_C: return 'C';
		case KeyEvent.VK_D: return 'D';
		case KeyEvent.VK_E: return 'E';
		case KeyEvent.VK_F: return 'F';
		case KeyEvent.VK_G: return 'G';
		case KeyEvent.VK_H: return 'H';
		case KeyEvent.VK_I: return 'I';
		case KeyEvent.VK_J: return 'J';
		case KeyEvent.VK_K: return 'K';
		case KeyEvent.VK_L: return 'L';
		case KeyEvent.VK_M: return 'M';
		case KeyEvent.VK_N: return 'N';
		case KeyEvent.VK_O: return 'O';
		case KeyEvent.VK_P: return 'P';
		case KeyEvent.VK_Q: return 'Q';
		case KeyEvent.VK_R: return 'R';
		case KeyEvent.VK_S: return 'S';
		case KeyEvent.VK_T: return 'T';
		case KeyEvent.VK_U: return 'U';
		case KeyEvent.VK_V: return 'V';
		case KeyEvent.VK_W: return 'W';
		case KeyEvent.VK_X: return 'X';
		case KeyEvent.VK_Y: return 'Y';
		case KeyEvent.VK_Z: return 'Z';
		}
		return 0;
	}
}
