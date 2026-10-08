package engine.world;

public interface DragComponent extends Component {
  void beginDrag(GameObject object, double worldX, double worldY);

  void drag(GameObject object, double worldX, double worldY);

  void endDrag(GameObject object);
}
