import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentials {
        public String name;
        public String password;
        public String email;

        public UserCredentials(String name, String password, String email){
            this.name = name;
            this.password = password;
            this.email = email;
        }

        public UserCredentials(){
        }

        public static UserCredentials from(User user){
            return new UserCredentials(user.name, user.password, user.email);
        }

        public UserCredentials setName(String name){
            this.name = name;
            return this;
        }

        public UserCredentials setPassword(String password){
            this.password = password;
            return this;
        }

        public UserCredentials setEmail(String email){
        this.email = email;
        return this;
        }

        public static UserCredentials withNameOnly(User user){
            return new UserCredentials().setName(user.name);
        }

        public static UserCredentials withPasswordOnly(User user){
            return new UserCredentials().setPassword(user.password);
        }

        public static UserCredentials withEmailOnly(User user){
        return new UserCredentials().setEmail(user.email);
        }

        public static UserCredentials withNewName(User user){
        return new UserCredentials()
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setEmail(user.email)
                .setPassword(user.password);
        }

        public static UserCredentials withNewPassword(User user){
        return new UserCredentials()
                .setPassword(RandomStringUtils.randomAlphabetic(10))
                .setEmail(user.email)
                .setName(user.name);
        }

        public static UserCredentials withNewEmail(User user){
        return new UserCredentials()
                .setEmail((RandomStringUtils.randomAlphabetic(10)+"@yandex.ru").toLowerCase())
                .setName(user.name)
                .setPassword(user.password);
        }

        public static UserCredentials nonRegistered(String login, String password, String email){
            return new UserCredentials(login, password, email);
        }
    }
