package Models;

public class User
{
   private String first_name;
   private String last_name;
   private String email_id;
   private String password;
   private String user_type;
   private String gender;
   private String phone_number;
   private String bio;
   private String Image;
   private String status;

   public User(String first_name, String last_name, String email_id,
               String password, String user_type, String gender,
               String phone_number, String bio, String image, String status)
   {
      this.first_name = first_name;
      this.last_name = last_name;
      this.email_id = email_id;
      this.password = password;
      this.user_type = user_type;
      this.gender = gender;
      this.phone_number = phone_number;
      this.bio = bio;
      this.Image = image;
      this.status = status;
   }

   @Override
   public String toString()
   {
      return "User{" +
              "first_name='" + first_name + '\'' +
              ", last_name='" + last_name + '\'' +
              ", email_id='" + email_id + '\'' +
              ", password='" + password + '\'' +
              ", user_type='" + user_type + '\'' +
              ", gender='" + gender + '\'' +
              ", phone_number='" + phone_number + '\'' +
              ", bio='" + bio + '\'' +
              ", Image='" + Image + '\'' +
              ", status='" + status + '\'' +
              '}';
   }

   public String getFirst_name() {
      return first_name;
   }

   public void setFirst_name(String first_name) {
      this.first_name = first_name;
   }

   public String getLast_name() {
      return last_name;
   }

   public void setLast_name(String last_name) {
      this.last_name = last_name;
   }

   public String getEmail_id() {
      return email_id;
   }

   public void setEmail_id(String email_id) {
      this.email_id = email_id;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getUser_type() {
      return user_type;
   }

   public void setUser_type(String user_type) {
      this.user_type = user_type;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public String getPhone_number() {
      return phone_number;
   }

   public void setPhone_number(String phone_number) {
      this.phone_number = phone_number;
   }

   public String getBio() {
      return bio;
   }

   public void setBio(String bio) {
      this.bio = bio;
   }

   public String getImage() {
      return Image;
   }

   public void setImage(String image) {
      Image = image;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
