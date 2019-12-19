package types;

public final class TStructCell extends TCell {
  private String fieldId;

  public TStructCell(String id, String fieldId) {
    super(id);
    this.fieldId = fieldId;
  }

  public String getFieldId() {
    return fieldId;
  }
}
