package Models;

import java.util.List;

public class Participants
{
    private String Participant_ID;
    private String Participant_Name;
    private String Participant_Photo;
    private String Participant_Status;
    private List<String> list_participants;

    public Participants(List<String> ls)
    {
       list_participants = ls;
    }

    public Participants(String participant_ID, String participant_Name,
                        String participant_Photo, String participant_Status)
    {
        Participant_ID = participant_ID;
        Participant_Name = participant_Name;
        Participant_Photo = participant_Photo;
        Participant_Status = participant_Status;
    }

    public Participants(String participant_ID, String participant_Name, String participant_Status)
    {
        Participant_ID = participant_ID;
        Participant_Name = participant_Name;
        Participant_Status = participant_Status;
    }

    @Override
    public String toString()
    {
        return "Participants{" +
                "Participant_ID='" + Participant_ID + '\'' +
                ", Participant_Name='" + Participant_Name + '\'' +
                ", Participant_Photo='" + Participant_Photo + '\'' +
                ", Participant_Status='" + Participant_Status + '\'' +
                '}';
    }

    public String getParticipant_ID() {
        return Participant_ID;
    }

    public void setParticipant_ID(String participant_ID) {
        Participant_ID = participant_ID;
    }

    public String getParticipant_Name() {
        return Participant_Name;
    }

    public void setParticipant_Name(String participant_Name) {
        Participant_Name = participant_Name;
    }

    public String getParticipant_Photo() {
        return Participant_Photo;
    }

    public void setParticipant_Photo(String participant_Photo) {
        Participant_Photo = participant_Photo;
    }

    public String getParticipant_Status() {
        return Participant_Status;
    }

    public void setParticipant_Status(String participant_Status) {
        Participant_Status = participant_Status;
    }
}
