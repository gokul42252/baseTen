package ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class BasePage extends JDialog {

    private JButton convertToImageButton;
    private JTextArea sourceTextArea;
    private JPanel mainPane;
    private JScrollPane scrollPane;
    private AnActionEvent anActionEvent;

    private BasePage() {

    }

    public BasePage(AnActionEvent actionEvent) {
        setContentPane(mainPane);
        setTitle("Convert Base64 To Image");
        setName("Convert Base64 To Image");
        anActionEvent = actionEvent;
        setModal(true);
        mainPane.setSize(600, 600);
        mainPane.setPreferredSize(new Dimension(300, 300));
        mainPane.setMinimumSize(new Dimension(600, 600));
        int width = 500, height = 500;
        Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
        if (editor != null) {
            editor.getComponent();
            width = editor.getComponent().getWidth();
            height = editor.getComponent().getHeight();
        }
        setBounds(width / 2, height / 2, (width / 2), (height / 2));
        sourceTextArea.setVisible(true);
        sourceTextArea.setBounds(0, 0, 500, 500);
        sourceTextArea.setAutoscrolls(true);
        sourceTextArea.setLineWrap(true);
        sourceTextArea.setWrapStyleWord(true);
        sourceTextArea.setToolTipText("Base64 String Input");
        convertToImageButton.addActionListener(e -> ConvertImage()
        );

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        mainPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        sourceTextArea.addKeyListener(new KeyAdapter() {
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

    /**
     * Method to convert image
     */
    private void ConvertImage() {
        String data = "";
        try {
            data = sourceTextArea.getDocument().getText(0, sourceTextArea.getDocument().getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        if (data.trim().equals("")) {
            JOptionPane.showMessageDialog(new JFrame(), "Empty Base64 String");
        } else {
            BufferedImage bufImg = decodeToImage(data.trim());
            if (bufImg != null) {
                bufImg = resizeImage(bufImg);
                JLabel label = new JLabel(new ImageIcon(bufImg));
                label.setSize(new Dimension(800, 800));
                JOptionPane.showMessageDialog(mainPane, label);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Base64 String");
            }

        }
    }

    /**
     * Method to resize the image
     * @param img buffered image
     * @return buffered image
     */
    private static BufferedImage resizeImage(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(.5, .5);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(img, after);
        Graphics2D g2d = after.createGraphics();
        g2d.drawImage(after, 0, 0, null);
        g2d.dispose();
        return after;
    }

    /**
     * Method to decode image
     *
     * @param imageString base 64 string to image
     * @return buffered image
     */
    private static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Invalid Base64 String");
        }
        return image;
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        BasePage dialog = new BasePage();
        dialog.pack();
        dialog.setVisible(true);
    }
}
