public class Inspector {

    private int inspectorId;
    private String fullName;
    private String district;
    private String activeStatus;

    // Constructor without ID (for ADD)
    public Inspector(String fullName, String district, String activeStatus) {
        this.fullName = fullName;
        this.district = district;
        this.activeStatus = activeStatus;
    }

    // Constructor with ID (for UPDATE)
    public Inspector(int inspectorId, String fullName, String district, String activeStatus) {
        this.inspectorId = inspectorId;
        this.fullName = fullName;
        this.district = district;
        this.activeStatus = activeStatus;
    }

    // Getters
    public int getInspectorId() { 
        return inspectorId; 
    }

    public String getFullName() {
        return fullName;
    }

    public String getDistrict() {
        return district;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    // Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }
}