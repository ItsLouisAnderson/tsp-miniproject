package algorithm;

import javafx.animation.Transition;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.*;

public abstract class SolveStrategy {
	private static final Duration TRANS_DURATION = Duration.millis(500);
	
	public Path createPath(City c1, City c2) {
		Path p = new Path(c1, c2);
		
		return p;
	}
	
	public FillTransition colorPath(Path p, Color cl) {
		FillTransition t = new FillTransition();
		t.setShape(p);
		t.setFromValue((Color)p.getFill());
		t.setToValue(cl);
		t.setDuration(TRANS_DURATION);
		
		return t;
	}
	
	public FillTransition colorCity(City c, Color cl) {
		FillTransition t = new FillTransition();
		t.setShape(c);
		t.setFromValue((Color)c.getFill());
		t.setToValue(cl);
		t.setDuration(TRANS_DURATION);
		
		return t;
	}
}
