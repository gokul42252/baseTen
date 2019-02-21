package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import ui.BasePageInverse;

public class ImageToBAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BasePageInverse basePage = new BasePageInverse(e);
        basePage.setVisible(true);
    }
}
