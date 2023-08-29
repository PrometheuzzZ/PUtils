
package promej.putils.pro.putils.config.buttons;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PUButton {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("command")
    @Expose
    private String command;
    @SerializedName("item_id")
    @Expose
    private Integer itemId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

}
