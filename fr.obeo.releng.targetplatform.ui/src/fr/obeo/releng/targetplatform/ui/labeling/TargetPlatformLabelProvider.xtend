/*
* generated by Xtext
*/
package fr.obeo.releng.targetplatform.ui.labeling

import com.google.inject.Inject
import fr.obeo.releng.targetplatform.targetplatform.IU
import fr.obeo.releng.targetplatform.targetplatform.Location
import fr.obeo.releng.targetplatform.targetplatform.TargetPlatform
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.viewers.StyledString
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 */
class TargetPlatformLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	def text(Location object) {
		val ss = new StyledString();
		ss.append(object.getUri());
		if (object.getId() != null) {
			ss.append(" " + object.getId(), StyledString.DECORATIONS_STYLER);
		}
		ss;
	}
	
	def image(Location object) {
		"obj16/location_obj.gif";
	}
	
	def text(IU object) {
		val ss = new StyledString();
		ss.append(object.getID());
		if (object.getVersion() != null) {
			ss.append(" " + object.getVersion(), StyledString.COUNTER_STYLER);
		}
		ss;
	}
	
	def image(IU object) {
		"obj16/iu_obj.gif";
	}
	
	def text(TargetPlatform object) {
		object.getName();
	}
	
	def image(TargetPlatform object) {
		"obj16/target_obj.gif";
	}
}
