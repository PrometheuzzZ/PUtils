
package promej.putils.pro.putils.config.buttons;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PUButtonsList {

    @SerializedName("buttons")
    @Expose
    private List<PUButton> buttons;

    public List<PUButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<PUButton> buttons) {
        this.buttons = buttons;
    }

}
