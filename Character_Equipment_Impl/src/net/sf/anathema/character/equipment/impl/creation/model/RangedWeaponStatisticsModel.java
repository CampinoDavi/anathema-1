package net.sf.anathema.character.equipment.impl.creation.model;

import net.sf.anathema.character.equipment.creation.model.IRangedCombatStatisticsModel;
import net.sf.anathema.lib.data.Range;
import net.sf.anathema.lib.workflow.intvalue.IIntValueModel;
import net.sf.anathema.lib.workflow.intvalue.RangedIntValueModel;

public class RangedWeaponStatisticsModel extends OffensiveStatisticsModel implements IRangedCombatStatisticsModel {

  private IIntValueModel rangeModel = new RangedIntValueModel(new Range(1, Integer.MAX_VALUE), 1);

  public IIntValueModel getRangeModel() {
    return rangeModel ;
  }
}