package supportgame.members;

import de.fhdortmund.j2t2.wise2019.gamelogic.ChatPartner;

import java.io.Serializable;

public class Profile implements ChatPartner, Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private String jobTitle;

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getImageUri() {
        return photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
