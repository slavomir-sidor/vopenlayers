package org.vaadin.vol.client.wrappers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class TileMapServiceLayer extends Layer {

	protected TileMapServiceLayer() {
	};

	public native final static TileMapServiceLayer create(String display,
			String url, boolean isBaseLayer, JavaScriptObject getUrlMethod)
	/*-{
	 	debugger;
		var params = {};
 		params.getURL = getUrlMethod;
//		params.transparent = true;
//		params.alpha = true;
		params.type = "png";
		params.isBaseLayer = isBaseLayer;
		params.sphericalMercator = true;
		return new $wnd.OpenLayers.Layer.TMS(display, url, params);
	}-*/;

	public native final void setTileOrigin(LonLat bottomLeft) 
	/*-{
		this.tileOrigin = bottomLeft;
	}-*/;

}
