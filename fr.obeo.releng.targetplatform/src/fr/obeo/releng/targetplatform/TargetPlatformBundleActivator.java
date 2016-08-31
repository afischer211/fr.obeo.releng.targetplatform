/*******************************************************************************
 * Copyright (c) 2012-2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package fr.obeo.releng.targetplatform;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.IProvisioningAgentProvider;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;


/**
 * @author <a href="mailto:mikael.barbero@obeo.fr">Mikael Barbero</a>
 *
 */
public class TargetPlatformBundleActivator extends Plugin {

	public static final String PLUGIN_ID = "fr.obeo.releng.targetplatform";
	
	public static final String TARGET_PLATFORM_LANGUAGE_NAME = "fr.obeo.releng.TargetPlatform";
		
	private static final Logger logger = LoggerFactory.getLogger(TargetPlatformBundleActivator.class);

	private static TargetPlatformBundleActivator INSTANCE;
	
	private Map<String, Injector> injectors;

	private BundleContext context;
	
	private IProvisioningAgent agent;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		INSTANCE = this;
		this.context = context;
		injectors = Collections.synchronizedMap(Maps.<String, Injector> newHashMapWithExpectedSize(1));
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		IProvisioningAgent oldAgent = agent;
		agent = null;
		if (oldAgent != null) {
			oldAgent.stop();
		}
		
		injectors.clear();
		
		context = null;
		INSTANCE = null;
		
		super.stop(context);
	}
	
	public static TargetPlatformBundleActivator getInstance() {
		return INSTANCE;
	}

	public Injector getInjector(String language) {
		synchronized (injectors) {
			Injector injector = injectors.get(language);
			if (injector == null) {
				injectors.put(language, injector = createInjector(language));
			}
			return injector;
		}
	}
	
	protected Injector createInjector(String language) {
		try {
			Module runtimeModule = getRuntimeModule(language);
			return Guice.createInjector(runtimeModule);
		} catch (Exception e) {
			logger.error("Failed to create injector for " + language);
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Failed to create injector for " + language, e);
		}
	}
	
	public IProvisioningAgent getProvisioningAgent() {
		if (agent == null) {
			ServiceReference<?> serviceReference = context.getServiceReference(IProvisioningAgentProvider.class.getName());
			if (serviceReference != null) {
				IProvisioningAgentProvider agentProvider = (IProvisioningAgentProvider) context.getService(serviceReference);
				if (agentProvider != null) {
					try {
						agent = agentProvider.createAgent(getStateLocation().toFile().toURI());
					} catch (ProvisionException e) {
						getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
					} catch (IllegalStateException e) {
						getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
					}
				}
				context.ungetService(serviceReference);
			}
		}
		return agent;
	}
	
	protected Module getRuntimeModule(String grammar) {
		if (TARGET_PLATFORM_LANGUAGE_NAME.equals(grammar)) {
			return new TargetPlatformRuntimeModule();
		}
		
		throw new IllegalArgumentException(grammar);
	}
	
}
