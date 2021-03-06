/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.xtext.ui.testing;

import java.lang.reflect.Field;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ErrorEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;

/**
 * @author Peter Friese - Initial contribution and API
 * 
 * @since 2.12
 */
public abstract class AbstractEditorTest extends AbstractWorkbenchTest {

	static final long STEP_DELAY = 0;
	
	protected abstract String getEditorId();

	protected XtextEditor openEditor(IFile file) throws Exception {
		IEditorPart openEditor = openEditor(file, getEditorId());
		XtextEditor xtextEditor = EditorUtils.getXtextEditor(openEditor);
		if (xtextEditor != null) {
			ISourceViewer sourceViewer = xtextEditor.getInternalSourceViewer();
			((ProjectionViewer)sourceViewer).doOperation(ProjectionViewer.EXPAND_ALL);
			return xtextEditor;
		}
		else if (openEditor instanceof ErrorEditorPart) {
			Field field = openEditor.getClass().getDeclaredField("error");
			field.setAccessible(true);
			throw new IllegalStateException("Couldn't open the editor.",((Status)field.get(openEditor)).getException());
		}
		else {
			fail("Opened Editor with id:" + getEditorId() + ", is not an XtextEditor");
		}
		return null;
	}

	private IEditorPart openEditor(IFile file, String editorId) throws PartInitException {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
				new FileEditorInput(file), editorId);
	}

}