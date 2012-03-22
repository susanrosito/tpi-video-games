package ar.com.unq.tpi;

import java.awt.Dimension;

import com.uqbar.vainilla.DesktopGameLauncher;
import com.uqbar.vainilla.Game;

public class Demo extends Game {

	@Override
	protected void initializeResources() {

	}

	@Override
	protected void setUpScenes() {
		

	}

	@Override
	public Dimension getDisplaySize() {
		return new Dimension(800, 600);
	}

	@Override
	public String getTitle() {
		return "Demo";
	}
	
	public static void main(String[] args) {
		new DesktopGameLauncher(new Demo()).launch();
	}

}
