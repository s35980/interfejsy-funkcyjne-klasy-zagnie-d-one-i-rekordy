import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

    record UserForm(String email, String password, int age) {
        public UserForm {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email nie może być pusty!");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Hasło nie może być puste!");
            }
        }
    }

    class UserValidator {
        private final List<Predicate<UserForm>> rules = new ArrayList<>();

        public void addRule(Predicate<UserForm> rule) {
            rules.add(rule);
        }

        public boolean isValid(UserForm form) {
            return rules.stream().allMatch(rule -> rule.test(form));
        }
    }

    public class Task1 {
        public static void main(String[] args) {
            UserValidator validator = new UserValidator();

            // Dodawanie reguł jako lambdy
            validator.addRule(form -> form.email().contains("@"));
            validator.addRule(form -> form.password().length() >= 8);
            validator.addRule(form -> form.age() >= 18);

            UserForm form = new UserForm("anna@example.com", "bezpieczne123", 20);
            System.out.println("Czy formularz Anny jest poprawny? " + validator.isValid(form));

            // Test negatywny
            UserForm badForm = new UserForm("jan_bez_malpy.com", "krotkie", 15);
            System.out.println("Czy formularz Jana jest poprawny? " + validator.isValid(badForm));
        }
    }

