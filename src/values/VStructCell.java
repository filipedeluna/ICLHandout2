package values;

public final class VStructCell extends VCell {
  private String fieldId;

  public VStructCell(int address, String fieldId) {
    super(address);
    this.fieldId = fieldId;
  }

  public String getFieldId() {
    return fieldId;
  }
}
