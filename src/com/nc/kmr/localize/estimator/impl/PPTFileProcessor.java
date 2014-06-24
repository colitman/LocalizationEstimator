package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hslf.model.Notes;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.record.TextHeaderAtom;
import org.apache.poi.hslf.usermodel.SlideShow;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.util.Utils;

public class PPTFileProcessor implements FileProcessor {
	
	private File file;
	private FileInputStream fis;
	private SlideShow sh;
	private boolean ready = false;
	private Exception notReadyException;
	private boolean text;
	private boolean titles;
	private boolean notS;
	private boolean targetMode;
	private String scope;
	private int processingMode;
	private List<String> content;
	
	private Slide[] slides;
//	private Notes[] notes;
	private boolean wasEmpty = false;

	public PPTFileProcessor(File[] files) {
		this.file = files[0];
		init();
	}

	private void init() {
		try {
			fis = new FileInputStream(file);
			sh = new SlideShow(fis);
			fis.close();
			slides = sh.getSlides();
//			notes = sh.getNotes();
			setScope("");
			ready = true;
		} catch (IOException e) {
			// TODO Add logging
			notReadyException = e;
		}
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public Exception getNotReadyException() {
		return notReadyException;
	}

	@Override
	public String[] getTargets() {
		String[] targets = new String[3];
		
		targets[0] = "Slide text";
		targets[1] = "Slide titles";
		targets[2] = "Slide notes";
		
		return targets;
	}

	@Override
	public boolean setTarget(String... target) {
		
		text = false;
		titles = false;
		notS = false;
		targetMode = false;
		
		if(target == null || target.length == 0) {
			targetMode = false;
			return false;
		}
		
		for(String s:target) {
			if("TEXT".equals(s)) {
				text = true;
				targetMode = true;
			}
			
			if("TITLES".equals(s)) {
				titles = true;
				targetMode = true;
			}
			
			if("NOTES".equals(s)) {
				notS = true;
				targetMode = true;
			}
		}
		
		return true;
	}

	@Override
	public String getTarget() {
		String target = "";
		
		if(text) {
			target = target + "Text";
		}
		
		if(titles) {
			target = (target.isEmpty())? "Titles": target + ", Titles";
		}
		
		if(notS) {
			target = (target.isEmpty())? "Notes": target + ", Notes";
		}
		
		return target;
	}

	@Override
	public boolean setScope(String scope) {
		if((!scope.matches("\\d+((,\\d+)|(-\\d+))*") || scope == null) && !scope.isEmpty()) {
			scope = null;
			wasEmpty = false;
			return false;
		}
		
		if(scope.isEmpty()) {
			this.scope = "1-" + slides.length;
			wasEmpty = true;
			return true;
		}
		
		this.scope = scope;
		wasEmpty = false;
		
		return true;
	}

	@Override
	public String getScope() {
		return (processingMode == Utils.EXCLUDE_SCOPE)? (wasEmpty)? scope:scope + " (excluded)": scope;
	}

	@Override
	public String getFileName() {
		String path = null;
		
		try {
			path = file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Add logging
			e.printStackTrace();
		}
		
		return path;
	}

	@Override
	public String getSimpleFileName() {
		return file.getName();
	}

	@Override
	public boolean setScopeProcessingMode(int processingMode) {
		if(processingMode < -1 || processingMode > 1) {
			this.processingMode = -10000;
			return false;
		}
		
		this.processingMode = processingMode;
		return true;
	}

	@Override
	public int getScopeProcessingMode() {
		return processingMode ;
	}

	@Override
	public List<String> process() {
		// TODO Auto-generated method stub
		content = new ArrayList<String>();
		if(!ready || !targetMode || scope == null) {
			return content;
		}
		
		List<Integer> pages = processScope();
		
		for(Integer page:pages) {
			
			if(page <= 0) {
				continue;
			}
			
			if(page > slides.length) {
				break;
			}
			
			Slide slide = slides[page - 1];
			
			if(text) {
				TextRun[] textRuns = slide.getTextRuns();
				for(TextRun textRun:textRuns) {
					if(textRun.getRunType() != TextHeaderAtom.TITLE_TYPE && textRun.getRunType() != TextHeaderAtom.CENTER_TITLE_TYPE) {
						String textItem = textRun.getText();
						String[] sentences = textItem.split("\\. ");
						for(String s:sentences) {
							if(s != null && !s.isEmpty()) {
								System.out.println(slide.getSlideNumber() + ": " + s);
								content.add(s);
							}
						}
					}
				}
			}
			
			if(titles) {
				String title = slide.getTitle();
				
				String[] sentences = title.split("\\. ");
				for(String s:sentences) {
					if(s != null && !s.isEmpty()) {
						System.out.println(slide.getSlideNumber() + ": " + s);
						content.add(s);
					}
				}
			}
			
			if(notS) {
				Notes note = slide.getNotesSheet();
				if(note != null) {
					TextRun[] textRuns = note.getTextRuns();
					for(TextRun textRun:textRuns) {
						if(textRun.getRunType() != TextHeaderAtom.TITLE_TYPE && textRun.getRunType() != TextHeaderAtom.CENTER_TITLE_TYPE) {
							String textItem = textRun.getText();
							String[] sentences = textItem.split("\\. ");
							for(String s:sentences) {
								if(s != null && !s.isEmpty() && !"*".equals(s)) {
									System.out.println(slide.getSlideNumber() + ": " + s);
									content.add(s);
								}
							}
						}
					}
				}
			}
		}
		
		return content;
	}

	private List<Integer> processScope() {
		// TODO Auto-generated method stub
		List<Integer> pages = new ArrayList<Integer>();
		
		String[] scopeParts = scope.split(",");
		
		if(processingMode == Utils.INCLUDE_SCOPE || processingMode == Utils.NO_SCOPE_DEFINED) {
			for(String s:scopeParts) {
				if(!s.matches("\\d+")) {
					String[] pageParts = s.split("-");
					for(int i = Integer.valueOf(pageParts[0]); i <= Integer.valueOf(pageParts[1]); i++) {
						pages.add(i);
					}
				} else {
					pages.add(Integer.valueOf(s));
				}
			}
		} else if(processingMode == Utils.EXCLUDE_SCOPE) {
			
			for(int i = 1; i <= slides.length; i++) {
				pages.add(i);
			}
			
			if(wasEmpty) {
				return pages;
			}
			
			for(String s:scopeParts) {
				if(!s.matches("\\d+")) {
					String[] pageParts = s.split("-");
					for(int i = Integer.valueOf(pageParts[0]); i <= Integer.valueOf(pageParts[1]); i++) {
						pages.remove(pages.indexOf(i));
					}
				} else {
					pages.remove(pages.indexOf(Integer.valueOf(s)));
				}
			}
		}
		

		return pages;
	}

	@Override
	public void releaseResource() {
		// TODO Auto-generated method stub

	}

}
