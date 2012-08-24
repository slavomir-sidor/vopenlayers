package org.vaadin.vol.client.ui.vcom;

import org.vaadin.vol.GoogleTerrainMapLayer;
import org.vaadin.vol.client.ui.VGoogleTerrainMapLayer;

import com.vaadin.shared.ui.Connect;
import com.vaadin.terminal.gwt.client.ui.Vaadin6Connector;

@Connect(GoogleTerrainMapLayer.class)
public class GoogleTerrainMapLayerConnector extends Vaadin6Connector {

    @Override
    public VGoogleTerrainMapLayer getWidget() {
        return (VGoogleTerrainMapLayer) super.getWidget();
    }
    
}
