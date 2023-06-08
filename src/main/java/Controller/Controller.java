package Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class Controller {
    private static final String USERS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\users.json";
    private static final String MENU_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\menu.json";
    private static final String ORDERS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\orders.json";

    private final List<User> users = new ArrayList<>();
    private final List<Menu> menus = new ArrayList<>();

    @GetMapping("/hi")
    public String hello() {
        return "Hello";
    }

}
