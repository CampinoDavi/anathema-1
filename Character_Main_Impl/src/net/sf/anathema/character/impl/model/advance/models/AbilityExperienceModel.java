package net.sf.anathema.character.impl.model.advance.models;

import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.character.generic.template.abilities.IGroupedTraitType;
import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.impl.model.advance.IPointCostCalculator;
import net.sf.anathema.character.library.trait.favorable.IFavorableTrait;
import net.sf.anathema.character.model.ICharacterStatistics;
import net.sf.anathema.character.model.traits.ICoreTraitConfiguration;

public class AbilityExperienceModel extends AbstractIntegerValueModel {

  private final ICoreTraitConfiguration traitConfiguration;
  private final IPointCostCalculator calculator;
  private final ICharacterStatistics statistics;

  public AbilityExperienceModel(
      ICoreTraitConfiguration traitConfiguration,
      IPointCostCalculator calculator,
      ICharacterStatistics statistics) {
    super("Experience", "Abilities"); //$NON-NLS-1$ //$NON-NLS-2$
    this.traitConfiguration = traitConfiguration;
    this.calculator = calculator;
    this.statistics = statistics;
  }

  public Integer getValue() {
    return getAbilityCosts();
  }

  private int getAbilityCosts() {
    int experienceCosts = 0;
    for (IFavorableTrait ability : getAllAbilities()) {
      experienceCosts += calculator.getAbilityCosts(ability, ability.getFavorization().isCaste()
          || ability.getFavorization().isFavored());
    }
    return experienceCosts;
  }

  private IFavorableTrait[] getAllAbilities() {
    List<ITraitType> abilityTypes = new ArrayList<ITraitType>();
    for (IGroupedTraitType type : statistics.getCharacterTemplate().getAbilityGroups()) {
      abilityTypes.add(type.getTraitType());
    }
    return traitConfiguration.getFavorableTraits(abilityTypes.toArray(new ITraitType[abilityTypes.size()]));
  }
}