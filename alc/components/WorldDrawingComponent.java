package alc.components;

import engine.world.DrawableComponent;
import engine.world.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WorldDrawingComponent implements DrawableComponent {
  @Override
  public void draw(GraphicsContext g, GameObject object) {
    g.setFill(Color.web("#262537"));
    g.fillRect(-1400, -1000, 2800, 2000);

    g.setStroke(Color.web("#383650"));
    g.setLineWidth(2);
    for (int coordinate = -1400; coordinate <= 1400; coordinate += 80) {
      g.strokeLine(coordinate, -1000, coordinate, 1000);
    }
    for (int coordinate = -1000; coordinate <= 1000; coordinate += 80) {
      g.strokeLine(-1400, coordinate, 1400, coordinate);
    }
    
    g.setFill(Color.web("#312F46"));
    g.fillOval(80, 60, 360, 230);
    g.fillOval(570, 350, 460, 300);
    g.setStroke(Color.web("#51486B"));
    g.setLineWidth(5);
    g.strokeOval(80, 60, 360, 230);
    g.strokeOval(570, 350, 460, 300);
  }
}
