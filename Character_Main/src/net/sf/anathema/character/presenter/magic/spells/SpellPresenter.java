package net.sf.anathema.character.presenter.magic.spells;

import net.disy.commons.core.util.ArrayUtilities;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.DedicatedCharacterChangeAdapter;
import net.sf.anathema.character.generic.framework.magic.stringbuilder.IMagicSourceStringBuilder;
import net.sf.anathema.character.generic.framework.magic.stringbuilder.MagicInfoStringBuilder;
import net.sf.anathema.character.generic.framework.magic.stringbuilder.ScreenDisplayInfoStringBuilder;
import net.sf.anathema.character.generic.framework.magic.stringbuilder.source.MagicSourceStringBuilder;
import net.sf.anathema.character.generic.framework.magic.view.IMagicViewListener;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.generic.magic.spells.CircleType;
import net.sf.anathema.character.model.ICharacterStatistics;
import net.sf.anathema.character.model.IMagicLearnListener;
import net.sf.anathema.character.model.ISpellConfiguration;
import net.sf.anathema.character.presenter.magic.detail.DetailDemandingMagicPresenter;
import net.sf.anathema.character.presenter.magic.detail.ShowMagicDetailListener;
import net.sf.anathema.character.view.magic.IMagicViewFactory;
import net.sf.anathema.character.view.magic.ISpellView;
import net.sf.anathema.lib.compare.I18nedIdentificateComparator;
import net.sf.anathema.lib.compare.I18nedIdentificateSorter;
import net.sf.anathema.lib.control.objectvalue.IObjectValueChangedListener;
import net.sf.anathema.lib.gui.IView;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.util.IIdentificate;
import net.sf.anathema.lib.workflow.labelledvalue.IValueView;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpellPresenter implements DetailDemandingMagicPresenter {

  public static SpellPresenter ForSorcery(ICharacterStatistics statistics, IResources resources,
          IMagicViewFactory factory) {
    SpellModel spellModel = new SorceryModel(statistics);
    return new SpellPresenter(spellModel, statistics, resources, factory);
  }

  public static SpellPresenter ForNecromancy(ICharacterStatistics statistics, IResources resources,
          IMagicViewFactory factory) {
    SpellModel spellModel = new NecromancyModel(statistics);
    return new SpellPresenter(spellModel, statistics, resources, factory);
  }

  private final ISpellConfiguration spellConfiguration;
  private SpellModel spellModel;
  private final ICharacterStatistics statistics;
  private final MagicInfoStringBuilder creator;
  private final IResources resources;
  private CircleType circle;
  private final IMagicSourceStringBuilder<ISpell> sourceStringBuilder;
  private final SpellViewProperties properties;
  private final ISpellView view;

  public SpellPresenter(SpellModel spellModel, ICharacterStatistics statistics, IResources resources,
          IMagicViewFactory factory) {
    this.spellModel = spellModel;
    this.statistics = statistics;
    this.properties = new SpellViewProperties(resources, statistics);
    this.resources = resources;
    this.creator = new ScreenDisplayInfoStringBuilder(resources);
    this.sourceStringBuilder = new MagicSourceStringBuilder<ISpell>(resources);
    this.spellConfiguration = statistics.getSpells();
    this.view = factory.createSpellView(properties);
    circle = spellModel.getCircles()[0];
  }

  @Override
  public void initPresentation() {
    IIdentificate[] allowedCircles = spellModel.getCircles();
    initDetailsView();
    view.initGui(allowedCircles);
    view.addMagicViewListener(new IMagicViewListener() {
      @Override
      public void magicRemoved(Object[] removedSpells) {
        List<ISpell> spellList = new ArrayList<ISpell>();
        for (Object spellObject : removedSpells) {
          spellList.add((ISpell) spellObject);
        }
        spellConfiguration.removeSpells(spellList.toArray(new ISpell[spellList.size()]));
      }

      @Override
      public void magicAdded(Object[] addedSpells) {
        List<ISpell> spellList = new ArrayList<ISpell>();
        for (Object spellObject : addedSpells) {
          if (spellConfiguration.isSpellAllowed((ISpell) spellObject)) {
            spellList.add((ISpell) spellObject);
          }
        }
        spellConfiguration.addSpells(spellList.toArray(new ISpell[spellList.size()]));
      }
    });
    view.addCircleSelectionListener(new IObjectValueChangedListener<CircleType>() {
      @Override
      public void valueChanged(CircleType circleType) {
        circle = circleType;
        view.setMagicOptions(getSpellsToShow());
      }
    });
    spellConfiguration.addMagicLearnListener(new IMagicLearnListener<ISpell>() {
      @Override
      public void magicForgotten(ISpell[] magic) {
        forgetSpellListsInView(view, magic);
      }

      @Override
      public void magicLearned(ISpell[] magic) {
        learnSpellListsInView(view, magic);
      }
    });
    initSpellListsInView(view);
    statistics.getCharacterContext().getCharacterListening().addChangeListener(new DedicatedCharacterChangeAdapter() {
      @Override
      public void experiencedChanged(boolean experienced) {
        view.clearSelection();
      }
    });
  }

  private void initDetailsView() {
    final JLabel titleView = view.addDetailTitleView();
    titleView.setText(" "); //$NON-NLS-1$
    final IValueView<String> circleView = view.addDetailValueView(properties.getCircleString() + ":"); //$NON-NLS-1$
    final IValueView<String> costView = view.addDetailValueView(properties.getCostString() + ":"); //$NON-NLS-1$
    final IValueView<String> targetView = view.addDetailValueView(properties.getTargetString() + ":"); //$NON-NLS-1$
    final IValueView<String> sourceView = view.addDetailValueView(properties.getSourceString() + ":"); //$NON-NLS-1$
    final ListSelectionListener detailListener = new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        final ISpell spell = (ISpell) ((JList) e.getSource()).getSelectedValue();
        if (spell == null) {
          return;
        }
        titleView.setText(resources.getString(spell.getId()));
        circleView.setValue(resources.getString(spell.getCircleType().getId()));
        costView.setValue(creator.createCostString(spell));
        if (spell.getTarget() == null) {
          targetView.setValue(properties.getUndefinedString());
        } else {
          targetView.setValue(resources.getString("Spells.Target." + spell.getTarget())); //$NON-NLS-1$
        }
        sourceView.setValue(sourceStringBuilder.createSourceString(spell));
      }
    };
    view.addOptionListListener(detailListener);
    view.addSelectionListListener(detailListener);
  }

  private void initSpellListsInView(final ISpellView spellView) {
    spellView.setLearnedMagic(getCircleFilteredSpellList(spellConfiguration.getLearnedSpells()));
    spellView.setMagicOptions(getSpellsToShow());
  }

  private void forgetSpellListsInView(final ISpellView spellView, ISpell[] spells) {
    spellView.removeLearnedMagic(spells);
    ISpell[] supportedSpells = getSpellsOfCurrentCircle(spells);
    spellView.addMagicOptions(supportedSpells, new I18nedIdentificateComparator(resources));
  }

  private void learnSpellListsInView(final ISpellView spellView, ISpell[] spells) {
    ISpell[] supportedSpells = getSpellsOfCurrentCircle(spells);
    spellView.addLearnedMagic(supportedSpells);
    spellView.removeMagicOptions(supportedSpells);
  }

  private ISpell[] getSpellsOfCurrentCircle(ISpell[] spells) {
    List<ISpell> supportedSpells = new ArrayList<ISpell>();
    for (ISpell spell : spells) {
      if (circle == spell.getCircleType()) {
        supportedSpells.add(spell);
      }
    }
    return supportedSpells.toArray(new ISpell[supportedSpells.size()]);
  }

  private ISpell[] getSpellsToShow() {
    List<ISpell> showSpells = new ArrayList<ISpell>();
    Collections.addAll(showSpells, spellConfiguration.getSpellsByCircle(circle));
    showSpells.removeAll(Arrays.asList(spellConfiguration.getLearnedSpells()));
    int count = showSpells.size();
    ISpell[] sortedSpells = new ISpell[count];
    sortedSpells = new I18nedIdentificateSorter<ISpell>()
            .sortAscending(showSpells.toArray(new ISpell[count]), sortedSpells, resources);
    return sortedSpells;
  }

  private ISpell[] getCircleFilteredSpellList(ISpell[] spells) {
    List<ISpell> spellList = new ArrayList<ISpell>();
    for (ISpell spell : spells) {
      if (ArrayUtilities.containsValue(spellModel.getCircles(), spell.getCircleType())) {
        spellList.add(spell);
      }
    }
    return spellList.toArray(new ISpell[spellList.size()]);
  }

  @Override
  public IView getView() {
    return view;
  }

  @Override
  public void addShowDetailListener(final ShowMagicDetailListener listener) {
    ListSelectionListener editListener = new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        final ISpell spell = (ISpell) ((JList) e.getSource()).getSelectedValue();
        if (spell == null) {
          return;
        }
        listener.showDetail(spell.getId());
      }
    };
    view.addOptionListListener(editListener);
    view.addSelectionListListener(editListener);
  }
}
