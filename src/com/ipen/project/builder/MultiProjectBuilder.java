package com.ipen.project.builder;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MultiProjectBuilder extends JFrame implements ActionListener {
    JLabel lblProjectPath;
    JTextField txtFilePath;
    JButton go;
    JButton btnAddToDocker;
    JButton devDeploy;
    JButton prodDeploy;
    
    JMenuBar mb;
	JMenu file;
	JMenuItem singleProject;
	JMenuItem multiProject;
    
    private static MultiProjectBuilder instance = null;

	public static MultiProjectBuilder getInstance() {
		if (instance == null) {
			instance = new MultiProjectBuilder();
		}
		return instance;
	}

    private MultiProjectBuilder() {
    	
    	mb = new JMenuBar();
		file = new JMenu("File");

		singleProject = new JMenuItem("Single Project Builder");
		multiProject = new JMenuItem("Multi Project Builder");

		file.add(singleProject);
		file.add(multiProject);
		mb.add(file);
		this.setJMenuBar(mb);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources//image.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(550, 550, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		setContentPane(new JLabel(imageIcon));
    	
    	this.setTitle("Multiple Project Builder");
		this.setMinimumSize(new Dimension(550, 0));
		this.setMaximumSize(new Dimension(450, Integer.MAX_VALUE));
		this.setResizable(false);
    	
    	MultiProjectLayout customLayout = new MultiProjectLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);

        lblProjectPath = new JLabel("Select File Path ");
        getContentPane().add(lblProjectPath);

        txtFilePath = new JTextField("");
        getContentPane().add(txtFilePath);

        Icon warnIcon = new ImageIcon("resources//open.gif");
		go = new JButton(warnIcon);
		getContentPane().add(go);
		go.addActionListener(this);

        btnAddToDocker = new JButton("Add To Docker");
        getContentPane().add(btnAddToDocker);

        devDeploy = new JButton("Dev Deploy ");
        getContentPane().add(devDeploy);

        prodDeploy = new JButton("Prod Deploy");
        getContentPane().add(prodDeploy);

        setSize(getPreferredSize());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        singleProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultiProjectBuilder.getInstance().setVisible(false);
				SingleProjectBuilder.getInstance().setVisible(true);
			}
		});

		multiProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingleProjectBuilder.getInstance().setVisible(false);
				MultiProjectBuilder.getInstance().setVisible(true);
			}
		});
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
