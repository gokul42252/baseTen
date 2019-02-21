package ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.codec.binary.Base64;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class BasePageInverse extends JDialog {
    private JTextField txtImgPath;
    private JButton chooseImageButton;
    private JTextArea textArea1;
    private JPanel mainPane;

    public BasePageInverse(AnActionEvent anActionEvent) {
        chooseImageButton.addActionListener(evt -> chooseFile());
        setContentPane(mainPane);
        setModal(true);
        setTitle("Convert Image To Base64");
        setName("Convert Image To Base64");
        mainPane.setSize(500, 500);
        mainPane.setMinimumSize(new Dimension(500, 500));
        mainPane.setPreferredSize(new Dimension(300, 300));
        textArea1.setSize(500, 500);
        textArea1.setMinimumSize(new Dimension(500, 500));
        int width = 500, height = 500;
        setBounds(width / 2, height / 2, (width / 2), (height / 2));
        textArea1.setVisible(true);
        textArea1.setBounds(0, 0, 500, 500);
        textArea1.setAutoscrolls(true);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea1.setToolTipText("Base64 String Output");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        mainPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        textArea1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        pack();
    }

    private BasePageInverse() {

    }

    /**
     * Method to choose file from pc
     */
    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setDialogTitle("Select an image");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PNG and GIF images", "png", "gif");
        chooser.addChoosableFileFilter(filter);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            txtImgPath.setText(chooser.getSelectedFile().getAbsolutePath());
            textArea1.setText(encodeFileToBase64Binary(f));
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        BasePageInverse dialog = new BasePageInverse();
        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Method to encode file to base 64 string
     *
     * @param file image file
     * @return base 64 string
     */
    private static String encodeFileToBase64Binary(File file) {
        String encodedFile;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[(int) file.length()];
        try {
            if (fileInputStreamReader != null) {
                fileInputStreamReader.read(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        encodedFile = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
        return encodedFile;
    }

}
