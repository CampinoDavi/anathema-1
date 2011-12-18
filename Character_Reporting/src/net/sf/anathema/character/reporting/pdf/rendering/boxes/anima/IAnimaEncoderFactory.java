package net.sf.anathema.character.reporting.pdf.rendering.boxes.anima;

import net.sf.anathema.character.reporting.pdf.rendering.general.box.IBoxContentEncoder;

public interface IAnimaEncoderFactory {

  public IBoxContentEncoder createAnimaEncoder();
}