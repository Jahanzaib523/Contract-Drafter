package Models;

import java.util.ArrayList;
import java.util.List;

public class Contact
{
    private String CEmail;
    private String CFirstName;
    private String CLastName;
    private String CUserType;
    private String CGender;
    private String CPhoneNumber;
    private String CBio;
    private String CImage;
    private String COnlineStatus;
    private boolean Checked;
    private List<String> participants;
    private String Room_name;

    public Contact(List<String> parti, String Room_name)
    {
        this.Room_name = Room_name;
        this.participants = parti;
    }

    public Contact(String CEmail, String CFirstName,
                   String CLastName, String CImage, String COnlineStatus, boolean isChecked)
    {
        this.CEmail = CEmail;
        this.CFirstName = CFirstName;
        this.CLastName = CLastName;
        this.CImage = CImage;
        this.COnlineStatus = COnlineStatus;
        this.Checked = isChecked;
    }

    public Contact(String CEmail, String CFirstName,
                   String CLastName, String COnlineStatus, boolean isChecked, String Image)
    {
        this.CEmail = CEmail;
        this.CFirstName = CFirstName;
        this.CLastName = CLastName;
        this.COnlineStatus = COnlineStatus;
        this.Checked = isChecked;
        this.CImage = Image;
    }

    public Contact(String CEmail, String CFirstName, String CLastName,
                   String CUserType, String CGender, String CPhoneNumber,
                   String CBio, String CImage, String COnlineStatus, boolean isChecked)
    {
        this.CEmail = CEmail;
        this.CFirstName = CFirstName;
        this.CLastName = CLastName;
        this.CUserType = CUserType;
        this.CGender = CGender;
        this.CPhoneNumber = CPhoneNumber;
        this.CBio = CBio;
        this.CImage = CImage;
        this.COnlineStatus = COnlineStatus;
        this.Checked = isChecked;
    }

    @Override
    public String toString()
    {
        return "Contact{" +
                "CEmail='" + CEmail + '\'' +
                ", CFirstName='" + CFirstName + '\'' +
                ", CLastName='" + CLastName + '\'' +
                ", CUserType='" + CUserType + '\'' +
                ", CGender='" + CGender + '\'' +
                ", CPhoneNumber='" + CPhoneNumber + '\'' +
                ", CBio='" + CBio + '\'' +
                ", CImage='" + CImage + '\'' +
                ", COnlineStatus='" + COnlineStatus + '\'' +
                ", Checked='" + Checked + '\'' +
                '}';
    }

    public String getCEmail() {
        return CEmail;
    }

    public void setCEmail(String CEmail) {
        this.CEmail = CEmail;
    }

    public String getCFirstName() {
        return CFirstName;
    }

    public void setCFirstName(String CFirstName) {
        this.CFirstName = CFirstName;
    }

    public String getCLastName() {
        return CLastName;
    }

    public void setCLastName(String CLastName) {
        this.CLastName = CLastName;
    }

    public String getCUserType() {
        return CUserType;
    }

    public void setCUserType(String CUserType) {
        this.CUserType = CUserType;
    }

    public String getCGender() {
        return CGender;
    }

    public void setCGender(String CGender) {
        this.CGender = CGender;
    }

    public String getCPhoneNumber() {
        return CPhoneNumber;
    }

    public void setCPhoneNumber(String CPhoneNumber) {
        this.CPhoneNumber = CPhoneNumber;
    }

    public String getCBio() {
        return CBio;
    }

    public void setCBio(String CBio) {
        this.CBio = CBio;
    }

    public String getCImage() {
        return CImage;
    }

    public void setCImage(String CImage) {
        this.CImage = CImage;
    }

    public String getCOnlineStatus() {
        return COnlineStatus;
    }

    public void setCOnlineStatus(String COnlineStatus) {
        this.COnlineStatus = COnlineStatus;
    }

    public boolean Get_Checked()
    {
        return Checked;
    }

    public void Set_Checked(boolean checked)
    {
        Checked = checked;
    }
}
