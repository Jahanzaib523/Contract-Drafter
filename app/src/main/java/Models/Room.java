package Models;

public class Room
{
    private String Room_name;
    private String Start_time;
    private String Status;

    public Room(String room_name, String start_time, String status)
    {
        Room_name = room_name;
        Start_time = start_time;
        Status = status;
    }

    @Override
    public String toString()
    {
        return "Room{" +
                "Room_name='" + Room_name + '\'' +
                ", Start_time='" + Start_time + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }

    public String getRoom_name() {
        return Room_name;
    }

    public void setRoom_name(String room_name) {
        Room_name = room_name;
    }

    public String getStart_time() {
        return Start_time;
    }

    public void setStart_time(String start_time) {
        Start_time = start_time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
