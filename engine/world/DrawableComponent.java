package engine.world;

import javafx.scene.canvas.GraphicsContext;

public interface DrawableComponent extends Component {
  void draw(GraphicsContext g, GameObject object);
}
