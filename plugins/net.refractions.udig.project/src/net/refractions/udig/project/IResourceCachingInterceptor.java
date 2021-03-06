/* uDig - User Friendly Desktop Internet GIS client
 * http://udig.refractions.net
 * (C) 2004, Refractions Research Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * (http://www.eclipse.org/legal/epl-v10.html), and the Refractions BSD
 * License v1.0 (http://udig.refractions.net/files/bsd3-v10.html).
 */
package net.refractions.udig.project;

import net.refractions.udig.catalog.IGeoResource;

/**
 * Controls the caching of resources returned by:
 * {@link ILayer#getResource(Class, org.eclipse.core.runtime.IProgressMonitor)}.
 * <p>
 *    Example: 
 *    If a new feature store was returned each time then every plugin that is interested in 
 *    events would need to create a resource interceptor and every request for a FeatureStore 
 *    would result in both a new FeatureStore and a new listener for every interested plugin.  
 *    Since the Datastore's Listener manager often keeps the listeners indefinately then we 
 *    would very quickly have 10s to 100s of listeners that can't be garbage collected and 
 *    possibly featurestores as well.
 * </p>
 * <p>
 * The "active" caching strategy is stored in the 
 *  ProjectPlugin.getPlugin().getPreferenceStore().getString( "P_LAYER_RESOURCE_CACHING_STRATEGY" ). 
 *  </p><p>
 *  Set the preference to activate a custom Caching strategy.
 * </p>
 * @author Jesse
 * @since 1.1.0
 */
public interface IResourceCachingInterceptor {
    /**
     * Called to see if the resource should be obtained from cache
     *
     * @param <T> the type of the resource.  Can be most any type of Object
     * @param layer the layer that the resource is being obtained from
     * @param geoResource the resource obtained from the GeoResource.  <b>NO</b> interceptors have ran on the resource.
     * @param requestedType the type of object that the caller requested
     * @return true if the resource should be obtained from the cache.
     */
    <T> boolean isCached(ILayer layer, IGeoResource geoResource, Class<T> requestedType);
    /**
     * Obtains the resource from the cache.
     *
     * @param <T>  the type of the resource.  Can be most any type of Object
     * @param layer the layer that the resource is being obtained from
     * @param requestedType the type of object that the caller requested
     * @return the cached instance of the resource.
     */
    <T> T get( ILayer layer, Class<T> requestedType ) ;
    /**
     * Caches the resource. 
     *
     * @param <T> the type of the resource.  Can be most any type of Object
     * @param layer the layer that the resource is being obtained from
     * @param resource the resource obtained from the GeoResource.  All the PRE interceptors have ran on the resource.
     * @param requestedType the type of object that the caller requested
     */
    <T> void put( ILayer layer, T resource, Class<T> requestedType ) ;
    
    
}
