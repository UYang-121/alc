package engine.ui;

import engine.support.Vec2d;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/** Centered text that can be laid out like the other UI elements. */
public class TextElement extends UIElement {

  private String text;
  private Color color;
  private double fontSize;
  private FontWeight weight;
  
  public TextElement(Vec2d position, Vec2d size, String text, double fontSize, Color color) {
    super(position, size);
    this.text = text;
    this.fontSize = fontSize;
    this.color = color;
    weight = FontWeight.NORMAL;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setFontSize(double fontSize) {
    this.fontSize = fontSize;
  }

  public void setWeight(FontWeight weight) {
    this.weight = weight;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public void draw(GraphicsContext g) {
    g.save();
    g.setFill(color);
    g.setFont(Font.font("Avenir Next", weight, fontSize));
    g.setTextAlign(TextAlignment.CENTER);
    g.setTextBaseline(VPos.CENTER);
    g.fillText(text, position.x + size.x / 2, position.y + size.y / 2, size.x);
    g.restore();
  }
}
