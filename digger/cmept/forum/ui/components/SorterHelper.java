package digger.cmept.forum.ui.components;

import digger.cmept.forum.ui.celestun4ik.component.Component;
import digger.cmept.forum.ui.celestun4ik.component.impl.ModuleComponent;

import java.util.Comparator;

public class SorterHelper implements Comparator<Component> {

    @Override
    public int compare(Component component, Component component2) {
        if (component instanceof ModuleComponent && component2 instanceof ModuleComponent) {
            return component.getName().compareTo(component2.getName());
        }
        return 0;
    }
}