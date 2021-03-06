package net.sf.anathema.character.sidereal.colleges;

import net.sf.anathema.character.generic.additionaltemplate.IAdditionalModel;
import net.sf.anathema.character.generic.framework.additionaltemplate.IAdditionalViewFactory;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.sidereal.colleges.presenter.ISiderealCollegeModel;
import net.sf.anathema.character.sidereal.colleges.presenter.ISiderealCollegeViewProperties;
import net.sf.anathema.character.sidereal.colleges.presenter.SiderealCollegePresenter;
import net.sf.anathema.character.sidereal.colleges.presenter.SiderealCollegeViewProperties;
import net.sf.anathema.character.sidereal.colleges.view.SiderealCollegeView;
import net.sf.anathema.lib.gui.IView;
import net.sf.anathema.lib.resources.IResources;

public class SiderealCollegeViewFactory implements IAdditionalViewFactory {

  public IView createView(IAdditionalModel model, IResources resources, ICharacterType type) {
    ISiderealCollegeViewProperties properties = new SiderealCollegeViewProperties(resources);
    SiderealCollegeView view = new SiderealCollegeView(properties);
    new SiderealCollegePresenter(resources, view, (ISiderealCollegeModel) model).initPresentation();
    return view;
  }
}