package engine.world;

public interface TickableComponent extends Component {
  void tick(long nanosSincePreviousTick, GameObject object);
}
