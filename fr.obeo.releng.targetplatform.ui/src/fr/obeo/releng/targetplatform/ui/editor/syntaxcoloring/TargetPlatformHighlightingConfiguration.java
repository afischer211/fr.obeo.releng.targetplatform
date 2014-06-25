package fr.obeo.releng.targetplatform.ui.editor.syntaxcoloring;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class TargetPlatformHighlightingConfiguration extends DefaultHighlightingConfiguration {

	public static final String VERSION_RANGE_ID = "versionRange";
	public static final String OPTION_ID = "option";
	
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(KEYWORD_ID, "Keyword", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(COMMENT_ID, "Comment", commentTextStyle());
		acceptor.acceptDefaultHighlighting(TASK_ID, "Task Tag", taskTextStyle());
		acceptor.acceptDefaultHighlighting(STRING_ID, "String", stringTextStyle());
		acceptor.acceptDefaultHighlighting(DEFAULT_ID, "Default", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(OPTION_ID, "Option", optionTextStyle());
		acceptor.acceptDefaultHighlighting(VERSION_RANGE_ID, "Version Range", versionRangeTextStyle());
		acceptor.acceptDefaultHighlighting(INVALID_TOKEN_ID, "Invalid Symbol", errorTextStyle());
	}
	
	public TextStyle optionTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(125, 125, 125));
		return textStyle;
	}

	public TextStyle versionRangeTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(JFaceResources.getColorRegistry().getRGB(JFacePreferences.DECORATIONS_COLOR));
		return textStyle;
	}
}
