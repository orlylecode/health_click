package io.cogitech.healthclick.Model;

public class IssueInfo {
    private String Description;
    private String DescriptionShort;
    private String MedicalCondition;
    private String Name;
    private String PossibleSymptoms;
    private String ProfName;
    private String Synonyms;
    private String TreatmentDescription;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescriptionShort() {
        return DescriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        DescriptionShort = descriptionShort;
    }

    public String getMedicalCondition() {
        return MedicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        MedicalCondition = medicalCondition;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPossibleSymptoms() {
        return PossibleSymptoms;
    }

    public void setPossibleSymptoms(String possibleSymptoms) {
        PossibleSymptoms = possibleSymptoms;
    }

    public String getProfName() {
        return ProfName;
    }

    public void setProfName(String profName) {
        ProfName = profName;
    }

    public String getSynonyms() {
        return Synonyms;
    }

    public void setSynonyms(String synonyms) {
        Synonyms = synonyms;
    }

    public String getTreatmentDescription() {
        return TreatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        TreatmentDescription = treatmentDescription;
    }
}
