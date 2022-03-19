package tw.Max.RunCode;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import tw.Max.Class.myTabbedPane;
import tw.Max.Class.myTextArea;

public class TextEditor extends JFrame {
	private JButton addSheet, save, newSave, load, delSheet;
	private JPanel topPanel;
	private myTabbedPane tabbedPane;
	
	public TextEditor() {
		// 定義視窗
		super("Text Editor");
		setLayout(new BorderLayout());
		
		// 放置上方工具列
		topPanel = new JPanel(new FlowLayout());
		add(topPanel, BorderLayout.NORTH);
		
		// 視窗頁籤
		tabbedPane = new myTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
		
		// 新增頁籤
		addSheet = new JButton("New");
		topPanel.add(addSheet);	
		
		// 刪除頁籤
		delSheet = new JButton("Delete");
		topPanel.add(delSheet);

		// 儲存
		save = new JButton("Save");
		topPanel.add(save);
		
		// 另存新檔
		newSave = new JButton("Save As");
		topPanel.add(newSave);
		
		// 開啟舊檔
		load = new JButton("Load");
		topPanel.add(load);
		
		setSize(640, 480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setListener();
	}
	
	private void setListener() {
		// 新增頁籤
		addSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addSheet();
			}
		});
		
		// 刪除頁籤
		delSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delSheet();
			}
		});
		
		// 存檔
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 沒有頁籤的時候，不能執行儲存
				if(tabbedPane.getTabSize() > 0) {
					save();
				}
			}
		});
	
		// 另存新檔
		newSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 沒有頁籤的時候，不能執行儲存
				if(tabbedPane.getTabSize() > 0) {
					newSave();
				}
			}
		});
		
		// 讀取
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
	}
	
	private void addSheet() {
		tabbedPane.addNewTabs();
	}
	
	private void delSheet() {
		tabbedPane.delSheet();
	}
	
	private void save() {
		tabbedPane.saveTextArea();
	}
	
	private void newSave() {
		tabbedPane.newSave();
	}

	private void load() {
		tabbedPane.load();
	}
	
	public static void main(String[] args) {
		new TextEditor();
	}

}
