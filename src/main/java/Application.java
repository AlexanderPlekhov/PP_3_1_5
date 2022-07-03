import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class Application {

    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();
    private static StringBuilder finalString = new StringBuilder();

    public static void main(String[] args) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<List> responseEntity = getAll(objectHttpEntity);

        httpHeaders.set("Cookie", String.join(";", Objects.requireNonNull(responseEntity.getHeaders().get("set-cookie"))));

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 33);

        HttpEntity<User> userHttpEntity = new HttpEntity<>(user, httpHeaders);
        createUser(userHttpEntity);

        user.setName("Thomas");
        user.setLastName("Shelby");

        userHttpEntity = new HttpEntity<>(user, httpHeaders);
        editUser(userHttpEntity);
        deleteUser(userHttpEntity);

        System.out.println(finalString);
    }
    public static ResponseEntity<List> getAll(HttpEntity<Object> objectHttpEntity) {
        ResponseEntity<List> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, objectHttpEntity, List.class);
        return responseEntity;
    }

    public static void createUser(HttpEntity<User> userHttpEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, userHttpEntity, String.class);
        finalString.append(responseEntity.getBody());
    }

    public static void editUser(HttpEntity<User> userHttpEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, userHttpEntity, String.class);
        finalString.append(responseEntity.getBody());
    }

    public static void deleteUser(HttpEntity<User> userHttpEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, userHttpEntity, String.class);
        finalString.append(responseEntity.getBody());
    }
}
