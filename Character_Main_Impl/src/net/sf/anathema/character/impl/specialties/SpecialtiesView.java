package net.sf.anathema.character.impl.specialties;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.disy.commons.swing.layout.grid.GridAlignment;
import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.disy.commons.swing.layout.grid.GridDialogLayoutData;
import net.sf.anathema.character.generic.framework.ITraitReference;
import net.sf.anathema.character.impl.view.SpecialtyView;
import net.sf.anathema.character.library.intvalue.IIntValueDisplayFactory;
import net.sf.anathema.character.presenter.specialty.ISpecialtiesConfigurationView;
import net.sf.anathema.character.view.ISpecialtyView;
import net.sf.anathema.character.view.basic.IButtonControlledComboEditView;
import net.sf.anathema.framework.presenter.view.ISimpleTabView;

public class SpecialtiesView implements ISpecialtiesConfigurationView, ISimpleTabView {

  private final IIntValueDisplayFactory factory;
  private JPanel mainPanel = new JPanel(new GridDialogLayout(1, false));
  private JPanel specialtyPanel = new JPanel(new GridDialogLayout(5, false));

  public SpecialtiesView(IIntValueDisplayFactory factory) {
    this.factory = factory;
  }

  public ISpecialtyView addSpecialtyView(
      String abilityName,
      String specialtyName,
      Icon deleteIcon,
      int value,
      int maxValue) {
    SpecialtyView specialtyView = new SpecialtyView(factory, abilityName, deleteIcon, specialtyName, value, maxValue);
    specialtyView.addComponents(specialtyPanel);
    return specialtyView;
  }

  public IButtonControlledComboEditView<ITraitReference> addSpecialtySelectionView(
      String labelText,
      ListCellRenderer renderer,
      Icon addIcon) {
    ButtonControlledComboEditView<ITraitReference> objectSelectionView = new ButtonControlledComboEditView<ITraitReference>(
        addIcon,
        renderer);
    mainPanel.add(objectSelectionView.getComponent());
    return objectSelectionView;
  }

  public JComponent getComponent() {
    GridDialogLayoutData data = new GridDialogLayoutData();
    data.setHorizontalAlignment(GridAlignment.FILL);
    data.setVerticalAlignment(GridAlignment.BEGINNING);
    mainPanel.add(specialtyPanel, data);
    return mainPanel;
  }

  public boolean needsScrollbar() {
    return false;
  }
}