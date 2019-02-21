package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import ui.BasePage;

public class BToImageAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BasePage basePage = new BasePage(e);
        basePage.setVisible(true);
    }
}
