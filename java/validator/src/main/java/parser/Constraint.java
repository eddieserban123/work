package parser;

public class Constraint {
    private String name;
    private String docId;
    private String businessMsg;


    public Constraint(String name, String docId, String businessMsg) {
        this.name = name;
        this.docId = docId;
        this.businessMsg = businessMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getBusinessMsg() {
        return businessMsg;
    }

    public void setBusinessMsg(String businessMsg) {
        this.businessMsg = businessMsg;
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "name='" + name + '\'' +
                ", docId='" + docId + '\'' +
                ", businessMsg='" + businessMsg + '\'' +
                '}';
    }
}
