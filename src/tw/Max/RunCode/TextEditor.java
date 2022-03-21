package tw.Max.RunCode;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import tw.Max.Class.myComboBox;
import tw.Max.Class.myTabbedPane;
import tw.Max.Class.myTextArea;

public class TextEditor extends JFrame {
	private JMenuBar menuBar;
	private JButton addSheet, save, newSave, load, delSheet;
	private myComboBox colorComboBox, fontComboBox, sizeComboBox, fontＷeightComboBox;
	private JPanel topPanel, mainPanel, textPanel;
	private myTabbedPane tabbedPane;
	
	public TextEditor() {
		// 定義視窗
		super("Text Editor");
		setLayout(new BorderLayout());
		
		// 選單列
		menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);
		
		// 主畫面
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		
		// 放置主畫面上方 => 工具列
		topPanel = new JPanel(new FlowLayout());
//		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new FlowLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		
		// 字體調整
		fontComboBox = new myComboBox();
		fontComboBox.addItem("--字體調整--");
		fontComboBox.addItem("新細明體");
		fontComboBox.addItem("標楷體");
		fontComboBox.addItem("細明體");
		fontComboBox.addItem("Arial");
		fontComboBox.addItem("Calibri");
		fontComboBox.addItem("Lucida Grande");
		topPanel.add(fontComboBox);
		
		// 字體大小
		sizeComboBox = new myComboBox();
		sizeComboBox.addItem("--字體大小--");
		sizeComboBox.addItem("9");
		sizeComboBox.addItem("10");
		sizeComboBox.addItem("11");
		sizeComboBox.addItem("12");
		sizeComboBox.addItem("16");
		sizeComboBox.addItem("24");
		sizeComboBox.addItem("36");
		topPanel.add(sizeComboBox);
		
		// 字體粗細
		fontＷeightComboBox = new myComboBox();
		fontＷeightComboBox.addItem("--字體粗細--");
		fontＷeightComboBox.addItem("粗");
		fontＷeightComboBox.addItem("正常");
		fontＷeightComboBox.addItem("細");
		topPanel.add(fontＷeightComboBox);
		
		// 字體顏色
		colorComboBox = new myComboBox();
		colorComboBox.addItem("--字體顏色--");
		colorComboBox.addItem("紅");
		colorComboBox.addItem("藍");
		colorComboBox.addItem("黑");
		topPanel.add(colorComboBox);
		
		// textarea, tabs
		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		mainPanel.add(textPanel, BorderLayout.CENTER);
		
		// 視窗頁籤
		tabbedPane = new myTabbedPane();
//		add(tabbedPane, BorderLayout.CENTER);
		textPanel.add(tabbedPane, BorderLayout.CENTER);
		
		// 新增頁籤
		addSheet = new JButton("New");
//		topPanel.add(addSheet);	
		menuBar.add(addSheet);
		
		// 刪除頁籤
		delSheet = new JButton("Delete");
//		topPanel.add(delSheet);
		menuBar.add(delSheet);

		// 儲存
		save = new JButton("Save");
//		topPanel.add(save);
		menuBar.add(save);
		
		// 另存新檔
		newSave = new JButton("Save As");
//		topPanel.add(newSave);
		menuBar.add(newSave);
		
		// 開啟舊檔
		load = new JButton("Load");
//		topPanel.add(load);
		menuBar.add(load);
		
		setSize(640, 480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setListener();
	}
	
	private void setListener() {
		// 字體調整
		fontComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setTextAreaFont();
//				fontComboBox.getSelectedItem()
//				System.out.println(fontComboBox.getSelectedItem().toString());
			}
		});
		
		// 字體大小
		sizeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setTextAreaFontSize();
			}
		});
		
		// 字體粗細
		fontＷeightComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// 字體顏色
		colorComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setTextAreaFontColor();
			}
		});
		
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
	
	private void setTextAreaFont() {
		String font = fontComboBox.getSelectedItem().toString();
		tabbedPane.setTextAreaFont(font);
	}
	
	private void setTextAreaFontSize() {
		String fontSize = sizeComboBox.getSelectedItem().toString();
		tabbedPane.setTextAreaFontSize(fontSize);
	}
	
	private void setTextAreaFontColor() {
		String fontColor = colorComboBox.getSelectedItem().toString();
		tabbedPane.setTextAreaFontColor(fontColor);
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
