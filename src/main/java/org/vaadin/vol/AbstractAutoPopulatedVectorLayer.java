package org.vaadin.vol;

import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.Map;

import org.vaadin.vol.VectorLayer.SelectionMode;
import org.vaadin.vol.client.AutoPopulatedVectorLayerState;

/**
 * An abstract implementation (based on client side vector layer) that populates
 * it content on the client side only. Server side only controls how it is
 * populated, styled and provides methods for selection features.
 *
 */
public abstract class AbstractAutoPopulatedVectorLayer extends AbstractLayerBase {

    public AbstractAutoPopulatedVectorLayer() {
        this.getState().displayName = "WFS";
    }

    /*@SuppressWarnings("unchecked")
    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        super.changeVariables(source, variables);
        String fid = (String) variables.get("fid");
        Map<String,Object> attr = (Map<String, Object>) variables.get("attr");
        String wkt = (String) variables.get("wkt");
        String sSel=(String)variables.get(FeatureSelectedListener.EVENT_ID);
        String sUnsel=(String)variables.get(FeatureUnSelectedListener.EVENT_ID);
        String sBefsel=(String)variables.get(BeforeFeatureSelectedListener.EVENT_ID);
        if (sSel!=null) {
            FeatureSelectedEvent featureSelectedEvent =
                    new FeatureSelectedEvent(this, fid, attr, wkt);
            fireEvent(featureSelectedEvent);
        }
        else if (sUnsel!=null) {
            FeatureUnSelectedEvent featureUnSelectedEvent =
                    new FeatureUnSelectedEvent(this, fid, attr, wkt);
            fireEvent(featureUnSelectedEvent);
        }
        else if (sBefsel!=null) {
            BeforeFeatureSelectedEvent beforeFeatureSelectedEvent =
                    new BeforeFeatureSelectedEvent(this, fid, attr, wkt);
            fireEvent(beforeFeatureSelectedEvent);
        }
    }*/

    /*@Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);
        if (visibilitySet) {
            target.addAttribute("visibility",visibility);
            visibilitySet=false;
        }
        if (filterSet) {
            target.addAttribute("filterValue",filterValue);
            target.addAttribute("filterType",filterType);
            target.addAttribute("filterProp",filterProp);
            if (filterRefresh) {
                target.addAttribute("filterRefresh",true);
                filterRefresh=false;
            }
            filterSet=false;
        }
    }*/

    /**
     * @return the stylemap
     */
    public StyleMap getStyleMap() {
        return this.getState().stylemap;
    }

    /**
     * @param stylemap
     *            the stylemap to set
     */
    public void setStyleMap(StyleMap stylemap) {
        this.getState().stylemap = stylemap;
        markAsDirty();
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        this.getState().selectionMode = selectionMode.toString();
        markAsDirty();
    }
    public SelectionMode getSelectionMode() {
        return SelectionMode.valueOf(this.getState().selectionMode);
    }

    public void setSelectionCtrlId(String selectionCtrlId) {
        this.getState().selectionCtrlId = selectionCtrlId;
    }
    public String getSelectionCtrlId() {
        return this.getState().selectionCtrlId;
    }

    /**
     * allows to set visibility of a layer. A call with 'setVisibility(false)'
     * displays layer in layer switcher but don't load its data.
     * @return
     */
    public void setVisibility(boolean visibility) {
        this.getState().visibility = visibility;
        markAsDirty();
    }

    public void setFilter(String filterType, String filterProp, String filterValue) {
        this.getState().filterType = filterType;
        this.getState().filterProp = filterProp;
        this.getState().filterValue = filterValue;
        markAsDirty();
    }

    public void setFilterAndRefresh(String filterType, String filterProp, String filterValue) {
        this.setFilter(filterType, filterProp, filterValue);
        this.getState().filterRefresh = true;
    }

    @Override
    public AutoPopulatedVectorLayerState getState() {
        return AutoPopulatedVectorLayerState.class.cast(super.getState());
    }

    public interface FeatureSelectedListener {

        public final String EVENT_ID = "vsel";

        public final Method method = ReflectTools.findMethod(
          FeatureSelectedListener.class, "featureSelected",
          FeatureSelectedEvent.class);

        public void featureSelected(FeatureSelectedEvent event);

    }

    public void addListener(FeatureSelectedListener listener) {
        addListener(FeatureSelectedListener.EVENT_ID, FeatureSelectedEvent.class,
                listener, FeatureSelectedListener.method);
    }

    public void removeListener(FeatureSelectedListener listener) {
        removeListener(FeatureSelectedListener.EVENT_ID,
                FeatureSelectedEvent.class, listener);
    }

    public void addListener(BeforeFeatureSelectedListener listener) {
        addListener(BeforeFeatureSelectedListener.EVENT_ID, BeforeFeatureSelectedEvent.class,
                listener, BeforeFeatureSelectedListener.method);
    }

    public void removeListener(BeforeFeatureSelectedListener listener) {
        removeListener(BeforeFeatureSelectedListener.EVENT_ID,
                BeforeFeatureSelectedEvent.class, listener);
    }

    public class FeatureSelectedEvent extends Event {

        private String featureId;
        private Map<String, Object> attributes;
        private String wkt;

        public FeatureSelectedEvent(Component source, String featureId, Map<String, Object> attr, String wkt2) {
            super(source);
            this.setFeatureId(featureId);
            this.setAttributes(attr);
            this.setWkt(wkt2);
        }

        private void setWkt(String wkt2) {
            this.wkt = wkt2;
        }

        public void setAttributes(Map<String, Object> attr) {
            this.attributes = attr;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public String getFeatureId() {
            return featureId;
        }

        public void setFeatureId(String featureId) {
            this.featureId = featureId;
        }

        public String getWkt() {
            return wkt;
        }

    }

    public interface FeatureUnSelectedListener {

        public final String EVENT_ID = "vusel";

        public final Method method = ReflectTools.findMethod(
                FeatureUnSelectedListener.class, "featureUnSelected",
                FeatureUnSelectedEvent.class);

        public void featureUnSelected(FeatureUnSelectedEvent event);

    }

    public interface BeforeFeatureSelectedListener {

        public final String EVENT_ID = "vbefsel";

        public final Method method = ReflectTools.findMethod(
                BeforeFeatureSelectedListener.class, "beforeFeatureSelected",
                BeforeFeatureSelectedEvent.class);

        public boolean beforeFeatureSelected(BeforeFeatureSelectedEvent event);

    }

    public void addListener(FeatureUnSelectedListener listener) {
        addListener(FeatureUnSelectedListener.EVENT_ID,
                FeatureUnSelectedEvent.class, listener,
                FeatureUnSelectedListener.method);
    }

    public void removeListener(FeatureUnSelectedListener listener) {
        removeListener(FeatureUnSelectedListener.EVENT_ID,
                FeatureUnSelectedEvent.class, listener);
    }

    public class FeatureUnSelectedEvent extends FeatureSelectedEvent {

        public FeatureUnSelectedEvent(Component source, String featureId,Map<String, Object> attr, String wkt) {
            super(source, featureId, attr, wkt);
        }

    }

    public class BeforeFeatureSelectedEvent extends FeatureSelectedEvent {
        public BeforeFeatureSelectedEvent(Component source, String featureId,Map<String, Object> attr, String wkt) {
            super(source, featureId, attr, wkt);
        }
    }

}
