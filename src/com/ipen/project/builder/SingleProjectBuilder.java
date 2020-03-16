package com.ipen.project.builder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

@SuppressWarnings("serial")
public class SingleProjectBuilder extends JFrame implements ActionListener {
	
	private static SingleProjectBuilder instance = null;

	public static SingleProjectBuilder getInstance() {
		if (instance == null) {
			instance = new SingleProjectBuilder();
		}
		return instance;
	}

	JMenuBar mb;
	JMenu file;
	JMenuItem singleProject;
	JMenuItem multiProject;

	JLabel lblProjectName;
	JTextField txtProjectName;
	JLabel lblProjectPath;
	JButton go;
	JLabel lblDatabaseList;
	JComboBox<String> cbDatabaseNames;
	JButton buildBtn;
	JLabel lblProjectType;
	JComboBox<String> cbProjectType;
	JLabel lblPackageName;
	JTextField txtPackageName;
	JButton btnTest;
	JLabel lblMsg;
	JPanel panel_1;
	JTextField txtFilePath;
	JLabel lblCache;
	JComboBox<String> cbCacheProvider;
	JButton button_1;
	JFileChooser chooser;
	JButton btnAddToDocker;
	JButton deployLocal;
	JButton devDeploy;
	JButton prodDeploy;

	String targetFileName;
	String choosertitle;
	String directoryDir;
	String selectedDir = "";
	String projectName = "";

