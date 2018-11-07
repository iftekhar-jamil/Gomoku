package kk;


import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JMenuBar;

public class FileFrameNew {

	public JFrame fileFrame;
	private JTree tree;
	private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
	private JScrollPane scrollPane;
	private String selectedDirectory;
	private String moveSelectedDirectory;
	private String moveFileName;
	private File workingDirectory;
	JPanel treePanel;
	JPanel filePanel;
	JPopupMenu filePopupMenu;
	JMenuItem pasteMenuItem;
	JLabel fileLabel;
	String userName;
	Font fn;
	
	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileFrameNew window = new FileFrameNew("iit");
					window.fileFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FileFrameNew(String str) throws FontFormatException, IOException {
		userName = str;
		initialize();
	}

	private void initialize() throws FontFormatException, IOException {
		fn = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Aquib\\Downloads\\kalpurush.ttf"));
		fn = fn.deriveFont(Font.BOLD, 15);
		moveSelectedDirectory = "";
		moveFileName = "";
		
		fileFrame = new JFrame(userName);
		fileFrame.setBounds(100, 100, 1000, 480);
		fileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fileFrame.getContentPane().setLayout(null);
		
		treePanel = new JPanel();
		treePanel.setFont(fn);
		treePanel.setBounds(0, 0, 340, 450);
		UIManager.put("ContentPane.Font", fn);
		UIManager.put("OptionPane.buttonFont", fn);
		fileFrame.getContentPane().add(treePanel);
		treePanel.setLayout(null);
		
		workingDirectory = new File("E:\\"+userName);
        createTree(workingDirectory);

        tree = new JTree(treeModel);
        tree.setForeground(new Color(240, 240, 240));
		tree.setRootVisible(false);
		tree.setFont(fn);
		tree.setBackground(new Color(240, 240, 240));
		tree.setBounds(0, 50, 340, 385);
		
		scrollPane = new JScrollPane(tree);
		
		JPopupMenu treePopupMenu = new JPopupMenu();
		addPopup(tree, treePopupMenu);
		treePopupMenu.setBounds(0, 0, 200, 50);
		
		JMenuItem refreshMenuItem = new JMenuItem("সতেজ করুন");
		refreshMenuItem.setFont(fn);
		refreshMenuItem.setBounds(0, 0, 129, 22);
		treePopupMenu.add(refreshMenuItem);
		scrollPane.setBounds(0, 30, 340, 415);
		treePanel.add(scrollPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 340, 32);
		treePanel.add(menuBar);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("আমাদের সম্পর্কে");
		mntmNewMenuItem.setFont(fn);
		menuBar.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("OptionPane.messageFont", fn);
				UIManager.put("OptionPane.buttonFont", fn);
				JOptionPane.showMessageDialog (null, "শুভ\nইউশা\nরেশাদ\nআকিব\nরাশেদ\nনিলয়\nসাব্বির\nমাহি\nসাফি", "সদস্যদের নাম", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JMenuItem mntmCredit = new JMenuItem("কৃতজ্ঞতা");
		mntmCredit.setFont(fn);
		menuBar.add(mntmCredit);
		menuBar.add(mntmNewMenuItem);
		mntmCredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("OptionPane.messageFont", fn);
				UIManager.put("OptionPane.buttonFont", fn);
				JOptionPane.showMessageDialog (null, "Md. Saeed Siddik\nLecturer\nIIT,DU", "কৃতজ্ঞতা", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		filePanel = new JPanel();
		filePanel.setBounds(350, 50, 650, 400);
		fileFrame.getContentPane().add(filePanel);
		filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		filePopupMenu = new JPopupMenu();
		filePopupMenu.setBounds(0, 0, 80, 94);
		
		JMenuItem openMenuItem = new JMenuItem("খুলুন");
		openMenuItem.setFont(fn);
		openMenuItem.setBounds(0, 22, 140, 22);
		filePopupMenu.add(openMenuItem);
		
		JMenuItem removeMenuItem = new JMenuItem("মুছে ফেলুন");
		removeMenuItem.setFont(fn);
		removeMenuItem.setBounds(0, 22, 140, 22);
		filePopupMenu.add(removeMenuItem);
		
		JMenuItem renameMenuItem = new JMenuItem("নাম পরিবর্তন করুন");
		renameMenuItem.setFont(fn);
		renameMenuItem.setBounds(0, 44, 140, 22);
		filePopupMenu.add(renameMenuItem);
		
		JMenuItem cutMenuItem = new JMenuItem("কাট করুন");
		cutMenuItem.setFont(fn);
		cutMenuItem.setBounds(0, 66, 140, 22);
		filePopupMenu.add(cutMenuItem);
		
		JMenuItem copyMenuItem = new JMenuItem("অনুলিপি করুন");
		copyMenuItem.setFont(fn);
		copyMenuItem.setBounds(0, 66, 140, 22);
		filePopupMenu.add(copyMenuItem);
		
		createFilesAndFolders(workingDirectory.getAbsolutePath(), filePanel, filePopupMenu);
		
		refreshMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tree.setVisible(false);
				treePanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				createTree(workingDirectory);
				tree.setModel(treeModel);
				tree.setVisible(true);
				treePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		fileFrame.setResizable(false);
		
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBounds(0, 0, 133, 94);
		filePanel.add(popupMenu);
		
		JMenuItem chooserRefreshMenuItem = new JMenuItem("সতেজ করুন");
		chooserRefreshMenuItem.setFont(fn);
		chooserRefreshMenuItem.setBounds(0, 0, 140, 22);
		popupMenu.add(chooserRefreshMenuItem);
		
		JMenuItem createFolderMenuItem = new JMenuItem("ফোল্ডার সংযুক্ত করুন");
		createFolderMenuItem.setFont(fn);
		createFolderMenuItem.setBounds(0, 22, 140, 22);
		popupMenu.add(createFolderMenuItem);
		
		JMenuItem createDocumentMenuItem = new JMenuItem("ফাইল সংযুক্ত করুন");
		createDocumentMenuItem.setFont(fn);
		createDocumentMenuItem.setBounds(0, 44, 140, 22);
		popupMenu.add(createDocumentMenuItem);
		
		pasteMenuItem = new JMenuItem("পেস্ট করুন");
		pasteMenuItem.setFont(fn);
		pasteMenuItem.setBounds(0, 66, 140, 22);
		pasteMenuItem.setEnabled(false);
		popupMenu.add(pasteMenuItem);
		
		JButton homeButton = new JButton("হোম");
		homeButton.setFont(fn);
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				workingDirectory = new File("E:\\"+userName);
				createFilesAndFolders(workingDirectory.getAbsolutePath(), filePanel, filePopupMenu);
		        createTree(workingDirectory);
			}
		});
		homeButton.setBounds(600, 12, 90, 25);
		fileFrame.getContentPane().add(homeButton);
		
		chooserRefreshMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				createFilesAndFolders(workingDirectory.getAbsolutePath(), filePanel, filePopupMenu);
			}
		});
		
		pasteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("ContentPane.Font", fn);
				UIManager.put("OptionPane.buttonFont", fn);
				String str = workingDirectory.getAbsolutePath()+"\\";
				File file = new File (moveSelectedDirectory);
				File newFile = new File (str+moveFileName);
				if (newFile.exists()) JOptionPane.showMessageDialog(null, "দুঃখিত।একই নামে আরেকটি ফাইল রয়েছে");
				else file.renameTo(newFile);
				createFilesAndFolders(str, filePanel, filePopupMenu);
				moveSelectedDirectory = "";
				moveFileName = "";
				pasteMenuItem.setEnabled(false);
			}
		});
		
		removeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("OptionPane.messageFont", fn);
				UIManager.put("OptionPane.buttonFont", fn);
				UIManager.put("TextField.font", fn );
				String str = workingDirectory.getAbsolutePath()+"\\"+selectedDirectory;
				File file = new File (str);
				if (file.delete()) JOptionPane.showMessageDialog(null, selectedDirectory + " সফল্ভাবে মোছা হয়েছে");;
				createFilesAndFolders(workingDirectory.getAbsolutePath(), filePanel, filePopupMenu);
			}
		});
		copyMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File f = new File(workingDirectory.getAbsolutePath()+"\\"+selectedDirectory);
				
				
			}
		});
		//File f = new File(selectedDirectory);
		
		cutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveSelectedDirectory = workingDirectory.getAbsolutePath()+"\\"+selectedDirectory;
				moveFileName = selectedDirectory;
				pasteMenuItem.setEnabled(true);
			}
		});
		
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = workingDirectory.getAbsolutePath()+"\\"+selectedDirectory;
				File file = new File (str);
				if (file.isDirectory()) {
					createFilesAndFolders (str, filePanel, filePopupMenu);
					workingDirectory = file;
				}
				
				else {
					ProcessBuilder pb = new ProcessBuilder("Notepad.exe", file.getAbsolutePath());
					try {
						pb.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		renameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("OptionPane.messageFont", fn);
				UIManager.put("OptionPane.buttonFont", fn);
				UIManager.put("TextField.font", fn );
				String str = workingDirectory.getAbsolutePath()+"\\";
				File file = new File (str+selectedDirectory);
				String newName = JOptionPane.showInputDialog(null, "নতুন নাম লিখুন");
				File newFile = new File (str+newName);
				if (newFile.exists()) JOptionPane.showMessageDialog(null, "দুঃখিত। অন্য নাম ব্যবহার করুন");
				else file.renameTo(newFile);
				createFilesAndFolders(str, filePanel, filePopupMenu);
			}
		});
		
		createFolderMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("OptionPane.messageFont", fn);
				UIManager.put("OptionPane.buttonFont", fn);
				UIManager.put("TextField.font", fn );
				
				String name = JOptionPane.showInputDialog(null, "ফোল্ডার এর নাম লিখুন:");
				String dir = workingDirectory.getAbsolutePath();
				File fileName = new File(dir+"/"+name);
				if (!fileName.exists()) fileName.mkdir();
				else JOptionPane.showMessageDialog(null, "দুঃখিত। এই নামে ফোল্ডার রয়েছে");
				createFilesAndFolders(workingDirectory.getAbsolutePath(), filePanel, filePopupMenu);
			}
		});
		
		createDocumentMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("OptionPane.messageFont", fn);
				UIManager.put("OptionPane.buttonFont", fn);
				UIManager.put("TextField.font", fn );
				
				String name = JOptionPane.showInputDialog(null, "ফাইল এর নাম লিখুন:");
				String dir = workingDirectory.getAbsolutePath();
				File fileName = new File(dir+"/"+name);
				if (!fileName.exists()) {
					try {
						if (!fileName.createNewFile()) JOptionPane.showMessageDialog(null, "ভুল হয়েছে");;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else JOptionPane.showMessageDialog(null, "দুঃখিত। এই নামে ফোল্ডার রয়েছে");
				createFilesAndFolders(workingDirectory.getAbsolutePath(), filePanel, filePopupMenu);
			}
		});
		
		JButton logoutButton = new JButton("লগ আউট");
		logoutButton.setFont(fn);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//connector.close();
				JFrame temp = null;
				try {
					temp = new UserInterface().loginFrame;
				} catch (FontFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileFrame.setVisible(false);
				temp.setVisible(true);
			}
		});
		
		logoutButton.setBounds(700, 12, 90, 25);
		fileFrame.getContentPane().add(logoutButton);
		
		
		addPopup(filePanel, popupMenu);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void createTree(File directory) {
        root = new DefaultMutableTreeNode();
        treeModel = new DefaultTreeModel(root);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(directory.getName());

        root.add(node);
        populate(directory, node);
        
    }

    private void populate(File directory, DefaultMutableTreeNode node) {
        String[] files = directory.list();
        for(String file : files) {
            File currentFile = new File(directory, file);
            addLeaf(node, currentFile);
        }
    }
    
    private void createFilesAndFolders (String directory, JPanel c, JPopupMenu popup) {
    	c.removeAll();
    	
    	File currentFile = new File(directory);
    	String[] files = currentFile.list();
    	//img = ImageIO.read(new File("/" + picName + ".png"));

    	ImageIcon fileIcon = new ImageIcon("G:\\Eclipse\\RescueOS\\Resource\\file-icon.png");
    	ImageIcon folderIcon = new ImageIcon("G:\\Eclipse\\RescueOS\\Resource\\folder-icon.png");
    	
    	//Image fileIcon = ImageIO.read(new File("/Resource/file-icon.png"));
    	//ImageIcon folderIcon = ImageIO.read(new File("/Resource/folder-icon.png"));
    	
    	for(String file : files) {
    		File tempFile = new File(currentFile, file);
    		final JLabel fileLabel = new JLabel (file);
    		fileLabel.setFont(fn);
    		//fileLabel.add(popup);
    		fileLabel.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					selectedDirectory = fileLabel.getText();
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					selectedDirectory = fileLabel.getText();
				}
				
				@Override
				public void mouseClicked(MouseEvent event) {
					
					if (event.getClickCount() == 2) {
					    System.out.println("double clicked");
						String str = workingDirectory.getAbsolutePath()+"\\"+fileLabel.getText();
						System.out.println(str);
						File file = new File (str);
						if (file.isDirectory()) {
							createFilesAndFolders (str, filePanel, filePopupMenu);
							workingDirectory = file;
						}
							
						else {
							ProcessBuilder pb = new ProcessBuilder("Notepad.exe", file.getAbsolutePath());
							try {
								pb.start();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					
					else selectedDirectory = fileLabel.getText();
				}
			});
    		
    		addPopup(fileLabel, popup);
    		if (tempFile.isFile()) {
    			System.out.println(tempFile.getName() + " is a file.");
    			fileLabel.setIcon(fileIcon);
    			c.add(fileLabel);
    		}
    		else {
    			System.out.println(tempFile.getName() + " is a folder.");
    			fileLabel.setIcon(folderIcon);
    			c.add(fileLabel);
    		}
        }
    	c.setVisible(false);
    	c.setVisible(true);
    	
    	tree.setVisible(false);
		treePanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		createTree(workingDirectory);
		tree.setModel(treeModel);
		tree.setVisible(true);
		treePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    private void addLeaf(DefaultMutableTreeNode node, File currentFile) {
        if(currentFile.isFile() && !currentFile.isHidden()) {
            DefaultMutableTreeNode leafFile = new DefaultMutableTreeNode(currentFile.getName());
            node.add(leafFile);
        }
        if(currentFile.isDirectory() && !currentFile.isHidden()) {
            DefaultMutableTreeNode folder = new DefaultMutableTreeNode(currentFile.getName());
            node.add(folder);
            populate(currentFile, folder);
        }
    }
}
