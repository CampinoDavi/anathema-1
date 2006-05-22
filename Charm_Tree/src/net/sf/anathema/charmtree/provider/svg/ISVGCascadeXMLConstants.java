package net.sf.anathema.charmtree.provider.svg;

import org.apache.batik.util.SVGConstants;

public interface ISVGCascadeXMLConstants {

  public static final String DOT = ","; //$NON-NLS-1$
  public static final String SPACE = " "; //$NON-NLS-1$

  public static final String ATTRIB_FILL = "fill"; //$NON-NLS-1$
  public static final String ATTRIB_FILL_OPACITY = "fill-opacity"; //$NON-NLS-1$
  public static final String ATTRIB_HEIGHT = "height"; //$NON-NLS-1$
  public static final String ATTRIB_STROKE = "stroke"; //$NON-NLS-1$
  public static final String ATTRIB_TRANSFORM = "transform"; //$NON-NLS-1$
  public static final String ATTRIB_WIDTH = "width"; //$NON-NLS-1$
  public static final String ATTRIB_X = "x"; //$NON-NLS-1$
  public static final String ATTRIB_Y = "y"; //$NON-NLS-1$
  public static final String ATTRIB_IS_CHARM = "isCharm"; //$NON-NLS-1$
  public static final String ATTRIB_IS_ARROW = "isArrow"; //$NON-NLS-1$

  public static final String VALUE_15 = "15"; //$NON-NLS-1$
  public static final String VALUE_3 = "3"; //$NON-NLS-1$
  public static final String VALUE_ARROWHEAD_POINTS = "0,0 15,15 30,0 15,5"; //$NON-NLS-1$
  public static final String VALUE_ARROWHEAD_ID = "ArrowHead"; //$NON-NLS-1$
  public static final String VALUE_ARROWHEAD_REFERENCE = "#" + VALUE_ARROWHEAD_ID; //$NON-NLS-1$
  public static final String VALUE_FRAME_ID = "Frame"; //$NON-NLS-1$
  public static final String VALUE_FRAME_REFERENCE = "#" + VALUE_FRAME_ID; //$NON-NLS-1$
  public static final String VALUE_CASCADE_ID = "cascade"; //$NON-NLS-1$
  public static final String VALUE_COLOR_BLACK = "rgb(0,0,0)"; //$NON-NLS-1$
  public static final String VALUE_STROKE_MEDIUM_GRAY = "rgb(128,128,128)"; //$NON-NLS-1$
  public static final String VALUE_VIEWBOX_SIZE = "0 0 1400 625"; //$NON-NLS-1$
  public static final String VALUE_XMID_YMIN_MEET = SVGConstants.SVG_XMIDYMIN_VALUE + " meet"; //$NON-NLS-1$
  public static final String VALUE_SVG_VERSION = "1.2"; //$NON-NLS-1$
}