package Models;

public class Users {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String bloodGroup;
    private String gender;
    private String address;
    private int age;
    private String password;

    public Users(){

    }

    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return id;
    }



}
