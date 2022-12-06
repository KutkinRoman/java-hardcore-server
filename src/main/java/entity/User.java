package entity;

import orm.ColumnName;
import orm.Entity;
import orm.PrimaryKey;
import orm.TableName;

@TableName("user")
public class User implements Entity {

    @PrimaryKey
    @ColumnName("id")
    private Integer id;

    @ColumnName("first_name")
    private String firstName;

    @ColumnName("last_name")
    private String lastName;

    @ColumnName("email")
    private String email;

    @ColumnName("age")
    private Integer age;

    @ColumnName("description")
    private String description;

    public User () {
    }

    public Integer getId () {
        return this.id;
    }

    public String getFirstName () {
        return firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public String getEmail () {
        return email;
    }

    public Integer getAge () {
        return age;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public static Builder createBuilder () {
        return new Builder ();
    }

    @Override
    public String toString () {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", description=" + description +
                '}';
    }

    public static class Builder {

        private final User user;

        public Builder () {
            this.user = new User ();
        }

        public Builder withId (Integer id) {
            this.user.id = id;
            return this;
        }

        public Builder withFirstName (String firstName) {
            this.user.firstName = firstName;
            return this;
        }

        public Builder withLastName (String lastName) {
            this.user.lastName = lastName;
            return this;
        }

        public Builder withEmail (String email) {
            this.user.email = email;
            return this;
        }

        public Builder withEge (Integer age) {
            this.user.age = age;
            return this;
        }

        public Builder withDescription (String description) {
            this.user.description = description;
            return this;
        }

        public User build () {
            return this.user;
        }
    }
}
