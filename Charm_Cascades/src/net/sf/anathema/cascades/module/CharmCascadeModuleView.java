package net.sf.anathema.cascades.module;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.disy.commons.swing.layout.grid.GridDialogLayoutData;
import net.sf.anathema.cascades.view.CascadeView;
import net.sf.anathema.charmtree.presenter.view.ICharmTreeViewProperties;
import net.sf.anathema.framework.view.IItemView;
import net.sf.anathema.lib.control.stringvalue.IStringValueChangedListener;

public class CharmCascadeModuleView implements IItemView {
  private final JPanel panel = new JPanel(new GridDialogLayout(1, false));
  private final String printName;
  private final Icon icon;

  public CharmCascadeModuleView(String printName, Icon icon) {
    this.printName = printName;
    this.icon = icon;
  }

  public CascadeView addCascadeView(ICharmTreeViewProperties properties) {
    CascadeView view = new CascadeView(properties);
    panel.add(view.getComponent(), GridDialogLayoutData.FILL_BOTH);
    return view;
  }

  public JComponent getComponent() {
    return panel;

  }

  public void setName(String newName) {
    // Nothing to do
  }

  public String getName() {
    return printName;
  }

  public Icon getIcon() {
    return icon;
  }

  public void addNameChangedListener(IStringValueChangedListener nameListener) {
    // Nothing to do
  }

  public void removeNameChangedListener(IStringValueChangedListener nameListener) {
    // Nothing to do
  }

  public void dispose() {
    // Nothing to do
  }
}