	private SingleProjectBuilder() {
		
		this.setTitle("Project Builder");
		this.setMinimumSize(new Dimension(550, 0));
		this.setMaximumSize(new Dimension(450, Integer.MAX_VALUE));
		this.setResizable(false);
		
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
		Border blackline = BorderFactory.createLineBorder(Color.black);
		SingleProjectBuilderLayout customLayout = new SingleProjectBuilderLayout();

		getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
		getContentPane().setLayout(customLayout);

		lblProjectName = new JLabel("Project Name");
		getContentPane().add(lblProjectName);

		txtProjectName = new JTextField("");
		getContentPane().add(txtProjectName);

		lblProjectPath = new JLabel("Select Your Project");
		getContentPane().add(lblProjectPath);

		Icon warnIcon = new ImageIcon("resources//open.gif");
		go = new JButton(warnIcon);
		getContentPane().add(go);
		go.addActionListener(this);

		lblDatabaseList = new JLabel("Database Name");
		getContentPane().add(lblDatabaseList);

		cbDatabaseNames = new JComboBox<String>();
		cbDatabaseNames.addItem("MongoDB");
		cbDatabaseNames.addItem("Oracle");
		cbDatabaseNames.addItem("Mysql");
		cbDatabaseNames.addItem("Postgresql");
		getContentPane().add(cbDatabaseNames);

		cbDatabaseNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = (String) cbDatabaseNames.getItemAt(cbDatabaseNames.getSelectedIndex());
				addDatabaseDependency(a);
			}
		});

		buildBtn = new JButton("Build");
		getContentPane().add(buildBtn);

		buildBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buildProject();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		lblProjectType = new JLabel("Project Type");
		getContentPane().add(lblProjectType);

		cbProjectType = new JComboBox<String>();
		cbProjectType.addItem("REST");
		cbProjectType.addItem("Web");
		getContentPane().add(cbProjectType);

		cbProjectType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = (String) cbProjectType.getItemAt(cbProjectType.getSelectedIndex());
				setProjectPackaging(a);
			}
		});

		lblPackageName = new JLabel("Package Name");
		getContentPane().add(lblPackageName);

		txtPackageName = new JTextField("");
		getContentPane().add(txtPackageName);

		btnTest = new JButton("Test");
		getContentPane().add(btnTest);

		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testProject();
			}
		});

		lblMsg = new JLabel("");
		getContentPane().add(lblMsg);

		panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setBorder(blackline);
		panel_1.setVisible(false);

		txtFilePath = new JTextField("");
		getContentPane().add(txtFilePath);

		lblCache = new JLabel("Cache");
		getContentPane().add(lblCache);

		cbCacheProvider = new JComboBox<String>();
		cbCacheProvider.addItem("Redis");
		cbCacheProvider.addItem("Ehcache");
		cbCacheProvider.addItem("Hazelcast");
		getContentPane().add(cbCacheProvider);

		cbCacheProvider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = (String) cbCacheProvider.getItemAt(cbCacheProvider.getSelectedIndex());
				addCacheDependency(a);
			}
		});

		button_1 = new JButton("button_1");
		getContentPane().add(button_1);
		button_1.setVisible(false);

		btnAddToDocker = new JButton("Add To Docker");
		getContentPane().add(btnAddToDocker);
		btnAddToDocker.setVisible(false);
		
		btnAddToDocker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToDocker();
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

		deployLocal = new JButton("Local Deploy");
		getContentPane().add(deployLocal);
		deployLocal.setVisible(false);

		deployLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deployLocally();
			}
		});

		devDeploy = new JButton("Dev Deploy");
		getContentPane().add(devDeploy);
		devDeploy.setVisible(false);

		prodDeploy = new JButton("Prod Deploy");
		getContentPane().add(prodDeploy);
		prodDeploy.setVisible(false);

		setSize(getPreferredSize());

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		buildBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buildProject();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void addToDocker() {
		try {
			String containerName = getRandomtString();
			modifyFile(selectedDir + "\\src\\main\\resources\\application.properties",
					"spring.data.mongodb.host=localhost",
					"spring.data.mongodb.host=" + txtProjectName.getText().toLowerCase() + containerName);
			String uri = "spring.data.mongodb.uri: mongodb://" + txtProjectName.getText().toLowerCase() + containerName
					+ ":27017/api-database";
			modifyFile(selectedDir + "\\src\\main\\resources\\application.properties",
					"spring.data.mongodb.uri: mongodb://127.0.0.1:27017/api-database", uri);
			modifyFile(selectedDir + "\\docker-compose.yml", "api-docker-image",
					txtProjectName.getText().toLowerCase());
			modifyFile(selectedDir + "\\docker-compose.yml", "api-database-container",
					txtProjectName.getText().toLowerCase() + containerName);

			// String cmd1 = "cd " + selectedDir + " && mode con:cols=80 lines=40 && mvn
			// clean install && docker build -f Dockerfile -t "+ projectName +" .";
			String cmd1 = "cd " + selectedDir + " && mvn clean install && docker build -f Dockerfile -t " + projectName
					+ " .";
			String command = "cmd /c start cmd.exe /K \" " + cmd1 + "\" ";
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);

		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong ");
			e.printStackTrace();
		}
		deployLocal.setVisible(true);
	}

	protected String getRandomtString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public void deployLocally() {
		try {
			String cmd1 = "cd " + selectedDir + "&& docker-compose up --build ";
			String command = "cmd /c start cmd.exe /K \" " + cmd1 + "\" ";
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);

		} catch (Exception e) {
			System.out.println("HEY Buddy ! U r Doing Something Wrong ");
			e.printStackTrace();
		}
	}

	public void resetFields() {
		txtProjectName.setText("");
		txtFilePath.setText("");
		txtPackageName.setText("");
		cbDatabaseNames.setSelectedItem("MongoDB");
		cbProjectType.setSelectedItem("REST");
		cbCacheProvider.setSelectedItem("Redis");
		panel_1.setVisible(false);
		button_1.setVisible(false);
		btnAddToDocker.setVisible(false);
	}

	public void setProjectPackaging(String pkgType) {
		MavenXpp3Reader mavenreader = new MavenXpp3Reader();
		Model model;
		try {
			model = mavenreader.read(new FileReader(new File(targetFileName)));

			if (pkgType.equals("Web"))
				model.setPackaging("war");
			else if (pkgType.equals("REST"))
				model.setPackaging("jar");
			Writer writer = new FileWriter(targetFileName);
			new MavenXpp3Writer().write(writer, model);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildProject() throws IOException {
		projectName = txtProjectName.getText().toLowerCase();
		if ((!txtPackageName.getText().equals("") || !txtProjectName.getText().equals("")) && (!selectedDir.equals("") || selectedDir != null)) {
			panel_1.setVisible(false);
			button_1.setVisible(false);
			btnAddToDocker.setVisible(false);
			buildBtn.setEnabled(false);
			btnTest.setEnabled(false);
			lblMsg.setVisible(true);
			try {
				String cmd1 = "cd " + selectedDir + " && mode con:cols=80 lines=40 && mvn clean install";
				String command = "cmd /c start cmd.exe /K \" " + cmd1 + "\" ";
				Runtime rt = Runtime.getRuntime();
				Process proc = rt.exec(command);
				BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				StringBuffer output = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					output.append(line + "\n");
				}
				this.getTargetFileNameAndPath();
			} catch (Exception e) {
				System.out.println("HEY Buddy ! U r Doing Something Wrong ");
				e.printStackTrace();
			}

		} else
			JOptionPane.showMessageDialog(this, "Please enter Project and Package name", "Alert",
					JOptionPane.WARNING_MESSAGE);
		buildBtn.setEnabled(true);
		btnTest.setEnabled(true);
	}

	public void testProject() {
		projectName = txtProjectName.getText().toLowerCase();
		if (!txtPackageName.getText().equals("") || !txtProjectName.getText().equals("") || !selectedDir.isEmpty()) {
			buildBtn.setEnabled(false);
			btnTest.setEnabled(false);
			try {
				String cmd1 = "cd " + selectedDir + " && mode con:cols=80 lines=40 && mvn compile";
				String command = "cmd /c start cmd.exe /K \" " + cmd1 + "\" ";
				Runtime rt = Runtime.getRuntime();
				Process proc = rt.exec(command);
				BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				StringBuffer output = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					output.append(line + "\n");
				}
			} catch (Exception e) {
				System.out.println("HEY Buddy ! U r Doing Something Wrong ");
				e.printStackTrace();
			}

		} else
			JOptionPane.showMessageDialog(this, "Please enter Project and Package name", "Alert",
					JOptionPane.WARNING_MESSAGE);

		buildBtn.setEnabled(true);
		btnTest.setEnabled(true);
	}

	public void addCacheDependency(String dbName) {
		try {
			MavenXpp3Reader mavenreader = new MavenXpp3Reader();
			Model model = mavenreader.read(new FileReader(new File(targetFileName)));
			Writer writer = new FileWriter(targetFileName);
			ArrayList<Dependency> dependencyList = new ArrayList<Dependency>();
			List<Dependency> existingDependencyList = model.getDependencies();
			dependencyList.addAll(existingDependencyList);

			if (dbName.equals("Redis")) {
				Dependency dep = new Dependency();
				dep.setGroupId("org.springframework.boot");
				dep.setArtifactId("spring-boot-starter-data-redis");
				dependencyList.add(dep);
			} else if (dbName.equals("Ehcache")) {
				Dependency dep = new Dependency();
				dep.setGroupId("org.springframework.boot");
				dep.setArtifactId("spring-boot-starter-cache");
				dependencyList.add(dep);
			} else if (dbName.equals("Hazelcast")) {
				Dependency dep = new Dependency();
				dep.setGroupId("com.hazelcast");
				dep.setArtifactId("hazelcast-spring");
				dependencyList.add(dep);
			}

			model.setDependencies(dependencyList);
			new MavenXpp3Writer().write(writer, model);
			writer.close();

		} catch (IOException | XmlPullParserException e1) {
			e1.printStackTrace();
		}

	}

	public void addDatabaseDependency(String dbName) {
		try {
			MavenXpp3Reader mavenreader = new MavenXpp3Reader();
			Model model = mavenreader.read(new FileReader(new File(targetFileName)));
			Writer writer = new FileWriter(targetFileName);
			ArrayList<Dependency> dependencyList = new ArrayList<Dependency>();
			List<Dependency> existingDependencyList = model.getDependencies();
			dependencyList.addAll(existingDependencyList);

			if (dbName.equals("MongoDB")) {
				Dependency dep = new Dependency();
				dep.setGroupId("org.springframework.boot");
				dep.setArtifactId("spring-boot-starter-data-mongodb");
				dependencyList.add(dep);
			} else if (dbName.equals("Oracle")) {
				Dependency dep = new Dependency();
				dep.setGroupId("com.oracle");
				dep.setArtifactId("ojdbc14");
				dependencyList.add(dep);
			}

			else if (dbName.equals("Postgresql")) {
				Dependency dep = new Dependency();
				dep.setGroupId("org.postgresql");
				dep.setArtifactId("postgresql");
				dependencyList.add(dep);
			}

			else if (dbName.equals("Mysql")) {
				Dependency dep = new Dependency();
				dep.setGroupId("mysql");
				dep.setArtifactId("mysql-connector-java");
				dependencyList.add(dep);
			}

			model.setDependencies(dependencyList);
			new MavenXpp3Writer().write(writer, model);
			writer.close();

		} catch (IOException | XmlPullParserException e1) {
			e1.printStackTrace();
		}
	}

	public void getTargetFileNameAndPath() throws IOException {
		File dir = new File(directoryDir);
		String[] extensions = new String[] { "original" };
		List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
		String targetFile = null;
		for (File file : files) {
			targetFile = file.getName().replaceFirst(".original", "");
		}
		if (targetFile != null && !targetFile.equals("")) {
			button_1.setText(targetFile);
			lblMsg.setVisible(false);
			panel_1.setVisible(true);
			button_1.setVisible(true);
			btnAddToDocker.setVisible(true);
		} else {
			panel_1.setVisible(false);
			button_1.setVisible(false);
			btnAddToDocker.setVisible(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (!txtPackageName.getText().equals("") && !txtProjectName.getText().equals("")) {
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle(choosertitle);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				String sourcePom = "resources//pom.xml";
				String sourceDocker = "resources//Dockerfile";
				String sourceDockerCompose = "resources//docker-compose.yml";

				selectedDir = chooser.getSelectedFile().toString();
				txtFilePath.setText(selectedDir);
				String target = selectedDir.replaceAll("\\\\", "\\\\\\\\");
				directoryDir = target + "\\target\\";
				File sourceFile = new File(sourcePom);
				File sourceDockerFile = new File(sourceDocker);
				File sourceDockerComposeFile = new File(sourceDockerCompose);

				String name = sourceFile.getName();
				String dockername = sourceDockerFile.getName();
				String dockercomposename = sourceDockerComposeFile.getName();

				targetFileName = target + "\\" + name;
				File targetFile = new File(targetFileName);

				String targetFileName1 = target + "\\" + dockername;
				File targetDockerFile = new File(targetFileName1);

				String targetDockerCompose = target + "\\" + dockercomposename;
				File targetDockerComposeFile = new File(targetDockerCompose);

				try {

					File srcDir = new File(selectedDir);
					String destination = selectedDir + "\\src\\main\\java\\";
					String packageName = txtPackageName.getText();
					String[] arrSplit = packageName.split("\\.");
					for (int i = 0; i < arrSplit.length; i++) {
						destination = destination + "\\" + arrSplit[i];
					}
					File destDir = new File(destination);

					move(srcDir, destDir);

					File oneMoreDirectory = new File(selectedDir + "\\src\\main" + File.separator + "resources");
					boolean isCreated = oneMoreDirectory.mkdir();
					if (isCreated) {
						System.out.printf("\n3. Successfully created new directory, path:%s",
								oneMoreDirectory.getCanonicalPath());
					} else { // Directory may already exist
						System.out.printf("\n3. Unable to create directory");
					}

					File propDir = new File(destination + "\\application.properties");
					File yamlDir = new File(destination + "\\application.yaml");

					FileSystem fileSys = FileSystems.getDefault();

					try {
						if (propDir.exists()) {
							Path srcPath = fileSys.getPath(destination + "\\application.properties");
							Path destPath = fileSys
									.getPath(selectedDir + "\\src\\main\\resources\\application.properties");
							Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
						} else if (yamlDir.exists()) {
							Path srcPath = fileSys.getPath(destination + "\\application.yaml");
							Path destPath = fileSys.getPath(selectedDir + "\\src\\main\\resources\\application.yaml");
							Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
						}

					} catch (IOException ioe) {
						ioe.printStackTrace();
					}

					FileUtils.copyFile(sourceFile, targetFile);
					FileUtils.copyFile(sourceDockerFile, targetDockerFile);
					FileUtils.copyFile(sourceDockerComposeFile, targetDockerComposeFile);

					modifyFile(selectedDir + "//Dockerfile", "test", txtProjectName.getText().toLowerCase());

					MavenXpp3Reader mavenreader = new MavenXpp3Reader();
					Model model = mavenreader.read(new FileReader(new File(targetFileName)));
					model.setGroupId(txtPackageName.getText());
					model.setArtifactId(txtProjectName.getText().toLowerCase());
					model.setName(txtProjectName.getText());
					Writer writer = new FileWriter(targetFileName);
					new MavenXpp3Writer().write(writer, model);
					writer.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else {
				System.out.println("No Selection ");
			}
		} else
			JOptionPane.showMessageDialog(this, "Please enter Project and Package name", "Alert",
					JOptionPane.WARNING_MESSAGE);
	}

	private void move(File sourceFile, File destFile) {
		if (sourceFile.isDirectory()) {
			File[] files = sourceFile.listFiles();

			assert files != null;
			for (File file : files) {
				// if(file.getName().endsWith(".yaml") ||
				// file.getName().endsWith(".properties"))
				move(file, new File(destFile, file.getName()));
			}

			sourceFile.delete();

		} else {
			if (!destFile.getParentFile().exists())
				if (!destFile.getParentFile().mkdirs())
					throw new RuntimeException();
			if (!sourceFile.renameTo(destFile))
				throw new RuntimeException();
		}
	}

	static void modifyFile(String filePath, String oldString, String newString) {
		File fileToBeModified = new File(filePath);
		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));
			String line = reader.readLine();
			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();
				line = reader.readLine();
			}

			String newContent = oldContent.replaceAll(oldString, newString);
			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Closing the resources

				reader.close();

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static void changeServerPort(String filePath, String oldString, String newString) {
		File fileToBeModified = new File(filePath);
		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));
			String line = reader.readLine();
			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();
				line = reader.readLine();
			}
			String newContent = "";

			if (oldContent.equals("") || oldContent == null)
				newContent = "server.port = 8085";
			else
				newContent = oldContent.replaceAll(oldString, newString);
			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Closing the resources

				reader.close();

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
