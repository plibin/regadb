package net.sf.regadb.db;


/**
 * UserAttribute generated by hbm2java
 */
public class UserAttribute implements java.io.Serializable {

    private Integer userAttributeIi;

    private ValueType valueType;

    private SettingsUser settingsUser;

    private String name;

    private String value;

    private byte[] data;

    public UserAttribute() {
    }

    public UserAttribute(ValueType valueType, SettingsUser settingsUser,
            String name, String value, byte[] data) {
        this.valueType = valueType;
        this.settingsUser = settingsUser;
        this.name = name;
        this.value = value;
        this.data = data;
    }

    public Integer getUserAttributeIi() {
        return this.userAttributeIi;
    }

    public void setUserAttributeIi(Integer userAttributeIi) {
        this.userAttributeIi = userAttributeIi;
    }

    public ValueType getValueType() {
        return this.valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public SettingsUser getSettingsUser() {
        return this.settingsUser;
    }

    public void setSettingsUser(SettingsUser settingsUser) {
        this.settingsUser = settingsUser;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
