package one.senri.event;

public class SpaceFighterEvent extends Object {
  private Object source;
  
  public SpaceFighterEvent() {
    this(null);
  }
  
  public SpaceFighterEvent(Object source) {
    super();
    this.source = source;
  }

  public Object getSource() {
    return this.source;
  }
}
  
